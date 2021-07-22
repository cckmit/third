package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardCheck;

public interface SignCardCheckService {
	SignCardCheck createSignCardCheck(SignCardCheck signCardCheck);

	SignCardCheck getSignCardCheck(Long signCardId);

	SignCardCheck updateSignCardCheck(SignCard signCard,
			SignCardCheck signCardCheck);
}
