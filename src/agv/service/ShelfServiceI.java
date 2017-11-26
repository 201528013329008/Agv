package agv.service;
import agv.pageModel.Shelf;

import java.util.ArrayList;

import agv.pageModel.JqGrid;
import agv.pageModel.SessionInfo;

/**
 * 货架信息管理类service接口
 * @author
 */
public interface ShelfServiceI {
	
	/**
	 * 新增货架信息管理记录
	 * @param Shelf
	 * @param sessionInfo
	 * @throws Exception
	 */
	public void add(Shelf shelf,SessionInfo sessionInfo) throws Exception;
	/**
	 * 获取货架信息管理记录
	 * @param id
	 * @return
	 */
	public Shelf get(int id);
	
	/**
	 * 获取货架信息
	 * @param SN
	 * @return
	 */
	public Shelf get(String SN);
	
	/**
	 * 获得所有货架
	 * @return
	 */
	public ArrayList<Shelf> getAll();
	
	/**
	 * 编辑货架信息管理记录
	 * @param shelf
	 * @param sessionInfo
	 * @throws Exception
	 */
	public void edit(Shelf shelf,SessionInfo sessionInfo) throws Exception;
	/**
	 * 删除货架信息管理记录
	 * @param id
	 * @param sessionInfo
	 */
	public void delete(int id,SessionInfo sessionInfo);
	
	/**
	 * 删除货架信息
	 * @param SN
	 * @param sessionInfo
	 */
	public void delete(String SN, SessionInfo sessionInfo);
	/**
	 * 货架信息管理记录表格
	 * @param shelf
	 * @param ph
	 * @param sessionInfo
	 * @return
	 */
	public JqGrid dataGrid(Shelf shelf, JqGrid ph,SessionInfo sessionInfo);
	

}
