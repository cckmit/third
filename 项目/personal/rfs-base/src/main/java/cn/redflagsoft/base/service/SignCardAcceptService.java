package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardAccept;

public interface SignCardAcceptService {
	SignCardAccept createSignCardAccept(SignCardAccept signCardAccept);
	
	SignCardAccept getSignCardAccept(Long signCardId);
	
	SignCardAccept updateSignCardAccept(SignCard signCard,SignCardAccept signCardAccept);
}
