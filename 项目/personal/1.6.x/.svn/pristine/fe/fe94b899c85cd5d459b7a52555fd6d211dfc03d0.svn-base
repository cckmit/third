package org.opoo.web.servlet;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public class ServletConfigWrapper implements ServletConfig{
	private final ServletConfig config;
	public ServletConfigWrapper(ServletConfig config) {
		super();
		this.config = config;
	}

	public String getInitParameter(String name) {
		return config.getInitParameter(name);
	}

	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		return config.getInitParameterNames();
	}

	public ServletContext getServletContext() {
		return config.getServletContext();
	}

	public String getServletName() {
		return config.getServletName();
	}
}
