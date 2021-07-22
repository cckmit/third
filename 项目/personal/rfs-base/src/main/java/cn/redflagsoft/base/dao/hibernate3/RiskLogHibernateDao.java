package cn.redflagsoft.base.dao.hibernate3;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.id.IdGeneratorProvider;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.RiskLog;
import cn.redflagsoft.base.dao.RiskLogDao;
import cn.redflagsoft.base.event.RiskLogEvent;
import cn.redflagsoft.base.event2.ObjectNameChangeListener;
import cn.redflagsoft.base.event2.ObjectSnChangeListener;

public class RiskLogHibernateDao extends AbstractBaseHibernateDao<RiskLog,Long> 
		implements RiskLogDao,ObjectNameChangeListener,ObjectSnChangeListener{
	private static final Log log = LogFactory.getLog(RiskLogHibernateDao.class);
	
	@Override
	public void setIdGeneratorProvider(IdGeneratorProvider<Long> idGeneratorProvider) {
		//super.setIdGeneratorProvider(idGeneratorProvider);
		setIdGenerator(idGeneratorProvider.getIdGenerator("riskLog"));
	}
	
	public List<RiskLog> findGradeChanged(Date mStart,Date mEnd,byte grade,List<Long> dutyClerkIDS) {
		String qs = "select a from RiskLog a, RFSObject b where a.refObjectId=b.id";
		Criterion c =Restrictions.logic(Restrictions.in("b.dutyClerkID", dutyClerkIDS))
		.and(Restrictions.eq("a.busiType", RiskLogEvent.RISK_GRADE_CHANGE))
		.and(Restrictions.eq("a.grade", grade))
		.and(Restrictions.ge("a.modificationTime", mStart))
		.and(Restrictions.le("a.modificationTime", mEnd));
		
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(c);
		rf.setOrder(Order.asc("a.refType"));
		return getQuerySupport().find(qs, rf);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.ObjectNameChangeListener#objectNameChange(cn.redflagsoft.base.bean.RFSObjectable, java.lang.String, java.lang.String)
	 */
	public void objectNameChange(RFSObjectable object, String oldValue,	String newValue) {
		log.debug("对象名称发生了变化，同步更新RiskLog：" + oldValue + " --> " + newValue);
		
		String sql = "update RISK_LOG set PROJECT_NAME=? where PROJECT_ID=? and REF_OBJECT_TYPE=1002";
		int rows = executeSQLUpdate(sql, newValue, object.getId());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("清理了 RiskLog 缓存");
		}
		log.debug("共更新RiskLog 的相关对象名称 ‘" + rows + "’个");
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.ObjectNameChangeListener#objectNameChange(cn.redflagsoft.base.bean.RFSObjectable, java.lang.String, java.lang.String)
	 */
	public void objectSnChange(RFSObjectable object, String oldValue,String newValue) {
		log.debug("对象名称发生了变化，同步更新RiskLog：" + oldValue + " --> " + newValue);
		
		String sql = "update RISK_LOG set PROJECT_SN=? where PROJECT_ID=? and REF_OBJECT_TYPE=1002";
		int rows = executeSQLUpdate(sql, newValue, object.getId());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("清理了 RiskLog 缓存");
		}
		log.debug("共更新RiskLog 的相关对象名称 ‘" + rows + "’个");
	}

}
