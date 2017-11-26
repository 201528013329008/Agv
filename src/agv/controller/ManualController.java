package agv.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import agv.pageModel.Agv;
import agv.pageModel.JqGrid;
import agv.pageModel.SateJson;
import agv.pageModel.SessionInfo;
import agv.pageModel.Task;
import agv.service.AgvServiceI;
import agv.service.TaskServiceI;
import agv.web.WebContant;
import net.sf.json.JSONObject;


/**
 * 车辆信息管理控制类
 * @author 
 * @date 2015-12-21 17:17:56
 * @version 1.0
 */
@Controller
@RequestMapping("/manual")
public class ManualController extends BaseController {
	static String destIP = "localhost";
	static Integer destPort = 4700;
	@Autowired
	private AgvServiceI agvService;
	@Autowired
	private TaskServiceI taskService;
	
	@RequestMapping("/index")
	public String manager(int id, HttpServletRequest request) {
		request.setAttribute("id", id);
		return "/manual/manual";
	}
	
	@RequestMapping("/virtual")
	public String virtual() {
		return "/manual/virtual_device";
	}
	
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public JqGrid dataGrid(Agv agv, JqGrid ph, HttpServletRequest req) {
		return agvService.dataGrid(agv, ph,WebContant.getCurrentSessionInfo(req));
	}
	
	
}
