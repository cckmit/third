package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.BizMatter;
import cn.redflagsoft.base.dao.BizMatterDao;
import cn.redflagsoft.base.service.BizMatterService;

public class BizMatterServiceImpl implements BizMatterService{
	private BizMatterDao bizMatterDao;
	
	public void setBizMatterDao(BizMatterDao bizMatterDao) {
		this.bizMatterDao = bizMatterDao;
	}

	public int deleteBizMatter(BizMatter bizMatter) {
		return bizMatterDao.delete(bizMatter);
	}

	public BizMatter getBizMatter(Long sn) {
		return bizMatterDao.get(sn);
	}

	public BizMatter updateBizMatter(BizMatter bizMatter) {
		return bizMatterDao.update(bizMatter);
	}
}
