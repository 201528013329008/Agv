package agv.service;

import agv.pageModel.JqGrid;
import agv.pageModel.SessionInfo;
import agv.pageModel.User;


/**
 * 用户Service
 * 
 * @author China
 * 
 */
public interface UserServiceI {

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            里面包含登录名和密码
	 * @return 用户对象
	 */
	public User login(User user);


	/**
	 * 获取用户表格
	 * @param user
	 * @param ph
	 * @param sessionInfo 从登录用户中获取chaoji关联查询权限
	 * @return
	 */
	public JqGrid dataGrid(User user, JqGrid ph,SessionInfo sessionInfo);

	/**
	 * 添加用户
	 * @param user
	 * @param sessionInfo
	 * @throws Exception
	 */
	public void add(User user,SessionInfo sessionInfo) throws Exception;

	/**
	 * 获得用户对象
	 * 
	 * @param userid
	 * @return
	 */
	public User get(Integer userid);

	/**
	 * 编辑用户
	 * @param user
	 * @param sessionInfo 从登录用户中获取编辑人
	 * @throws Exception
	 */
	public void edit(User user,SessionInfo sessionInfo) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param userid
	 */
	public void delete(Integer userid,SessionInfo sessionInfo);
}
