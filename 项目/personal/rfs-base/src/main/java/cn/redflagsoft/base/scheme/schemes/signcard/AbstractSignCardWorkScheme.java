package cn.redflagsoft.base.scheme.schemes.signcard;

import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.service.ObjectService;
import cn.redflagsoft.base.service.SignCardService;

public class AbstractSignCardWorkScheme extends AbstractWorkScheme{
	private SignCard signCard;
	
	public SignCard getSignCard() {
		return signCard;
	}

	public void setSignCard(SignCard signCard) {
		this.signCard = signCard;
	}

	@Override
	public void setObjectService(@SuppressWarnings("rawtypes") ObjectService objectService) {
		Assert.isInstanceOf(SignCardService.class, objectService, "objectService 必须配置成  SignCardService 的实现类。");
		super.setObjectService(objectService);
	}

	public SignCardService getSignCardService(){
		return (SignCardService) getObjectService();
	}
}
