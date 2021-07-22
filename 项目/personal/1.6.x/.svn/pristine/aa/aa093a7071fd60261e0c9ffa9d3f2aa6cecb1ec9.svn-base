package org.opoo.apps.id.sequence;

import java.sql.SQLException;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.StaleStateException;
import org.opoo.apps.bean.core.SysId;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

public class HibernateSysIdAccessor {
	protected final Log log = LogFactory.getLog(getClass()); 
	private static Random random = new Random();
	
	private final HibernateTemplate hibernateTemplate;
	private final String name;
	
	//configuration
	private int maxAttempts = 3;
	
	HibernateSysIdAccessor(HibernateTemplate hibernateTemplate, String name){
		Assert.notNull(hibernateTemplate, "hibernateTemplate is required.");
		Assert.notNull(name, "sequence name is required.");
		this.hibernateTemplate = hibernateTemplate;
		this.name = name;
	}
	HibernateSysIdAccessor(HibernateTemplate hibernateTemplate, String name, int maxAttempts){
		this(hibernateTemplate, name);
		this.maxAttempts = maxAttempts;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * 获取数据库中当前ID的值，并更新数据库。
	 * 
	 * @param blockSize
	 * @return
	 */
	public long getCurrentId(int blockSize) {
		long currentId = -1L;
		for (int attempts = maxAttempts; (attempts > 0) && (currentId == -1); attempts--) {
			try {
				// acquire block
				currentId = getCurrentIdInternal(blockSize);

				if(log.isDebugEnabled() && blockSize > 1){
					long maxId = currentId + blockSize - 1;
					log.debug("acquired new id block [" + currentId + "-" + maxId + "]");
				}
			} catch (StaleStateException e) {
				// optimistic locking exception indicating another thread tried
				// to
				// acquire a block of ids concurrently
				attempts--;

				// if no attempts left
				if (attempts == 0) {
					// fail the surrounding transaction
					throw new RuntimeException("couldn't acquire block of ids, tried " + maxAttempts + " times");
				}

				// if there are still attempts left, first wait a bit
				int millis = 20 + random.nextInt(200);
				log.debug("optimistic locking failure while trying to acquire id block.  retrying in " + millis
						+ " millis");
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e1) {
					log.debug("waiting after id block locking failure got interrupted");
				}
			}
		}
		return currentId;
	}
	
	/**
	 * 获取当前ID，并将数据库中当前ID向前移动blockSize指定大小。
	 * 
	 * @param blockSize
	 * @return
	 */
	private long getCurrentIdInternal(final int blockSize){
		return ((Number) getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SysId sysId = (SysId) session.get(SysId.class, name, LockMode.UPGRADE);
				long currentId = 1L;
				if(sysId != null){
					currentId = sysId.getCurrent();
					sysId.setCurrent(currentId + blockSize);
					session.update(sysId);
				}else{
					currentId = 1L;
					sysId = new SysId();
					sysId.setCurrent(currentId + blockSize);
					sysId.setId(name);
					sysId.setStep(1);
					session.save(sysId);
				}
				session.flush();
				return currentId;
			}
		})).longValue();
	}
}
