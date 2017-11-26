package agv.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import agv.pageModel.JqGrid;
import agv.pageModel.SateJson;
import agv.pageModel.SessionInfo;
import agv.pageModel.Task;
import agv.pageModel.Task.State;
import agv.service.TaskServiceI;
import agv.web.WebContant;




/**
 * 车辆信息管理控制类
 * @author 
 * @date 2015-12-21 17:17:56
 * @version 1.0
 */
@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {
	static String destIP = "localhost";
	static Integer destPort = 4700;
	@Autowired
	private TaskServiceI taskService;

	@RequestMapping(method = RequestMethod.GET)
	public String manager() {
		return "/task/task";
	}
	
	/**
	 * runtime 运行时任务情况
	 * @return
	 */
	@RequestMapping("/rt")
	public String rt() {
		return "/task/task_rt";
	}
	
	/**
	 * runtime 普通用户使用的任务列表页面
	 * @return
	 */
	@RequestMapping("/user")
	public String user() {
		return "/task/task_user";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public JqGrid dataGrid(Task task, JqGrid ph, HttpServletRequest req) {
		return taskService.dataGrid(task, ph, TaskServiceI.ALL, WebContant.getCurrentSessionInfo(req));
	}
	
	@RequestMapping("/dataGridCurrent")
	@ResponseBody
	public JqGrid dataGridCurrent(Task task, JqGrid ph, HttpServletRequest req) {
		return taskService.dataGrid(task, ph, TaskServiceI.CURRENT, WebContant.getCurrentSessionInfo(req));
	}
	
	@RequestMapping("/dataGridHistory")
	@ResponseBody
	public JqGrid dataGridHistory(Task task, JqGrid ph, HttpServletRequest req) {
		return taskService.dataGrid(task, ph, TaskServiceI.HISTORY, WebContant.getCurrentSessionInfo(req));
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public Task getInfo(int id) {
		return taskService.get(id);
	}
	
	@RequestMapping("/addTaskPage")
	public String addPage(int typeId, HttpServletRequest request) {
		request.setAttribute("type", typeId);
		return "/task/editTask";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public SateJson add(Long scheduleTimeInt, Task task, HttpServletRequest req) {
		task.setScheduleTime(new Date(scheduleTimeInt));
		task.setIfRead(false);
		if (task.getPriority() == null){
			task.setPriority(0);
		}
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		try {
			task = taskService.add(task,sessionInfo);
			systemModule.addTask(task.getId());
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(task);
					
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
		
	@RequestMapping("/editTaskPage")
	public String editPage(HttpServletRequest request, int id) {
		Task task = taskService.get(id);
		request.setAttribute("task", task);
		return "/task/editTask";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public SateJson edit(Task task,HttpServletRequest req) {
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		try {
			taskService.edit(task,sessionInfo);
			systemModule.updateTask(task.getId());
			j.setSuccess(true);
			j.setMsg("编辑成功");
			j.setObj(task);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/cancel")
	@ResponseBody
	public SateJson cancel(HttpServletRequest req,int taskId) {
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		Task t = taskService.get(taskId); 
		if (t!=null){
			t.setState(State.cancelled);
			try {
				taskService.edit(t,sessionInfo);
				systemModule.cancelTask(t.getId());
				j.setSuccess(true);
				j.setMsg("取消成功！");
				j.setObj(t);
			} catch (Exception e) {
				systemLogger.error(e.getMessage());
				e.printStackTrace();
				j.setMsg(e.getMessage());
			}
		}else{
			j.setMsg("任务不存在");
			systemLogger.error("试图取消不存在的任务");
		}
		return j;
	}
	
	@RequestMapping("/loadFinish")
	@ResponseBody
	public SateJson loadFinish(HttpServletRequest req,int taskId) {
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		Task t = taskService.get(taskId); 
		if (t!=null){
			t.setState(State.loadingFinish);
			try {
				taskService.edit(t,sessionInfo);
				systemModule.loadFinish(t.getId());
				j.setSuccess(true);
				j.setMsg("设置成功");
				j.setObj(t);
			} catch (Exception e) {
				systemLogger.error(e.getMessage());
				e.printStackTrace();
				j.setMsg(e.getMessage());
			}
		}else{
			j.setMsg("任务不存在");
			systemLogger.error("试图控制不存在的任务");
		}
		return j;
	}
	
	@RequestMapping("/unloadFinish")
	@ResponseBody
	public SateJson unloadFinish(HttpServletRequest req,int taskId) {
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		Task t = taskService.get(taskId); 
		if (t!=null){
			t.setState(State.unloadingFinish);
			try {
				taskService.edit(t,sessionInfo);
				systemModule.unloadFinish(t.getId());
				j.setSuccess(true);
				j.setMsg("设置成功");
				j.setObj(t);
			} catch (Exception e) {
				systemLogger.error(e.getMessage());
				e.printStackTrace();
				j.setMsg(e.getMessage());
			}
		}else{
			j.setMsg("任务不存在");
			systemLogger.error("试图控制不存在的任务");
		}
		return j;
	}
	
}
