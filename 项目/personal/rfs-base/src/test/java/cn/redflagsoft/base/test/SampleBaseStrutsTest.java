package cn.redflagsoft.base.test;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;

public class SampleBaseStrutsTest extends BaseStrutsTests {
	
	/**
	 * 如果要测试自定义的 struts.xml ，必须在这里加进配置管理器。
	 */
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
	
	public void testList() throws Exception{
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
}
