package cn.redflagsoft.base.dao.hibernate3;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.redflagsoft.base.bean.Msg;
import cn.redflagsoft.base.dao.MsgDao;

/**
 * @deprecated
 */
public class MsgHibernateDao extends AbstractBaseHibernateDao<Msg, Long> implements MsgDao {
	public static final Log log = LogFactory.getLog(MsgHibernateDao.class);
	
	@SuppressWarnings("unchecked")
	public List<Msg> findAcceptMessage(final Long id, final Long entityId, Byte readStatus) {
		final StringBuffer sql = new StringBuffer();
		sql.append("select ID as id,SMSGID as sendingMsgId,TP as type,TITLE as title,CONTENT as content,TOID as toId,");
		sql.append("TOTYPE as toType,TOADDR as toAddr,TONAME as toName,FR as fromId,SEND_STATUS as sendStatus,");
		sql.append("SEND_TIME as sendTime,READ_STATUS as readStatus,READER as reader,READ_TIME as readTime,");
		sql.append("ATTACHED as attached,CTIME as creationTime,MTIME as modificationTime from MSG where (READ_STATUS=" + readStatus + ")");
		sql.append(entityId != null ? " and ((TOID=? and TOTYPE=?) or (TOID=? and TOTYPE=?))" : " and (TOID=? and TOTYPE=?)");
		sql.append(" order by SEND_TIME desc");
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql.toString());
				query.setParameter(0, id);
				query.setParameter(1, Msg.TOTYPE_CLERK);
				if (entityId != null) {
					query.setParameter(2, entityId);
					query.setParameter(3, Msg.TOTYPE_ORG);
				}
				query.addScalar("id", Hibernate.LONG);
				query.addScalar("sendingMsgId", Hibernate.LONG);
				query.addScalar("type",Hibernate.BYTE);
				query.addScalar("title", Hibernate.STRING);
				query.addScalar("content", Hibernate.STRING);
				query.addScalar("toId", Hibernate.LONG);
				query.addScalar("toType",Hibernate.BYTE);
				query.addScalar("toAddr",Hibernate.STRING);
				query.addScalar("toName",Hibernate.STRING);
				query.addScalar("fromId",Hibernate.LONG);
				query.addScalar("sendStatus",Hibernate.BYTE);
				query.addScalar("sendTime",Hibernate.DATE);
				query.addScalar("readStatus",Hibernate.BYTE);
				query.addScalar("reader",Hibernate.LONG);
				query.addScalar("readTime",Hibernate.DATE);
				query.addScalar("attached",Hibernate.BOOLEAN);
				query.addScalar("creationTime",Hibernate.DATE);
				query.addScalar("modificationTime",Hibernate.DATE);
				query.setResultTransformer(Transformers.aliasToBean(Msg.class));
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Msg> findUnsentMessage(Long fromId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select a from Msg a where (a.sendStatus=? or a.sendStatus=?)");
		sb.append(fromId != null ? " and fromId=" + fromId : "");
		sb.append(" order by sendTime desc");
		return getHibernateTemplate().find(sb.toString(), new Object[]{Msg.SEND_STATUS_UNSENT, Msg.SEND_STATUS_FAIL});
	}

	@SuppressWarnings("unchecked")
	public List<Msg> findAcceptMessageBySendTime(Long toId, Date start, Date end) {
		String hql = "select a from Msg a where a.toId=? and a.sendTime>=? and a.sendTime<=?";
		return getHibernateTemplate().find(hql, new Object[]{toId, start, end});
	}
	
	@SuppressWarnings("unchecked")
	public List<Msg> findWaitSend(int maxTryCount) {
		String hql = "select a from Msg a where (a.type=? or a.type=?) and (a.sendStatus=? or a.sendStatus=?) and a.publishTime<=? and a.expirationTime>? and a.trySendCount<=?";
//		Date todayBegin=new Date();
//		todayBegin.setHours(0);
//		todayBegin.setMinutes(0);
//		todayBegin.setSeconds(0);
//		
//		Date todayEnd=new Date();
//		todayEnd.setHours(23);
//		todayEnd.setMinutes(59);
//		todayEnd.setSeconds(59);
		
		Date today=new Date();
		return getHibernateTemplate().find(hql, new Object[]{Msg.TYPE_SMS,Msg.TYPE_EMAIL,Msg.SEND_STATUS_UNSENT,Msg.SEND_STATUS_FAIL,today,today,maxTryCount});
	}
	
	
	//部门接收到的消息
	@SuppressWarnings("unchecked")
	public List<Msg> findEntityReceiveMsgByEntityID(ResultFilter filter,Long entityID) {
		String hql = "select a from Msg a where (a.toType=0 and a.toId in(select b.id from Clerk b where b.entityID="+entityID+")) or (a.toType=1 and a.toId="+entityID+")";
		return getHibernateTemplate().find(hql, filter);
	}
	//部门发出的消息
	@SuppressWarnings("unchecked")
	public List<Msg> findEntitySendMsgByEntityID(ResultFilter filter,Long entityID){
		String hql = "select a from Msg a where a.fromId in(select b.id from Clerk b where b.entityID="+entityID+"))";
		return getHibernateTemplate().find(hql, filter);
	}
}
