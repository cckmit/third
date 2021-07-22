package org.opoo.apps.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.util.StrutsTestCaseHelper;
import org.opoo.apps.web.context.AppsContextLoaderListener;
import org.springframework.mock.web.MockServletContext;

import com.opensymphony.xwork2.XWorkTestCase;
import com.opensymphony.xwork2.config.ConfigurationProvider;

public abstract class StrutsTests extends XWorkTestCase {
	private Dispatcher dispatcher;
	private AppsContextLoaderListener contextLoaderListener;
	private MockServletContext servletContext;
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.XWorkTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		//初始化容器
		contextLoaderListener = new AppsContextLoaderListener();
		servletContext = new MockServletContext();
		servletContext.addInitParameter(AppsContextLoaderListener.CONTEXT_LOADER_CLASS_PARAM, getContextLoaderClassName());
		servletContext.addInitParameter(AppsContextLoaderListener.INSTANCE_LOCATION_PARAM, getInstanceLocation());
		customizeServletContext(servletContext);
		
		//启动
		contextLoaderListener.contextDestroyed(servletContext);
		
		//启动未出错则初始化dispatcher
		initDispatcher(null);
		
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
	private Dispatcher initDispatcher(Map<String,String> params) {   
		Dispatcher du = getOrCreateDispatcher();
		Dispatcher.setInstance(du);
        configurationManager = du.getConfigurationManager();
        configuration = configurationManager.getConfiguration();
        container = configuration.getContainer();
        return du;
    }
	
	private Dispatcher getOrCreateDispatcher(){
		if(dispatcher == null){
			Map<String,String> params = new HashMap<String,String>();
			Dispatcher du = new Dispatcher(servletContext, params);
			du.init();
			dispatcher = du;
		}
		return dispatcher;
	}


	protected void tearDown() throws Exception {
        super.tearDown();
        if(servletContext != null && contextLoaderListener != null){
        	contextLoaderListener.contextDestroyed(servletContext);
        }
        StrutsTestCaseHelper.tearDown();
    }
    
    /**
     * 个性化配置Servlet上下文。
     * @param sc
     */
    protected void customizeServletContext(MockServletContext sc){
    	
    }
    /**
     * 获取AppsContextLoader的实际类型。
     * @return
     */
    protected String getContextLoaderClassName() {
		return TestConstants.CONTEXT_LOADER_CLASS_NAME;
	}
    /**
	 * 获取产品实例配置文件路径，如“classpath:instance-a.xml”。
	 * @return
	 */
	protected String getInstanceLocation() {
		return TestConstants.INSTANCE_LOCATION;
	}
}
