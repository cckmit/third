package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.dao.RiskRuleDao;


public class RiskRuleHibernateDao extends AbstractBaseHibernateDao<RiskRule, Long> implements RiskRuleDao {

	public List<RiskRule> findRiskRules(int objectType) {
		//String hql = "select a from RiskRule a where a.objectType=? and a.status=? and a.tag=?";
		//return getHibernateTemplate().find(hql, new Object[]{objectType, (byte)1, (short)0});
		
		Logic logic = Restrictions.logic(Restrictions.eq("objectType", objectType))
			.and(Restrictions.eq("status", RiskRule.STATUS_ON_USE))
			.and(Restrictions.eq("tag", RiskRule.TAG_AUTO));
		return find(new ResultFilter(logic, Order.asc("id")));
	}

	public List<RiskRule> findRiskRules(int objectType, String objectAttr, int refType) {
		//String hql = "select a from RiskRule a where a.objectType=? and objectAttr=? and refType=? and a.status=? and a.tag=?";
		//return getHibernateTemplate().find(hql, new Object[]{objectType, objectAttr, refType, (byte)1, (short)2});
	
		Logic logic = Restrictions.logic(Restrictions.eq("objectType", objectType))
				.and(Restrictions.eq("objectAttr", objectAttr))
				.and(Restrictions.eq("refType", refType))
				.and(Restrictions.eq("status", RiskRule.STATUS_ON_USE))
				.and(Restrictions.eq("tag", RiskRule.TAG_HALF_OTHER));
		return find(new ResultFilter(logic, Order.asc("id")));
	}
	
	public List<RiskRule> findRiskRules(int objectType, int refType) {
		//String hql = "select a from RiskRule a where a.objectType=? and a.refType=? and a.status=? and a.tag=?";
		//return getHibernateTemplate().find(hql, new Object[]{objectType, refType, (byte)1, (short)1});
		
		Logic logic = Restrictions.logic(Restrictions.eq("objectType", objectType))
				.and(Restrictions.eq("refType", refType))
				.and(Restrictions.eq("status", RiskRule.STATUS_ON_USE))
				.and(Restrictions.eq("tag", RiskRule.TAG_HALF_AUTO));
		return find(new ResultFilter(logic, Order.asc("id")));
	}
	
	public List<RiskRule> findRiskRules(int objectType, String objectAttr) {
		//String hql = "select a from RiskRule a where a.objectType=? and objectAttr=? and a.status=?";
		//return getHibernateTemplate().find(hql, new Object[]{objectType, objectAttr, (byte)1});
		
		Logic logic = Restrictions.logic(Restrictions.eq("objectType", objectType))
				.and(Restrictions.eq("objectAttr", objectAttr))
				.and(Restrictions.eq("status", RiskRule.STATUS_ON_USE));
		return find(new ResultFilter(logic, Order.asc("id")));
	}
}
