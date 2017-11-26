package agv.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import agv.model.Tuser;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agv.dao.TaskDaoI;
import agv.model.Ttask;
import agv.pageModel.Agv;
import agv.pageModel.AgvType;
import agv.pageModel.JqGrid;
import agv.pageModel.SessionInfo;
import agv.pageModel.Shelf;
import agv.pageModel.Task;
import agv.pageModel.User;
import agv.service.AgvServiceI;
import agv.service.AgvTypeServiceI;
import agv.service.ShelfServiceI;
import agv.service.TaskServiceI;
import agv.util.CommonUtil;
import agv.web.WebContant;

/**
 * 获奖信息管理类service实现
 * @author
 * @date 2015-12-21 17:18:43
 * @version 1.0
 */
@Service("taskService")
public class TaskServiceImpl extends BaseServiceImpl implements TaskServiceI {

	@Autowired
	private TaskDaoI taskDao;
	
	@Autowired
	private AgvServiceI agvService;
	
	@Autowired
	private AgvTypeServiceI agvTypeService;
	
	@Autowired
	private ShelfServiceI shelfService;
	
	private JqGrid jqgrid;
	private BigDecimal count = null;// 统计数目
	@Override
	public Task add(Task task, SessionInfo sessionInfo) throws Exception {
		Ttask t = new Ttask();
		BeanUtils.copyProperties(task, t, CommonUtil.getNullPropertyNames(task));
		t.setCreateUserid(sessionInfo.getUser().getUserId());
		t.setCreateDate(new Date());
		t.setState("waiting");
		t.setIfRead(false);
		taskDao.save(t);
		BeanUtils.copyProperties(t, task);
		return task;
	}

	@Override
	public Task get(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",id);
		Ttask t = taskDao.get(Ttask.class,id);
		Task task = new Task();
		if(t!=null){
			BeanUtils.copyProperties(t,task);
		}
		return task;
	}
	
	@Override
	public void edit(Task task, SessionInfo sessionInfo) throws Exception {
		
		Ttask t = taskDao.get(Ttask.class,task.getId());
		if(t!=null){
			BeanUtils.copyProperties(task, t, CommonUtil.getNullPropertyNames(task));
			taskDao.update(t);
		}
	}

	@Override
	public void delete(int id, SessionInfo sessionInfo) {
		if("person".equals(sessionInfo.getUser().getUserType())){
			return;//普通用户不能进行删除，如允许删除请删掉此段代码
		}
		taskDao.delete(taskDao.get(Ttask.class, id));

	}

	@Override
	public JqGrid dataGrid(Task task, JqGrid ph, String dataType, SessionInfo sessionInfo){
		JqGrid jqgrid = new JqGrid();
		String hql;
		hql = " from Ttask a, Tuser b "
				+ " where a.createUserid = b.userId ";
				
		if (CURRENT.equals(dataType)){
			hql += " and a.state <> 'finished' and a.state <> 'cancelled' ";
		}else if(HISTORY.equals(dataType)){
			hql += " and ( a.state = 'finished' or a.state = 'cancelled' ) ";
		}
		
				
		Map<String, Object> params = new HashMap<String, Object>();
		String orderString = "";
		hql = addWhere(task, hql, params, sessionInfo);
		jqgrid.setTotalRows(taskDao.count("select count(*) " + hql, params));
		hql = addOrder(jqgrid, hql);
		ArrayList l = (ArrayList) taskDao.find(hql, params, jqgrid.getCurPage(),
				jqgrid.getPageSize());
		List<Task> ul = new ArrayList<Task>();
		changeModel(l, ul);
		jqgrid.setData(ul);
		jqgrid.setCurPage(ph.getCurPage());
		jqgrid.setSuccess(true);
		
		return jqgrid;
	}
	

	private String addOrder(JqGrid jqgrid, String hql) {
		hql +=" order by a.createDate desc";
		return hql;
	}
	
	private void changeModel(ArrayList l, List<Task> ul) {
		Iterator iterator1 = l.iterator();
		while (iterator1.hasNext()) {
			Object[] obj = (Object[]) iterator1.next();
			Ttask t = (Ttask) obj[0];
			Tuser tu = (Tuser)obj[1];
			
			Task task = new Task();
			User user = new User();
			if (t != null) {
				BeanUtils.copyProperties(t, task);
			}
			if(tu!=null){
				BeanUtils.copyProperties(tu, user);
				task.setUser(user);
			}
			
			if (t.getAgvId()!=null){
				Agv agv = agvService.get(t.getAgvId());
				task.setAgv(agv);
			}
			
			if (t.getAgvTypeId()!=null){
				AgvType agvType = agvTypeService.get(t.getAgvTypeId());
				task.setAgvType(agvType);
			}
			
			if (t.getShelf1Id()!=null){
				Shelf shelf = shelfService.get(t.getShelf1Id());
				task.setShelf1(shelf);
			}
			
			if (t.getShelf2Id()!=null){
				Shelf shelf = shelfService.get(t.getShelf2Id());
				task.setShelf2(shelf);
			}
			
			ul.add(task);
		}
	}
	private String addWhere(Task task, String hql, Map<String, Object> params,SessionInfo sessionInfo) {
		
		hql += " and a.id>0 ";//跳过默认任务
		
		if (task != null) {
			
			String usertype = sessionInfo.getUser().getUserType();
			//普通用户权限
			if("person".equals(usertype)){
				hql +=" and a.createUserid = :userid ";
				int userid = sessionInfo.getUser().getUserId();
				params.put("userid",userid);
			}
			if(task.getName()!=null &&!"".equals(task.getName())){
				hql += " and a.name like :name)";
				params.put("name", '%'+ task.getName()+'%');
			}
			if(task.getTypeId()!=null &&!"".equals(task.getTypeId())){
				hql += " and a.typeId = :typeId";
				params.put("typeId", '%'+ task.getTypeId()+'%');
			}
			if (task.getId()!=null && !"".equals(task.getId())) {
				hql += " and a.id = :id";
				params.put("id",  task.getId());
			}
			
		}
		return hql;
	}

	@Override
	public String exportData(Task task, JqGrid ph, SessionInfo sessionInfo) {

		OutputStream os = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = sdf.format(date);
		String downLoadPath = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ "../../" + WebContant.DOWNLOAD_PATH + "\\";
		try {
			ph.setPageSize(65535);
			JqGrid jqgrid = dataGrid(task, ph, TaskServiceI.ALL, sessionInfo);
			Long totalRows = jqgrid.getTotalRows();
			List list = jqgrid.getData();
			Workbook book = null;
			CellStyle style = null;
			if (totalRows < 65535) {
				// 创建工作区(97-2003)
				book = new HSSFWorkbook();
				fileName = fileName + ".xls";
			} else {
				book = new XSSFWorkbook();
				fileName = fileName + ".xlsx";
			}
			os = new FileOutputStream(downLoadPath + fileName);
			style = book.createCellStyle();
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			// 把垂直对齐方式指定为居中
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			Font font = book.createFont();
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			// font.setFontName("宋体");
			font.setFontHeight((short) 200);
			style.setFont(font);
			Sheet sheet = book.createSheet("数据导出");
			String[] title = new String[] { "序号", "获奖信息名称","作者", "获奖时间", "颁发单位",
					"获奖信息简介", "上传人", "上传时间", "获奖信息状态", "审批人", "审批时间" };
			int[] width = new int[] { 44, 180, 180,180, 180, 250, 70, 180, 70, 70, 180 };
			int columnBestWidth[] = new int[title.length];
			// 保存最佳列宽数据的数组
			// 生成标题
			Row row = sheet.createRow(0);
			Cell cell = null;
			for (int i = 0; i < title.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(title[i]);
				sheet.autoSizeColumn(i);
				row.setHeight((short) (32 * 12));
			}
			style = book.createCellStyle();
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			font = book.createFont();
			font.setBoldweight(XSSFFont.DEFAULT_FONT_SIZE);
			// font.setFontName("宋体");
			font.setFontHeight((short) 200);
			style.setFont(font);

			for (int i = 0; i < totalRows; i++) {
				row = sheet.createRow(i + 1);
				row.setHeight((short) (32 * 12));
				Task p = (Task) list.get(i);
				System.out.println("export:" + i + "行");

				/*cell = row.createCell(0);// 序号
				cell.setCellValue(i + 1);
				cell.setCellStyle(style);
				cell = row.createCell(1);
				cell.setCellValue(p.getTaskName().toString());
				cell.setCellStyle(style);
				cell = row.createCell(2);
				cell.setCellValue(p.getAuthorName().toString());
				cell.setCellStyle(style);
				cell = row.createCell(3);
				cell.setCellValue(p.getPublishDate() == null ? null : p.getPublishDate().toString());
				cell.setCellStyle(style);
				cell = row.createCell(4);
				cell.setCellValue(p.getPublication().toString());
				cell.setCellStyle(style);
				cell = row.createCell(5);
				cell.setCellValue(p.getTaskInfo().toString());
				cell.setCellStyle(style);
				cell = row.createCell(6);
				cell.setCellValue(p.getAddUserName().toString());
				cell.setCellStyle(style);
				cell = row.createCell(7);
				cell.setCellValue(p.getAddDate().toString());
				cell.setCellStyle(style);
				cell = row.createCell(8);
				int state = p.getState();
				String stateName = "待审批";
				if(state==1){
					stateName="有效";
				}else if(state==2){
					stateName="无效";
				}
				cell.setCellValue(stateName);
				cell.setCellStyle(style);
				cell = row.createCell(9);
				cell.setCellValue(p.getApproveUser());
				cell.setCellStyle(style);
				cell = row.createCell(10);
				cell.setCellValue(p.getApproveDate() == null ? null : p
						.getApproveDate().toString());
				cell.setCellStyle(style);*/

			}
			style = book.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			font = book.createFont();
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			// font.setFontName("宋体");
			font.setFontHeight((short) 200);
			style.setFont(font);
			for (int i = 0; i < columnBestWidth.length; i++) { // /设置每列宽
				sheet.setColumnWidth(i, 32 * width[i]);
				sheet.setDefaultColumnStyle(i, style);
			}
			book.write(os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}

	@Override
	public ArrayList<Task> getAll() {
		String hql;
		hql = " from Ttask a, Tuser b "
				+ " where a.createUserid = b.userId and a.state <> 'finished' and a.state <> 'cancelled' ";
				
		ArrayList l = (ArrayList) taskDao.find(hql);
		ArrayList<Task> ul = new ArrayList<Task>();
		changeModel(l, ul);
		
		return ul;
	}
}
