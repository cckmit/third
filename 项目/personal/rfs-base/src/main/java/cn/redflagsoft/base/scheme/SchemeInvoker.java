/*
 * $Id: SchemeInvoker.java 6350 2014-02-28 07:19:27Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.lifecycle.Application;
import org.springframework.util.StopWatch;

import cn.redflagsoft.base.aop.ParametersSetter;
import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.aop.interceptor.Prepareable;
import cn.redflagsoft.base.scheme.event.SchemeEvent;
import cn.redflagsoft.base.scheme.schemes.user.UserScheme;

import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.NoParameters;
import com.opensymphony.xwork2.interceptor.PrefixMethodInvocationUtil;
import com.opensymphony.xwork2.util.profiling.UtilTimerStack;

/**
 * Scheme执行器。
 * 
 * @author Alex Lin
 *
 */
public class SchemeInvoker {
	
	private static final Log log = LogFactory.getLog(SchemeInvoker.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	//private static final Executor executor = Executors.new
	
	private final static String PREPARE_PREFIX = "prepare";
	private final static String ALT_PREPARE_PREFIX = "prepareDo";
	private static boolean alwaysInvokePrepare = true;
	
	private final static String VALIDATE_PREFIX = "validate";
	private final static String ALT_VALIDATE_PREFIX = "validateDo";
	private static boolean alwaysInvokeValidate = true;
	
	/**
	 * 执行Scheme方法。
	 * 
	 * @param object scheme对象
	 * @return scheme执行结果。
	 * @throws SchemeException
	 */
	public static Object invoke(Scheme object) throws Exception{
		return invoke(object, null);
	}
	/**
	 * 执行Scheme方法。
	 * 
	 * @param object scheme对象
	 * @param methodName 方法名
	 * @return scheme执行结果。
	 * @throws SchemeException
	 */
	public static Object invoke(Scheme object, String methodName) throws Exception{
//		if (IS_DEBUG_ENABLED) {
//        	log.debug("Executing scheme method = " + methodName);
//        }
        String profileKey = "scheme";
        UtilTimerStack.push(profileKey);
        
        Class<? extends Scheme> clazz = object.getClass();
        
        String schemeName = object.getBeanName();
        if(schemeName == null){
        	schemeName = clazz.getName();
        	object.setBeanName(schemeName);
        }
        
    	if(StringUtils.isBlank(methodName)){
    		methodName = object.getMethod();
			if (IS_DEBUG_ENABLED) {
        		log.debug("Executing config scheme method '" + schemeName + "#" +  methodName + "'.");
			}
    	}else if(!methodName.equals(object.getMethod())){
    		if (IS_DEBUG_ENABLED) {
        		log.debug("Scheme '" + schemeName + "' config method is '" + object.getMethod() 
        				+ "', but actual invoke '" + methodName + "'.");
			}
    		object.setMethod(methodName);
    	}
    	
        
//        if(methodName == null && object instanceof AbstractWorkScheme){
//        	methodName = ((AbstractWorkScheme) object).getMethod();
//			if (IS_DEBUG_ENABLED) {
//	    		log.debug("Executing config scheme method = " + methodName);
//			}
//        }
        
        if(StringUtils.isBlank(methodName)){
        	methodName = "scheme";
        	object.setMethod(methodName);
        }
        
        if (IS_DEBUG_ENABLED) {
        	log.debug("Executing scheme: " + schemeName + "#" + methodName);
        }
        
        try {
        	long start = System.currentTimeMillis();
        	Object invoke;
        	Method method;
        	if(/*methodName == null || */"scheme".equals(methodName)){
        		invoke = object.doScheme();
        		method = object.getClass().getMethod("doScheme");
        	}else{
	        	//String altMethodName = "do" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
	            //Method method = object.getClass().getMethod(altMethodName, new Class[0]);
	            /*Method*/ method = lookupSchemeMethod(clazz, methodName);
	        	//Object methodResult = method.invoke(object);
	            //return methodResult;
	            
	            invoke = method.invoke(object);
	            //methodName = method.getName();
        	}
        	
            long time = System.currentTimeMillis() - start;
            
            if (IS_DEBUG_ENABLED) {
            	log.debug("Scheme executed: " + schemeName + "#" + methodName + " " + time + "ms.");
            }
            
            dispatchSchemeExecuteEvent(object, time, method, invoke);
            
            return invoke;
        } catch (NoSuchMethodException e) {
        	if(IS_DEBUG_ENABLED){
        		log.error("Executing scheme", e);
        	}
        	
            throw new SchemeException("The " + methodName + "() is not defined in scheme " + clazz + "");
        } catch (InvocationTargetException e) {
        	if(IS_DEBUG_ENABLED){
        		log.error("Executing scheme", e);
        	}
            Throwable t = e.getTargetException();

            if (t instanceof Exception) {
                throw (Exception) t;
            } else {
                throw e;
            }
        } catch (IllegalArgumentException e) {
        	if(IS_DEBUG_ENABLED){
        		log.error("Executing scheme", e);
        	}
        	
			throw e;
		} catch (IllegalAccessException e) {
			if(IS_DEBUG_ENABLED){
        		log.error("Executing scheme", e);
        	}
			
			throw new SchemeException(e);
		}finally{
			UtilTimerStack.pop(profileKey);
		}
	}
	

	/**
	 * Scheme的执行方法，在这个方法依次进行：
	 * 参数设置、方法设置、数据准备（Prepareable-do*）、数据验证（validate、ValidationAware-do*）
	 * 
	 * 注意，调用这个方法必须在ApspectJ中去掉相关的拦截器。
	 * 
	 * @param object
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static Object invokeAll(Scheme object, String methodName) throws Exception{
//		if (IS_DEBUG_ENABLED) {
//        	log.debug("Executing scheme method = " + methodName);
//		}
        
        //参数设置
        if(object instanceof ParametersAware && !(object instanceof NoParameters)){
        	if(IS_DEBUG_ENABLED){
    			log.debug("为 Scheme '" + object + "' 设置参数");
    		}
        	String setparamsKey = "setparams";
        	UtilTimerStack.push(setparamsKey);
        	try{
	        	ParametersAware bean = (ParametersAware) object;
	        	getParametersSetter().setParameters(bean, bean.getParameters());
        	//}catch(Exception e){
	        //如果是非验证消息，则直接抛出异常
        	}finally{
        		UtilTimerStack.pop(setparamsKey);
        	}
        	//设置的过程中，可以出现错误
        	if(object instanceof ValidationAware && ((ValidationAware) object).hasErrors()){
        		return toModel((ValidationAware)object);
        	}
        }
        
        //被调用方法设置
        Class<? extends Scheme> clazz = object.getClass();
        String schemeName = object.getBeanName();
        if(schemeName == null){
        	schemeName = clazz.getName();
        	object.setBeanName(schemeName);
        }
    	if(StringUtils.isBlank(methodName)){
    		methodName = object.getMethod();
			if (IS_DEBUG_ENABLED) {
        		log.debug("Executing config scheme method '" + schemeName + "#" +  methodName + "'.");
			}
    	}else if(!methodName.equals(object.getMethod())){
    		if (IS_DEBUG_ENABLED) {
        		log.debug("Scheme '" + schemeName + "' config method is '" + object.getMethod() 
        				+ "', but actual invoke '" + methodName + "'.");
			}
    		object.setMethod(methodName);
    	}
        
//        if(methodName == null && object instanceof AbstractWorkScheme){
//        	methodName = ((AbstractWorkScheme) object).getMethod();
//			if (IS_DEBUG_ENABLED) {
//	    		log.debug("Executing config scheme method = " + methodName);
//			}
//        }
        
        if(StringUtils.isBlank(methodName)){
        	methodName = "scheme";
        	object.setMethod(methodName);
        }
        if (IS_DEBUG_ENABLED) {
        	log.debug("Executing scheme: " + schemeName + "#" + methodName);
        }
        
        //准备数据
        if(object instanceof Prepareable){
         	try {
         		Method method = PrefixMethodInvocationUtil.getPrefixedMethod(new String[] { PREPARE_PREFIX, ALT_PREPARE_PREFIX }, methodName, object);
         		if (method != null) {
        			method.invoke(object);
        		}
         	}
         	catch(Exception e) {
         		// just in case there's an exception while doing reflection, 
         		// we still want prepare() to be able to get called.
         		log.warn("an exception occured while trying to execute prefixed method", e);
         	}
         	if (alwaysInvokePrepare) {
         		((Prepareable) object).prepare();
         	}
         	//准备数据的过程中，可以出现错误
        	if(object instanceof ValidationAware && ((ValidationAware) object).hasErrors()){
        		return toModel((ValidationAware)object);
        	}
         }
        
        //调用前验证
        if(object instanceof Validateable){
        	// keep exception that might occured in validateXXX or validateDoXXX
        	Exception exception = null; 
        	
            Validateable validateable = (Validateable) object;
            if (log.isDebugEnabled()) {
            	log.debug("Invoking validate() on scheme "+validateable);
            }
            
            try {
            	Method method = PrefixMethodInvocationUtil.getPrefixedMethod(new String[] { VALIDATE_PREFIX, ALT_VALIDATE_PREFIX }, 
            			methodName, object);
            	if(method != null){
            		method.invoke(object);
            	}
            }
            catch(Exception e) {
            	// If any exception occurred while doing reflection, we want 
            	// validate() to be executed
            	log.warn("an exception occured while executing the prefix method", e);
            	exception = e;
            }
            
            if (alwaysInvokeValidate) {
            	validateable.validate();
            }
            
            if (exception != null) { 
            	// rethrow if something is wrong while doing validateXXX / validateDoXXX 
            	throw exception;
            }
            
            if(object instanceof ValidationAware && ((ValidationAware) object).hasErrors()){
        		return toModel((ValidationAware)object);
        	}
        }
        
        
        String profileKey = "scheme";
        UtilTimerStack.push(profileKey);
        try {
        	long start = System.currentTimeMillis();
        	Object invoke;
        	Method method;
        	if(/*methodName == null || */"scheme".equals(methodName)){
        		invoke = object.doScheme();
        		method = object.getClass().getMethod("doScheme");
        	}else{
	        	//String altMethodName = "do" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
	            //Method method = object.getClass().getMethod(altMethodName, new Class[0]);
	            /*Method*/ method = lookupSchemeMethod(clazz, methodName);
	        	//Object methodResult = method.invoke(object);
	            //return methodResult;
	            
	            invoke = method.invoke(object);
	            //methodName = method.getName();
        	}
        	
            long time = System.currentTimeMillis() - start;
            
            if (IS_DEBUG_ENABLED) {
            	log.debug("Scheme executed: " + schemeName + "#" + methodName + " " + time + "ms.");
            }
            
            dispatchSchemeExecuteEvent(object, time, method, invoke);
            
            return invoke;
        } catch (NoSuchMethodException e) {
        	if(IS_DEBUG_ENABLED){
        		log.error("Executing scheme", e);
        	}
        	
            throw new SchemeException("The " + methodName + "() is not defined in scheme " + clazz + "");
        } catch (InvocationTargetException e) {
        	if(IS_DEBUG_ENABLED){
        		log.error("Executing scheme", e);
        	}
            Throwable t = e.getTargetException();

            if (t instanceof Exception) {
                throw (Exception) t;
            } else {
                throw e;
            }
        } catch (IllegalArgumentException e) {
        	if(IS_DEBUG_ENABLED){
        		log.error("Executing scheme", e);
        	}
        	
			throw e;
		} catch (IllegalAccessException e) {
			if(IS_DEBUG_ENABLED){
        		log.error("Executing scheme", e);
        	}
			
			throw new SchemeException(e);
		}finally{
			UtilTimerStack.pop(profileKey);
		}
	}
	
	private static ParametersSetter getParametersSetter(){
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private static Model toModel(ValidationAware object){
		Model model = new Model();
		model.setSuccess(false);
		model.setErrors(object.getFieldErrors());
		
		Collection<?> messages = object.getActionMessages();
		Collection<?> errors = object.getActionErrors();
		if(messages != null && !messages.isEmpty()){
			model.setMessage((String) messages.iterator().next());
		}
		if(errors != null && !errors.isEmpty()){
			model.setMessage((String) errors.iterator().next());
		}
		return model;
	}
	
	/**
	 * @param object
	 * @param time
	 */
	private static void dispatchSchemeExecuteEvent(Scheme object, long time, Method method, Object result) {
		EventDispatcher dispatcher = Application.getContext().get("eventDispatcher", EventDispatcher.class);
		if(dispatcher != null){
			SchemeEvent event = new SchemeEvent(SchemeEvent.Type.EXECUTED, object, time, method, result);
			dispatcher.dispatchEvent(event);
		}
	}


	/**
	 * 缓存已查找过的方法。
	 */
	private static Map<String,Method> methods = new ConcurrentHashMap<String,Method>();
	/**
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException
	 */
	private static Method lookupSchemeMethod(Class<? extends Scheme> clazz, String methodName) throws NoSuchMethodException{
		String key = clazz.getName() + "." + methodName;
		Method m = methods.get(key);
		if(m != null){
			return m;
		}
		
		
		String suffix = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		String name = "do" + suffix;
		
		try{
			m = clazz.getMethod(name);
		}catch(NoSuchMethodException e){
			if(IS_DEBUG_ENABLED){
				log.debug(e.getMessage());
			}
		}
		
		if(m == null){
			name = "view" + suffix;
			//m = clazz.getMethod(name);
			try{
				m = clazz.getMethod(name);
			}catch(NoSuchMethodException e){
				if(IS_DEBUG_ENABLED){
					log.debug(e.getMessage());
				}
			}
		}
		
		
		if(m == null){
			throw new NoSuchMethodException(clazz.getName() + ".do" + suffix + " or " + clazz.getName() + ".view" + suffix);
		}
		
		methods.put(key, m);
		return m;
	}
	/*
	private static Method lookupSchemeMethod(Object object, String methodName) throws Exception{
		long start = System.currentTimeMillis();
		String suffix = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		String altMethodName = "do" + suffix;
		String viewMethodName = "view" + suffix;
		String[] names = new String[]{altMethodName, viewMethodName};
		System.out.println(System.currentTimeMillis() - start);
		
		Method m = lookupSchemeMethod(object, names);
		System.out.println(System.currentTimeMillis() - start);
		return m;
	}
	private static Method lookupSchemeMethod(Object object, String[] methodNames) throws Exception{
		Exception e = null;
		for(String name: methodNames){
			try {
				return object.getClass().getMethod(name);
			} catch (NoSuchMethodException ex) {
				e = ex;
			}
		}
		
		throw e;
	}
	*/
	
	
	/**
	 * 解析SchemeInfo信息。
	 * @param schemeInfo
	 * @return
	 */
	public static SchemeInfo parseSchemeInfo(String schemeInfo){
		String[] info = schemeInfo.split("!");
		if (info.length >= 2 && StringUtils.isNotBlank(info[0]) && StringUtils.isNotBlank(info[1])) {
			return new SchemeInfo(info[0], info[1]);
		}
		return new SchemeInfo(info[0], null);
	}
	
	/**
	 * 调用Scheme。
	 * 
	 * @param schemeManager
	 * @param schemeInfo
	 * @param parameters
	 * @return
	 */
	public static Object invoke(SchemeManager schemeManager, String schemeInfo, Map<?,?> parameters){
		SchemeInfo info = parseSchemeInfo(schemeInfo);
		return invoke(schemeManager, info, parameters);
	}
	
	/**
	 * 调用Scheme。
	 * 
	 * @param schemeManager
	 * @param schemeInfo
	 * @param parameters
	 * @return
	 */
	public static Object invoke(SchemeManager schemeManager, SchemeInfo schemeInfo, Map<?,?> parameters){
		Scheme scheme = schemeManager.getScheme(schemeInfo.getName());
		if(parameters != null && scheme instanceof ParametersAware){
			((ParametersAware) scheme).setParameters(parameters);
		}
		try {
			return invoke(scheme, schemeInfo.getMethod());
		} catch (Exception e) {
			throw new SchemeException(e);
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		StopWatch stop = new StopWatch("测试个别方法的性能");
		Class<UserScheme> clazz = UserScheme.class;
		
		System.out.println(UserScheme.class.getName() 
				+"#"+ UserScheme.class.getMethod("doScheme"));
		
		
		
		
		stop.start("new UserScheme");
		UserScheme us = new UserScheme();
		stop.stop();
		
		
		stop.start("SchemeInvoker.lookupSchemeMethod");
		Method m = SchemeInvoker.lookupSchemeMethod(clazz, "updateUserInfo");
		stop.stop();
		System.out.println(m + " # " + m.getName());
		
		stop.start("SchemeInvoker.lookupSchemeMethod");
		m = SchemeInvoker.lookupSchemeMethod(clazz, "updateUserInfo");
		stop.stop();
		System.out.println(m);
		
		stop.start("SchemeInvoker.lookupSchemeMethod");
		m = SchemeInvoker.lookupSchemeMethod(clazz, "updateUserInfo");
		stop.stop();
		System.out.println(m);
		
		
		
		stop.start("doGetUserInfo");
		m = us.getClass().getMethod("viewGetUserInfo");
		stop.stop();
		
		System.out.println(m);
		System.out.println(stop.prettyPrint());
	}
}
