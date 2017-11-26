package agv.service;

import agv.pageModel.Agv;
import agv.pageModel.JqGrid;
import agv.pageModel.SessionInfo;

public interface LogServiceI {
	
	public static enum LogScope{
		USER,SYSTEM;
	}
	
	public void trace(String msg);
	public void trace(String msg, LogScope scope);
	
	public void debug(String msg);
	public void debug(String msg, LogScope scope);
	
	public void info(String msg);
	public void info(String msg, LogScope scope);
	
	public void warn(String msg);
	public void warn(String msg, LogScope scope);
	
	public void error(String msg);
	public void error(String msg, LogScope scope);
	
	public void fatal(String msg);
	public void fatal(String msg, LogScope scope);
	
	/**
	 * 日志表格
	 * @param ph
	 * @param sessionInfo
	 * @return
	 */
	public JqGrid dataGrid(JqGrid ph,SessionInfo sessionInfo);
	
	
}
