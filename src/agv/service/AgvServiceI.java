package agv.service;
import agv.pageModel.Agv;
import agv.pageModel.JqGrid;
import agv.pageModel.SessionInfo;

/**
 * 车辆信息管理类service接口
 * @author
 */
public interface AgvServiceI {
	
	/**
	 * 新增车辆信息管理记录
	 * @param Agv
	 * @param sessionInfo
	 * @throws Exception
	 */
	public void add(Agv agv,SessionInfo sessionInfo) throws Exception;
	/**
	 * 获取车辆信息管理记录
	 * @param id
	 * @return
	 */
	public Agv get(int id);
	/**
	 * 编辑车辆信息管理记录
	 * @param agv
	 * @param sessionInfo
	 * @throws Exception
	 */
	public void edit(Agv agv,SessionInfo sessionInfo) throws Exception;
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
	public JqGrid dataGrid(Agv agv, JqGrid ph,SessionInfo sessionInfo);
	
	/**
	 * 导出数据
	 * @param paper
	 * @param ph
	 * @param sessionInfo
	 * @return 返回文件名
	 */
	public  String exportData(Agv agv, JqGrid ph,SessionInfo sessionInfo);

}
