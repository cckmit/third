package cn.redflagsoft.base.scheduling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.redflagsoft.base.service.InfoStatService;

/**
 * 定时计算指定对象信息项统计的Job。
 *
 */
public class ObjectInfoStatJob extends SingleNodeQuartzJobBean {

	public static final String INFOSTAT_ENABLED = "ObjectInfoStatJob.%s.enabled";
	private static final Log log = LogFactory.getLog(ObjectInfoStatJob.class);
	
	private InfoStatService infoStatService;
	private int objectType;
	
	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public InfoStatService getInfoStatService() {
		return infoStatService;
	}

	public void setInfoStatService(InfoStatService infoStatService) {
		this.infoStatService = infoStatService;
	}

	@Override
	protected void executeInSeniorClusterMember(JobExecutionContext arg0) throws JobExecutionException {
		String propertyName = String.format(INFOSTAT_ENABLED, objectType);
		if(Application.isInitialized() && Application.isContextInitialized() && AppsGlobals.isSetup()
				&& AppsGlobals.getProperty(propertyName, false)){
			try {
				infoStatService.stat(getObjectType());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
}
