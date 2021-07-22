package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.BizDatum;
import cn.redflagsoft.base.dao.BizDatumDao;
import cn.redflagsoft.base.service.BizDatumService;

public class BizDatumServiceImpl implements BizDatumService{
	private BizDatumDao bizDatumDao;

	public void setBizDatumDao(BizDatumDao bizDatumDao) {
		this.bizDatumDao = bizDatumDao;
	}

	public int deleteBizDatum(BizDatum bizDatum) {
		return bizDatumDao.delete(bizDatum);
	}

	public BizDatum getBizDatum(Long sn) {
		return bizDatumDao.get(sn);
	}

	public BizDatum updateBizDatum(BizDatum bizDatum) {
		return bizDatumDao.update(bizDatum);
	}
}
