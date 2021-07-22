package org.opoo.apps.security.context;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.security.MutableUser;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserManager;
import org.opoo.apps.security.UserWrapper;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.event.authentication.AbstractAuthenticationEvent;
import org.springframework.security.event.authentication.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.ui.WebAuthenticationDetails;


/**
 * 用户登录事件监听器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SecurityApplicationListener implements ApplicationListener {
	private static final Log log = LogFactory.getLog(SecurityApplicationListener.class);
	
	private UserManager userManager;
	
	
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof AbstractAuthenticationEvent){
			log.debug("处理用户事件：" + event.getClass().getName());
			
			AbstractAuthenticationEvent e = (AbstractAuthenticationEvent) event;
			
			if(event instanceof InteractiveAuthenticationSuccessEvent){
				User user = (User) e.getAuthentication().getPrincipal();
				//log.debug(user.getClass().getName());
				//设置状态
				if(user instanceof MutableUser){
					MutableUser mu = (MutableUser) user;
					mu.setLastLoginTime(mu.getLoginTime());
					mu.setLoginTime(new Date());
				}
				
				Object details = e.getAuthentication().getDetails();
				if(details instanceof WebAuthenticationDetails){
					final String loginAddress = ((WebAuthenticationDetails)details).getRemoteAddress();
					user = new UserWrapper(user){
						private static final long serialVersionUID = -8380326207341937453L;
						public String getLoginAddress() {
							return loginAddress;
						}
					};
				}
				
				userManager.loginSuccess(user);
			}else if(event instanceof AuthenticationFailureBadCredentialsEvent){
				Object o = e.getAuthentication().getPrincipal();
				if(o instanceof String){
					String username = (String) o;
					userManager.loginFailure(username);
				}
			}
		}
	}


	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return userManager;
	}


	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
