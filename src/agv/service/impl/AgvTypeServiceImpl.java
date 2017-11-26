package agv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import agv.dao.AgvTypeDaoI;
import agv.model.TagvType;
import agv.pageModel.AgvType;
import agv.pageModel.JqGrid;
import agv.pageModel.SessionInfo;
import agv.service.AgvTypeServiceI;
import agv.util.CommonUtil;

/**
 * 车辆信息管理类service实现
 * @author
 * @date 2015-12-21 17:17:56
 * @version 1.0
 */
@Service("agvTypeService")
public class AgvTypeServiceImpl extends BaseServiceImpl implements AgvTypeServiceI {

	
	@Autowired
	private AgvTypeDaoI agvTypeDao;

	private JqGrid jqgrid;
	private BigDecimal count = null;// 统计数目

	@Override
	public void add(AgvType agvType, SessionInfo sessionInfo) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", agvType.getName());
		if (agvTypeDao.count(
				"select count(*) from TagvType t where t.name = :name",
				params) > 0) {
			throw new Exception("AGV类型已存在！");
		} 
		TagvType t = new TagvType();
		BeanUtils.copyProperties(agvType, t,CommonUtil.getNullPropertyNames(agvType));
		agvTypeDao.save(t);
	}

	@Override
	public AgvType get(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",id);
		TagvType t = agvTypeDao.get(TagvType.class,id);
		AgvType agvType = new AgvType();
		if(t!=null){
			BeanUtils.copyProperties(t,agvType);
		}
		return agvType;
	}
	
	@Override
	public void edit(AgvType agvType, SessionInfo sessionInfo) throws Exception {
		TagvType t = agvTypeDao.get(TagvType.class,agvType.getId());
		if(t!=null){
			if("super".equals(sessionInfo)){
				BeanUtils.copyProperties(agvType, t, CommonUtil.getNullPropertyNames(agvType));
				agvTypeDao.update(t);
			}
			
		}
	}

	@Override
	public void delete(int id, SessionInfo sessionInfo) {
		if("person".equals(sessionInfo)){
			return;//普通用户不能进行删除，如允许删除请删掉此段代码
		}
		agvTypeDao.delete(agvTypeDao.get(TagvType.class, id));
	}

	@Override
	public JqGrid dataGrid(AgvType agvType, JqGrid ph,SessionInfo sessionInfo){
		JqGrid jqgrid = new JqGrid();
		String hql;
		
		hql = " from TagvType a ";
		
		Map<String, Object> params = new HashMap<String, Object>();
		String orderString = "";
		hql = addWhere(agvType, hql, params, sessionInfo);
		jqgrid.setTotalRows(agvTypeDao.count("select count(*) " + hql, params));
		hql = addOrder(jqgrid, hql);
		ArrayList l = (ArrayList) agvTypeDao.find(hql, params, jqgrid.getCurPage(),
				jqgrid.getPageSize());
		List<AgvType> ul = new ArrayList<AgvType>();
		changeModel(l, ul);
		jqgrid.setData(ul);
		if (ph!=null){
			jqgrid.setCurPage(ph.getCurPage());
		}
		jqgrid.setSuccess(true);
		
		return jqgrid;
	}
	

	private String addOrder(JqGrid jqgrid, String hql) {
		hql +=" order by a.id ";
		return hql;
	}
	
	private void changeModel(ArrayList l, List<AgvType> ul) {
		Iterator iterator1 = l.iterator();
		while (iterator1.hasNext()) {
			TagvType tagvType = (TagvType) iterator1.next();
			AgvType agvType = new AgvType();
			
			if (tagvType != null) {
				BeanUtils.copyProperties(tagvType, agvType);
			}
			ul.add(agvType);
		}
	}
	
	private String addWhere(AgvType agvType, String hql, Map<String, Object> params,SessionInfo sessionInfo) {
		if (agvType != null) {
			
			if (agvType.getId()!=null) {
				hql += " and a.id = :id";
				params.put("id",  agvType.getId());
			}
			if (agvType.getName()!=null && "".equals(agvType.getName())) {
				hql += " and a.name like :name";
				params.put("name", '%'+ agvType.getName()+'%');
			}
		}
		return hql;
	}
	

}
