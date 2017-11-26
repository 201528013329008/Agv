package agv.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import agv.dao.LogDaoI;
import agv.model.Tlog;
import agv.pageModel.JqGrid;
import agv.pageModel.Log;
import agv.pageModel.SessionInfo;
import agv.pageModel.User;
import agv.service.LogServiceI;
import agv.service.UserServiceI;

@Service("logService")
public class LogServiceImpl implements LogServiceI {
	
	private Logger userLogger;
	private Logger systemLogger;
	
	@Autowired
	private LogDaoI logDao;
	
	@Autowired
	private UserServiceI userService;
	
	private static LogServiceImpl myLogger;
	
	public static LogServiceImpl getLogger(){
		if (myLogger==null){
			myLogger = new LogServiceImpl(userService);
		}
		return myLogger;
	}
	
	private LogServiceImpl(UserServiceI userService) {
		this.userService = userService;
		systemLogger = Logger.getLogger("agv");
		userLogger = Logger.getLogger("agv.user");
	}

	@Override
	public void debug(String msg) {
		systemLogger.debug(msg);
	}

	@Override
	public void debug(String msg, LogScope scope) {
		switch (scope) {
		case SYSTEM:
			systemLogger.debug(msg);
			break;
		case USER:
			userLogger.debug(msg);
			break;
		default:
			break;
		}
	}

	@Override
	public void info(String msg) {
		systemLogger.info(msg);
	}

	@Override
	public void info(String msg, LogScope scope) {
		switch (scope) {
		case SYSTEM:
			systemLogger.info(msg);
			break;
		case USER:
			userLogger.info(msg);
			break;
		default:
			break;
		}
	}

	@Override
	public void warn(String msg) {
		systemLogger.warn(msg);
	}

	@Override
	public void warn(String msg, LogScope scope) {
		switch (scope) {
		case SYSTEM:
			systemLogger.warn(msg);
			break;
		case USER:
			userLogger.warn(msg);
			break;
		default:
			break;
		}
	}

	@Override
	public void error(String msg) {
		systemLogger.error(msg);
	}

	@Override
	public void error(String msg, LogScope scope) {
		switch (scope) {
		case SYSTEM:
			systemLogger.error(msg);
			break;
		case USER:
			userLogger.error(msg);
			break;
		default:
			break;
		}
	}

	@Override
	public void fatal(String msg) {
		systemLogger.fatal(msg);
	}

	@Override
	public void fatal(String msg, LogScope scope) {
		switch (scope) {
		case SYSTEM:
			systemLogger.fatal(msg);
			break;
		case USER:
			userLogger.fatal(msg);
			break;
		default:
			break;
		}
	}

	@Override
	public void trace(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(String msg, LogScope scope) {
		systemLogger.trace(msg);
	}

	@Override
	public JqGrid dataGrid(JqGrid ph, SessionInfo sessionInfo) {
		JqGrid jqgrid = new JqGrid();
		String hql;
		int userId;
		
		hql = " from Tlog a order by a.createDate desc";
		Map<String, Object> params = new HashMap<String, Object>();
		jqgrid.setTotalRows(logDao.count("select count(*) " + hql, params));
		ArrayList l = (ArrayList) logDao.find(hql, params, ph.getCurPage(),
				ph.getPageSize());
		List<Log> ul = new ArrayList<Log>();
		changeModel(l, ul);
		jqgrid.setData(ul);
		jqgrid.setCurPage(ph.getCurPage());
		jqgrid.setSuccess(true);
		
		return jqgrid;
	}
	
	private void changeModel(ArrayList l, List<Log> ul) {
		Iterator iterator1 = l.iterator();
		while (iterator1.hasNext()) {
			Tlog t = (Tlog) iterator1.next();
			
			Log log = new Log();
			
			if (t != null) {
				BeanUtils.copyProperties(t, log);
			}
			
			if (t.getUserId()!=null && t.getUserId()>0){
				User user = userService.get(t.getUserId());
				log.setUser(user);
			}
			
			ul.add(log);
		}
	}
	

}
