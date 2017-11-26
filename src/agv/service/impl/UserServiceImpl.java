package agv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agv.dao.UserDaoI;
import agv.model.Tuser;
import agv.pageModel.JqGrid;
import agv.pageModel.SessionInfo;
import agv.pageModel.User;
import agv.service.UserServiceI;
import agv.util.MD5Util;
import agv.web.WebContant;
/**
 * 管理员数据库操作实现类，实现了UserServiceI接口
 * @author China
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserServiceI {

	@Autowired
	private UserDaoI userDao;

	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getLoginName());
		params.put("pwd", MD5Util.md5(user.getUserPassword()));
		Tuser t = userDao.get(
				"from Tuser t where t.loginName = :name and t.userPassword = :pwd and t.userStatus=1 ",
				params);
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			return user;
		}
		return null;
	}

	@Override
	public JqGrid dataGrid(User user, JqGrid ph, SessionInfo sessionInfo) {
		JqGrid dg = new JqGrid();
		String hql = " from Tuser t ";
		Map<String, Object> params = new HashMap<String, Object>();
		
		hql = addWhere(user, hql, params, sessionInfo);
		hql = addOrder(ph, hql);
		List<Tuser> l = userDao.find(hql, params, ph.getCurPage(),
				ph.getPageSize());
		List<User> ul = new ArrayList<User>();
		changeModel(l, ul);
		dg.setData(ul);
		dg.setCurPage(ph.getCurPage());
		dg.setSuccess(true);
		dg.setTotalRows(userDao.count("select count(*) " + hql, params));
		return dg;
	}

	@Override
	synchronized public void add(User user, SessionInfo sessionInfo)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getLoginName());
		if (userDao.count(
				"select count(*) from Tuser t where t.loginName = :name",
				params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = new Tuser();
			BeanUtils.copyProperties(user, u);
			u.setUserStatus(1);
			u.setCreateDate(new Date());
			u.setCreateUserid(sessionInfo.getUser().getUserId());
			u.setUserPassword(MD5Util.md5(user.getUserPassword()));
			userDao.save(u);
		}
	}

	private void changeModel(List<Tuser> l, List<User> ul) {
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
		}
	}

	private String addOrder(JqGrid ph, String hql) {

		return hql;
	}

	private String addWhere(User user, String hql, Map<String, Object> params,
			SessionInfo sessionInfo) {
		if (user != null) {
			hql += " where t.userId>0 ";
			if (user.getSel() != null) {
				hql += " and t." + user.getSel() + " like :search";
				params.put("search", "%" + user.getSearch() + "%");
			}
			if (WebContant.isNotNull(user.getLoginName())) {
				hql += " and t.loginName like :loginName";
				params.put("loginName", "%" + user.getLoginName() + "%");
			}
			if (WebContant.isNotNull(user.getUserName())) {
				hql += " and t.userName like :userName";
				params.put("userName", "%" + user.getUserName() + "%");
			}

		}
		return hql;
	}

	@Override
	public User get(Integer userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		Tuser t = userDao.get(Tuser.class, userId);
		User u = new User();
		BeanUtils.copyProperties(t, u);
		u.setUserPassword(null);//前台不显示密码
		return u;
	}

	@Override
	synchronized public void edit(User user, SessionInfo sessionInfo)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getUserId());
		params.put("loginname", user.getLoginName());
		params.put("username", user.getUserName());
		String hql = "select count(*) from Tuser t where (t.loginName = :loginname";
		hql += " or t.userName = :username ) and t.userId != :userId";
		if (userDao.count(hql, params) > 0) {
			throw new Exception("登录名或姓名已存在！");
		} else {
			Tuser u = userDao.get(Tuser.class, user.getUserId());
			if(WebContant.isNotNull(user.getUserPassword())){
				user.setUserPassword(MD5Util.md5(user.getUserPassword()));
			}else{
				user.setUserPassword(u.getUserPassword());//不进行修改密码
			}
			BeanUtils.copyProperties(user, u, new String[] {"userId" });//userId不进行修改
			
		}
	}

	@Override
	public void delete(Integer userId, SessionInfo sessionInfo) {
		userDao.delete(userDao.get(Tuser.class, userId));
	}

}
