package agv.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import agv.pageModel.JqGrid;
import agv.service.LogServiceI;
import agv.web.WebContant;


/**
 * 车辆信息管理控制类
 * @author 
 * @date 2015-12-21 17:17:56
 * @version 1.0
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {
	static String destIP = "localhost";
	static Integer destPort = 4700;
	@Autowired
	private LogServiceI logService;
	
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public JqGrid dataGrid(JqGrid ph, HttpServletRequest req) {
		return logService.dataGrid(ph, WebContant.getCurrentSessionInfo(req));
	}
	
}
