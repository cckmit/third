package cn.redflagsoft.base.scheme.schemes.smsg;

import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.service.SmsgService;

public class SmsgScheme extends AbstractScheme {
	
	private SmsgService smsgService;
	
	private Long smsgReceiverId ;
	
	
	public SmsgService getSmsgService() {
		return smsgService;
	}


	public void setSmsgService(SmsgService smsgService) {
		this.smsgService = smsgService;
	}

	public Long getSmsgReceiverId() {
		return smsgReceiverId;
	}


	public void setSmsgReceiverId(Long smsgReceiverId) {
		this.smsgReceiverId = smsgReceiverId;
	}


	public Object doCancelSmsg(){
		Assert.notNull(smsgReceiverId, "必须指定smsgReceiverId");
		SmsgReceiver smsgReceiver = new SmsgReceiver();
		smsgReceiver.setId(smsgReceiverId);
		smsgService.cancelSmsgReceiver(smsgReceiver);
		return "业务处理成功！";
	}
	
	
	public Object doRepeatSendSmsg(){
		Assert.notNull(smsgReceiverId, "必须指定smsgReceiverId");
		SmsgReceiver smsgReceiver = new SmsgReceiver();
		smsgReceiver.setId(smsgReceiverId);
		smsgService.resetSmsgReceiver(smsgReceiver);
		return "业务处理成功！";
	}
	
}
