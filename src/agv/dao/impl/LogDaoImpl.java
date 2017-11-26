package agv.dao.impl;

import org.springframework.stereotype.Repository;

import agv.dao.LogDaoI;
import agv.model.Tlog;

/**
 * 日志信息管理类dao接口实现
 * @author
 */
@Repository
public class LogDaoImpl extends BaseDaoImpl<Tlog> implements LogDaoI {

}
