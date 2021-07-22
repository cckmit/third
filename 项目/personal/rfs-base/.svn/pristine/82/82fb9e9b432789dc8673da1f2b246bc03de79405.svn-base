package cn.redflagsoft.base.scheme.schemes.signcard;

import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardPenalty;
import cn.redflagsoft.base.service.SignCardPenaltyService;

/****
 * 	责任追究
 * 	7060
 * @author lifeng
 *
 */
public class SignCardPenaltyWorkScheme extends AbstractSignCardWorkScheme {
	private SignCardPenalty signCardPenalty;
	private SignCardPenaltyService signCardPenaltyService;

	public SignCardPenalty getSignCardPenalty() {
		return signCardPenalty;
	}

	public void setSignCardPenalty(SignCardPenalty signCardPenalty) {
		this.signCardPenalty = signCardPenalty;
	}

	public SignCardPenaltyService getSignCardPenaltyService() {
		return signCardPenaltyService;
	}

	public void setSignCardPenaltyService(
			SignCardPenaltyService signCardPenaltyService) {
		this.signCardPenaltyService = signCardPenaltyService;
	}

	@Override
	public Object doScheme() {
		try {
			SignCard signCard = (SignCard) getObject();
			signCardPenaltyService.updateSignCardPenalty(signCard,
					signCardPenalty);
			getMattersHandler().finishMatters(getTask(), getWork(),
					getObject(), getMatterVO().getMatterIds());
			return super.doScheme();
		} catch (Exception e) {
			return "业务处理失败！";
		}
	}

}
