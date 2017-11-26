package agv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agv.pageModel.SessionInfo;
import agv.pageModel.Shelf;
import agv.pageModel.Task;
import agv.pageModel.User;
import agv.service.AutoTaskServiceI;
import agv.service.ShelfServiceI;
import agv.service.TaskServiceI;

@Service("autoTaskService")
public class AutoTaskServiceImpl implements AutoTaskServiceI {
	
	private static final  Logger systemLogger = Logger.getLogger("agv");
	private static final Logger userLogger = Logger.getLogger("agv.user");

	@Autowired
	private ShelfServiceI shelfService;
	@Autowired
	private TaskServiceI taskService;
	
	@Override
	public void autoTransportTrayTask() {
		ArrayList<Shelf> shelfList;
		ArrayList<Task> taskList;
    	HashMap<Integer, Shelf> shelfMap = new HashMap<Integer, Shelf>(); 
    	shelfList = shelfService.getAll();
    	taskList = taskService.getAll();
    	
    	for (Shelf shelf : shelfList){
    		shelfMap.put(shelf.getId(), shelf);
    	}
    	
    	//将货物从A货位运到B货位
    	if (shelfMap.get(1)!=null && shelfMap.get(3)!=null && shelfMap.get(1).getOccupied() && (!shelfMap.get(3).getOccupied())){
    		boolean find = false;
    		for (Task task : taskList){
    			if (task.getEndPos()!=null && task.getEndPos()==3){
    				find = true;
    				break;
    			}
    		}
    		if (!find){
    			Task task = new Task();
    			SessionInfo sessionInfo = new SessionInfo();
    			User user = new User();
    			user.setUserId(0);
    			sessionInfo.setUser(user);

    			task.setName("自动运输");
    			task.setStartPos(1);
    			task.setEndPos(3);
    			task.setScheduleTime(new Date(new Date().getTime() + 15*60*1000));//15分钟内执行完
    			task.setTypeId(1);//运输任务，使用货架的数据库信息
    			try {
    				taskService.add(task, sessionInfo);
    				systemLogger.info("添加自动托盘任务1->3");
				} catch (Exception e) {
					systemLogger.error("执行自动托盘任务失败："+e.getMessage());
				}
    		}
    	}
    	
    	//将空托盘从B货位运到A货位
    	if (shelfMap.get(2)!=null && shelfMap.get(4)!=null &&  shelfMap.get(4).getOccupied() && (!shelfMap.get(2).getOccupied())){
    		boolean find = false;
    		for (Task task : taskList){
    			if (task.getEndPos()!=null && task.getEndPos()==2){
    				find = true;
    				break;
    			}
    		}
    		if (!find){
    			Task task = new Task();
    			SessionInfo sessionInfo = new SessionInfo();
    			User user = new User();
    			user.setUserId(0);
    			sessionInfo.setUser(user);

    			task.setName("自动运输");
    			task.setStartPos(4);
    			task.setEndPos(2);
    			task.setScheduleTime(new Date(new Date().getTime() + 15*60*1000));//15分钟内执行完
    			task.setTypeId(1);//运输任务，使用货架的数据库信息
    			try {
    				taskService.add(task, sessionInfo);
    				systemLogger.info("添加自动托盘任务4->2");
				} catch (Exception e) {
					systemLogger.error("执行自动托盘任务失败："+e.getMessage());
				}
    		}
    	}
	}
	
	@Override
	public void autoShelfTrayTask() {
		ArrayList<Shelf> shelfList;
		ArrayList<Task> taskList;
    	HashMap<Integer, Shelf> shelfMap = new HashMap<Integer, Shelf>(); 
    	shelfList = shelfService.getAll();
    	taskList = taskService.getAll();
    	
    	for (Shelf shelf : shelfList){
    		shelfMap.put(shelf.getId(), shelf);
    	}
    	
    	//将货物从A货位运到B货位
    	if (shelfMap.get(1)!=null && shelfMap.get(3)!=null && shelfMap.get(1).getOccupied() && (!shelfMap.get(3).getOccupied())){
    		boolean find = false;
    		for (Task task : taskList){
    			if (task.getShelf2Id()!=null && task.getShelf2Id()==3){
    				find = true;
    				break;
    			}
    		}
    		if (!find){
    			Task task = new Task();
    			SessionInfo sessionInfo = new SessionInfo();
    			User user = new User();
    			user.setUserId(0);
    			sessionInfo.setUser(user);

    			task.setName("自动叉车");
    			task.setShelf1Id(1);
    			task.setShelf2Id(3);
    			task.setScheduleTime(new Date(new Date().getTime() + 15*60*1000));//15分钟内执行完
    			task.setTypeId(2);//叉车任务，使用货架的数据库信息
    			try {
    				taskService.add(task, sessionInfo);
    				systemLogger.info("添加自动叉车托盘任务1->3");
				} catch (Exception e) {
					systemLogger.error("执行自动叉车托盘任务失败："+e.getMessage());
				}
    		}
    	}
    	
    	//将空托盘从B货位运到A货位
    	if (shelfMap.get(2)!=null && shelfMap.get(4)!=null &&  shelfMap.get(4).getOccupied() && (!shelfMap.get(2).getOccupied())){
    		boolean find = false;
    		for (Task task : taskList){
    			if (task.getShelf2Id()!=null && task.getShelf2Id()==2){
    				find = true;
    				break;
    			}
    		}
    		if (!find){
    			Task task = new Task();
    			SessionInfo sessionInfo = new SessionInfo();
    			User user = new User();
    			user.setUserId(0);
    			sessionInfo.setUser(user);

    			task.setName("自动叉车");
    			task.setShelf1Id(4);
    			task.setShelf2Id(2);
    			task.setScheduleTime(new Date(new Date().getTime() + 15*60*1000));//15分钟内执行完
    			task.setTypeId(2);//叉车任务，使用货架的数据库信息
    			try {
    				taskService.add(task, sessionInfo);
    				systemLogger.info("添加自动叉车托盘任务4->2");
				} catch (Exception e) {
					systemLogger.error("执行自动叉车托盘任务失败："+e.getMessage());
				}
    		}
    	}
	}

}
