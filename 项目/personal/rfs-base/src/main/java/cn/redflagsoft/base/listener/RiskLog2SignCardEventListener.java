package cn.redflagsoft.base.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
//import cn.redflagsoft.base.bean.Risk;
//import cn.redflagsoft.base.bean.RiskLog;
//import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.dao.SignCardDao;
//import cn.redflagsoft.base.event.RiskLogEvent;

public class RiskLog2SignCardEventListener implements ApplicationListener {
	private SignCardDao signCardDao;

	public SignCardDao getSignCardDao() {
		return signCardDao;
	}

	public void setSignCardDao(SignCardDao signCardDao) {
		this.signCardDao = signCardDao;
	}

	// public void handle(RiskLogEvent e) {
	// RiskLog riskLog = (RiskLog) e.getSource();
	// if (riskLog.getGrade() == 2 || riskLog.getGrade() == 3
	// || riskLog.getGrade() == 4 || riskLog.getGrade() == 5
	// || riskLog.getGrade() == 6) {
	// SignCard sc = new SignCard();
	// sc.setGrade(riskLog.getGrade());
	// sc.setLabel(riskLog.getProjectName() + "项目-"
	// + riskLog.getTaskName() + "-"
	// + Risk.getGradeExplain(riskLog.getGrade()));
	// sc.setType(riskLog.getRuleID().shortValue());
	// // sc.setRvDutyerType(riskLog.getDutyerType());
	// sc.setRvDutyerID(riskLog.getDutyerID());
	// sc.setRvDesc(riskLog.getConclusion());
	// sc.setJuralLimit(riskLog.getJuralLimit());
	// sc.setRefRiskLogID(riskLog.getId());
	// sc.setRefObjectType(riskLog.getRefObjectType());
	// sc.setRefObjectId(riskLog.getRefObjectId());
	// sc.setRvOccurTime(riskLog.getCreationTime());
	// sc.setCreateType(SignCard.CREATE_TYPE_AUTO);
	// sc.setCode(riskLog.getId().toString());
	// signCardDao.save(sc);
	//
	// }
	//
	// }

	public void onApplicationEvent(ApplicationEvent event) {
//		if (event instanceof RiskLogEvent) {
//			RiskLogEvent e = (RiskLogEvent) event;
//			RiskLog riskLog = (RiskLog) e.getSource();
//			if (riskLog.getGrade() == 2 || riskLog.getGrade() == 3
//					|| riskLog.getGrade() == 4 || riskLog.getGrade() == 5
//					|| riskLog.getGrade() == 6) {
//				SignCard sc = new SignCard();
//				sc.setGrade(riskLog.getGrade());
//				sc.setLabel(riskLog.getProjectName() + "项目-"
//						+ riskLog.getTaskName() + "-"
//						+ Risk.getGradeExplain(riskLog.getGrade()));
//				sc.setType(riskLog.getRuleID().intValue());
//				// sc.setRvDutyerType(riskLog.getDutyerType());
//				sc.setRvDutyerID(riskLog.getDutyerID());
//				sc.setRvDesc(riskLog.getConclusion());
//				sc.setJuralLimit(riskLog.getJuralLimit());
//				sc.setRefRiskLogID(riskLog.getId());
//				sc.setRefObjectType(riskLog.getRefObjectType());
//				sc.setRefObjectId(riskLog.getRefObjectId());
//				sc.setRvOccurTime(riskLog.getCreationTime());
//				sc.setCreateType(SignCard.CREATE_TYPE_AUTO);
//				sc.setCode(riskLog.getId().toString());
//				signCardDao.save(sc);
//			}
//		}

	}

}
