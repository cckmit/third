package org.opoo.apps.dao;

import java.util.Random;

import org.hibernate.StaleStateException;
import org.opoo.apps.test.SpringTests;

public class SysIdTest extends SpringTests {

	protected SysIdDao sysIdDao;
	
	static Random random = new Random();
	
	private long nextId = -1L;
	private long maxId = -2L;
	
	private int blockSize = 10;
	private int maxAttempts = 3;
	
	public synchronized long getNextId(){
		if(maxId < nextId){
			nextId = -1L;
			maxId = -2L;
			acquireIdBlock();
		}
		return nextId++;
	}
	
	
	private void acquireIdBlock() {
		// int blockSize = 10;
		// nextId = sysIdDao.getNextId("myTestId", blockSize);
		// maxId = nextId + blockSize - 1;
		//		
		for (int attempts = maxAttempts; (attempts > 0) && (nextId == -1); attempts--) {
			try {
				// acquire block
				nextId = sysIdDao.getNextId("myTestId", blockSize);
				maxId = nextId + blockSize - 1;

				System.out.println("acquired new id block [" + nextId + "-" + maxId + "]");

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
				System.out.println("optimistic locking failure while trying to acquire id block.  retrying in "
						+ millis + " millis");
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e1) {
					System.out.println("waiting after id block locking failure got interrupted");
				}
			}
		}
	}


	public void testIdGenerate(){
		super.setComplete();
//		int blockSize = 10;
//		long nextId = sysIdDao.getNextId("myTestId", blockSize);
//		System.out.println(nextId);
//		for(long i = nextId, n = nextId + blockSize; i < n; i++){
//			System.out.println(i);
//		}
		
		for(int i = 0 ; i < 100 ; i++){
			System.out.println(getNextId());
		}
	}

}
