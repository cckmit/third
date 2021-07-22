package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardCheck;
import cn.redflagsoft.base.dao.SignCardCheckDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.SignCardCheckService;

public class SignCardCheckServiceImpl implements SignCardCheckService {
	private SignCardCheckDao signCardCheckDao;

	public SignCardCheckDao getSignCardCheckDao() {
		return signCardCheckDao;
	}

	public void setSignCardCheckDao(SignCardCheckDao signCardCheckDao) {
		this.signCardCheckDao = signCardCheckDao;
	}

	public SignCardCheck createSignCardCheck(SignCardCheck signCardCheck) {
		return signCardCheckDao.save(signCardCheck);
	}

	public SignCardCheck getSignCardCheck(Long signCardId) {
		ResultFilter filters = ResultFilter.createPageableResultFilter(0, 1);
		SimpleExpression criterions = Restrictions.eq("signCardId", signCardId);
		filters.setCriterion(criterions);
		List<SignCardCheck> list = signCardCheckDao.find(filters);

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public SignCardCheck updateSignCardCheck(SignCard signCard,
			SignCardCheck signCardCheck) {
		// ��õ�ǰ��¼ϵͳ�û���Ϣ
		Clerk clerk = UserClerkHolder.getClerk();
		
		SignCardCheck signCardCheckResult = getSignCardCheck(signCard.getId());
		if(signCardCheckResult == null){
			SignCardCheck newSignCardCheck = new SignCardCheck();
			newSignCardCheck.setSignCardId(signCard.getId());
			SignCardCheck createSignCardCheck = createSignCardCheck(newSignCardCheck);
			signCardCheckResult = createSignCardCheck;
		}
		//�������ժҪ
		signCardCheckResult.setCheckSummary(signCardCheck.getCheckSummary());
		//���ĺ˲��˱��
		signCardCheckResult.setCheckDutyerId(signCardCheck.getCheckDutyerId());
		//���ĺ˲�������
		signCardCheckResult.setCheckDutyerName(signCardCheck.getCheckDutyerName());
		//���ĺ˲�ʱ��
		signCardCheckResult.setCheckActualTime(signCardCheck.getCheckActualTime());
		//���ĺ˲鱸ע
		signCardCheckResult.setCheckRemark(signCardCheck.getCheckRemark());
		//���ĺ˲���Ϣ�Ǽ���
		signCardCheckResult.setCheckCreator(clerk.getId());
		//���ĺ˲�ϵͳ����ʱ��
		signCardCheckResult.setCheckTime(new Date());
		//������
		signCardCheckResult.setCreator(clerk.getId());
		//����ʱ��
		signCardCheckResult.setCreationTime(new Date());
		
		return signCardCheckDao.update(signCardCheckResult);
	}
	
}
