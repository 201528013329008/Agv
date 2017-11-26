package agv.controller;

import javax.servlet.http.HttpServletRequest;
import agv.pageModel.JqGrid;
import agv.pageModel.SateJson;
import agv.pageModel.SessionInfo;
import agv.pageModel.Shelf;
import agv.service.ShelfServiceI;
import agv.web.WebContant;


/**
 * 货架信息管理控制类
 * @author 
 * @date 2015-12-21 17:17:56
 * @version 1.0
 */
@Controller
@RequestMapping("/shelf")
class ShelfController extends BaseController {
	static String destIP = "localhost";
	static Integer destPort = 4700;
	@Autowired
	private ShelfServiceI shelfService;
	@RequestMapping("/dataGrid")
	@ResponseBody
	public JqGrid dataGrid(Shelf shelf, JqGrid ph, HttpServletRequest req) {
		return shelfService.dataGrid(shelf, ph, WebContant.getCurrentSessionInfo(req));
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public Shelf getInfo(String sn) {
		Shelf shelf = shelfService.get(sn);
		if (shelf!=null){
			return shelf;
		}else{
			return null;
		}
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public SateJson add(Shelf shelf,HttpServletRequest req) {
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		try {
			shelfService.add(shelf,sessionInfo);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(shelf);
			userLogger.info("添加货架："+shelf.getSn());
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
			userLogger.warn("添加货架失败："+e.getMessage());
		}
		return j;
	}
	
	
	@RequestMapping("/edit")
	@ResponseBody
	public SateJson edit(Shelf shelf,HttpServletRequest req) {
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		try {
			shelfService.edit(shelf,sessionInfo);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(shelf);
			userLogger.info("修改货架："+shelf.getId());
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
			userLogger.warn("修改货架失败："+e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public SateJson delete(String sn, HttpServletRequest req) {
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		SateJson j = new SateJson();
		if (sn != "" && !"preson".equals(sessionInfo.getUser().getUserType())) {
			shelfService.delete(sn, sessionInfo);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
			j.setMsg("删除失败！");
		}
		return j;
	}
	
}
