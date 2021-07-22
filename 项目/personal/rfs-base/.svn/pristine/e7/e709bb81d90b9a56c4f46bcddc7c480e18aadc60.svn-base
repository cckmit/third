package cn.redflagsoft.base.dest.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import cn.redflagsoft.base.bean.ExDataRisk;
import cn.redflagsoft.base.bean.RiskLog;
import cn.redflagsoft.base.dao.ExDataRiskDao;
import cn.redflagsoft.base.dao.RiskLogDao;
import cn.redflagsoft.base.dest.service.ExDataRiskService;
import cn.redflagsoft.base.event.RiskLogEvent;


@Deprecated
public class ExDataRiskServiceImpl implements ExDataRiskService{

	private RiskLogDao riskLogDao;
	private ExDataRiskDao exDataRiskDao;
	private ApplicationListener applicationListener;

	public ExDataRiskDao getExDataRiskDao() {
		return exDataRiskDao;
	}

	public void setExDataRiskDao(ExDataRiskDao exDataRiskDao) {
		this.exDataRiskDao = exDataRiskDao;
	}

	public ApplicationListener getApplicationListener() {
		return applicationListener;
	}

	public void setApplicationListener(ApplicationListener applicationListener) {
		this.applicationListener = applicationListener;
	}

	public RiskLogDao getRiskLogDao() {
		return riskLogDao;
	}

	public void setRiskLogDao(RiskLogDao riskLogDao) {
		this.riskLogDao = riskLogDao;
	}

	public void exDataRisk() {
		List<ExDataRisk> ers=exDataRiskDao.findWaitExRisk();
		for(ExDataRisk er:ers){
			RiskLog rl=riskLogDao.get(er.getDataID());
			ApplicationEvent event = new RiskLogEvent(rl,rl.getBusiType(),er);
			er.setExTime(new Date());
			try{
				
				applicationListener.onApplicationEvent(event);
				//er.setStatus(ExDataRisk.STATUS_SUCCESS);
			}catch(Exception e){
				//e.printStackTrace();
				//er.setStatus(ExDataRisk.STATUS_LOSE);
			}
			//exDataRiskDao.update(er);
		
		}
	}

	
	
}
