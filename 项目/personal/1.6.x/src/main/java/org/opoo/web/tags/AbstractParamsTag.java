package org.opoo.web.tags;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 *
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public abstract class AbstractParamsTag extends BodyTagSupport  {
   /**
	 * 
	 */
	private static final long serialVersionUID = 4629639587558696302L;
private Map<String, String> parameters = new HashMap<String, String>();

   public void addParameter(String name, String value) {
       parameters.put(name, value);
   }

   public Map<String,String> getParameters() {
       return parameters;
   }

   public void clearParameters() {
       parameters.clear();
   }

   public void release() {
       parameters.clear();
       super.release();
   }
}
