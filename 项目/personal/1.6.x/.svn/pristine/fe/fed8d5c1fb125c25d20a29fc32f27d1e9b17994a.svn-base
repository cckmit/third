package org.opoo.apps.web.sitemesh;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.filter.PageFilter;

public class AppsPageFilter extends PageFilter {
	
	private static final Log log = LogFactory.getLog(AppsPageFilter.class);
	 //private FilterConfig filterConfig = null;
	
	@Override
	public void init(FilterConfig filterConfig) {
		//this.filterConfig = filterConfig;
		// Uses are sitemesh factory instead of the default one so that we can
        // add ignore urls at runtime
		AppsSitemeshFactory factory = new AppsSitemeshFactory(new Config(filterConfig));
        filterConfig.getServletContext().setAttribute("sitemesh.factory", factory);

        super.init(filterConfig);
        log.debug("AppsPageFilter init");
	}

	@Override
	protected void applyDecorator(Page page, Decorator decorator, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(log.isDebugEnabled()){
			log.debug("decorator.page : " + decorator.getPage());
		}
		super.applyDecorator(page, decorator, request, response);
		/*
        try {
            request.setAttribute(PAGE, page);
            ServletContext context = filterConfig.getServletContext();
            // see if the URI path (webapp) is set
            if (decorator.getURIPath() != null) {
                // in a security conscious environment, the servlet container
                // may return null for a given URL
                if (context.getContext(decorator.getURIPath()) != null) {
                    context = context.getContext(decorator.getURIPath());
                }
            }
            
            //System.out.println("---> " + decorator.getPage());
            
            // get the dispatcher for the decorator
            RequestDispatcher dispatcher = context.getRequestDispatcher(decorator.getPage());
            // create a wrapper around the response
            dispatcher.include(request, response);
            
            //System.out.println("---> " + request.getPathInfo());
            
            // set the headers specified as decorator init params
            while (decorator.getInitParameterNames().hasNext()) {
                String initParam = (String) decorator.getInitParameterNames().next();
                if (initParam.startsWith("header.")) {
                    response.setHeader(initParam.substring(initParam.indexOf('.')), decorator.getInitParameter(initParam));
                }
            }

            request.removeAttribute(PAGE);
        }
        catch (RuntimeException e) {
            // added a print message here because otherwise Tomcat swallows
            // the error and you never see it = bad!
            if (Container.get() == Container.TOMCAT)
                e.printStackTrace();

            throw e;
        }
        */
	}

	@Override
	public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) throws IOException, ServletException {
		super.doFilter(rq, rs, chain);
	}

	@Override
	protected Page parsePage(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		return super.parsePage(request, response, chain);
	}

}
