package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.bean.RiskMessage;
import cn.redflagsoft.base.dao.RiskMessageDao;

public class RiskMessageHibernateDao extends AbstractBaseHibernateDao<RiskMessage, Long> implements RiskMessageDao {

	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findAcceptSMSPersons(Byte grade) {
		String hql = "select new map(a.clerkId as clerkId,b.name as name) from RiskMessage a,Clerk b where a.clerkId=b.id and a.type=?";
		return getHibernateTemplate().find(hql, grade.shortValue());
	}
}
