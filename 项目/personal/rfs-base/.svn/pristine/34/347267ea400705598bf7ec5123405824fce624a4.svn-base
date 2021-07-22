package cn.redflagsoft.base.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsTestCase;
import org.apache.struts2.dispatcher.Dispatcher;
import org.opoo.apps.test.SpringTests;
import org.opoo.apps.web.context.ConfigurationProviderManager;
import org.opoo.apps.web.context.SpringContextLoader;
import org.springframework.mock.web.MockServletContext;

import cn.redflagsoft.base.web.context.BaseConfigurationProviderImpl;

import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;

@Deprecated
public abstract class OldBaseStrutsTests extends StrutsTestCase {
	
	public OldBaseStrutsTests() {
		super();
		//初始化环境
		new SpringTests(){};
	}

	@Override
	protected Dispatcher initDispatcher(Map<String, String> params) {
		System.out.println("initDispatcher............");
		Dispatcher du = customInitDispatcher(params);// StrutsTestCaseHelper.initDispatcher(params);
		configurationManager = du.getConfigurationManager();
		configuration = configurationManager.getConfiguration();
		container = configuration.getContainer();
		return du;
	}

	private Dispatcher customInitDispatcher(Map<String, String> params) {
		if (params == null) {
			params = new HashMap<String, String>();
		}
		MockServletContext servletContext = new MockServletContext();
//		servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, ""
//				+ "classpath:spring-base-cache.xml,classpath:spring-base-dao.xml,"
//				+ "classpath:spring-base-service.xml,classpath:spring-base-web.xml," + "classpath:spring-base-aop.xml");

		servletContext.addInitParameter(ConfigurationProviderManager.CONTEXT_CONFIGURATION_PROVIDER_PARAM, 
				BaseConfigurationProviderImpl.class.getName());
		//ConfigurationProviderManager.setConfigurationProviderClass(BaseConfigurationProviderImpl.class);
		new SpringContextLoader().initWebApplicationContext(servletContext);

		Dispatcher du = new Dispatcher(servletContext, params);
		du.init();
		Dispatcher.setInstance(du);
		return du;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.StrutsTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		XmlConfigurationProvider c = new XmlConfigurationProvider("struts-tests.xml");
		configurationManager.addConfigurationProvider(c);
		configurationManager.reload();
		/**
		 * 关键是调用这个方法，使用在StrutsTestCase里被装入的所有provider来初始化XWorkTestCase下的
		 * ConfigurationManager configurationManager; Configuration
		 * configuration; Container container; ActionProxyFactory
		 * actionProxyFactory; 如果不调用这个方法重新构建configurationManager，将抛出java.lang.
		 * UnsupportedOperationException异常
		 * 
		 */
		loadConfigurationProviders(configurationManager.getConfigurationProviders().toArray(
				new ConfigurationProvider[0]));
	}

}
