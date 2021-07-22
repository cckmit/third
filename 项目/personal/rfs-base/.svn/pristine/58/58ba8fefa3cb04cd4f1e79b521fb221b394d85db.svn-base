package cn.redflagsoft.base.web.struts2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsTestCase;
import org.apache.struts2.dispatcher.Dispatcher;
import org.opoo.apps.QueryParameter;
import org.opoo.apps.web.context.SpringContextLoader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;

public class QueryAction2Test extends StrutsTestCase {
	
	
    protected Dispatcher initDispatcher(Map<String,String> params) {
    	System.out.println("initDispatcher............");
        Dispatcher du = initDispatcher2(params);//StrutsTestCaseHelper.initDispatcher(params);
        configurationManager = du.getConfigurationManager();
        configuration = configurationManager.getConfiguration();
        container = configuration.getContainer();
        return du;
    }
    
    protected Dispatcher initDispatcher2(Map<String,String> params) {
        if (params == null) {
            params = new HashMap<String,String>();
        }
        
        MockServletContext servletContext = new MockServletContext();
        servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, 
        		"" +
        		"classpath:spring-base-cache.xml,classpath:spring-base-dao.xml," +
        		"classpath:spring-base-service.xml,classpath:spring-base-web.xml," +
        		"classpath:spring-base-aop.xml");
        
        new SpringContextLoader().initWebApplicationContext(servletContext);
        
        
        Dispatcher du = new Dispatcher(servletContext, params);
        du.init();
        Dispatcher.setInstance(du);
        return du;
    }
	/* (non-Javadoc)
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
         * actionProxyFactory;  
         * 如果不调用这个方法重新构建configurationManager，将抛出java.lang.UnsupportedOperationException异常  
         *   
         */  
		loadConfigurationProviders(configurationManager.getConfigurationProviders().toArray(new ConfigurationProvider[0]));
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.StrutsTestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

    public void etestConfig() throws Exception {
        //assertNotNull(configurationManager);
        
    	 //RuntimeConfiguration configuration = configurationManager.getConfiguration().getRuntimeConfiguration();
         //ActionConfig config = configuration.getActionConfig("/xml", "query");
         //System.out.println(config);
       
    	
    	
         ActionProxy proxy = actionProxyFactory.createActionProxy("/xml", "query", null, true, false);
         proxy.getInvocation().getInvocationContext().setParameters(new HashMap());
         ServletActionContext.setContext(proxy.getInvocation().getInvocationContext());   
         MockHttpServletRequest request = new MockHttpServletRequest();   
         MockHttpServletResponse response = new MockHttpServletResponse(); 
         request.setParameter("target", "clerk");
         
         ServletActionContext.setRequest(request);   
         ServletActionContext.setResponse(response);   
         
         proxy.setMethod("list");
         String execute = proxy.execute();
         System.out.println(execute);
    }
    
    public void etest00() throws Exception{
    	ActionProxy proxy = actionProxyFactory.createActionProxy("/xml", "query", null, true, false);
    	QueryAction2 action = (QueryAction2) proxy.getAction();
    	action.setTarget("clerk");
    	List<QueryParameter> qq = new ArrayList<QueryParameter>();
    	QueryParameter q = new QueryParameter();
    	q.setN("name");
    	q.setV("aaa");
    	qq.add(q);
    	action.setQ(qq);
    	
    	String list = action.list();
    	
    	
    }
    
    public void test01() throws Exception{
    	
    	Map<String, String> params = new HashMap<String, String>();
//        params.put("name", "");
//        params.put("count", "15");
//        params.put("models[0].name", "0.name");
//        params.put("models[0].count", "34");
//        params.put("model2.name", "model2.name");
    	
    	params.put("target", "clerk");
    	params.put("q[0].n", "name");
    	params.put("q[0].v", "abc");
        
        HashMap extraContext = new HashMap();
        extraContext.put(ActionContext.PARAMETERS, params);
        
    	ActionProxy proxy = actionProxyFactory.createActionProxy("/jt", "query", extraContext, true, false);
		proxy.setMethod("list");
		
    	proxy.execute();
    }
}
