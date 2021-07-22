package cn.redflagsoft.base.dest.scheduling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.redflagsoft.base.dest.service.ExDataRiskService;





@SuppressWarnings("deprecation")
@Deprecated
public class ExDataQuartzJob extends QuartzJobBean implements InitializingBean {
	private static final Log log = LogFactory.getLog(ExDataQuartzJob.class);
	private ExDataRiskService exDataRiskService;

	public ExDataRiskService getExDataRiskService() {
		return exDataRiskService;
	}

	public void setExDataRiskService(ExDataRiskService exDataRiskService) {
		this.exDataRiskService = exDataRiskService;
	}

	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		log.debug("执行Job：" + this);
			try{
				exDataRiskService.exDataRisk();
			}catch(Exception e){
				log.info("执行cn.redflagsoft.base.scheduling.ExDataQuartzJob时发生异常！");
			}
		log.debug("执行完毕：" + this);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		log.info("启动Job：" + this);		
	}
}
