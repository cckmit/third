package org.opoo.apps.web.struts2;

import java.lang.reflect.InvocationTargetException;

/**
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AppsActionExceptionHandlerImpl implements AppsActionExceptionHandler {
	
	//private static final Log log = LogFactory.getLog(AppsActionExceptionHandlerImpl.class);
	
	public void handle(AbstractAppsAction action, Exception e) throws Exception {
		//if(e instanceof AccessDeniedException){
		//	throw (AccessDeniedException)e;
		//}
		//log.error("ActionÖ´ÐÐ³ö´í£º" + action, e);
		//action.model.setException(e);
		
		if(e instanceof InvocationTargetException){
			Throwable t = ((InvocationTargetException)e).getTargetException();
            if (t instanceof Exception) {
                throw (Exception) t;
            } else {
                throw e;
            }
		}
	}
}
