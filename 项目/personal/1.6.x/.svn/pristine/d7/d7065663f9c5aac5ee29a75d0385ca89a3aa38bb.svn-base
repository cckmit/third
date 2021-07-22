package org.opoo.apps.test;

//import java.util.concurrent.atomic.AtomicBoolean;
//
//import org.opoo.apps.AppsGlobals;
//import org.opoo.apps.AppsHome;
import java.io.FileNotFoundException;
import java.net.URL;

import org.opoo.apps.Apps;
import org.opoo.apps.license.AppsInstanceLoader;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.lifecycle.spring.AppsContextLoader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.util.ResourceUtils;

public abstract class SpringTests extends AbstractTransactionalDataSourceSpringContextTests {
//	private static AtomicBoolean initialized = new AtomicBoolean(false);

	public SpringTests() {
		super();
		initialize();
	}

	public SpringTests(String name) {
		super(name);
		initialize();
	}

	private void initialize() {
		super.setPopulateProtectedVariables(true);
		super.setAutowireMode(AUTOWIRE_BY_NAME);

//		if (!initialized.get()) {
//			initializeApplication();
//			initialized.set(true);
//		}
	}
//
//	private void initializeApplication() {
//		if (AppsGlobals.isSetup()) {
//			Log4JConfigurator.configureAppsLogsPath();
//			AppsHome.initialize();
//			try {
//				ModuleManagerImpl.getInstance().preInit();
//			} catch (ModuleException e) {
//				throw new IllegalStateException(e);
//			}
//		} else {
//			throw new IllegalStateException("系统还没有安装，无法执行测试。");
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @seeorg.springframework.test.AbstractSingleSpringContextTests#
//	 * createApplicationContext(java.lang.String[])
//	 */
//	@Override
//	protected ConfigurableApplicationContext createApplicationContext(String[] locations) {
//		for (String loc : locations) {
//			System.out.println("发现配置： " + loc);
//		}
//		SpringContext context = new SpringContext();// super.createApplicationContext(locations);
//		context.setConfigLocations(locations);
//		context.refresh();
//		return context;
//	}
//
//	protected final String[] getConfigLocations() {
//		ConfigurationProvider impl = getConfigurationProvider();
//		String[] locations = impl.getConfigLocations();
//
//		String[] tmp = getContextFiles();
//		if (tmp != null && tmp.length > 0) {
//			return ArrayUtils.concat(locations, tmp);
//		} else {
//			return locations;
//		}
//	}
//
//	/**
//	 * 其他测试需要的 Spring Context 配置。
//	 * 
//	 * @return
//	 */
//	protected String[] getContextFiles() {
//		return null;
//	}
//
//	/**
//	 * 获取ConfigurationProvider。
//	 */
//	protected ConfigurationProvider getConfigurationProvider() {
//		return ConfigurationProviderManager.getConfigurationProvider();
//	}

	@Override
	protected void onSetUp() throws Exception {
		// System.out.println("4>>>" + Application.getContext());
		super.onSetUp();
		if (!Application.isContextInitialized()) {
			throw new IllegalStateException("应用程序没有初始化。");
		}
	}

	private ConfigurableApplicationContext applicationContext;
	/**
	 * 无论 Key 如何，产生的 ApplicationContext 都是不变的。
	 */
	@Override
	protected final ConfigurableApplicationContext loadContext(Object key) throws Exception {
		if(applicationContext == null){
			applicationContext = createAppsApplicationContext();
		}
		return applicationContext;
	}
	protected final Object contextKey(){
		return "springContext";
	}
	
	protected final ConfigurableApplicationContext createAppsApplicationContext() throws Exception{
		String instanceLocation = getInstanceLocation();
		try {
			URL url = ResourceUtils.getURL(instanceLocation);
			AppsInstanceLoader.initialize(url);
		} catch (FileNotFoundException e) {
            throw new RuntimeException("无法找到产品实例配置文件", e);
		}
		
        AppsContextLoader loader = createContextLoader();
        if(!Apps.getInstance().start(loader)) {
            final String msg = "Apps service failed to start. Please see the system logs.";
            throw new RuntimeException(msg);
        }else{
        	return (ConfigurableApplicationContext) Apps.getInstance().getContext();
        }
	}

	/**
	 * 获取产品实例配置文件路径，如“classpath:instance-a.xml”。
	 * @return
	 */
	protected String getInstanceLocation() {
		return TestConstants.INSTANCE_LOCATION;
	}
	
	/**
	 * 创建AppsContextLoader实例。
	 * @return
	 */
	protected AppsContextLoader createContextLoader(){
		return TestConstants.createContextLoader();
	}
}
