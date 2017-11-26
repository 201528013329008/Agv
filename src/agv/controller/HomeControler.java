package agv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import agv.module.SystemModuleI;
/**
 * 系统控制器
 * @author China
 *
 */
@Controller
@RequestMapping("/")
public class HomeControler extends BaseController {
		
	@RequestMapping(method=RequestMethod.GET)
	public String root(HttpServletRequest req) {
		return "/login";
	}
		
	@RequestMapping("/login")
	public String login(HttpServletRequest req) {
		return "/login";
	}
	
	@RequestMapping("/index")
	public String index(HttpServletRequest req,HttpServletResponse rsp) {
		return "/index";
	}
	
	/*@RequestMapping("/main")
	public String main(HttpServletRequest req) {
		return "/main";
	}
	
	@RequestMapping("/left")
	public String left(HttpServletRequest req) {
		return "/left";
	}
	
	@RequestMapping("/right")
	public String right(HttpServletRequest req) {
		return "/right";
	}
	
	@RequestMapping("/top")
	public String top(HttpServletRequest req) {
		return "/top";
	}
	
	@RequestMapping("/footer")
	public String footer(HttpServletRequest req) {
		return "/footer";
	}*/

}
