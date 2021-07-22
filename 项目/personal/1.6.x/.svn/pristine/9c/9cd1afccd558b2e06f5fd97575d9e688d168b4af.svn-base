package org.opoo.apps.id;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.dao.SysIdDao;
import org.opoo.apps.dao.SysIdDaoAware;


/**
 * 通过Dao访问数据的 ID 生成器的提供者。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public class DaoIdGeneratorProvider extends AbstractCachedIdGeneratorProvider
	implements SysIdDaoAware, IdGeneratorProvider<Long>{
	private static final Log log = LogFactory.getLog(DaoIdGeneratorProvider.class);
	
	private int blockSize = AbstractBlockIdGenerator.DEFAULT_BLOCK_SIZE;
	private int step = AbstractBlockIdGenerator.DEFAULT_STEP;
	private SysIdDao persistentIdDao;
		
	public DaoIdGeneratorProvider(){
	}
	public DaoIdGeneratorProvider(SysIdDao persistentIdDao){
		this.persistentIdDao = persistentIdDao;
	}
	
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public void setStep(int step) {
		this.step = step;
	}

	@Override
	protected <K extends Serializable> IdGenerator<Long> createIdGenerator(K key) {
		String s = String.valueOf(key);
		log.debug("create idGenerator for: " + key);
		return new DaoIdGenerator(persistentIdDao, s, blockSize, step);
	}

	@Override
	protected <K extends Serializable> boolean supportsKey(K key) {
		return key instanceof String || key instanceof Long || key.getClass().equals(long.class);
	}

	public void setSysIdDao(SysIdDao persistentIdDao) {
		this.persistentIdDao = persistentIdDao;
	}
}
