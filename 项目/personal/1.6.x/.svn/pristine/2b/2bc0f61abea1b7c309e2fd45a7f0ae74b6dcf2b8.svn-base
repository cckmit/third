package org.opoo.apps.id.sequence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.springframework.util.ClassUtils;


/**
 * Sequence π‹¿Ì∆˜°£
 * @author Alex Lin(alex@opoo.org)
 *
 */
@SuppressWarnings("unchecked")
public class SequenceManager {
	public static final String SEQUENCE_PROVIDER_CLASSNAME = "SequenceProvider.className";
	
	
	
	private static final Log log = LogFactory.getLog(SequenceManager.class);
	private static SequenceProvider provider;// = new SimpleSequenceProvider();
	private static boolean initialized = false;
	static {
		initialize();
	}
	
	public static synchronized void initialize(){
		provider = null;
		if(Application.isContextInitialized()){
			provider = Application.getContext().get("sequenceProvider", SequenceProvider.class);
		}
		if(provider == null){
			String className = AppsGlobals.getProperty(SEQUENCE_PROVIDER_CLASSNAME);
			if (className != null) {
				log.debug("Loading custom SequenceProvider from class: " + className);
				try {
					Class<SequenceProvider> c = ClassUtils.forName(className);
					provider =  c.newInstance();
					log.debug("Custom SequenceProvider '" + className + "' loaded successfully");
				} catch (Exception e) {
					log.error("Error loading custom SequenceProvider " + className,	e);
				}
			}
		}
		
		if(provider == null) {
			provider = new DefaultSequenceProvider();
		}
		log.debug("init SequenceProvider: " + provider);
		initialized = true;
	}
	
	
	public static boolean isInitialized(){
		return initialized;
	}
	
	public static Long getNext(String name){
		return provider.getSequence(name).getNext();
	}
	
	public static Long getCurrent(String name){
		return provider.getSequence(name).getCurrent();
	}
	
	public static Sequence getSequence(String name){
		return provider.getSequence(name);
	}
	
	public static SequenceProvider getSequenceProvider(){
		return provider;
	}
}
