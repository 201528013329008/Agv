package agv.dao.impl;

import org.springframework.stereotype.Repository;

import agv.dao.TaskDaoI;
import agv.model.Ttask;

/**
 * 任务信息管理类dao接口实现
 * @author
 */
@Repository
public class TaskDaoImpl extends BaseDaoImpl<Ttask> implements TaskDaoI {

}
