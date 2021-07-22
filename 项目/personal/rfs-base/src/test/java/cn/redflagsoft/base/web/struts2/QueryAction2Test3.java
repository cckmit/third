package cn.redflagsoft.base.web.struts2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.test.BaseStrutsTests;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;

public class QueryAction2Test3 extends BaseStrutsTests {

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
	
	public void etestList() throws Exception{
		Map<String, String> params = new HashMap<String, String>();
  	
		params.put("target", "clerk");
		params.put("q[0].n", "name");
		params.put("q[0].v", "abc");
		params.put("q[0].o", "like");
		params.put("start", "0");
		params.put("limit", "10");
		
      
		HashMap extraContext = new HashMap();
		extraContext.put(ActionContext.PARAMETERS, params);
		
		ActionProxy proxy = actionProxyFactory.createActionProxy("/jt", "query", extraContext, false, false);
		proxy.setMethod("list");
		
    	proxy.execute();
	}
	
	public void testProcess() throws Exception{
		Map<String, String> params = new HashMap<String, String>();
	  	
		params.put("processType", "6022");
		
      
		HashMap extraContext = new HashMap();
		extraContext.put(ActionContext.PARAMETERS, params);
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("fgj-user1", "123"));
		
		ActionProxy proxy = actionProxyFactory.createActionProxy("/json", "process", extraContext, false, false);
		
    	proxy.execute();
    	
	}
}
