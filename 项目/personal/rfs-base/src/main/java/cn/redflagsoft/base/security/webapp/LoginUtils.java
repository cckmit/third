package cn.redflagsoft.base.security.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.web.servlet.JCaptchaServlet;
import org.opoo.lang.DefaultRandomStringGenerator;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import com.octo.captcha.Captcha;

/**
 * 校验串相关工具类。
 * 
 * 当前台使用增强型安全登录时，密码加密后再传输到后台，格式：md5(md5(password) + "|" + challenge)。
 * 其中challege是在创建登录表单时，后台产生的随机串。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class LoginUtils {
	private static final Log log = LogFactory.getLog(LoginUtils.class);
	public static final String CAPTCHA_REQUIRED_KEY = "__RFS_CAPTCHA_REQUIRED";
	public static final String CHALLENGE_KEY = "__RFS_CHALLENGE_KEY";
	public static final String TOKEN_KEY = "__RFS_TOKEN_KEY";
	public static final String AES_KEY = "__RFS_AES_KEY";
	public static final String AES_IV = "__RFS_AES_IV";
	public static final String TOKEN_PARAM = ".tk";
	public static final String PASSWORD_HASHED_PARAM = ".js";
	public static final String HASHED_PASSWORD_PARAM = ".fk";
	
	//public static final String TARGET_URL_PARAM = "ru";
	
	private static DefaultRandomStringGenerator gen = new DefaultRandomStringGenerator(23);
	private static Md5PasswordEncoder md5 = new Md5PasswordEncoder();
	
	public static String generateChallenge(HttpSession session){
		String sid = gen.getNewString();
		session.setAttribute(CHALLENGE_KEY, sid);
		return sid;
	}
	
	public static String generateToken(HttpSession session){
		String token = gen.getNewString();
//		UUID.randomUUID().toString();
		session.setAttribute(TOKEN_KEY, token);
		return token;
	}
	
	public static String generateAESKey(HttpSession session){
		String key = gen.getNewString(16);
		session.setAttribute(AES_KEY, key);
		return key;
	}
	
	public static String generateAESIV(HttpSession session){
		String iv = gen.getNewString(16);
		session.setAttribute(AES_IV, iv);
		return iv;
	}
	
	public static String getDecryptText(HttpSession session, String data){
		if(data == null || session == null){
			return null;
		}
		
		String key = (String) session.getAttribute(AES_KEY);
		String iv = (String) session.getAttribute(AES_IV);
		session.removeAttribute(AES_KEY);
		session.removeAttribute(AES_IV);
		
		if(key == null || iv == null){
			log.warn("Session 中未存储有效的 key 和 iv。");
			return null;
		}
		
		try {
			return CryptoUtils.decryptByAES(data, key, iv);
		} catch (Exception e) {
			log.error("解密数据出错", e);
		}
		return null;
	}
	
	public static String getBackendChallenge(HttpSession session){
		String sid = (String) session.getAttribute(CHALLENGE_KEY);
		if(sid != null){
			session.removeAttribute(CHALLENGE_KEY);
		}
		return sid;
	}
	
	public static String getBackendToken(HttpSession session){
		String token = (String) session.getAttribute(TOKEN_KEY);
		if(token != null){
			session.removeAttribute(TOKEN_KEY);
		}
		return token;
	}
	
	public static Captcha getBackendCaptcha(HttpSession session){
		Captcha backendCaptcha = (Captcha) session.getAttribute(JCaptchaServlet.PROPERTY_USER_CAPTCHA_KEY);
		session.removeAttribute(JCaptchaServlet.PROPERTY_USER_CAPTCHA_KEY);
		return backendCaptcha;
	}
	
	public static boolean validateToken(HttpSession session, String token){
		String sid = getBackendToken(session);
		if(sid == null){
			return false;
		}
		return sid.equals(token);
	}
	
	public static boolean validateToken(HttpServletRequest request){
		String token = getToken(request);
		if(token == null){
			return false;
		}
		HttpSession session = request.getSession(false);
		if(session == null){
			return false;
		}
		String backendToken = getBackendToken(session);
		return token.equals(backendToken);
	}
	
	
	public static boolean isPasswordHashed(HttpServletRequest request){
		String hashed = request.getParameter(PASSWORD_HASHED_PARAM);
//		System.out.println("[" + hashed + "]");
//		System.out.println();
//		Enumeration<?> parameterNames = request.getParameterNames();
//		while(parameterNames.hasMoreElements()){
//			System.out.println(parameterNames.nextElement());
//		}
//		System.out.println("1".equals(hashed));
		return "1".equals(hashed);
	}
	
	public static String getHashedPassword(HttpServletRequest request){
		return request.getParameter(HASHED_PASSWORD_PARAM/*"hashedPassword"*/);
	}
	
	public static boolean isRequireCaptcha(HttpSession session){
		return session.getAttribute(CAPTCHA_REQUIRED_KEY) != null;
	}
	
	public static void setRequireCaptcha(HttpSession session, boolean f){
		if(f){
			log.debug("设置验证锁定");
			session.setAttribute(CAPTCHA_REQUIRED_KEY, "1");
		}else{
			log.debug("移除验证锁定");
			session.removeAttribute(CAPTCHA_REQUIRED_KEY);
		}
	}
	
//	public static boolean isRequireCaptcha(){
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if(authentication != null && authentication.getDetails() != null){
//			Object object = authentication.getDetails();
//			if(object instanceof RFSWebAuthenticationDetails){
//				RFSWebAuthenticationDetails details = (RFSWebAuthenticationDetails) object;
//				if(details.isRequireCaptcha()){
//					log.debug("用户锁定，需要验证");
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//	
//	public static void setCaptchaSuccess(){
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if(authentication != null && authentication.getDetails() != null){
//			Object object = authentication.getDetails();
//			if(object instanceof RFSWebAuthenticationDetails){
//				RFSWebAuthenticationDetails details = (RFSWebAuthenticationDetails) object;
//				details.setRequireCaptcha(false);
//				log.debug("用户验证成功，解除锁定");
//			}
//		}
//	}
	
	
	public static String getToken(HttpServletRequest request){
		return request.getParameter(TOKEN_PARAM);
	}
	
	public static void plusSessionLoginErrorCount(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		String count = (String)session.getAttribute("__RFS_SESSION_LOGIN_ERROR_COUNT");
		int n = 0;
		if(count == null){
			n = 1;
		}else{
			n = Integer.parseInt(count) + 1;
		}
		session.setAttribute("__RFS_SESSION_LOGIN_ERROR_COUNT", String.valueOf(n));
	}
	
	public static boolean isSessionLoginErrorCountExceed(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session == null){
			return false;
		}
		return isSessionLoginErrorCountExceed(session);
	}
	
	public static boolean isSessionLoginErrorCountExceed(HttpSession session){
		String count = (String)session.getAttribute("__RFS_SESSION_LOGIN_ERROR_COUNT");
		if(count == null){
			return false;
		}else{
			int i = Integer.parseInt(count);
			return i >= getMaxSessionLoginErrorCount();
		}
	}
	
	public static void removeSessionLoginErrorCount(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null){
			session.removeAttribute("__RFS_SESSION_LOGIN_ERROR_COUNT");
		}
	}

	public static int getMaxSessionLoginErrorCount(){
		//取比较大的值相当于不验证。
		return AppsGlobals.getSetupProperty("login.maxSessionLoginErrorCount", Integer.MAX_VALUE);
	}
	
	/**
	 * @param password
	 * @param challengeInSession
	 * @return
	 */
	public static String encodePassword(String password, String challenge) {
		return md5.encodePassword(password + "|" + challenge, null);
	}
	
	public static boolean useToken(){
		return AppsGlobals.getSetupProperty("login.useToken", false);
	}
	
	public static boolean usePasswordHash(){
		return AppsGlobals.getSetupProperty("login.usePasswordHash", false);
	}
	/**
	 * 必要时使用验证码
	 * @return
	 */
	public static boolean useCaptcha(){
		return AppsGlobals.getSetupProperty("login.useCaptcha", true);
	}
	
	/**
	 * 始终使用验证码
	 * @return
	 */
	public static boolean alwaysUseCaptcha(){
		return AppsGlobals.getSetupProperty("login.alwaysUseCaptcha", false);
	}
	
	public static boolean useUsernameEncrypt(){
		return AppsGlobals.getSetupProperty("login.useUsernameEncrypt", false);
	}
}

