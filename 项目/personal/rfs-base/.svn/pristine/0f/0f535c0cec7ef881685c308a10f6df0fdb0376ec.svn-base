package cn.redflagsoft.base.web.struts2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.SecurityUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.WebAuthenticationDetailsSource;
import org.springframework.security.ui.logout.LogoutFilter;
import org.springframework.security.ui.logout.LogoutHandler;

public class FiltersTest {
	private static final Log logger = LogFactory.getLog(FiltersTest.class);

	public void testFindFilter() throws Exception{
		ApplicationEventPublisher eventPublisher = null;
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		String username = "";
		String password = "";
		String msg = "";
		
		
		//need auth
		if(username != null && password != null){
			logger.debug("登录认证");
			
			username = username.trim();
			
			AuthenticationManager authenticationManager = Application.getContext().get("authenticationManager", AuthenticationManager.class);

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();
			authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
		        
			Authentication authResult = null;
			try {
				authResult = authenticationManager.authenticate(authRequest);
			} catch (AuthenticationException e) {
				logger.debug("认证失败：" + e.getMessage(), e);
				
				//login failure
				SecurityContextHolder.getContext().setAuthentication(null);
				msg = e.getMessage(); 
			}
			
			//login success
			if(authResult != null){
				if (logger.isDebugEnabled()) {
		            logger.debug("认证成功: " + authResult.toString());
		        }
				msg = "";
				
				SecurityContextHolder.getContext().setAuthentication(authResult);
				
				if (logger.isDebugEnabled()) {
		            logger.debug("Updated SecurityContextHolder to contain the following Authentication: '" + authResult + "'");
		        }
				
				//fire event
				if (eventPublisher != null) {
		            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
		        }
				
			}
		}
		
		//redirect if current user is admin
		User user = UserHolder.getNullableUser();
		if(user != null && SecurityUtils.isGranted(user, "ROLE_ADMIN")){
			response.sendRedirect("index.jsp");
			return;
		}else{
			msg = "用户没有权限访问系统。";
		}
		
		
		
		
	}
	
	public static void main(String[] args) throws Exception, NoSuchFieldException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		LogoutFilter logoutFilter = Application.getContext().get("_logoutFilter", LogoutFilter.class);
		java.lang.reflect.Field field = LogoutFilter.class.getDeclaredField("handlers");
		//System.out.println(field);
		field.setAccessible(true);
		LogoutHandler[] handlers = (LogoutHandler[]) field.get(logoutFilter);
		
		logger.info("登出处理器数量：" + handlers.length);

		if (logger.isDebugEnabled()) {
			logger.debug("Logging out user '" + auth + "' and redirecting to logout page");
		}   

		for (int i = 0; i < handlers.length; i++) {
			//handlers[i].logout(request, response, auth);
		}
	}
	
}
