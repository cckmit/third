package cn.redflagsoft.base.test;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext; 
//import com.mockrunner.mock.web.MockHttpServletRequest;
//import com.mockrunner.mock.web.MockHttpServletResponse;
//import com.mockrunner.mock.web.MockHttpSession;
//import com.mockrunner.mock.web.MockServletContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionProxyFactory;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;


@Deprecated
public class StrutsTestContext {
	public StrutsTestContext(Dispatcher dispatcher, MockServletContext servletContext) {
		this.dispatcher = dispatcher;
		this.mockServletContext = servletContext;
	}

	private Dispatcher dispatcher;
	private MockServletContext mockServletContext;
	private MockHttpServletRequest mockRequest;
	private MockHttpServletResponse mockResponse;

	public static Dispatcher prepareDispatcher() {
		return prepareDispatcher(null, null);
	}

	public static Dispatcher prepareDispatcher(ServletContext servletContext, Map<String, String> params) {
		if (params == null) {
			params = new HashMap<String, String>();
		}
		Dispatcher dispatcher = new Dispatcher(servletContext, params);
		dispatcher.init();
		Dispatcher.setInstance(dispatcher);
		return dispatcher;
	}

	public static ActionProxy createActionProxy(Dispatcher dispatcher, String namespace, String actionName,
			HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws Exception {
		// BEGIN: Change for Struts 2.1.6
		Container container = dispatcher.getContainer();
		ValueStack stack = container.getInstance(ValueStackFactory.class).createValueStack();
		ActionContext.setContext(new ActionContext(stack.getContext()));
		// END: Change for Struts 2.1.6

		ServletActionContext.setRequest(request);
		ServletActionContext.setResponse(response);
		ServletActionContext.setServletContext(servletContext);

		ActionMapping mapping = null;
		return dispatcher.getContainer().getInstance(ActionProxyFactory.class).createActionProxy(namespace, actionName,
				dispatcher.createContextMap(request, response, mapping, servletContext), true, // execute
																								// result
				false);
	}

	public ActionProxy createActionProxy(String namespace, String actionName, Map<String, String> requestParams,
			Map<String, Object> sessionAttributes) throws Exception {
		mockRequest = new MockHttpServletRequest();
		mockRequest.setSession(new MockHttpSession());
		mockResponse = new MockHttpServletResponse();

		if (requestParams != null) {
			for (Map.Entry<String, String> param : requestParams.entrySet()) {
				mockRequest.setParameter(param.getKey(), param.getValue());
				// mockRequest.setupAddParameter(param.getKey(),
				// param.getValue());
			}
		}
		if (sessionAttributes != null) {
			for (Map.Entry<String, ?> attribute : sessionAttributes.entrySet()) {
				mockRequest.getSession().setAttribute(attribute.getKey(), attribute.getValue());
			}
		}

		return createActionProxy(dispatcher, namespace, actionName, mockRequest, mockResponse, mockServletContext);
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public MockHttpServletRequest getMockRequest() {
		return mockRequest;
	}

	public MockHttpServletResponse getMockResponse() {
		return mockResponse;
	}

	public MockServletContext getMockServletContext() {
		return mockServletContext;
	}
/**
 * 
 * 
 * 
 */
}