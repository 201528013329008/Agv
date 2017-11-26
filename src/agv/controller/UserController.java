package agv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import agv.model.Tuser;
import agv.module.impl.WebSocketModule;
import agv.pageModel.JqGrid;
import agv.pageModel.Msg;
import agv.pageModel.SateJson;
import agv.pageModel.SessionInfo;
import agv.pageModel.User;
import agv.service.LogServiceI.LogScope;
import agv.service.UserServiceI;
import agv.util.ConfigUtil;
import agv.util.IpUtil;
import agv.web.WebContant;

/**
 * 用户控制器
 * 
 * @author China
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserServiceI userService;

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            用户对象
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/login")
	public SateJson login(User user, HttpSession session, HttpServletRequest request)
			throws Exception {
		SateJson j = new SateJson();
		User u = userService.login(user);
		if (u != null) {
			j.setSuccess(true);
			j.setMsg("登陆成功！");
			SessionInfo sessionInfo = new SessionInfo();
			sessionInfo.setUser(u);
			session.setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
			userLogger.info(u.getLoginName()+"/"+u.getUserName()+"：登入成功");
		} else {
			j.setMsg("用户名或密码错误！");
			userLogger.info(user.getLoginName()+"：登入失败");
		}
		return j;
	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public SateJson logout(HttpSession session) {
		SateJson j = new SateJson();
		User user;
		if (session != null) {
			SessionInfo sessionInfo = (SessionInfo) session
					.getAttribute(ConfigUtil.getSessionInfoName());
			user = sessionInfo.getUser();
			userLogger.info(user.getLoginName()+"/"+user.getUserName()+"：注销成功");
			session.invalidate();
		}
		j.setSuccess(true);
		j.setMsg("注销成功！");
		return j;
	}
	
	/**
	 * 修改用户密码页面
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/changepwdPage")
	public String changepwdPage(HttpServletRequest request, Integer id) {
		return "/user/changepwd";
	}
	
	/**
	 * 修改用户密码
	 * 
	 * @param user
	 * @param sessionInfo
	 * @return
	 */
	@RequestMapping("/changepwd")
	@ResponseBody
	public SateJson changepwd(String oldPassword, User user,HttpServletRequest request){
		SateJson j = new SateJson();
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(request);
		
		try {
			User u = userService.get(sessionInfo.getUser().getUserId());
			u.setUserPassword(oldPassword);
			u = userService.login(u);
			if (u != null) {
				u.setUserPassword(user.getUserPassword());
				userService.edit(u, sessionInfo);
				j.setSuccess(true);
				j.setMsg("编辑成功！");
				j.setObj(user);
				userLogger.info(u.getLoginName()+"/"+u.getUserName()+"：修改密码成功");
			}else{
				j.setMsg("原密码错误！");
				userLogger.warn(user.getLoginName()+"/"+user.getUserName()+"：修改密码失败");
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			systemLogger.error(e.getMessage());
		}
		return j;
	}

/**---------------------------------管理员特权------------------------------------------**/
	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/super/managePage")
	public String manager() {
		return "user/manageUser";
	}

	/**
	 * 获取用户数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/super/dataGrid")
	@ResponseBody
	public JqGrid dataGrid(User user, JqGrid ph,HttpServletRequest request){
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(request);
		return userService.dataGrid(user, ph, sessionInfo);
	}

	/**
	 * 跳转到添加用户页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/super/addPage")
	public String addPage(HttpServletRequest request) {
		return "user/addUser";
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	@RequestMapping("/super/add")
	@ResponseBody
	public SateJson add(User user,HttpServletRequest request){
		SateJson j = new SateJson();
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(request);
		try {
			if (!"super".equals(sessionInfo.getUser().getUserType())) {
				j.setMsg("非管理员不能添加用户！");
				return j;
			}
			userService.add(user, sessionInfo);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(user);
			userLogger.info("添加用户："+user.getLoginName()+"/"+user.getUserName());
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			systemLogger.error(e.getMessage());
		}
		return j;
	}

	/**
	 * 跳转到用户修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/super/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		User u = userService.get(id);
		request.setAttribute("user", u);
		return "user/editUser";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/super/edit")
	@ResponseBody
	public SateJson edit(User user,HttpServletRequest request){
		SateJson j = new SateJson();
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(request);
		if (!"super".equals(sessionInfo.getUser().getUserType())
				&& sessionInfo.getUser().getUserId() != user.getUserId()) {
			j.setMsg("非管理员不能修改其他用户的信息！");
			User opUser = sessionInfo.getUser();
			userLogger.warn(opUser.getLoginName()+"/"+opUser.getUserName()+" 试图编辑 "+
						user.getLoginName()+"/"+user.getUserName());
			return j;
		}
		try {
			userService.edit(user, sessionInfo);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(user);
			userLogger.info("编辑用户："+user.getLoginName()+"/"+user.getUserName());
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			systemLogger.error(e.getMessage());
		}
		return j;
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/super/delete")
	@ResponseBody
	public SateJson delete(Integer id, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil
				.getSessionInfoName());
		SateJson j = new SateJson();
		if (!"super".equals(sessionInfo.getUser().getUserType())) {
			j.setMsg("非管理员不能删除用户！");
			User opUser = sessionInfo.getUser();
			userLogger.warn(opUser.getLoginName()+"/"+opUser.getUserName()+" 试图删除UserId="+
					id);
			return j;
		}
		if (id != null && !id.equals(sessionInfo.getUser().getUserId())) {// 不能删除自己
			userService.delete(id, sessionInfo);
			j.setMsg("删除成功！");
			j.setSuccess(true);
			User u = userService.get(id);
			userLogger.info("删除用户："+u.getLoginName()+"/"+u.getUserName());
		} else {
			j.setSuccess(false);
			j.setMsg("删除失败！");
			userLogger.warn("删除用户失败："+id);
		}
		return j;
	}

}
