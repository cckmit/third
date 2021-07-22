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
		//��ʼ������
		contextLoaderListener = new AppsContextLoaderListener();
		servletContext = new MockServletContext();
		servletContext.addInitParameter(AppsContextLoaderListener.CONTEXT_LOADER_CLASS_PARAM, getContextLoaderClassName());
		servletContext.addInitParameter(AppsContextLoaderListener.INSTANCE_LOCATION_PARAM, getInstanceLocation());
		customizeServletContext(servletContext);
		
		//����
		contextLoaderListener.contextDestroyed(servletContext);
		
		//����δ�������ʼ��dispatcher
		initDispatcher(null);
		
		/**
		 * �ؼ��ǵ������������ʹ����StrutsTestCase�ﱻװ�������provider����ʼ��XWorkTestCase�µ�
		 * ConfigurationManager configurationManager; Configuration
		 * configuration; Container container; ActionProxyFactory
		 * actionProxyFactory; �������������������¹���configurationManager�����׳�java.lang.
		 * UnsupportedOperationException�쳣
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
     * ���Ի�����Servlet�����ġ�
     * @param sc
     */
    protected void customizeServletContext(MockServletContext sc){
    	
    }
    /**
     * ��ȡAppsContextLoader��ʵ�����͡�
     * @return
     */
    protected String getContextLoaderClassName() {
		return TestConstants.CONTEXT_LOADER_CLASS_NAME;
	}
    /**
	 * ��ȡ��Ʒʵ�������ļ�·�����硰classpath:instance-a.xml����
	 * @return
	 */
	protected String getInstanceLocation() {
		return TestConstants.INSTANCE_LOCATION;
	}
}
