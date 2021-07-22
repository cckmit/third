package org.opoo.apps.security.webapp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

public class FormLoginMapImpl implements FormLoginMap {
	private static final long serialVersionUID = -3707662034847561107L;
	
    protected final static Log logger = LogFactory.getLog(FormLoginMapImpl.class);

    private Map<Object, FormLogin> formLoginDefinitions = new LinkedHashMap<Object, FormLogin>();

    private UrlMatcher urlMatcher = new AntUrlPathMatcher();

    private boolean stripQueryStringFromUrls = true;

	public FormLoginMapImpl(UrlMatcher urlMatcher) {
		this.urlMatcher = urlMatcher;
	}
	
	/**
	 * 
	 * @param urlMatcher
	 * @param formLoginDefinitions
	 */
	public FormLoginMapImpl(UrlMatcher urlMatcher, Map<String, FormLogin> uncompileFormLoginBeans) {
		this.urlMatcher = urlMatcher;

		if(uncompileFormLoginBeans != null){
			Iterator<Entry<String, FormLogin>> iterator = uncompileFormLoginBeans.entrySet().iterator();
	        while (iterator.hasNext()) {
	            Map.Entry<String, FormLogin> entry = iterator.next();
	            addFormLogin(entry.getKey(), entry.getValue());
	        }
		}
	}


	void addFormLogin(String pattern, FormLogin formLogin){
		formLoginDefinitions.put(urlMatcher.compile(pattern), formLogin);

        if (logger.isDebugEnabled()) {
            logger.debug("Added form login pattern: " + pattern + "; details: " + formLogin);
        }
	}
	
	
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public FormLogin lookupFormLogin(String url){
		if(formLoginDefinitions.isEmpty()){
			return null;
		}
		
		if (stripQueryStringFromUrls) {
			// String query string - see SEC-953
			// Strip anything after a question mark symbol, as per SEC-161. See also SEC-321
			int firstQuestionMarkIndex = url.indexOf("?");

			if (firstQuestionMarkIndex != -1) {
				url = url.substring(0, firstQuestionMarkIndex);
			}
		}
		
        if (urlMatcher.requiresLowerCaseUrl()) {
            url = url.toLowerCase();

            if (logger.isDebugEnabled()) {
                logger.debug("Converted URL to lowercase, from: '" + url + "'; to: '" + url + "'");
            }
        }
        
        for(Map.Entry<Object, FormLogin> entry: formLoginDefinitions.entrySet()){
            Object p = entry.getKey();
            
            boolean matched = urlMatcher.pathMatchesUrl(p, url);

            if (logger.isDebugEnabled()) {
                logger.debug("Candidate is: '" + url + "'; pattern is " + p + "; matched=" + matched);
            }

            if (matched) {
                return entry.getValue();
            }
        }

        return null;
	}
	
	
	
    public void setUrlMatcher(UrlMatcher matcher) {
        this.urlMatcher = matcher;
    }

    public UrlMatcher getUrlMatcher() {
        return urlMatcher;
    }

    /**
     * If set to 'true', the query string will be stripped from the request URL before
     * attempting to find a matching filter chain. This is the default value.
     */
    public void setStripQueryStringFromUrls(boolean stripQueryStringFromUrls) {
        this.stripQueryStringFromUrls = stripQueryStringFromUrls;
    }

	public Collection<FormLogin> getFormLogins() {
		return new ArrayList<FormLogin>(formLoginDefinitions.values());
	}
}
