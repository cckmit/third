package org.opoo.apps.dao;

import org.opoo.apps.bean.core.SysId;
import org.opoo.ndao.Dao;



public interface SysIdDao extends Dao<SysId, String> {
	void update(String id, long next);
	
	/**
	 * 
	 * @param id
	 * @param blockSize
	 * @return
	 */
	long getNextId(final String id, final int blockSize);
}
