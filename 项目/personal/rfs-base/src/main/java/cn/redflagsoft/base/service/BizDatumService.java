package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.BizDatum;

public interface BizDatumService {
	BizDatum getBizDatum(Long sn);
	
	int deleteBizDatum(BizDatum bizDatum);
	
	BizDatum updateBizDatum(BizDatum bizDatum);
}
