package org.opoo.apps.web.struts2.dispatcher;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.struts2.RequestUtils;


@Deprecated
public class FilterDispatcher extends
		org.apache.struts2.dispatcher.FilterDispatcher {
	
	public static final String PREFFIX = "/download/";

	/* (non-Javadoc)
	 * @see org.apache.struts2.dispatcher.FilterDispatcher#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		String uri = getUri((HttpServletRequest) req);
		System.out.println("uri===================" + uri);
		if(uri.startsWith(PREFFIX)){
			//int pos = uri.lastIndexOf(".", fromIndex)
			//String id = uri

		}
		
		super.doFilter(req, res, chain);
	}
	
    String getUri(HttpServletRequest request) {
        // handle http dispatcher includes.
        String uri = (String) request.getAttribute("javax.servlet.include.servlet_path");
        if (uri != null) {
        	System.out.println("javax.servlet.include.servlet_path============" + uri);
            return uri;
        }

        uri = RequestUtils.getServletPath(request);
        if (uri != null && !"".equals(uri)) {
        	System.out.println("RequestUtils.getServletPath(request)===========" + uri);
            return uri;
        }

        uri = request.getRequestURI();
        return uri.substring(request.getContextPath().length());
    }
    
    @SuppressWarnings("unchecked")
    protected static class Request extends HttpServletRequestWrapper{
    	private String id;
		public Request(HttpServletRequest request, String id) {
			super(request);
			this.id = id;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
		 */
		@Override
		public String getParameter(String name) {
			if(name == "id"){
				return id;
			}
			return super.getParameter(name);
		}

		/* (non-Javadoc)
		 * @see javax.servlet.ServletRequestWrapper#getParameterMap()
		 */
		
		@Override
		public Map getParameterMap() {
			Map map = new HashMap();
			map.putAll(super.getParameterMap());
			map.put("id", id);
			return super.getParameterMap();
		}

		/* (non-Javadoc)
		 * @see javax.servlet.ServletRequestWrapper#getParameterNames()
		 */
		@Override
		public Enumeration getParameterNames() {
			// TODO Auto-generated method stub
			return super.getParameterNames();
		}

		/* (non-Javadoc)
		 * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
		 */
		@Override
		public String[] getParameterValues(String name) {
			if(name == "id"){
				return new String[]{id};
			}
			return super.getParameterValues(name);
		}
    	
    }

}
