package agv.controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * 车辆信息管理控制类
 * @author 
 * @date 2015-12-21 17:17:56
 * @version 1.0
 */
@Controller
@RequestMapping("/journal")
public class JournalController extends BaseController {
	static String destIP = "localhost";
	static Integer destPort = 4700;
		
	@RequestMapping(method = RequestMethod.GET)
	public String get(HttpServletRequest request) {
		return "/journal/journal";
	}
	
	
		
}
