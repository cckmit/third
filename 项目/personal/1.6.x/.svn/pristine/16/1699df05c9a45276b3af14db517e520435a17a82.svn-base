package org.opoo.apps.id;

import org.opoo.apps.bean.core.SysId;
import org.opoo.apps.dao.SysIdDao;
import org.opoo.apps.dao.SysIdDaoAware;
import org.springframework.util.Assert;



/**
 * 通过DAO访问数据库中的id，生成新id。
 * 依赖 于dao的实现。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public class DaoIdGenerator 
	extends AbstractBlockIdGenerator 
	implements LongIdGenerator, SysIdDaoAware{
	private SysIdDao persistentIdDao;
	
	public DaoIdGenerator(SysIdDao dao, String name, int blockSize, int step) {
		super(name);
		persistentIdDao = dao;
		setBlockSize(blockSize);
		setStep(step);
		//afterPropertiesSet();
	}

	@Override
	public void afterPropertiesSet()  {
		super.afterPropertiesSet();
		Assert.notNull(persistentIdDao);
	}

	@Override
	protected void createSysId(SysId persistentId) {
		persistentIdDao.save(persistentId);
	}

	@Override
	protected SysId loadSysId(String name) {
		return persistentIdDao.get(name);
	}

	@Override
	protected void updateSysId(String name, long current) {
		persistentIdDao.update(name, current);
	}

	public SysIdDao getPersistentIdDao() {
		return persistentIdDao;
	}

	public void setSysIdDao(SysIdDao persistentIdDao) {
		this.persistentIdDao = persistentIdDao;
	}
}
