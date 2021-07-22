/*
 * $Id: ParametersSetter.java 5447 2012-03-19 09:45:41Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.aop;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.Message;
import org.opoo.apps.Model;
import org.opoo.apps.ModelAware;

import cn.redflagsoft.base.process.WorkProcess;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.ParameterNameAware;
import com.opensymphony.xwork2.interceptor.ParametersInterceptor;
import com.opensymphony.xwork2.util.InstantiatingNullHandler;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.opensymphony.xwork2.util.OgnlValueStack;
import com.opensymphony.xwork2.util.TextParseUtil;

/**
 * 参数设置器。
 * 使用 XWork 的参数设置器，底层使用 OGNL 语法。
 * 
 * @author Alex Lin
 * @since 1.5
 */
public class ParametersSetter {
	static{
//		if(ObjectFactory.getObjectFactory() == null){
//			ObjectFactory.setObjectFactory(new ObjectFactory());
//		}
	}
	
	private Set<Pattern> excludeParams = Collections.emptySet();
	private boolean devMode = true;
	protected Log log = LogFactory.getLog(getClass());
	
	public ParametersSetter(){
		devMode = AppsGlobals.isDevMode();
	}
	
	@SuppressWarnings("unchecked")
	public void setParameters(Object object, final Map<?,?> parameters) {
//      System.out.println(object + ":" + parameters);
	    if(parameters == null || object == null){
			return;
		}
		
		ParameterNameAware parameterNameAware = (object instanceof ParameterNameAware) ? (ParameterNameAware) object : null;
		//OgnlValueStack.reset();
		Map<?,?> params = new TreeMap<Object,Object>(parameters);
		
		
		//去除平台信息
		params.remove("__rc");
		params.remove("_dc");
		
		if(object instanceof WorkProcess){
			params.remove("processType");
			params.remove("processName");
		}
		if(params.isEmpty()){
			log.debug("参数为空，不设置。");
			return;
		}
		
        if (log.isDebugEnabled()) {
            log.debug("ParametersInterceptor - [invoke]: Setting params " + getParameterLogMap(params));
        }
        
        if(ObjectFactory.getObjectFactory() == null){
			ObjectFactory.setObjectFactory(new ObjectFactory());
		}
		
		//ValueStack stack = ValueStackFactory.getFactory().createValueStack();
		OgnlValueStack stack = new OgnlValueStack();
		stack.getContext().put(InstantiatingNullHandler.CREATE_NULL_OBJECTS, Boolean.TRUE);
    	stack.push(object);

 /*    	stack.setValue("aaa", new String[]{"sdsd"});
    	stack.setValue("user.name", new String[]{"ssssssssssssdsd"}, true);
		stack.setValue("users1[0].name", "aasdklaskds", true);*/
		
    	for (Iterator<?> iterator = params.entrySet().iterator(); iterator.hasNext();) {
    		Map.Entry<?,?> entry = (Map.Entry<?,?>) iterator.next();
    		String name = entry.getKey().toString();

    		boolean acceptableName = acceptableName(name)
	                    && (parameterNameAware == null
	                    || parameterNameAware.acceptableParameterName(name));

    		if (acceptableName) {
    			Object value = entry.getValue();
	            try {
	            	stack.setValue(name, value, devMode);
	            } catch (Exception e) {
                    if (devMode) {
                        String developerNotification = LocalizedTextUtil.findText(ParametersInterceptor.class, "devmode.notification", ActionContext.getContext().getLocale(), "Developer Notification:\n{0}", new Object[]{
                                e.getMessage()
                        });
                        log.error("ParametersInterceptor - [setParameters]: " + developerNotification);
                        if (object instanceof ValidationAware) {
//					        ((ValidationAware) object).addActionMessage(developerNotification);
                        	((ValidationAware) object).addFieldError(name, developerNotification);
                        }else if(object instanceof Message){
                        	((Message) object).setSuccess(false);
                        	((Message) object).addError(name, developerNotification);
                        }else if(object instanceof ModelAware){
                        	Model model = ((ModelAware) object).getModel();
                        	if(model != null){
                        		model.addError(name, developerNotification);
                        	}
                        }
                    } else {
                        log.error("ParametersInterceptor - [setParameters]: Unexpected Exception caught setting '"+name+"' on '"+ object.getClass()+": " + e.getMessage());
                    }
                }
            }
        }
	}    
    
	public static String getParameterLogMap(Map<?,?> parameters) {
        if (parameters == null) {
            return "NONE";
        }

        StringBuffer logEntry = new StringBuffer();
        for (Iterator<?> paramIter = parameters.entrySet().iterator(); paramIter.hasNext();) {
            Map.Entry<?,?> entry = (Map.Entry<?,?>) paramIter.next();
            logEntry.append(String.valueOf(entry.getKey()));
            logEntry.append(" => ");
            if (entry.getValue() instanceof Object[]) {
                Object[] valueArray = (Object[]) entry.getValue();
                logEntry.append("[ ");
                for (int indexA = 0; indexA < (valueArray.length - 1); indexA++) {
                    Object valueAtIndex = valueArray[indexA];
                    logEntry.append(valueAtIndex);
                    logEntry.append(String.valueOf(valueAtIndex));
                    logEntry.append(", ");
                }
                logEntry.append(String.valueOf(valueArray[valueArray.length - 1]));
                logEntry.append(" ] ");
            } else {
                logEntry.append(String.valueOf(entry.getValue())).append(", ");
            }
        }

        return logEntry.toString();
    }
	
    protected boolean acceptableName(String name) {
        if (name.indexOf('=') != -1 || name.indexOf(',') != -1 || name.indexOf('#') != -1
                || name.indexOf(':') != -1 || isExcluded(name)) {
            return false;
        } else {
            return true;
        }
    }
    
    protected boolean isExcluded(String paramName) {
        if (!this.excludeParams.isEmpty()) {
            for (Pattern pattern : excludeParams) {
                Matcher matcher = pattern.matcher(paramName);
                if (matcher.matches()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    /**
     * Gets a set of regular expressions of parameters to remove
     * from the parameter map
     * 
     * @return A set of compiled regular expression patterns
     */
	protected Set<Pattern> getExcludeParamsSet() {
        return excludeParams;
    }

    /**
     * Sets a comma-delimited list of regular expressions to match 
     * parameters that should be removed from the parameter map.
     * 
     * @param commaDelim A comma-delimited list of regular expressions
     */
	public void setExcludeParams(String commaDelim) {
        Collection<String> excludePatterns = asCollection(commaDelim);
        if (excludePatterns != null) {
            excludeParams = new HashSet<Pattern>();
            for (String pattern : excludePatterns) {
                excludeParams.add(Pattern.compile(pattern));
            }
        }
    }
    
    /**
     * Return a collection from the comma delimited String.
     *
     * @param commaDelim the comma delimited String.
     * @return A collection from the comma delimited String. Returns <tt>null</tt> if the string is empty.
     */
    @SuppressWarnings("unchecked")
	private Collection<String> asCollection(String commaDelim) {
        if (commaDelim == null || commaDelim.trim().length() == 0) {
            return null;
        }
        return TextParseUtil.commaDelimitedStringToSet(commaDelim);
    }

	/**
	 * @return the devMode
	 */
	public boolean isDevMode() {
		return devMode;
	}

	/**
	 * @param devMode the devMode to set
	 */
	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}
	
	
	public static class TestBean{
		private Long value;
		private Double dd;

		/**
		 * @return the value
		 */
		public Long getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		public void setValue(Long value) {
			this.value = value;
		}

		/**
		 * @return the dd
		 */
		public Double getDd() {
			return dd;
		}

		/**
		 * @param dd the dd to set
		 */
		public void setDd(Double dd) {
			this.dd = dd;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		TestBean bean = new TestBean();
		OgnlValueStack stack = new OgnlValueStack();
		stack.getContext().put(InstantiatingNullHandler.CREATE_NULL_OBJECTS, Boolean.TRUE);
    	stack.push(bean);
    	stack.setValue("dd", new String[]{"2,342,432.0"});
    	stack.setValue("value", new String[]{"234234"});
    	System.out.println(bean.getDd());
	}
}
