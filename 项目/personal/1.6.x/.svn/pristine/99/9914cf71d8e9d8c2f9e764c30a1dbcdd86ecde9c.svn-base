package org.opoo.apps.web.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.PresenceManager;
import org.opoo.apps.security.Presence;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.logout.LogoutFilter;
import org.springframework.security.ui.logout.LogoutHandler;
import org.springframework.web.filter.OncePerRequestFilter;

public class PresenceFilter extends OncePerRequestFilter implements HttpSessionListener{
	private static final Log log = LogFactory.getLog(PresenceFilter.class);

    public static boolean setup = false;
    public static final String SESSION_PRESENCE = "apps.presence";
	private static PresenceManager presenceManager;
	private boolean stopped = false;
	
	public void init() {
		setup = AppsGlobals.isSetup();
		stopped = false;
		// System.out.println(this + " 初始化了");
	}

	public void destroy() {
		stopped = true;
	}
	    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!setup) {
			filterChain.doFilter(request, response);
			return;
		}
		HttpSession session = request.getSession();
		Presence presence = (Presence) session.getAttribute(SESSION_PRESENCE);
		User user = UserHolder.getNullableUser();
//		if (user != null) {
//			if (user.getUserId() <= 0L) {
//				log.warn("Ignoring presence request for system user.");
//				filterChain.doFilter(request, response);
//				return;
//			}
//		}
		
		
//		if(user == null){
//			filterChain.doFilter(request, response);
//			return;
//		}
		
		
		//System.out.println("User: " + user + "/ presense: " + presence);
		//System.out.println("原有的 Presence: " + presence);
		// Verify that the presence has been created
		if (presence == null && user != null) {
			presence = presenceManager.createPresence(user, session.getId(), request.getRemoteAddr());
			//System.out.println("User: " + user + "/ presense: " + presence);
			
			// It's possible for the presence to be null if the system hasn't yet initialized
			if (presence == null) {
				filterChain.doFilter(request, response);
				return;
			}
			
			// Store the presence in the session.
			session.setAttribute(SESSION_PRESENCE, presence);
		}
		// try
		// {
		// Update the time in the presence
		presence.setLastUpdateTime(System.currentTimeMillis());
		// }
		// catch(UnauthorizedException e)
		// {
		// Log.error(e);
		// }
		
		// User has logged in or logged out - reset the presence in the manager
		User presenceUser = presence.getUser();
		if(presenceUser != null && user == null
				|| presenceUser == null && user != null
				|| presenceUser != null && user != null && !presenceUser.getUsername().equals(user.getUsername())){
			
			log.debug("User has logged in or logged out - reset the presence in the manager");
			
			//将原来的设置为离线
			presenceManager.setOffline(presence);
			
			presence = presenceManager.createPresence(user, session.getId(), /*presence.getRemoteAddr()*/request.getRemoteAddr());
            // We've changed the presence, reset the session
            session.setAttribute(SESSION_PRESENCE, presence);
		}
		boolean prohibit = AppsGlobals.getProperty("ProhibitMultipleUserLogin", false);
		if(prohibit){
			//检查presence有效性
			if(presence.getLoginStatus() == -100){
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if(auth != null){
					LogoutFilter logoutFilter = Application.getContext().get("_logoutFilter", LogoutFilter.class);
					try {
						java.lang.reflect.Field field = LogoutFilter.class.getDeclaredField("handlers");
						field.setAccessible(true);
						LogoutHandler[] handlers;
						try {
							handlers = (LogoutHandler[]) field.get(logoutFilter);
							logger.info("登出处理器数量：" + handlers.length);
							
							if (logger.isDebugEnabled()) {
								logger.debug("Logging out user '" + auth + "' and redirecting to logout page");
							}   
					
							for (int i = 0; i < handlers.length; i++) {
								handlers[i].logout(request, response, auth);
							}
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				}
			}
			if(presence.getLoginStatus() != -100){
			//如果当前登陆用户登陆成功，则将当前登陆用户对应的以前的presense设置为无效
				List<Presence> list = presenceManager.getPresences(user);
				if(list!=null && list.size()>0){
					for(Presence p: list){
						if(!p.equals(presence)){
							//设置为强制退出
							p.setLoginStatus(-100);
						}
					}
				}
			}
		}
		// Pass the request on.
		filterChain.doFilter(request, response);
		
		
		/*
		//User presenceUser = presence.get
//		System.out.println(user);
//		System.out.println(presenceUser);
		if (presenceUser != null && user == null 
				|| presenceUser == null && user != null 
				|| presenceUser != null && user != null && presenceUser.getUserId().longValue() != user.getUserId().longValue()) {
			//System.out.println("sssssssssssssssssssssssssssssssssssssssssssssss");
			presenceManager.setOffline(presence);
			presence = presenceManager.createPresence(user, session.getId(), presence.getIPAddress());
			session.setAttribute(SESSION_PRESENCE, presence);
		}
		
		chain.doFilter(request, response);
		
		
		
		
		
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("本次获取的Authentication为： " + auth);
		
		User user = UserHolder.getNullableUser();
		if(user != null && !(auth instanceof WebAuthentication) && auth.getDetails() instanceof WebAuthenticationDetails){
			WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
			NewPresenceImpl impl = new NewPresenceImpl(user, details.getSessionId(), details.getRemoteAddress());
			SecurityContextHolder.getContext().setAuthentication(new WebAuthentication(auth, impl));
			
			System.out.println("设置了Authentication。");
		}
		filterChain.doFilter(request, response);
		
		
		*/
	}
	
	/*
	public static final class WebAuthentication implements Authentication{
		private static final long serialVersionUID = 8893996326727542623L;
		private final Authentication auth;
		private NewPresence presence;
		
		private WebAuthentication(Authentication auth, NewPresence presence){
			this.auth = auth;
			this.presence = presence;
		}
		
		
		public NewPresence getPresence() {
			return presence;
		}


		public void setPresence(NewPresence presence) {
			this.presence = presence;
		}


		public GrantedAuthority[] getAuthorities() {
			return auth.getAuthorities();
		}

		public Object getCredentials() {
			return auth.getCredentials();
		}

		public Object getDetails() {
			return auth.getDetails();
		}

		public Object getPrincipal() {
			return auth.getPrincipal();
		}

		public boolean isAuthenticated() {
			return auth.isAuthenticated();
		}

		public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
			auth.setAuthenticated(isAuthenticated);
		}

		public String getName() {
			return auth.getName();
		}
	}
*/

	public void sessionCreated(HttpSessionEvent se) {
		//no-op (presence change is done in filter)
		log.debug("sessionCreated");
	}

	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		 // Only process if the server hasn't been stopped.
        if (stopped) {
            return;
        }

        HttpSession session = sessionEvent.getSession();

        //if the presence manager is not configured, do nothing
        if (presenceManager == null || session == null) {
            return;
        }

        // Get the presence associated with this session and mark it as offline.
        Presence presence = presenceManager.getPresence(session.getId());
        if (presence != null) {
        	log.debug("sessionDestroyed, setOffline: " + presence.getUsername());
            presenceManager.setOffline(presence);
        }
	}

	public PresenceManager getPresenceManager() {
		return presenceManager;
	}

	public void setPresenceManager(PresenceManager presenceManager) {
		PresenceFilter.presenceManager = presenceManager;
	}

}
