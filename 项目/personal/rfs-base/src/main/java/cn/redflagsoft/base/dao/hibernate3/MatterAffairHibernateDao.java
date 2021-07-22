package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.dao.MatterAffairDao;

public class MatterAffairHibernateDao extends AbstractBaseHibernateDao<MatterAffair, Long>
		implements MatterAffairDao {
	public static final byte STATUS = 1;

//	public List<Long> findAffairs(Long... matterIds) {
//		if(matterIds == null || matterIds.length == 0) return null;
//		if(matterIds.length == 1) return getHibernateTemplate().find("select affairID from MatterAffair where status=1 and bizID = ?",matterIds[0]);
//		Criterion c = Restrictions.in("matterID", matterIds);
//		ResultFilter rf = ResultFilter.createEmptyResultFilter();
//		rf.setCriterion(c);
//		return getQuerySupport().find("select affairID from MatterAffair where status=1", rf);
//	
//	}
	
	public List<MatterAffair> findAffairs(byte category,Long  bizId,byte bizAction) {
		return findAffairs(category,bizId,bizAction,(short)0);	
	}
	public List<MatterAffair> findAffairs(byte category,Long  bizId,byte bizAction,short tag) {
		Criterion c = Restrictions.logic(Restrictions.eq("bizID", bizId)).and(Restrictions.eq("category",category )).and(Restrictions.eq("status", STATUS)).and(Restrictions.eq("bizAction", bizAction)).and(Restrictions.eq("tag", tag));
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(c);
		rf.setOrder(Order.asc("displayOrder"));
		return find(rf);
	
	}
//
//	public List<Long> findMatters(Long affairID) {
//		return getHibernateTemplate().find("select bizID from MatterAffair where status=1 and affairID=?",affairID);		
//	}
//
//	public Byte getAffairAction(Long affairID, Long matterID) {
//		 Criterion c = Restrictions.logic(Restrictions.eq("affairID", affairID))
//	     .and(Restrictions.eq("matterID", matterID)).and(
//		Restrictions.eq("status", STATUS));
//		 MatterAffair ma=get(c);
//		if(ma==null){
//			return null;	
//		}
//		return ma.getAction();
//	}
//	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.MatterAffairDao#findAffairs(byte, java.lang.Long, short, java.lang.Byte, short)
	 */
	@SuppressWarnings("unchecked")
	public List<MatterAffair> findAffairs(byte bizCategory, Long bizId,	int bizType, Byte bizAction, short tag) {
		String qs = "from MatterAffair where category=? and bizID=? and bizType=? and bizAction=? and tag=? and status=? order by displayOrder asc";
		return getHibernateTemplate().find(qs, new Object[]{bizCategory, bizId, bizType, bizAction, tag, STATUS});
	}
	
}
