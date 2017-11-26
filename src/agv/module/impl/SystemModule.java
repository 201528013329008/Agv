package agv.module.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import agv.module.SystemModuleI;
import agv.pageModel.Msg;

@Component("systemModule")
public class SystemModule implements SystemModuleI,Runnable{
	
	public enum ORDER {  
	    loadWaiting,loadStart,unloadWaiting,unloadStart,failTask,finishTask,
	    continueTask,
	    agvCharge,agvOffline,
	    systemOnSuc,systemOnFail,systemOffSuc,systemOffFail;  
	  
	    public static ORDER getOrder(String order) {  
	        return valueOf(order);  
	    }  
	}  
	
	protected Logger systemLogger = Logger.getLogger("agv");
	protected Logger userLogger = Logger.getLogger("agv.user");

	private ServerSocket server;
	private Socket client;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private boolean flag0 = true;
	private boolean flag1 = true;
	
	private Thread serverThread;
	
	/**
	 * 会自动在格式化 $msg#
	 */
	@Override
	public boolean sendToSystem(String msg) {
		if (out!=null){
			out.println("$"+msg+"#");
			out.flush();
			userLogger.info("向下位机发送信息："+msg);
			return true;
		}else{
			userLogger.error("无法发送信息："+msg+",输出流不存在");
			return false;
		}
		
	}

	@PostConstruct
	@Override
	public void init() {
		serverThread = new Thread(this);
		serverThread.start();
		userLogger.info("系统模块初始化成功");
	}

	@PreDestroy
	@Override
	public void destory() {
		userLogger.info("系统模块准备销毁");
		flag0 = false;
		flag1 = false;
	}

	@Override
	public void run() {
		userLogger.info("系统模块开始运行");
		try {
			server = new ServerSocket(20006);
			userLogger.info("系统模块开始监听");
			
			while (flag0){
				flag1 = true;
				try {
					client = server.accept();
					userLogger.info("系统模块与下位机建立连接");
					in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
					out = new PrintWriter(client.getOutputStream());
					while (flag1) {
						//接收从客户端发送过来的数据  
//						int c = in.read();
//						System.out.println(c);
		                String msg =  in.readLine();  
		                if(msg == null || "".equals(msg)){  
		                    flag1 = false;  
		                }else{  
		                    processMsg(msg);
		                }  
					}
					in.close();
					out.close();
					out = null;
					client.close();
					client = null;
					userLogger.info("系统模块与下位机断开连接");
				} catch (Exception e) {
					userLogger.error("系统模块与下位机建立连接错误："+e.getMessage());
					systemLogger.error(e.getMessage());
					e.printStackTrace();
				}
				userLogger.info("系统模块终止运行");
			}
			try {
				server.close();
				userLogger.info("系统模块成功关闭");
			} catch (IOException e) {
				userLogger.error("系统模块关闭错误："+e.getMessage());
				e.printStackTrace();
			}
		} catch (IOException e1) {
			userLogger.error("系统模块监听失败："+e1.getMessage());
			e1.printStackTrace();
		}
		
	}
	
	private void processMsg(String msgStrs){
		systemLogger.info("收到下位机指令："+msgStrs);
		String orderStr;
		int begin=0;
		int index;
		while ((index = msgStrs.indexOf("$",begin))>=0){
			begin = index;
			if ((index = msgStrs.indexOf("#",begin))>0){
				orderStr = msgStrs.substring(begin+1, index);
				try{
					Msg msg = new Msg(orderStr);
					executeOrder(msg);
				}catch(Exception e){
					userLogger.error("无法解析下位机指令："+orderStr);
					systemLogger.error(e.getMessage());
				}
				
			}else{
				break;
			}
			begin = index;
		}
		
		
	}
	
	/**
	 * 接收下位机发来的指令并执行
	 * @param msg
	 */
	private void executeOrder(Msg msg){
		String orderStr = msg.getOrder();
		
		userLogger.info("开始执行下位机指令："+orderStr);
		WebSocketModule.sendMsg(msg);
		
		ORDER order = ORDER.getOrder(orderStr);
		switch (order) {
		case loadWaiting:
			break;
		case loadStart:
			break;
		case continueTask:
			break;
		case unloadWaiting:
			break;
		case unloadStart:
			break;
		case failTask:
			break;
		case finishTask:
			break;
		case agvCharge:
			break;
		case agvOffline:
			break;
		case systemOnSuc:
			break;
		case systemOnFail:
			break;
		case systemOffSuc:
			break;
		case systemOffFail:
			break;
		default:
			userLogger.warn("无法识别下位机指令："+orderStr);
			break;
		}
	}

	@Override
	public boolean isSystemOnline() {
		if (client==null || client.isClosed()){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean addTask(Integer taskId){
		if (taskId!=null){
			return sendToSystem("addTask:"+taskId.intValue());
		}else{
			return false;
		}
	}
	
	public boolean updateTask(Integer taskId){
		if (taskId!=null){
			return sendToSystem("updateTask:"+taskId.intValue());
		}else{
			return false;
		}
	}
	
	public boolean cancelTask(Integer taskId){
		if (taskId!=null){
			return sendToSystem("cancelTask:"+taskId.intValue());
		}else{
			return false;
		}
		
	}
	
	public boolean loadFinish(Integer taskId){
		if (taskId!=null){
			return sendToSystem("loadFinish:"+taskId.intValue());
		}else{
			return false;
		}
	}
	
	public boolean unloadFinish(Integer taskId){
		if (taskId!=null){
			return sendToSystem("unloadFinish:"+taskId.intValue());
		}else{
			return false;
		}
	}
	
	public boolean addAgv(Integer agvId){
		if (agvId!=null){
			return sendToSystem("addAgv:"+agvId.intValue());
		}else {
			return false;
		}
	}
	
	public boolean forbidAgv(Integer agvId){
		if (agvId!=null){
			return sendToSystem("forbidAgv:"+agvId.intValue());
		}else{
			return false;
		}
	}
	
	public boolean activeAgv(Integer agvId){
		if (agvId!=null){
			return sendToSystem("activeAgv:"+agvId.intValue());
		}else{
			return false;
		}
	}
	
	public boolean updateAgv(Integer agvId){
		if (agvId!=null){
			return sendToSystem("updateAgv:"+agvId.intValue());
		}else{
			return false;
		}
	}
	
	public boolean deleteAgv(Integer agvId){
		if (agvId!=null){
			return sendToSystem("deleteAgv:"+agvId.intValue());
		}else{
			return false;
		}
	}
	
	public boolean systemOn(){
		return sendToSystem("systemOn");
	}
	
	public boolean systemOff(){
		return sendToSystem("systemOff");
	}
	
	public boolean systemShut(){
		return sendToSystem("systemShut");
	}
	
	public boolean serviceOn(){
		return sendToSystem("serviceOn");
	}
	
	public boolean serviceOff(){
		return sendToSystem("serviceOff");
	}
	
	public boolean updateMap(){
		return sendToSystem("updateMap");
	}
	
}
