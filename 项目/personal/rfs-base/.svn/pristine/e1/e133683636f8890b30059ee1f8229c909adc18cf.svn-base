package cn.redflagsoft.base.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IllegalCharacterFilter implements Filter{
	
	private static final Log log = LogFactory.getLog(IllegalCharacterFilter.class);

	public void destroy() {
		log.info("销毁过滤器");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new IllegalCharacterRequest((HttpServletRequest) request), response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		log.info("初始化过滤器");
	}
	
}
