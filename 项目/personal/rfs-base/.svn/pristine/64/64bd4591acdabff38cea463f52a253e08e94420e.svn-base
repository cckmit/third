package cn.redflagsoft.base.security.webapp;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.web.servlet.JCaptchaServlet;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.WebAuthenticationDetailsSource;
import org.springframework.security.ui.logout.LogoutFilter;
import org.springframework.security.ui.logout.LogoutHandler;
import org.springframework.security.util.TextUtils;


public abstract class AdminLoginHelper {
	private static final Log logger = LogFactory.getLog(AdminLoginHelper.class);
	
	public static final String ADMIN_LOGIN_FORM_USERNAME_KEY = "username";
	public static final String ADMIN_LOGIN_FORM_PASSWORD_KEY = "password";
	public static final String ADMIN_LOGIN_FORM_CAPTCHA_KEY = "captcha";
	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";
	
	
	private static WebAuthenticationDetailsSource webAuthenticationDetailsSource;// = new WebAuthenticationDetailsSource();
	static{
		webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
		webAuthenticationDetailsSource.setClazz(RFSWebAuthenticationDetails.class);
	}
	
	
	private static ApplicationEventPublisher getEventPublisher(){
		return (ApplicationEventPublisher) Application.getContext();
	}
	
	public static void attemptLogout(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null){
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
				handlers[i].logout(request, response, auth);
			}
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public static void attemptLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		attemptLogin(request, response, null);
	}
	public static void attemptLogin(HttpServletRequest request, HttpServletResponse response, AuthenticationCallback callback) throws IOException {
		if (requiresAuthentication(request, response)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Request is to process authentication");
			}
			Authentication authResult;
	
			try {
				//Check captcha
				onPreAuthentication(request, response);
				if(callback != null){
					callback.onPreAuthentication(request, response);
				}
				
				authResult = attemptAuthentication(request);
				if(callback != null){
					callback.onPostAuthentication(request, response, authResult);
				}
			} catch (AuthenticationException failed) {
				//Authentication failed
				unsuccessfulAuthentication(request, response, failed);
				return;
			}
			successfulAuthentication(request, response, authResult);
			return;
		}
	}
	
	private static void onPreAuthentication(HttpServletRequest request,	HttpServletResponse response) {
		if(logger.isDebugEnabled()){
			logger.debug("检验验证码");
		}
		String captcha = obtainCaptcha(request);

		if(StringUtils.isBlank(captcha)){
			throw new CaptchaInvalidException("必须输入验证码！");
		}
		if(!JCaptchaServlet.validateResponse(request, captcha)){
			throw new CaptchaInvalidException("验证码错误！");
		}
	}

	private static void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed) {
		SecurityContextHolder.getContext().setAuthentication(null);

        if (logger.isDebugEnabled()) {
            logger.debug("Updated SecurityContextHolder to contain null Authentication");
            logger.debug("Authentication request failed: " + failed.toString());
        }

        try {
            HttpSession session = request.getSession(false);

            if (session != null) {
                request.getSession().setAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY, failed);
            }
        }
        catch (Exception ignored) {
        }
	}

	private static void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success: " + authResult.toString());
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);

		if (logger.isDebugEnabled()) {
			logger.debug("Updated SecurityContextHolder to contain the following Authentication: '"
					+ authResult + "'");
		}

		// Fire event
		ApplicationEventPublisher eventPublisher = getEventPublisher();
		if (eventPublisher != null) {
			eventPublisher
					.publishEvent(new InteractiveAuthenticationSuccessEvent(
							authResult, AdminLoginHelper.class));
		}
		// 到管理页面
	}

	private static boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response){
		String username = obtainUsername(request);
        String password = obtainPassword(request);
        return (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password));
	}
	
	private static Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
	        String username = obtainUsername(request);
	        String password = obtainPassword(request);
	        
	        if(StringUtils.isNotBlank(password) && isAllStar(password)){
	        	//password = request.getParameter("hashedPassword");
	        	password = LoginUtils.getHashedPassword(request);
	        }

	        if (username == null) {
	            username = "";
	        }

	        if (password == null) {
	            password = "";
	        }
	        
	        username = username.trim();

	        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

	        // Place the last username attempted into HttpSession for views
	        HttpSession session = request.getSession(false);

	        if (session != null) {
	            request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, TextUtils.escapeEntities(username));
	        }

	        // Allow subclasses to set the "details" property
	        setDetails(request, authRequest);

	        AuthenticationManager authenticationManager = Application.getContext().get("authenticationManager", AuthenticationManager.class);
	        
	        return authenticationManager.authenticate(authRequest);
	    }
	 
	private static void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		//WebAuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();
		//authenticationDetailsSource.setClazz(RFSWebAuthenticationDetails.class);
		authRequest.setDetails(webAuthenticationDetailsSource.buildDetails(request));
	}
	
	

	/**
	 * Enables subclasses to override the composition of the password, such as
	 * by including additional values and a separator.
	 * <p>
	 * This might be used for example if a postcode/zipcode was required in
	 * addition to the password. A delimiter such as a pipe (|) should be used
	 * to separate the password and extended value(s). The
	 * <code>AuthenticationDao</code> will need to generate the expected
	 * password in a corresponding manner.
	 * </p>
	 * 
	 * @param request
	 *            so that request attributes can be retrieved
	 * 
	 * @return the password that will be presented in the
	 *         <code>Authentication</code> request token to the
	 *         <code>AuthenticationManager</code>
	 */
	protected static String obtainPassword(HttpServletRequest request) {
		return request.getParameter(ADMIN_LOGIN_FORM_PASSWORD_KEY);
	}

	/**
	 * Enables subclasses to override the composition of the username, such as
	 * by including additional values and a separator.
	 * 
	 * @param request
	 *            so that request attributes can be retrieved
	 * 
	 * @return the username that will be presented in the
	 *         <code>Authentication</code> request token to the
	 *         <code>AuthenticationManager</code>
	 */
	protected static String obtainUsername(HttpServletRequest request) {
		return request.getParameter(ADMIN_LOGIN_FORM_USERNAME_KEY);
	}
	
	
	protected static String obtainCaptcha(HttpServletRequest request){
		return request.getParameter(ADMIN_LOGIN_FORM_CAPTCHA_KEY);
	}
	
	private static boolean isAllStar(String string){
		for(int i = 0 ; i < string.length() ; i++){
			if(string.charAt(i) != '*'){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 *
	 */
	public static interface AuthenticationCallback{
		
		void onPreAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException;
		
		void onPostAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult)
				throws AuthenticationException;
	}
	
	public static void main(String[] args){
		System.out.println(isAllStar("****************"));
	}
}
