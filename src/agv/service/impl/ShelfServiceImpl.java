package agv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import agv.dao.ShelfDaoI;
import agv.model.Tshelf;
import agv.model.Tuser;
import agv.pageModel.Shelf;
import agv.pageModel.JqGrid;
import agv.pageModel.SessionInfo;
import agv.pageModel.User;
import agv.service.ShelfServiceI;
import agv.util.CommonUtil;

/**
 * 车辆信息管理类service实现
 * @author
 * @date 2015-12-21 17:17:56
 * @version 1.0
 */
@Service("shelfService")
public class ShelfServiceImpl extends BaseServiceImpl implements ShelfServiceI {

	@Autowired
	private ShelfDaoI shelfDao;

	private JqGrid jqgrid;
	private BigDecimal count = null;// 统计数目

	@Override
	public void add(Shelf shelf, SessionInfo sessionInfo) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sn", shelf.getSn());
		if (shelfDao.count(
				"select count(*) from Tshelf t where t.sn = :sn",
				params) > 0) {
			throw new Exception("货架编号已存在！");
		} 
		Tshelf t = new Tshelf();
		BeanUtils.copyProperties(shelf, t, CommonUtil.getNullPropertyNames(shelf));
		t.setCreateUserid(sessionInfo.getUser().getUserId());
		t.setCreateDate(new Date());
		shelfDao.save(t);
	}

	@Override
	public Shelf get(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",id);
		Tshelf t = shelfDao.get(Tshelf.class,id);
		Shelf shelf = new Shelf();
		if(t!=null){
			BeanUtils.copyProperties(t,shelf);
		}
		return shelf;
	}
	
	@Override
	public void edit(Shelf shelf, SessionInfo sessionInfo) throws Exception {
		Tshelf t = shelfDao.get(Tshelf.class,shelf.getId());
		if(t!=null){
			//if("super".equals(sessionInfo) || ("person".equals(sessionInfo) && t.getCreateUserid()==sessionInfo.getUser().getUserId())){
				BeanUtils.copyProperties(shelf, t, CommonUtil.getNullPropertyNames(shelf));
				shelfDao.update(t);
			//}
			
		}
	}

	@Override
	public void delete(int id, SessionInfo sessionInfo) {
		if("person".equals(sessionInfo)){
			return;//普通用户不能进行删除，如允许删除请删掉此段代码
		}
		shelfDao.delete(shelfDao.get(Tshelf.class, id));
	}
	
	@Override
	public Shelf get(String sn) {
		String hql;
		hql = " from Tshelf a where a.sn = :sn ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sn",sn);
		Tshelf t = shelfDao.get(hql, params);
		Shelf shelf = new Shelf();
		if(t!=null){
			BeanUtils.copyProperties(t,shelf);
		}
		return shelf;
	}

	@Override
	public void delete(String sn, SessionInfo sessionInfo) {
		if("person".equals(sessionInfo)){
			return;//普通用户不能进行删除，如允许删除请删掉此段代码
		}
		String hql;
		hql = " from Tshelf a where a.sn = :sn ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sn",sn);
		shelfDao.delete(shelfDao.get(hql, params));
	}
	
	

	@Override
	public JqGrid dataGrid(Shelf shelf, JqGrid ph,SessionInfo sessionInfo){
		JqGrid jqgrid = new JqGrid();
		String hql;
		int userId;
		hql = " from Tshelf a, Tuser b where a.createUserid = b.userId ";
		
		Map<String, Object> params = new HashMap<String, Object>();
		String orderString = "";
		hql = addWhere(shelf, hql, params, sessionInfo);
		jqgrid.setTotalRows(shelfDao.count("select count(*) " + hql, params));
		hql = addOrder(jqgrid, hql);
		ArrayList l = (ArrayList) shelfDao.find(hql, params, jqgrid.getCurPage(),
				jqgrid.getPageSize());
		List<Shelf> ul = new ArrayList<Shelf>();
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
	
	private void changeModel(ArrayList l, List<Shelf> ul) {
		Iterator iterator1 = l.iterator();
		while (iterator1.hasNext()) {
			Object[] obj = (Object[]) iterator1.next();
			Tshelf t = (Tshelf) obj[0];
			Tuser tu = (Tuser)obj[1];
			
			User u = new User();
			Shelf shelf = new Shelf();
			
			if (t != null) {
				BeanUtils.copyProperties(t, shelf);
			}
			
			if(tu!=null){
				BeanUtils.copyProperties(tu, u, new String[]{"userPassword"});
				shelf.setUser(u);
			}
			
			ul.add(shelf);
		}
	}
	
	
	private String addWhere(Shelf shelf, String hql, Map<String, Object> params,SessionInfo sessionInfo) {
		hql += " and a.id > 0 ";
		if (shelf != null) {
			String usertype = sessionInfo.getUser().getUserType();
			
			if (shelf.getId()!=null && shelf.getId() != 0) {
				hql += " and a.id = :id";
				params.put("id",  shelf.getId());
			}
			if (shelf.getSn()!=null && "".equals(shelf.getSn())) {
				hql += " and a.sn like :sn";
				params.put("sn", '%'+ shelf.getSn()+'%');
			}
			
		}
		return hql;
	}

	@Override
	public ArrayList<Shelf> getAll() {
		String hql;
		int userId;
		hql = "from Tshelf a, Tuser b where a.createUserid = b.userId ";
		ArrayList l = (ArrayList) shelfDao.find(hql);
		ArrayList<Shelf> ul = new ArrayList<Shelf>();
		changeModel(l, ul);
		return ul;
	}

	

}
