package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.BizAffair;

public interface BizAffairService {
	BizAffair getBizAffair(Long sn);
	
	int deleteBizAffair(BizAffair bizAffair);
	
	BizAffair updateBizAffair(BizAffair bizAffair);
}
