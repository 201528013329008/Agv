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

import agv.dao.AgvDaoI;
import agv.model.Tagv;
import agv.model.TagvType;
import agv.model.Tuser;
import agv.pageModel.Agv;
import agv.pageModel.AgvType;
import agv.pageModel.JqGrid;
import agv.pageModel.SessionInfo;
import agv.pageModel.Task;
import agv.pageModel.User;
import agv.service.AgvServiceI;
import agv.service.TaskServiceI;
import agv.util.CommonUtil;
import agv.web.WebContant;

/**
 * 车辆信息管理类service实现
 * @author
 * @date 2015-12-21 17:17:56
 * @version 1.0
 */
@Service("agvService")
public class AgvServiceImpl extends BaseServiceImpl implements AgvServiceI {

	@Autowired
	private AgvDaoI agvDao;
	
	@Autowired
	private TaskServiceI taskService;

	private JqGrid jqgrid;
	private BigDecimal count = null;// 统计数目

	@Override
	public void add(Agv agv, SessionInfo sessionInfo) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", agv.getName());
		if (agvDao.count(
				"select count(*) from Tagv t where t.name = :name",
				params) > 0) {
			throw new Exception("AGV编号已存在！");
		} 
		Tagv t = new Tagv();
		t.setTaskId(0);
		BeanUtils.copyProperties(agv, t,CommonUtil.getNullPropertyNames(agv));
		t.setCreateUserid(sessionInfo.getUser().getUserId());
		t.setCreateDate(new Date());
		agvDao.save(t);
	}

	@Override
	public Agv get(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",id);
		Tagv t = agvDao.get(Tagv.class,id);
		Agv agv = new Agv();
		if(t!=null){
			BeanUtils.copyProperties(t,agv);
		}
		return agv;
	}
	
	@Override
	public void edit(Agv agv, SessionInfo sessionInfo) throws Exception {
		Tagv t = agvDao.get(Tagv.class,agv.getId());
		if(t!=null){
			if("super".equals(sessionInfo) || ("person".equals(sessionInfo) && t.getCreateUserid()==sessionInfo.getUser().getUserId())){
				BeanUtils.copyProperties(agv, t, CommonUtil.getNullPropertyNames(agv));
				agvDao.update(t);
			}
			
		}
	}

	@Override
	public void delete(int id, SessionInfo sessionInfo) {
		if("person".equals(sessionInfo)){
			return;//普通用户不能进行删除，如允许删除请删掉此段代码
		}
		agvDao.delete(agvDao.get(Tagv.class, id));
	}

	@Override
	public JqGrid dataGrid(Agv agv, JqGrid ph,SessionInfo sessionInfo){
		JqGrid jqgrid = new JqGrid();
		String hql;
		int userId;
//		if (sessionInfo.getUser().getUserType().equals("super")){
//			hql = " from Tagv a,Tuser b, TagvType c where a.createUserid = b.userId and a.typeId = c.id";
//		}else{
//			userId = sessionInfo.getUser().getUserId();
//			hql = " from Tagv a,Tuser b, TagvType c where a.createUserid = b.userId and a.typeId = c.id and b.userId = "+userId;
//		}
		hql = " from Tagv a,Tuser b, TagvType c where a.createUserid = b.userId and a.typeId = c.id";
		
		Map<String, Object> params = new HashMap<String, Object>();
		String orderString = "";
		hql = addWhere(agv, hql, params, sessionInfo);
		jqgrid.setTotalRows(agvDao.count("select count(*) " + hql, params));
		hql = addOrder(ph, hql);
		ArrayList l = (ArrayList) agvDao.find(hql, params, ph.getCurPage(),
				ph.getPageSize());
		List<Agv> ul = new ArrayList<Agv>();
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
	
	private void changeModel(ArrayList l, List<Agv> ul) {
		Iterator iterator1 = l.iterator();
		while (iterator1.hasNext()) {
			Object[] obj = (Object[]) iterator1.next();
			Tagv t = (Tagv) obj[0];
			Tuser tu = (Tuser)obj[1];
			TagvType tagvType = (TagvType)obj[2];
			
			User u = new User();
			Agv agv = new Agv();
			AgvType agvType = new AgvType();
			
			if (t != null) {
				BeanUtils.copyProperties(t, agv);
			}
			if(tu!=null){
				BeanUtils.copyProperties(tu, u, new String[]{"userPassword"});
				agv.setUser(u);
			}
			if(tagvType!=null){
				BeanUtils.copyProperties(tagvType, agvType);
				agv.setAgvType(agvType);
			}
			
			if (agv.getTaskId() != null){
				Task task = taskService.get(agv.getTaskId());
				agv.setTask(task);
			}
			
			ul.add(agv);
		}
	}
	
	private String addWhere(Agv agv, String hql, Map<String, Object> params,SessionInfo sessionInfo) {
		hql +=" and a.id > 0 ";
		if (agv != null) {
			String usertype = sessionInfo.getUser().getUserType();
			//普通用户权限
			if("person".equals(usertype)){
				hql +=" and a.createUserid = :userid ";
				int userid = sessionInfo.getUser().getUserId();
				params.put("userid",userid);
			}
			
			if (agv.getId()!=null && agv.getId() != 0) {
				hql += " and a.id = :id";
				params.put("id",  agv.getId());
			}
			if (agv.getName()!=null && "".equals(agv.getName())) {
				hql += " and a.name like :name";
				params.put("name", '%'+ agv.getName()+'%');
			}
			if (agv.getTypeId()!=null && "".equals(agv.getTypeId())) {
				hql += " and a.type_id like :type_id";
				params.put("type_id", agv.getTypeId());
			}
			
		}
		return hql;
	}

	@Override
	public String exportData(Agv agv, JqGrid ph,
			SessionInfo sessionInfo) {
		OutputStream os = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = sdf.format(date);
		String downLoadPath = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ "../../" + WebContant.DOWNLOAD_PATH + "\\";
		try {
			ph.setPageSize(65535);
			JqGrid jqgrid = dataGrid(agv, ph, sessionInfo);
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
			String[] title = new String[] { "序号", "ID","AGV编号","车辆类型","最大负重","最大直线行驶速度", "拐弯速度",
					"状态", "添加人", "添加时间" };
			int[] width = new int[] { 50, 50,150, 150,150,180, 150, 150, 150, 150};
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
				Agv p = (Agv) list.get(i);
				System.out.println("export:" + i + "行");

				cell = row.createCell(0);// 序号
				cell.setCellValue(i + 1);
				cell.setCellStyle(style);
				cell = row.createCell(1);
				cell.setCellValue(p.getId());
				cell.setCellStyle(style);
				cell = row.createCell(2);
				cell.setCellValue(p.getName());
				cell.setCellStyle(style);
				cell = row.createCell(3);
				cell.setCellValue(p.getTypeId());
				cell.setCellStyle(style);
				cell = row.createCell(4);
				
				Boolean forbidden = p.getForbidden();
				String stateName = "无效";
				if(!forbidden){
					stateName="有效";
				}else if(forbidden){
					stateName="无效";
				}
				cell.setCellValue(stateName);
				cell.setCellStyle(style);
				cell = row.createCell(8);
				cell.setCellValue(p.getUser().getUserName());
				cell.setCellStyle(style);
				cell = row.createCell(9);
				cell.setCellValue(p.getCreateDate()== null ? null : p
						.getCreateDate().toString());
				cell.setCellStyle(style);
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


}
