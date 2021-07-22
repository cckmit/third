package org.opoo.apps.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 专门用于登录页面的过滤。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class LoginFilter extends AbstractFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(isRichClient(request, response)){
			sendToRichClient(request, response, HttpServletResponse.SC_UNAUTHORIZED, "login.jsp");
			if(request.getParameter("login_error") != null){
				response.getWriter().println("401/logerror");
				//System.out.println("401/logerror");
			}
		}else{
			filterChain.doFilter(request, response);
		}
	}
}
