package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardVO;



public interface SignCardService extends RFSObjectService<SignCard>{
	SignCardVO getSignCardVOById(Long id);
	
	SignCard createSignCard(SignCard signCard);
	
	SignCard getSignCard(Long id);
}
