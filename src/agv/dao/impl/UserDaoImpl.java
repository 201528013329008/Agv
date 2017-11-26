package agv.dao.impl;

import org.springframework.stereotype.Repository;

import agv.dao.UserDaoI;
import agv.model.Tuser;
/**
 * 管理员数据操作实现类，继承公共数据库操作实现类
 * @author China
 *
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<Tuser> implements UserDaoI {

}
