package cn.redflagsoft.base.dest.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.ExCfgDatafilterRisk;
import cn.redflagsoft.base.bean.ExDataRisk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.RiskLog;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.dao.ClerkDao;
import cn.redflagsoft.base.dao.ExCfgDatafilterRiskDao;
import cn.redflagsoft.base.dao.ExDataRiskDao;
import cn.redflagsoft.base.dao.OrgDao;
import cn.redflagsoft.base.dest.bean.BusiReport;
import cn.redflagsoft.base.dest.dao.BusiReportDao;
import cn.redflagsoft.base.event.RiskLogEvent;

@SuppressWarnings("deprecation")
@Deprecated
public class RiskLogListener implements ApplicationListener {
	private static final Log log = LogFactory.getLog(RiskLogListener.class);
	private ExCfgDatafilterRiskDao exCfgDatafilterRiskDao;
	private BusiReportDao busiReportDao;
	private OrgDao orgDao;
	
	private ClerkDao clerkDao;
	private ExDataRiskDao exDataRiskDao;

	
	public ExDataRiskDao getExDataRiskDao() {
		return exDataRiskDao;
	}

	public void setExDataRiskDao(ExDataRiskDao exDataRiskDao) {
		this.exDataRiskDao = exDataRiskDao;
	}

	public ClerkDao getClerkDao() {
		return clerkDao;
	}

	public void setClerkDao(ClerkDao clerkDao) {
		this.clerkDao = clerkDao;
	}


	public ExCfgDatafilterRiskDao getExCfgDatafilterRiskDao() {
		return exCfgDatafilterRiskDao;
	}

	public void setExCfgDatafilterRiskDao(
			ExCfgDatafilterRiskDao exCfgDatafilterRiskDao) {
		this.exCfgDatafilterRiskDao = exCfgDatafilterRiskDao;
	}

	public BusiReportDao getBusiReportDao() {
		return busiReportDao;
	}

	public void setBusiReportDao(BusiReportDao busiReportDao) {
		this.busiReportDao = busiReportDao;
	}

	public OrgDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}
		
	

	public void onApplicationEvent(ApplicationEvent event) {
		
		if(event instanceof RiskLogEvent){	
			ExDataRisk exDataRisk=exDataRiskDao.get(((RiskLogEvent) event).getExDataRisk().getId());	
			try{
			log.debug("系统事件：" + event + ", " + event.getSource());
			RiskLog riskLog=(RiskLog)event.getSource();
			List<ExCfgDatafilterRisk> elist= exCfgDatafilterRiskDao.findExCfgDatafilterRiskByRuleIDAndEvent(riskLog.getRuleID(),String.valueOf(((RiskLogEvent) event).getType()));
			if(elist!=null){
				if(elist.size()>0){
					ExCfgDatafilterRisk e=elist.get(0);
					if(e!=null){
						if(e.getFlag()==1){//可交换
							if(riskLog.getObjectType()==Task.OBJECT_TYPE){//RiskLog类型是任务的
								BusiReport busiReport=new BusiReport();
								busiReport.setBusiID(riskLog.getObjectID().toString());
								busiReport.setBusiType(String.valueOf(((RiskLogEvent) event).getType()));
								
								if(riskLog.getDutyerType()==1){
									busiReport.setOrgID(riskLog.getDutyerID());
									Org o=orgDao.get(riskLog.getDutyerID());
									busiReport.setOrgName(o.getAbbr());
									
								}
								if(riskLog.getItemID()!=null){
									busiReport.setItemID(riskLog.getRuleID().toString());
								}else {
									busiReport.setItemID("无");
								}
								if(riskLog.getItemName()!=null){
									busiReport.setItemName(riskLog.getName().toString());
								}else{
									busiReport.setItemName("无");
								}
								
								if(riskLog.getTaskName()!=null){
									busiReport.setContent(riskLog.getTaskName()+"("+riskLog.getProjectName()+")");
								}else{
									busiReport.setContent(riskLog.getProjectName());
								}
//										Long dutyClerkID=riskLog.getDutyClerkID();
//										if(dutyClerkID!=null){
//											Clerk dc=clerkDao.get(dutyClerkID);
//											if(dc!=null){
//												busiReport.setP2ID(dutyClerkID);
//												busiReport.setP2Name(dc.getName());
//												busiReport.setP2Phone(dc.getMobNo());
//											}
//										}
										//busiReport.setP3ID(0L);
										//busiReport.setP3Name("无");
										//busiReport.setP3Phone("无");
										Long creator=riskLog.getP3ID();
										if(creator!=null){
											Clerk c=clerkDao.get(creator);
											if(c!=null){
												busiReport.setP3ID(creator);
												busiReport.setP3Name(c.getName());
												busiReport.setP3Phone(c.getMobNo());
											}
										}

								busiReport.setCreationTime(riskLog.getCreationTime());
								busiReport.setTimeLimit(riskLog.getScaleValue().toBigInteger().intValue());
								if(RiskRule.VALUE_UNIT_WORKDAY == riskLog.getValueUnit()){
									busiReport.setTimeUnit("G");
								}else if(RiskRule.VALUE_UNIT_DAY == riskLog.getValueUnit()){
									busiReport.setTimeUnit("Z");
								}
								
								busiReport.setLimitStatus(changeGradeToLimitStatus(riskLog.getGrade()));		
								busiReport.setWriteTime(new Date());
								busiReport.setProjectID(riskLog.getProjectID());
								busiReport.setProjectSn(riskLog.getProjectSn());
								busiReport.setProjectName(riskLog.getProjectName());
								busiReport.setTimeUsed(riskLog.getTimeUsed());
								busiReport.setTimeRemain(riskLog.getTimeRemain());
								
								busiReportDao.save(busiReport);
							}
							
							
						}
						
					}
				}
			}	
			if(exDataRisk!=null){
				exDataRisk.setStatus(ExDataRisk.STATUS_SUCCESS);
			}
			}catch(Exception e){
				//log.debug("交换异常");
				e.printStackTrace();
				if(exDataRisk!=null){
					exDataRisk.setStatus(ExDataRisk.STATUS_LOSE);
				}
			}
			if(exDataRisk!=null){
				exDataRiskDao.update(exDataRisk);
			}
			
		}
		
		
	}
	
 private String changeGradeToLimitStatus(byte grade){
	String result="";
		switch(grade){
		case 0:
		case 1:result="0";break;
		case 2:result="1";break;
		case 3:result="2";break;
		case 4:result="4";break;
		case 5:result="5";break;
		default:result="";
		}	 
	 return result;
}
		
}	
