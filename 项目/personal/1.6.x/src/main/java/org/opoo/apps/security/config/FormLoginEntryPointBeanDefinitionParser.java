package org.opoo.apps.security.config;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.opoo.apps.security.webapp.FormLogin;
import org.opoo.apps.security.webapp.FormLoginFilterEntryPoint;
import org.opoo.apps.security.webapp.FormLoginMapImpl;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.RegexUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;


/**
 * ±íµ¥µÇÂ¼ÅäÖÃ½âÎöÆ÷¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class FormLoginEntryPointBeanDefinitionParser extends AbstractSingleBeanDefinitionParser implements BeanDefinitionParser {
	public static final String LOGIN_PAGE = "login-page";
	public static final String FORM_LOGIN_MAP = "form-login-map";
	public static final String FORM_LOGIN = "form-login";
	public static final String PATTERN = "pattern";
	public static final String TARGET_URL = "target-url";
	public static final String FORCE_HTTPS = "force-https";
	
	public static final String STRIP_QUERY_STRING_FROM_URLS = "strip-query-string-from-urls";// stripQueryStringFromUrls;
	
	static final String PATH_TYPE = "path-type";
	static final String DEF_PATH_TYPE_ANT = "ant";
	static final String OPT_PATH_TYPE_REGEX = "regex";

	static final String LOWERCASE_COMPARISONS = "lowercase-comparisons";
	static final String DEF_LOWERCASE_COMPARISONS = "true";
	
	static final String ALWAYS_USE_DEFAULT_TARGET_URL = "always-use-default-target"; 
	
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Class getBeanClass(Element element) {
		return FormLoginFilterEntryPoint.class;
	}


	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext, org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		String forHttps = element.getAttribute(FORCE_HTTPS);
		if(StringUtils.hasText(forHttps)){
			builder.addPropertyValue("forceHttps", "true".equalsIgnoreCase(forHttps) ? Boolean.TRUE : Boolean.FALSE);
		}
		
		String loginFormUrl = element.getAttribute(LOGIN_PAGE);
		if(StringUtils.hasText(loginFormUrl)){
			builder.addPropertyValue("loginFormUrl", loginFormUrl);
		}
		
		String alwaysUseDefault = element.getAttribute(ALWAYS_USE_DEFAULT_TARGET_URL);
		if(StringUtils.hasText(loginFormUrl) && "true".equalsIgnoreCase(alwaysUseDefault)){
			builder.addPropertyValue("alwaysUseDefaultTargetUrl", Boolean.TRUE);
		}
		
		
		
		
		
		Element map = DomUtils.getChildElementByTagName(element, FORM_LOGIN_MAP);
		if(map != null){
			UrlMatcher urlMatcher = new AntUrlPathMatcher();
		    boolean stripQueryStringFromUrls = true;
		    
		    String pathType = map.getAttribute(PATH_TYPE);
		    if(OPT_PATH_TYPE_REGEX.equalsIgnoreCase(pathType)){
		    	urlMatcher = new RegexUrlPathMatcher();
		    }
		    
		    String lc = map.getAttribute(LOWERCASE_COMPARISONS);
		    if(!StringUtils.hasText(lc)){
		    	lc = DEF_LOWERCASE_COMPARISONS;
		    }
		    if(DEF_LOWERCASE_COMPARISONS.equals(lc)){
		    	if(urlMatcher instanceof RegexUrlPathMatcher){
		    		((RegexUrlPathMatcher)urlMatcher).setRequiresLowerCaseUrl(true);
		    	}
		    }
		    
		    
		    String s = map.getAttribute(STRIP_QUERY_STRING_FROM_URLS);
		    if(!StringUtils.hasText(s) || "true".equalsIgnoreCase(s)){
		    	stripQueryStringFromUrls = true;
		    }else{
		    	stripQueryStringFromUrls = false;
		    }
		    
		    
		    
		    List list = DomUtils.getChildElementsByTagName(map, FORM_LOGIN);
		    Map<String,FormLogin> formLogins = new LinkedHashMap<String, FormLogin>();
		    Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				Element elt = (Element) iterator.next();
				String pattern = elt.getAttribute(PATTERN);
	            String loginPage = elt.getAttribute(LOGIN_PAGE);
	            String targetUrl = elt.getAttribute(TARGET_URL);
	            
	            
	            if (!StringUtils.hasLength(pattern)) {
	                parserContext.getReaderContext().error("The attribute '" + PATTERN + "' is required here.", elt);
	            }
	            
	            if (!StringUtils.hasLength(loginPage) && !StringUtils.hasLength(targetUrl)) {
	                parserContext.getReaderContext().error("The attribute '" + LOGIN_PAGE
	                		+ "' or '" + TARGET_URL + "' is required here.", elt);
	            }
	            
	            FormLogin formLogin = new FormLogin();
	            formLogin.setLoginPage(loginPage);
	            formLogin.setTargetUrl(targetUrl);
	            
	            formLogins.put(pattern, formLogin);
			}
		    
		    
		    
		    
		    
		    FormLoginMapImpl formLoginMap = new FormLoginMapImpl(urlMatcher, formLogins);
		    formLoginMap.setStripQueryStringFromUrls(stripQueryStringFromUrls);
			
			builder.addPropertyValue("formLoginMap", formLoginMap);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	protected void doParse2(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		String forHttps = element.getAttribute(FORCE_HTTPS);
		if(StringUtils.hasText(forHttps)){
			builder.addPropertyValue("forceHttps", "true".equalsIgnoreCase(forHttps) ? Boolean.TRUE : Boolean.FALSE);
		}
		
		String loginFormUrl = element.getAttribute(LOGIN_PAGE);
		if(StringUtils.hasText(loginFormUrl)){
			builder.addPropertyValue("loginFormUrl", loginFormUrl);
		}
		
		String alwaysUseDefault = element.getAttribute(ALWAYS_USE_DEFAULT_TARGET_URL);
		if(StringUtils.hasText(loginFormUrl) && "true".equalsIgnoreCase(alwaysUseDefault)){
			builder.addPropertyValue("alwaysUseDefaultTargetUrl", Boolean.TRUE);
		}
		
		
		Element map = DomUtils.getChildElementByTagName(element, FORM_LOGIN_MAP);
		if(map != null){
			UrlMatcher urlMatcher = new AntUrlPathMatcher();
		    boolean stripQueryStringFromUrls = true;
		    
		    String pathType = map.getAttribute(PATH_TYPE);
		    if(OPT_PATH_TYPE_REGEX.equalsIgnoreCase(pathType)){
		    	urlMatcher = new RegexUrlPathMatcher();
		    }
		    
		    String lc = map.getAttribute(LOWERCASE_COMPARISONS);
		    if(!StringUtils.hasText(lc)){
		    	lc = DEF_LOWERCASE_COMPARISONS;
		    }
		    if(DEF_LOWERCASE_COMPARISONS.equals(lc)){
		    	if(urlMatcher instanceof RegexUrlPathMatcher){
		    		((RegexUrlPathMatcher)urlMatcher).setRequiresLowerCaseUrl(true);
		    	}
		    }
		    
		    
		    String s = map.getAttribute(STRIP_QUERY_STRING_FROM_URLS);
		    if(!StringUtils.hasText(s) || "true".equalsIgnoreCase(s)){
		    	stripQueryStringFromUrls = true;
		    }else{
		    	stripQueryStringFromUrls = false;
		    }
		    
		    
		    
		    List list = DomUtils.getChildElementsByTagName(map, FORM_LOGIN);
		    Map<String,FormLogin> formLogins = new LinkedHashMap<String, FormLogin>();
		    Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				Element elt = (Element) iterator.next();
				String pattern = elt.getAttribute(PATTERN);
	            String loginPage = elt.getAttribute(LOGIN_PAGE);
	            String targetUrl = elt.getAttribute(TARGET_URL);
	            
	            
	            if (!StringUtils.hasLength(pattern)) {
	                parserContext.getReaderContext().error("The attribute '" + PATTERN + "' is required here.", elt);
	            }
	            
	            if (!StringUtils.hasLength(loginPage) && !StringUtils.hasLength(targetUrl)) {
	                parserContext.getReaderContext().error("The attribute '" + LOGIN_PAGE
	                		+ "' or '" + TARGET_URL + "' is required here.", elt);
	            }
	            
	            FormLogin formLogin = new FormLogin();
	            formLogin.setLoginPage(loginPage);
	            formLogin.setTargetUrl(targetUrl);
	            
	            formLogins.put(pattern, formLogin);
			}
		    
		    
		    
		    
		    
		    FormLoginMapImpl formLoginMap = new FormLoginMapImpl(urlMatcher, formLogins);
		    formLoginMap.setStripQueryStringFromUrls(stripQueryStringFromUrls);
			
			builder.addPropertyValue("formLoginMap", formLoginMap);
		}
	}
}
