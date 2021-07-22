package org.opoo.apps;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public class GlobalBeansInitializer implements ApplicationContextAware {
	private static final Log log = LogFactory.getLog(GlobalBeansInitializer.class);
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		//SpringBeansHolder h = new SpringBeansHolder(ac, AppsGlobals.getBeansHolder());
		//h.put("dataSources", ac.getBeansOfType(DataSource.class));
		//AppsGlobals.setBeansHolder(h);
		
		//log.debug("initializing global BeansHolder: " + h);
	}
	
	protected static class SpringBeansHolder extends DefaultBeansHolder implements BeansHolder{
		private final ApplicationContext ac;
		private final BeansHolder holder;
		private SpringBeansHolder(ApplicationContext ac, BeansHolder holder){
			this.ac = ac;
			this.holder = holder;
		}
		
		public Object get(String name) {
			Object o = super.get(name);
			if(o == null && holder != null){
				o = holder.get(name);
			}
			if(o == null){
				o = ac.getBean(name);
			}
			log.debug("get bean from BeansHolder: " + name + " in " + this + " is " + o);
			return o;
		}

		@SuppressWarnings("unchecked")
		public <T> T get(String name, Class<T> cls) {
			T t = super.get(name, cls);
			if(t == null && holder != null){
				t = holder.get(name, cls);
			}
			if(t == null){
				try {
					t = (T) ac.getBean(name);
				} catch (BeansException e) {
					//throw e;
				}
			}
			log.debug("get bean from BeansHolder: " + name + " in " + this + " is " + t);
			return t;
		}

		public void put(String name, Object object) {
			super.put(name, object);
			
			
		}
	}
}
