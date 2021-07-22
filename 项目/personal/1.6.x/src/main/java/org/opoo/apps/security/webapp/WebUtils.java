package org.opoo.apps.security.webapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;

public class WebUtils {
	
	public static final String TOKEN_KEY = "__APPS_TOKEN_KEY";
	public static final String TOKEN_PARAM = ".tk";
	public static final String TARGET_URL_PARAM = "ru";
	
	
	
	public static FormLogin getFormLogin(HttpServletRequest request){
		return (FormLogin) org.springframework.web.util.WebUtils.getSessionAttribute(request, FormLogin.SESSION_KEY);
	}
	
	public static void removeFormLogin(HttpServletRequest request){
		org.springframework.web.util.WebUtils.setSessionAttribute(request, FormLogin.SESSION_KEY, null);
	}
	
	public static void setFormLogin(HttpServletRequest request, FormLogin formLogin){
		org.springframework.web.util.WebUtils.setSessionAttribute(request, FormLogin.SESSION_KEY, formLogin);
	}
	
	public static FormLogin getAndRemoveFormLogin(HttpServletRequest request){
		return (FormLogin) getAndRemoveSessionAttribute(request, FormLogin.SESSION_KEY);
	}
	
	
	public static Object getAndRemoveSessionAttribute(HttpServletRequest request, String name){
		Assert.notNull(request, "Request must not be null");
		Object result = null;
		HttpSession session = request.getSession(false);
		if(session != null){
			result = session.getAttribute(name);
			session.removeAttribute(name);
		}
		return result;
	}
	
	
	public static String generateToken(HttpSession session){
		UUID uuid = UUID.randomUUID();
		String sid = uuid.toString();
		session.setAttribute(TOKEN_KEY, sid);
		return sid;
	}
	
	public static boolean validateToken(HttpSession session, String token){
		String sid = (String) session.getAttribute(TOKEN_KEY);
		if(sid == null){
			return false;
		}
		return sid.equals(token);
	}
	
	public static boolean validateToken(HttpServletRequest request){
		HttpSession session = request.getSession();
		String token = request.getParameter(TOKEN_PARAM);
		return validateToken(session, token);
	}
	
	
	public static String getTargetUrl(HttpServletRequest request){
		return request.getParameter(TARGET_URL_PARAM);
	}
	
	public static String getEncodeTargetUrl(HttpServletRequest request){
		String url = getTargetUrl(request);
		if(url != null){
			return encodeUrl(url);
		}
		return url;
	}
	
	public static String encodeUrl(String url){
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 not supported. Shouldn't be possible");
		}
	}
	
	
//	/**
//	 * 
//	 * @param session
//	 * @return
//	 */
//	public static String getTargetUrl(HttpSession session){
//		FormLogin formLogin = (FormLogin) session.getAttribute(FormLogin.SESSION_KEY);
//		if(formLogin != null && formLogin.getTargetUrl() != null){
//			return formLogin.getTargetUrl();
//		}
//		SavedRequest savedRequest = (SavedRequest) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY);
//		if(savedRequest != null){
//			return savedRequest.getFullRequestUrl();
//		}
//		return null;
//	}
}
