package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.BizAffair;
import cn.redflagsoft.base.dao.BizAffairDao;
import cn.redflagsoft.base.service.BizAffairService;

public class BizAffairServiceImpl implements BizAffairService{
	private BizAffairDao bizAffairDao;

	public void setBizAffairDao(BizAffairDao bizAffairDao) {
		this.bizAffairDao = bizAffairDao;
	}

	public int deleteBizAffair(BizAffair bizAffair) {
		return bizAffairDao.delete(bizAffair);
	}

	public BizAffair updateBizAffair(BizAffair bizAffair) {
		return bizAffairDao.update(bizAffair);
	}

	public BizAffair getBizAffair(Long sn) {
		return bizAffairDao.get(sn);
	}
}
