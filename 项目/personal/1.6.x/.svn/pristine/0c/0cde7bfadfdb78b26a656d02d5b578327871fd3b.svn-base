package org.opoo.apps.web.struts2.interceptor;

import org.opoo.apps.exception.SystemException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class SystemExceptionInterceptor implements Interceptor {
	private static final long serialVersionUID = -8371054187268744863L;

	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
}
