package cn.redflagsoft.base.dao.hibernate3;

import java.util.Date;
import java.util.List;

import cn.redflagsoft.base.bean.Log;
import cn.redflagsoft.base.dao.LogDao;

public class LogHibernateDao extends AbstractBaseHibernateDao<Log, Long> implements LogDao {

	@SuppressWarnings("unchecked")
	public List<Log> queryLogList(Date start, Date end) {
		String hql = "select a from Log a where a.creationTime>=? and a.creationTime<=?";
		Object[] objects;
		if (end == null) {
			objects = new Object[] { start, start };
		} else {
			objects = new Object[] { start, end };
		}
		return getHibernateTemplate().find(hql, objects);
	}
}
