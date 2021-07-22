package cn.redflagsoft.base.web.struts2;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.struts2.dispatcher.Dispatcher;
import org.springframework.mock.web.MockServletContext;

import cn.redflagsoft.base.test.StrutsTestContext;

import com.opensymphony.xwork2.ActionProxy;

public class QueryAction2Test2 extends TestCase{
	
	
	

	public void test0() throws Exception{
		/*
		** Create a Dispatcher.
		** This is an expensive operation as it has to load all
		** the struts configuration so you will want to reuse the Dispatcher for
		** multiple tests instead of re-creating it each time.
		**
		** In this example I'm setting configuration parameter to override the
		** values in struts.xml.
		*/
		  HashMap<String, String> params = new HashMap<String, String>();
		  // Override struts.xml config constants to use a guice test module
		  params.put("struts.objectFactory", "testSpringObjectFactory");
		  params.put("guice.module", "test.MyModule");

		  MockServletContext servletContext = new MockServletContext();
		  Dispatcher dispatcher = StrutsTestContext.prepareDispatcher(servletContext, params);

		/*
		**  Create an ActionProxy based on the namespace and action.
		**  Pass in request parameters and session attributes needed for this
		**  test.
		*/
		  StrutsTestContext context = new StrutsTestContext(dispatcher, servletContext);
		  Map<String, String> requestParams = new HashMap<String, String>();
		  Map<String, Object> sessionAttributes = new HashMap<String, Object>();
		  requestParams.put("username", "test");
		  requestParams.put("password", "test");

		  ActionProxy proxy = context.createActionProxy(
		      "/xml",      // namespace
		      "query", // Action
		      requestParams,
		      sessionAttributes);

//		  assertTrue(proxy.getAction() instanceof LoginAction);
//
//		  // Get the Action object from the proxy
//		  LoginAction action = (LoginAction) proxy.getAction();
//
//		  // Inject any mock objects or set any action properties needed
//		  action.setXXX(new MockXXX());
//
//		  // Run the Struts Interceptor stack and the Action
//		  String result = proxy.execute();
//
//		  // Check the results
//		  assertEquals("success", result);
//
//		  // Check the user was redirected as expected
//		  assertEquals(true, context.getMockResponse().wasRedirectSent());
//		  assertEquals("/admin/WelcomeUser.action",
//		      context.getMockResponse().getHeader("Location"));
//
//		  // Check the session Login object was set
//		  assertEquals(mockLogin,
//		      context.getMockRequest().getSession().getAttribute(
//		         Constants.SESSION_LOGIN));
	}


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		//TestSpringObjectFactory.setContext(springTests.getApplicationContext());
	}
}
