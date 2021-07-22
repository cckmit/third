package org.opoo.apps.web.filter;

import java.io.IOException;

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
import org.opoo.apps.security.OldPresence;
import org.opoo.apps.security.OldPresenceManager;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.opoo.apps.security.impl.OldPresenceManagerImpl;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class OldPresenceFilter extends OncePerRequestFilter implements HttpSessionListener
{
	private static final Log log = LogFactory.getLog(OldPresenceFilter.class);

    public static boolean setup = false;
    public static final String SESSION_PRESENCE = "apps.presence";
	private static OldPresenceManager presenceManager;
	private boolean stopped = false;

	
    public void init() {
		setup = AppsGlobals.isSetup();
		stopped = false;
		//System.out.println(this + " 初始化了");
	}

    public void destroy()
    {
        stopped = true;
    }

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (!setup) {
			chain.doFilter(request, response);
			return;
		}
		HttpSession session = request.getSession();
		OldPresence presence = (OldPresence) session.getAttribute(SESSION_PRESENCE);
		User user = UserHolder.getNullableUser();
		if (user != null) {
			if (user.getUserId() <= 0L) {
				log.warn("Ignoring presence request for system user.");
				chain.doFilter(request, response);
				return;
			}
		}
		//System.out.println("User: " + user + "/ presense: " + presence);
		//System.out.println("原有的 Presence: " + presence);
		if (presence == null) {
			presence = presenceManager.createPresence(user, session.getId(), request.getRemoteAddr());
			//System.out.println("User: " + user + "/ presense: " + presence);
			if (presence == null) {
				chain.doFilter(request, response);
				return;
			}
			session.setAttribute(SESSION_PRESENCE, presence);
		}
		// try
		// {
		presence.setLastUpdateTime(System.currentTimeMillis());
		// }
		// catch(UnauthorizedException e)
		// {
		// Log.error(e);
		// }
		User presenceUser = presence.getUser();
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
    }

    public void sessionCreated(HttpSessionEvent httpsessionevent)
    {
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent)
    {
        if(stopped){
            return;
        }
        HttpSession session = sessionEvent.getSession();
        if(presenceManager == null || session == null){
            return;
        }
        if(!(presenceManager instanceof OldPresenceManagerImpl)){
        	return;
        }
        OldPresence presence = ((OldPresenceManagerImpl)presenceManager).getPresence(session.getId());
        if(presence != null){
            presenceManager.setOffline(presence);
        }
    }

	/**
	 * @return the presenceManager
	 */
	public OldPresenceManager getPresenceManager() {
		return presenceManager;
	}

	/**
	 * @param presenceManager the presenceManager to set
	 */
	public void setPresenceManager(OldPresenceManager presenceManager) {
		OldPresenceFilter.presenceManager = presenceManager;
	}

}
