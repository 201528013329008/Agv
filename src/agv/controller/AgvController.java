package agv.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import agv.pageModel.Agv;
import agv.pageModel.JqGrid;
import agv.pageModel.SateJson;
import agv.pageModel.SessionInfo;
import agv.pageModel.Task;
import agv.service.AgvServiceI;
import agv.service.AgvTypeServiceI;
import agv.service.TaskServiceI;
import agv.service.LogServiceI.LogScope;
import agv.web.WebContant;
import net.sf.json.JSONObject;


/**
 * 车辆信息管理控制类
 * @author 
 * @date 2015-12-21 17:17:56
 * @version 1.0
 */
@Controller
@RequestMapping("/agv")
public class AgvController extends BaseController {
	static String destIP = "localhost";
	static Integer destPort = 4700;
	@Autowired
	private AgvServiceI agvService;
	@Autowired
	private AgvTypeServiceI agvTypeService;
	@Autowired
	private TaskServiceI taskService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String manager() {
		return "/agv/agv";
	}
	
	/**
	 * runtime 运行时车辆状态
	 * @return
	 */
	@RequestMapping("/rt")
	public String rt() {
		return "/agv/agv_rt";
	}
	
	@RequestMapping("/getAgvTypeList")
	@ResponseBody
	public JqGrid getAgvTypeList(JqGrid ph, HttpServletRequest req) {
		return agvTypeService.dataGrid(null, ph, WebContant.getCurrentSessionInfo(req));
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public JqGrid dataGrid(Agv agv, JqGrid ph, HttpServletRequest req) {
		return agvService.dataGrid(agv, ph, WebContant.getCurrentSessionInfo(req));
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public Agv getInfo(int id) {
		return agvService.get(id);
	}
	
	@RequestMapping("/addAgvPage")
	public String addPage(HttpServletRequest request) {
		return "/agv/editAgv";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public SateJson add(Agv agv,HttpServletRequest req) {
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		try {
			agv.setOnline(false);
			agvService.add(agv,sessionInfo);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(agv);
			userLogger.info("添加车辆："+agv.getName());
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
			userLogger.warn("添加车辆失败："+e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/editAgvPage")
	public String editPage(HttpServletRequest request, int id) {
		Agv agv = agvService.get(id);
		request.setAttribute("agv", agv);
		return "/agv/editAgv";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public SateJson edit(Agv agv,HttpServletRequest req) {
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		try {
			agvService.edit(agv,sessionInfo);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(agv);
			userLogger.info("修改车辆："+agv.getName());
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
			userLogger.warn("修改车辆失败："+e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/setAgvStateByTask")
	@ResponseBody
	public SateJson setAgvByTask(HttpServletRequest req,int taskId,int state) {
		
		SateJson j = new SateJson();
		try {
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		Task task = taskService.get(taskId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "setAGV");
		map.put("id",task.getAgvId().toString());
		map.put("agvState",String.format("%d", state));
		
		JSONObject jsonObject = JSONObject.fromObject( map );
		System.out.println(jsonObject.toString());
		char[] buf =  jsonObject.toString().toCharArray();
		try {
			Socket socket=new Socket(destIP,destPort);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(  
	                socket.getInputStream()));
			int count = 0;
			while (true) {  
				out.println(buf);
				out.flush();
				String str = null;
				while(str == null)
					str= in.readLine();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
				if(count > 10)break;
	            System.out.println(str);  
	            if(str.contains("OK"))break;
	        }  
	        socket.close();  
			
			
		} catch (UnknownHostException e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
			
		} catch (IOException e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}					
		if(state == 6)
		{
			//TODO
//			task.setState(state);
			try {
				taskService.edit(task, sessionInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return j;
	}	
	
	@RequestMapping("/setAgvState")
	@ResponseBody
	public SateJson setAgv(HttpServletRequest req,int agvId,int state) {
		
		SateJson j = new SateJson();
		try {
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "setAGV");
		map.put("id",String.format("%d", agvId));
		map.put("agvState",String.format("%d", state));
		
		JSONObject jsonObject = JSONObject.fromObject( map );
		System.out.println(jsonObject.toString());
		char[] buf =  jsonObject.toString().toCharArray();
		try {
			Socket socket=new Socket(destIP,destPort);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(  
	                socket.getInputStream()));
			int count = 0;
			while (true) {  
				out.println(buf);
				out.flush();
				String str = null;
				while(str == null)
					str= in.readLine();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
				if(count > 10)break;
	            System.out.println(str);  
	            if(str.contains("OK"))break;
	        }  
	        socket.close();  
			
			
		} catch (UnknownHostException e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
			
		} catch (IOException e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}					
		
		Agv agv = agvService.get(Integer.parseInt((String)req.getAttribute("agvId")));
		Task task = taskService.get(agv.getTaskId());
		
		try {
			task.setState("finished");
			taskService.edit(task, sessionInfo);
		} catch (Exception e) {
			systemLogger.error(e.getMessage());
		}
		return j;
	}	
	
	@RequestMapping("/delete")
	@ResponseBody
	public SateJson delete(int id,HttpServletRequest req) {
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		if (id != 0 && !"preson".equals(sessionInfo.getUser().getUserType())) {
			agvService.delete(id,sessionInfo);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
			j.setMsg("删除失败！");
		}
		return j;
	}
	
	@RequestMapping("/export")
	@ResponseBody
	public SateJson export(Agv agv, JqGrid ph,HttpServletRequest req) {
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		String name = agvService.exportData(agv,ph,sessionInfo);
		j.setMsg("生成文件成功！");
		j.setObj(name);
		j.setSuccess(true);
		return j;
	}
}
