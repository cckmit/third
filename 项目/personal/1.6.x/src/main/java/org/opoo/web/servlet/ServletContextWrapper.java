package org.opoo.web.servlet;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ServletContextWrapper implements ServletContext {

	private final ServletContext context;
	public ServletContextWrapper(ServletContext context) {
		super();
		this.context = context;
	}

	public Object getAttribute(String name) {
		return context.getAttribute(name);
	}

	@SuppressWarnings("unchecked")
	public Enumeration getAttributeNames() {
		return context.getAttributeNames();
	}

	public ServletContext getContext(String uripath) {
		return context.getContext(uripath);
	}

	public String getContextPath() {
		return context.getContextPath();
	}

	public String getInitParameter(String name) {
		return context.getInitParameter(name);
	}

	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		return context.getInitParameterNames();
	}

	public int getMajorVersion() {
		return context.getMajorVersion();
	}

	public String getMimeType(String file) {
		return context.getMimeType(file);
	}

	public int getMinorVersion() {
		return context.getMinorVersion();
	}

	public RequestDispatcher getNamedDispatcher(String name) {
		return context.getNamedDispatcher(name);
	}

	public String getRealPath(String path) {
		return context.getRealPath(path);
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return context.getRequestDispatcher(path);
	}

	public URL getResource(String path) throws MalformedURLException {
		return context.getResource(path);
	}

	public InputStream getResourceAsStream(String path) {
		return context.getResourceAsStream(path);
	}

	@SuppressWarnings("unchecked")
	public Set getResourcePaths(String path) {
		return context.getResourcePaths(path);
	}

	public String getServerInfo() {
		return context.getServerInfo();
	}

	@SuppressWarnings("deprecation")
	public Servlet getServlet(String name) throws ServletException {
		return context.getServlet(name);
	}

	public String getServletContextName() {
		return context.getServletContextName();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public Enumeration getServletNames() {
		return context.getServletNames();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public Enumeration getServlets() {
		return context.getServlets();
	}

	public void log(String msg) {
		context.log(msg);
	}

	@SuppressWarnings("deprecation")
	public void log(Exception exception, String msg) {
		context.log(exception, msg);
	}

	public void log(String message, Throwable throwable) {
		context.log(message, throwable);
	}

	public void removeAttribute(String name) {
		context.removeAttribute(name);
	}

	public void setAttribute(String name, Object object) {
		context.setAttribute(name, object);
	}

}
