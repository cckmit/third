package cn.redflagsoft.base.scheme.schemes.signcard;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.SignCard;

/****
 * 过错 登记
 * 
 * @author lifeng
 * 
 */
public class SignCardBookInWorkScheme extends AbstractSignCardWorkScheme {
	


	@Override
	protected RFSObject createObject() {
		SignCard signCard = getSignCard();
		SignCard createSignCard = getSignCardService().createSignCard(signCard);
		return createSignCard;
	}

	@Override
	public Object doScheme() {
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(),
				getMatterVO().getMatterIds());
		return "过错信息登记成功！";
	}
}
