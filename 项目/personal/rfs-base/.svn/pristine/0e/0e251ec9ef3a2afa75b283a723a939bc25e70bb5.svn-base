package cn.redflagsoft.base.scheme.schemes.signcard;

import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardNotice;
import cn.redflagsoft.base.service.SignCardNoticeService;

/****
 * 	纠错告知
 * 	7040
 * @author lifeng
 *
 */
public class SignCardNoticeWorkScheme extends AbstractSignCardWorkScheme {

	private SignCardNoticeService signCardNoticeService;
	private SignCardNotice signCardNotice;

	public SignCardNotice getSignCardNotice() {
		return signCardNotice;
	}

	public void setSignCardNotice(SignCardNotice signCardNotice) {
		this.signCardNotice = signCardNotice;
	}

	public SignCardNoticeService getSignCardNoticeService() {
		return signCardNoticeService;
	}

	public void setSignCardNoticeService(
			SignCardNoticeService signCardNoticeService) {
		this.signCardNoticeService = signCardNoticeService;
	}

	@Override
	public Object doScheme() {
		try {
			SignCard signCard = (SignCard) getObject();
			signCardNoticeService.updateSignCardNotice(signCard, signCardNotice);
			getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
			return super.doScheme();
		} catch (Exception e) {
			return "纠错告知处理失败！";
		}
	}

}
