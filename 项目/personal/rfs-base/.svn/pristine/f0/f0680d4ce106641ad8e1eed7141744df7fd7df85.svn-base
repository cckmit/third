package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.SelectDataSource;
import cn.redflagsoft.base.dao.SelectDataSourceDao;

public class SelectDataSourceHibernateDao extends AbstractBaseHibernateDao<SelectDataSource,Long>implements SelectDataSourceDao {
	
	public SelectDataSource getBySelectId(String selectId) {
		String hql = "select s from SelectDataSource s where s.selectId = ?";
		List<?> list = getHibernateTemplate().find(hql, selectId);
		if (!list.isEmpty() && list.size() > 0) {
			return (SelectDataSource) list.iterator().next();
		}
		return null;
	}

}
