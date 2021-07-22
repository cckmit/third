package cn.redflagsoft.base.scheme.schemes.signcard;

import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardCheck;
import cn.redflagsoft.base.service.SignCardCheckService;

/****
 * 	整改核查
 * 	7050
 * @author lifeng
 *
 */
public class SignCardCheckWorkScheme extends AbstractSignCardWorkScheme {

	private SignCardCheck signCardCheck;

	private SignCardCheckService signCardCheckService;

	public SignCardCheck getSignCardCheck() {
		return signCardCheck;
	}

	public void setSignCardCheck(SignCardCheck signCardCheck) {
		this.signCardCheck = signCardCheck;
	}

	public SignCardCheckService getSignCardCheckService() {
		return signCardCheckService;
	}

	public void setSignCardCheckService(
			SignCardCheckService signCardCheckService) {
		this.signCardCheckService = signCardCheckService;
	}

	@Override
	public Object doScheme() {
		try {
			SignCard signCard = (SignCard) getObject();
			signCardCheckService.updateSignCardCheck(signCard, signCardCheck);
			getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
			return super.doScheme();
		} catch (Exception e) {
			return "整改核查处理失败！";
		}
	}

}
