package agv.service;
import agv.pageModel.AgvType;
import agv.pageModel.JqGrid;
import agv.pageModel.SessionInfo;

/**
 * 车辆信息管理类service接口
 * @author
 */
public interface AgvTypeServiceI {
	
	/**
	 * 新增车辆信息管理记录
	 * @param AgvType
	 * @param sessionInfo
	 * @throws Exception
	 */
	public void add(AgvType agvType,SessionInfo sessionInfo) throws Exception;
	/**
	 * 获取车辆信息管理记录
	 * @param id
	 * @return
	 */
	public AgvType get(int id);
	/**
	 * 编辑车辆信息管理记录
	 * @param AgvType
	 * @param sessionInfo
	 * @throws Exception
	 */
	public void edit(AgvType agvType,SessionInfo sessionInfo) throws Exception;
	/**
	 * 删除车辆信息管理记录
	 * @param id
	 * @param sessionInfo
	 */
	public void delete(int id,SessionInfo sessionInfo);
	/**
	 * 车辆信息管理记录表格
	 * @param agv
	 * @param ph
	 * @param sessionInfo
	 * @return
	 */
	public JqGrid dataGrid(AgvType agvType, JqGrid ph,SessionInfo sessionInfo);

}
