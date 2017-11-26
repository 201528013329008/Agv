package agv.service;

/**
 * 定时执行任务
 * @author china
 *
 */
public interface ScheduledServiceI {

	/**
	 * 定时执行的任务
	 */
	public void run();
	
	public void init();
	
	/**
	 * 发送指令给下位机
	 * @param msg
	 */
	public void sendToSytem(String msg);
	
	

}
