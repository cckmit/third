package org.opoo.apps.conversion.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.opoo.apps.conversion.ConversionArtifactType;
import org.opoo.apps.conversion.dao.ConversionDao;
import org.opoo.apps.conversion.model.ConversionArtifactImpl;
import org.opoo.apps.conversion.model.ConversionErrorStepImpl;
import org.opoo.apps.conversion.model.ConversionRevisionImpl;
import org.opoo.ndao.hibernate3.HibernateDaoSupport;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.orm.hibernate3.HibernateCallback;

public class ConversionDaoImpl extends HibernateDaoSupport implements ConversionDao {
	
	public ConversionRevisionImpl getRevision(long revisionId) {
		return (ConversionRevisionImpl) getHibernateTemplate().get(ConversionRevisionImpl.class, revisionId);
	}

	public ConversionRevisionImpl saveRevision(ConversionRevisionImpl rev) {
		getHibernateTemplate().save(rev);
		return rev;
	}

	public ConversionRevisionImpl getRevision(final int objectType, final long objectId, final int revisionNumber) {
		final String qs = "from ConversionRevisionImpl a where a.objectType=? and a.objectId=? order by a.revisionNumber desc";
		List<?> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery(qs).setInteger(0, objectType).setLong(1, objectId).setFirstResult(0)
				.setMaxResults(1).list();
			}
		});
		if(list != null && !list.isEmpty()){
			return (ConversionRevisionImpl) list.iterator().next();
		}
		return null;
	}

	public ConversionRevisionImpl updateRevision(ConversionRevisionImpl revImpl) {
		getHibernateTemplate().update(revImpl);
		return revImpl;
	}

	public ConversionArtifactImpl getArtifact(final long revisionId, final ConversionArtifactType type, final int page) {
		final String qs = "from ConversionArtifactImpl a where a.revisionId=? and a.stringType=? and a.page=?";
		List<?> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
				return session.createQuery(qs).setLong(0, revisionId).setString(1, type.getName())
					.setInteger(2, page).list();
			}
		});
		
		if(list != null && !list.isEmpty()){
			ConversionArtifactImpl a = (ConversionArtifactImpl) list.iterator().next();
			if(a != null){
				//ÖØÉè
				a.setType(type);
				return a;
			}
		}
		return null;
	}

	public ConversionArtifactImpl saveArtifact(ConversionArtifactImpl a) {
//		ConversionArtifactImpl artifact = getConversionArtifact(a.getRevisionId(), a.getType(), a.getPage());
//		if(artifact != null){
//			getHibernateTemplate().delete(artifact);
//		}
		getHibernateTemplate().save(a);
		return a;
	}

	public int getArtifactCount(final long revisionId, final ConversionArtifactType type) {
		final String qs = "select count(*) from ConversionArtifactImpl where revisionId=? and stringType=?";
		Object result = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
				return session.createQuery(qs).setLong(0, revisionId).setString(1, type.getName()).uniqueResult();
			}
		});
		return ((Number)result).intValue();
	}

	public int removeErrorStepsByRevisionId(final long revisionId) {
		final String qs = "delete from ConversionErrorStepImpl where revisionId=?";
		return getQuerySupport().executeUpdate(qs, revisionId);
//		return ((Number)getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
//				return session.createQuery(qs).setLong(0, revisionId).executeUpdate();
//			}
//		})).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<ConversionErrorStepImpl> findErrorStepsByRevisionId(long revisionId) {
		String qs = "from ConversionErrorStepImpl where revisionId=?";
		return getHibernateTemplate().find(qs, new Object[]{revisionId});
	}

	public ConversionErrorStepImpl saveErrorStep(ConversionErrorStepImpl step) {
		getHibernateTemplate().save(step);
		return step;
	}

//	public int removeConversionErrorStepsByRevisionId(final long revisionId) {
//		final String qs = "delete from ConversionErrorStepImpl where revisionId=?";
//		return ((Number)getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
//				return session.createQuery(qs).setLong(0, revisionId).executeUpdate();
//			}
//		})).intValue();
//	}

	public int removeArtifactsByRevisionId(final long revisionId) {
		final String qs = "delete from ConversionErrorStepImpl where revisionId=?";
		return getQuerySupport().executeUpdate(qs, revisionId);
//		return ((Number)getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
//				return session.createQuery(qs).setLong(0, revisionId).executeUpdate();
//			}
//		})).intValue();
	}

	public int removeRevision(final long revisionId) {
		final String qs = "delete from ConversionRevisionImpl where revisionId=?";
		return getQuerySupport().executeUpdate(qs, revisionId);
//		return ((Number)getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
//				return session.createQuery(qs).setLong(0, revisionId).executeUpdate();
//			}
//		})).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<ConversionArtifactImpl> findArtifactsByRevisionId(long revisionId) {
		String qs = "from ConversionArtifactImpl c where c.revisionId=?";
		return getHibernateTemplate().find(qs, new Object[]{revisionId});
	}

	public int getRevisionCount() {
		String qs = "select count(distinct r.revisionId) from ConversionRevisionImpl r";
		@SuppressWarnings("unchecked")
		List<Number> list = getHibernateTemplate().find(qs);
		if(list != null && !list.isEmpty()){
			return list.iterator().next().intValue();
		}
		return 0;
	}

	public int getErrorRevisionCount() {
		//   private static final String SELECT_ERROR_REVISION_COUNT_SQL = "
		//SELECT count(distinct r.revisionID) FROM jiveDVRevision r, 
		//jiveDVRevStatus s where r.revisionID = s.revisionID and s.message is not null";
		String qs = "select count(distinct r.revisionId) from ConversionRevisionImpl r," +
				" ConversionErrorStepImpl s where r.revisionId=s.revisionId and s.message is not null";
		
		@SuppressWarnings("unchecked")
		List<Number> list = getHibernateTemplate().find(qs);
		if(list != null && !list.isEmpty()){
			return list.iterator().next().intValue();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List<Long> findErrorRevisionIds(ResultFilter filter) {
//		  "SELECT distinct r.revisionID, r.documentClassID, r.fileName, r.fileSize, r.pageCount, r.origin, r.creationDate, MAX(s.message) as message "
//        + "FROM jiveDVRevision r LEFT OUTER JOIN jiveDVRevStatus s ON (r.revisionID = s.revisionID) where message is not null "
//        + "GROUP BY r.revisionID, r.documentClassID, r.fileName, r.fileSize, r.pageCount, r.origin, r.creationDate, s.message ";
		String qs = "select distinct r.revisionId from ConversionRevisionImpl r," +
				" ConversionErrorStepImpl s where r.revisionId=s.revisionId and s.message is not null";
		return getQuerySupport().find(qs, filter);
	}
}
