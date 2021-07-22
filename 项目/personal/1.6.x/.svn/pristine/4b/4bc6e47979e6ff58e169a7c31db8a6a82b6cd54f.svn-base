package org.opoo.apps.web.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * 使用这个过滤器是为了解决一个很奇怪的BUG。
 * 	request.setCharacterEncoding("UTF-8");
 * 	request.getParameter("i");
 * 其中
 * 
 * @author Alex
 * @since 1.5
 */
public class EncodingFixedFilter implements Filter {

	private int i = 0;
	public void destroy() {

	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain fc) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		System.out.println("===================" + (i++) + "== " + request.getRequestURI());
		 request.setCharacterEncoding("UTF-8");
		request.getParameter("i");
		//if(un != null){
		//	System.out.println(un);
		//	System.out.println("=======================================");
		//	System.out.println(new String(un.getBytes("UTF-8"), "GBK"));
		//	System.out.println(new String(un.getBytes("utf-8"), "GBK"));
		//	System.out.println(new String(un.getBytes("GBK"), "UTF-8"));
		//}
		//org.opoo.web.filter.EncodingFilter
		
		java.util.Enumeration<String> en = request.getHeaderNames();
		while(en.hasMoreElements()){
			String name = en.nextElement();
			System.out.println("请求头：" + name + " -> "	+ request.getHeader(name));
		}
		
		en = request.getParameterNames();
		while(en.hasMoreElements()){
			String name = en.nextElement();
			System.out.println("参数：" + name + " -> "	+ request.getParameter(name));
		}
		
		System.out.println("方法: " + request.getMethod());
		fc.doFilter(request, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	public static void main(String[]args){
		int i=0;
		for(int j=0;j<100;j++){
			i=i++;
		}
		System.out.println(i);
	}
}
