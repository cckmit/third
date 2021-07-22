package cn.redflagsoft.base.scheme.schemes.signcard;

import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardAccept;
import cn.redflagsoft.base.service.SignCardAcceptService;

/***
 * 过错受理
 * 
 * @author lifeng
 * 
 */
public class SignCardAcceptWorkScheme extends AbstractSignCardWorkScheme {

    private SignCardAccept signCardAccept;
    private SignCardAcceptService signCardAcceptService;

    public SignCardAccept getSignCardAccept() {
        return signCardAccept;
    }

    public void setSignCardAccept(SignCardAccept signCardAccept) {
        this.signCardAccept = signCardAccept;
    }

    public SignCardAcceptService getSignCardAcceptService() {
        return signCardAcceptService;
    }

    public void setSignCardAcceptService(
            SignCardAcceptService signCardAcceptService) {
        this.signCardAcceptService = signCardAcceptService;
    }

    @Override
    public Object doScheme() {
        try {
            SignCard signCard = (SignCard) getObject();
            
            if (SignCardAccept.ACCEPTTYPE_受理 == signCardAccept.getAcceptType()) {
                getMattersHandler().finishMatter(getTask(), getWork(),
                        getObject(), getMatterVO().getMatterIds()[0],
                        (short) 7030, "过错受理");
            } else if (SignCardAccept.ACCEPTTYPE_不受理 == signCardAccept
                    .getAcceptType()) {
                getMattersHandler().finishMatter(getTask(), getWork(),
                        getObject(), getMatterVO().getMatterIds()[0],
                        (short) 7070, "过错不受理");
            } else {

            }

            signCardAcceptService
                    .updateSignCardAccept(signCard, signCardAccept);
            getMattersHandler().finishMatters(getTask(), getWork(),
                    getObject(), getMatterVO().getMatterIds());
            return super.doScheme();
        } catch (Exception e) {
            return "过错受理处理失败！";
        }

    }

}
