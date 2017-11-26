package agv.controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * 车辆信息管理控制类
 * @author 
 * @date 2015-12-21 17:17:56
 * @version 1.0
 */
@Controller
@RequestMapping("/layout")
public class LayoutController extends BaseController {
	static String destIP = "localhost";
	static Integer destPort = 4700;
		
	@RequestMapping("/super/drawMap")
	public String map(HttpServletRequest request) {
		return "/layout/drawMap";
	}
		
	@RequestMapping("/super/road")
	public String road(HttpServletRequest request) {
		return "/layout/road";
	}
	
	@RequestMapping("/super/stop")
	public String stop(HttpServletRequest request) {
		return "/layout/stop";
	}
	
	@RequestMapping("/super/workstation")
	public String workstation(HttpServletRequest request) {
		return "/layout/workstation";
	}
	
	@RequestMapping("/super/RFID")
	public String RFID(HttpServletRequest request) {
		return "/layout/RFID";
	}
	
	@RequestMapping("/super/charge")
	public String charge(HttpServletRequest request) {
		return "/layout/charge";
	}
	
	@RequestMapping("/super/isolation")
	public String isolation(HttpServletRequest request) {
		return "/layout/isolation";
	}
}
