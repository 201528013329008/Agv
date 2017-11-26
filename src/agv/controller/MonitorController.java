package agv.controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextResource;

import agv.pageModel.SessionInfo;
import agv.util.CommonUtil;
import agv.util.ConfigUtil;



/**
 * 车辆信息管理控制类
 * @author 
 * @date 2015-12-21 17:17:56
 * @version 1.0
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController extends BaseController {
	static String destIP = "localhost";
	static Integer destPort = 4700;
	
	@RequestMapping(method = RequestMethod.GET)
	public String root(HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		if ("super".equals(sessionInfo.getUser().getUserType())){
			return "/monitor/system";
		}else{
			return "/monitor/map";
		}
		
	}
	
	@RequestMapping("/super/system")
	public String system(HttpServletRequest request) {
		return "/monitor/system";
	}
	
	@RequestMapping("/super/operation")
	public String operation(HttpServletRequest request) {
		return "/monitor/operation";
	}
	
	@RequestMapping("/map")
	public String map(HttpServletRequest request) {
		return "/monitor/map";
	}
	
	@RequestMapping("/super/task")
	public String task(HttpServletRequest request) {
		return "/task/task";
	}
	
	@RequestMapping("/mapDatas")
	@ResponseBody
	public String mapDatas(HttpServletRequest request) {
		ServletContextResource resource = new ServletContextResource(request.getSession().getServletContext(),"content\\datas\\test.map");
				
		try{
			if (resource.isReadable()){
				BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(),"UTF-8"));
				StringBuffer  jsonBuf = new StringBuffer();
				String str;
				while ((str=reader.readLine())!=null){
					jsonBuf.append(str);
				}
		        
		        String jsonTxt = jsonBuf.toString();
		        jsonTxt = CommonUtil.removeUTF8BOM(jsonTxt);
		        jsonTxt = jsonTxt.replaceAll("\\s*", "");
		        reader.close();
		        return jsonTxt;
			}else{
				return "fail";
			}
		}catch(Exception e){
			return "fail";
		}
	}
		
}
