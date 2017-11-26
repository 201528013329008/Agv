package agv.module;

public interface SystemModuleI {
	public boolean sendToSystem(String msg);
	
	public void init();
	
	public void destory();
	
	public boolean isSystemOnline();
	
	public boolean addTask(Integer taskId);
	
	public boolean updateTask(Integer taskId);
	
	public boolean cancelTask(Integer taskId);
	
	public boolean loadFinish(Integer taskId);
	
	public boolean unloadFinish(Integer taskId);
	
	public boolean addAgv(Integer agvId);
	
	public boolean forbidAgv(Integer agvId);
	
	public boolean activeAgv(Integer agvId);
	
	public boolean updateAgv(Integer agvId);
	
	public boolean deleteAgv(Integer agvId);
	
	public boolean systemOn();
	
	public boolean systemOff();
	
	public boolean systemShut();
	
	public boolean serviceOn();
	
	public boolean serviceOff();
	
	public boolean updateMap();
}
