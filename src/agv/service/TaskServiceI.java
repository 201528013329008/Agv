package agv.service;
import java.util.ArrayList;

import agv.pageModel.JqGrid;
import agv.pageModel.SessionInfo;
import agv.pageModel.Task;

/**
 * 任务信息管理类service接口
 * @author 
 */
public interface TaskServiceI {
	
	public static String CURRENT = "current";
	public static String HISTORY = "history";
	public static String ALL = "all";
	
	/**
	 * 新增任务信息管理记录
	 * @param Task
	 * @param sessionInfo
	 * @throws Exception
	 */
	public Task add(Task task,SessionInfo sessionInfo) throws Exception;
	/**
	 * 获取任务信息管理记录
	 * @param id
	 * @return
	 */
	public Task get(int id);
	/**
	 * 编辑任务信息管理记录
	 * @param task
	 * @param sessionInfo
	 * @throws Exception
	 */
	public void edit(Task task,SessionInfo sessionInfo) throws Exception;
	/**
	 * 删除任务信息管理记录
	 * @param id
	 * @param sessionInfo
	 */
	public void delete(int id,SessionInfo sessionInfo);
	/**
	 * 任务信息管理记录表格
	 * @param task
	 * @param ph
	 * @param dataType current history
	 * @param sessionInfo
	 * @return
	 */
	public JqGrid dataGrid(Task task, JqGrid ph, String dataType, SessionInfo sessionInfo);
	
	/**
	 * 获得所有正在运行的任务
	 * @return
	 */
	public ArrayList<Task> getAll();
	
	/**
	 * 导出数据
	 * @param paper
	 * @param ph
	 * @param sessionInfo
	 * @return 返回文件名
	 */
	public  String exportData(Task task, JqGrid ph,SessionInfo sessionInfo);

}
