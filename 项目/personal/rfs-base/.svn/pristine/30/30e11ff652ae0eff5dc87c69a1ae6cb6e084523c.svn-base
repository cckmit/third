package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.dao.AdminUserDao;

public class AdminUserHibernateDao extends HibernateDaoSupport implements AdminUserDao{
	
	@SuppressWarnings("unchecked")
	public List<Clerk> findAllAdminUsers(){
		String hql = "select b from EntityGroup a,Clerk b where a.id=b.entityID and a.groupID = 1 order by a.displayOrder asc";
		return getHibernateTemplate().find(hql);
	}
}
