package cn.redflagsoft.base.scheme.schemes.signcard;

import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardSurvey;
import cn.redflagsoft.base.service.SignCardSurveyService;

/****
 * 	��ʵ�϶�
 * 	7030
 * @author lifeng
 *
 */
public class SignCardSurveyWorkScheme extends AbstractSignCardWorkScheme {
	private SignCardSurvey signCardSurvey;

	public SignCardSurvey getSignCardSurvey() {
		return signCardSurvey;
	}

	public void setSignCardSurvey(SignCardSurvey signCardSurvey) {
		this.signCardSurvey = signCardSurvey;
	}

	private SignCardSurveyService signCardSurveyService;

	public SignCardSurveyService getSignCardSurveyService() {
		return signCardSurveyService;
	}

	public void setSignCardSurveyService(
			SignCardSurveyService signCardSurveyService) {
		this.signCardSurveyService = signCardSurveyService;
	}

	@Override
	public Object doScheme() {
		try {
			SignCard signCard = (SignCard) getObject();
            
            if (SignCardSurvey.DEALTYPE_׷�� == signCardSurvey.getDealType()) {
                getMattersHandler().finishMatter(getTask(), getWork(),
                        getObject(), getMatterVO().getMatterIds()[0],
                        (short) 7040, "׷��");
            } else if (SignCardSurvey.DEALTYPE_����׷�� == signCardSurvey.getDealType()) {
                getMattersHandler().finishMatter(getTask(), getWork(),
                        getObject(), getMatterVO().getMatterIds()[0],
                        (short) 7070, "����׷��");
            } else {

            }
            
			signCardSurveyService.updateSignCardSurvey(signCard, signCardSurvey);
			getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
			return super.doScheme();
		} catch (Exception e) {
			return "ҵ����ʧ�ܣ�";
		}
	}
	
}
