package cn.redflagsoft.base.web.context;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.web.context.ConfigurationProviderImpl;

import cn.redflagsoft.base.aop.aspect.AspectJ;
import cn.redflagsoft.base.audit.proxy.AuditManagerProxy;

/**
 * 
 * @author lcj
 * @deprecated
 */
public class BaseConfigurationProviderImpl extends ConfigurationProviderImpl {
	private static final Log log = LogFactory.getLog(BaseConfigurationProviderImpl.class);
	public BaseConfigurationProviderImpl() {
		super();
	}

	public BaseConfigurationProviderImpl(ServletContext servletContext) {
		super(servletContext);
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.web.context.ConfigurationProviderImpl#getRuntimeContextFiles()
	 */
	@Override
	public List<String> getRuntimeContextFiles() {
		List<String> list = super.getRuntimeContextFiles();
		if(!isInSetup()){
			list.add("classpath:spring-base-application.xml");
			if(AspectJ.isCompileTimeWoven()){
				log.debug("源代码在编译期已经织入了切面");
				list.add("classpath:spring-base-aop-ctw.xml");
			}else{
				log.info("启用 Aop 动态代理");
				list.add("classpath:spring-base-aop.xml");
			}
			if(AuditManagerProxy.isAuditEnabledInProperties()){
				log.info("启动 用户操作审计");
			}else{
				log.info("禁用用户操作审计");
			}
			//无论是否启用都启动配置
			list.add("classpath:spring-base-audit.xml");
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.web.context.ConfigurationProviderImpl#getSetupContextFiles()
	 */
	@Override
	public List<String> getSetupContextFiles() {
		List<String> list = super.getSetupContextFiles();
		if(isInSetup()){
			list.add("classpath:spring-base-setup.xml");
		}
		return list;
	}

}
