package org.opoo.apps.dao.hibernate3;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.opoo.apps.bean.core.SysId;
import org.opoo.apps.dao.SysIdDao;
import org.springframework.orm.hibernate3.HibernateCallback;


public class SysIdHibernateDao extends AbstractAppsHibernateDao<SysId, String> implements
		SysIdDao {

	public void update(String id, long next) {
		String qs = "update SysId set current=? where id=?";
		getQuerySupport().executeUpdate(qs, new Object[]{next, id});		
	}

	@Override
	protected void initDao() throws Exception {
		super.initDao();
	}
	
	public long getNextId(final String id, final int blockSize) {
		return ((Number) getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SysId sysId = (SysId) session.get(SysId.class, id, LockMode.UPGRADE);
				long nextId = 1L;
				if(sysId != null){
					nextId = sysId.getCurrent();
					sysId.setCurrent(nextId + blockSize);
					session.update(sysId);
				}else{
					nextId = 1L;
					sysId = new SysId();
					sysId.setCurrent(nextId + blockSize);
					sysId.setId(id);
					sysId.setStep(1);
					session.save(sysId);
				}
				session.flush();
				return nextId;
			}
		})).longValue();
	}
}
