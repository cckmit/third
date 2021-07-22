package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.Affair;
import cn.redflagsoft.base.dao.AffairDao;
import cn.redflagsoft.base.service.AffairService;

public class AffairServiceImpl implements AffairService{
	private AffairDao affairDao;

	public void setAffairDao(AffairDao affairDao) {
		this.affairDao = affairDao;
	}

	public int deleteAffair(Affair affair) {
		return affairDao.delete(affair);
	}

	public Affair updateAffair(Affair affair) {
		return affairDao.update(affair);
	}

	public Affair getAffair(Long id) {
		return affairDao.get(id);
	}
}
