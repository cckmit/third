package org.opoo.apps.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;

public class SampleStrutsTest extends StrutsTests {
	public void test0() throws Exception{
		Map<String, String> params = new HashMap<String, String>();
	  	
//		params.put("target", "clerk");
//		params.put("q[0].n", "name");
//		params.put("q[0].v", "abc");
//		params.put("start", "0");
//		params.put("limit", "10");
		
      
		HashMap<String,Object> extraContext = new HashMap<String,Object>();
		extraContext.put(ActionContext.PARAMETERS, params);
		extraContext.put(ActionContext.SESSION, new HashMap<String,Object>());
		ServletActionContext.setRequest(new MockHttpServletRequest());
		ServletActionContext.setResponse(new MockHttpServletResponse());
		
		ActionProxy proxy = actionProxyFactory.createActionProxy("/admin/setup", "setup.license", extraContext, false, false);
		proxy.setMethod("input");
		
    	proxy.execute();
    	
    	@SuppressWarnings("unchecked")
		Map<String,Object> session = ActionContext.getContext().getSession();
    	System.out.println(session);
    }
}
