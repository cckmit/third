package org.opoo.apps.id.sequence;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateSequence extends HibernateSysIdAccessor implements Sequence {
	//private static final Log log = LogFactory.getLog(HibernateSequence.class);
	
	// configuration
	private int blockSize = 5;

	// runtime state  
	private long maxId = -2;
	private long currId = -1;
	
	HibernateSequence(HibernateTemplate hibernateTemplate, String name){
		super(hibernateTemplate, name);
		if(log.isDebugEnabled()){
			log.debug("Building HibernateSequence '" + name + "'");
		}
	}
	
	HibernateSequence(HibernateTemplate hibernateTemplate, String name, int blockSize, int maxAttempts){
		super(hibernateTemplate, name, maxAttempts);
		this.blockSize = blockSize;
		if(log.isDebugEnabled()){
			log.debug("Building HibernateSequence '" + name + "'");
		}
	}
	
	public Long getCurrent() {
		return currId;
	}

	public synchronized Long getNext() {
		// if no more ids available
		if (maxId < currId) {
			// acquire a next block of ids

			log.debug("maxId id " + maxId + " was consumed.  acquiring new block...");

			// reset the id block
			maxId = -2;
			currId = -1;

			// try couple of times
			try {
				currId = getCurrentId(blockSize);
				maxId = currId + blockSize - 1;
			} catch (Exception e) {
				throw new RuntimeException("couldn't acquire block of ids", e);
			}
		}

	    return currId++;
	}

//	private void acquireIdBlock() {
//		for (int attempts = maxAttempts; (attempts > 0) && (currId == -1); attempts--) {
//			try {
//				// acquire block
//				currId = getCurrentId();
//				maxId = currId + blockSize - 1;
//
//				log.debug("acquired new id block [" + currId + "-" + maxId + "]");
//
//			} catch (StaleStateException e) {
//				// optimistic locking exception indicating another thread tried
//				// to
//				// acquire a block of ids concurrently
//				attempts--;
//
//				// if no attempts left
//				if (attempts == 0) {
//					// fail the surrounding transaction
//					throw new RuntimeException("couldn't acquire block of ids, tried " + maxAttempts + " times");
//				}
//
//				// if there are still attempts left, first wait a bit
//				int millis = 20 + random.nextInt(200);
//				log.debug("optimistic locking failure while trying to acquire id block.  retrying in " + millis
//						+ " millis");
//				try {
//					Thread.sleep(millis);
//				} catch (InterruptedException e1) {
//					log.debug("waiting after id block locking failure got interrupted");
//				}
//			}
//		}
//	}
//	
//	private long getCurrentId(){
//		return ((Number) getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
//			public Object doInHibernate(Session session) throws HibernateException, SQLException {
//				SysId sysId = (SysId) session.get(SysId.class, name, LockMode.UPGRADE);
//				long currentId = 1L;
//				if(sysId != null){
//					currentId = sysId.getCurrent();
//					sysId.setCurrent(currentId + blockSize);
//					session.update(sysId);
//				}else{
//					currentId = 1L;
//					sysId = new SysId();
//					sysId.setCurrent(currentId + blockSize);
//					sysId.setId(name);
//					sysId.setStep(1);
//					session.save(sysId);
//				}
//				session.flush();
//				return currentId;
//			}
//		})).longValue();
//	}
//
//	protected HibernateTemplate getHibernateTemplate() {
//		return hibernateTemplate;
//	}
}
