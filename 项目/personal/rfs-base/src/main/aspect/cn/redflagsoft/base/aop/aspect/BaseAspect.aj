package cn.redflagsoft.base.aop.aspect;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.reflect.MethodSignature;
import org.opoo.apps.Model;

import cn.redflagsoft.base.aop.Callback;
import cn.redflagsoft.base.aop.ParametersSetter;
import cn.redflagsoft.base.aop.PrintDataHandler;
import cn.redflagsoft.base.aop.XWorkValidator;
import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.aop.interceptor.Prepareable;
import cn.redflagsoft.base.aop.annotation.PrintAfter;
import cn.redflagsoft.base.scheme.WorkScheme;
import cn.redflagsoft.base.scheme.interceptor.TaskWorkHandlerForWorkSchemeInterceptor;
import cn.redflagsoft.base.web.struts2.interceptor.Printable;

import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.PrefixMethodInvocationUtil;
import com.opensymphony.xwork2.util.profiling.UtilTimerStack;

/**
 * 基础切面。
 * 
 * 验证，参数设置，WorkScheme业务处理，打印数据处理等都定义在这个切面中。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public aspect BaseAspect{
//	declare precedence: TransactionAspect, BaseAspect;
	
	

	private static final Log log = LogFactory.getLog(BaseAspect.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	private TaskWorkHandlerForWorkSchemeInterceptor taskWorkHandlerForWorkSchemeInterceptor;
	private ParametersSetter parametersSetter;// = new ParametersSetter();
	private XWorkValidator validator = new XWorkValidator();
	private PrintDataHandler printDataHandler = new PrintDataHandler();
	
	private final static String PREPARE_PREFIX = "prepare";
	private final static String ALT_PREPARE_PREFIX = "prepareDo";
	private boolean alwaysInvokePrepare = true;
	
	private final static String VALIDATE_PREFIX = "validate";
	private final static String ALT_VALIDATE_PREFIX = "validateDo";
	private boolean alwaysInvokeValidate = true;
	
//	public pointcut awaresetter(): call(* *.*()) && this(ParametersAware);
	
//	public pointcut awaresetter(ParametersAware bean): execution(public * ParametersAware+.*()) 
//		&& !execution(* ParametersAware.*()) && this(bean) && @annotation(Parameters);
//	
//	before(ParametersAware bean): awaresetter(bean){
//		System.out.println("Call from jp: " + thisJoinPoint + " --> " + bean);
//	}
	
	
	public pointcut executionOfAllSchemeDoMethods(): execution(public * cn.redflagsoft.base.scheme.Scheme+.do*());

	public pointcut executionOfAllSchemeViewMethods(): execution(public * cn.redflagsoft.base.scheme.Scheme+.view*());

	public pointcut executionOfAllSchemeMethods(): executionOfAllSchemeDoMethods() || executionOfAllSchemeViewMethods();

	public pointcut executionOfProcessMethods(): 
		execution(public * cn.redflagsoft.base.process.WorkProcess+.execute(java.util.Map, boolean));

	/*
	public pointcut executionOfAllPrintableActionMethods(Printable printable): 
		execution(public String org.opoo.apps.web.struts2.AbstractAppsAction+.*()) && !execution(* *.get*()) && this(printable);
	*/
	public pointcut executionOfAllPrintableActionMethods(Printable printable): 
		execution(* org.opoo.apps.web.struts2.AbstractAppsAction+.*(..)) && @annotation(PrintAfter) && this(printable);
	
	public pointcut executionOfWorkSchemeDoMethods(): 
		execution(* cn.redflagsoft.base.scheme.WorkScheme+.do*(..));
	
	
	boolean around():execution(public static boolean cn.redflagsoft.base.aop..AspectJ.isCompileTimeWoven()){
		return true;
	}
	
	
	/**
	 * 设置参数。
	 * 
	 * @param bean
	 */
	/*before(ParametersAware bean): executionOfAllSchemeMethods() && this(bean) && !this(com.opensymphony.xwork2.interceptor.NoParameters){
		if(IS_DEBUG_ENABLED){
			log.debug("为 Scheme '" + bean + "' 设置参数");
		}
		parametersSetter.setParameters(bean, bean.getParameters());
	}
	*/
	Object around(ParametersAware bean): executionOfAllSchemeMethods() && this(bean) && !this(com.opensymphony.xwork2.interceptor.NoParameters){
		if(IS_DEBUG_ENABLED){
			log.debug("为 Scheme '" + bean + "' 设置参数");
		}
		String profileKey = "setparams";
		UtilTimerStack.push(profileKey);
		try{
			parametersSetter.setParameters(bean, bean.getParameters());
		}finally{
			UtilTimerStack.pop(profileKey);
		}

		//设置的过程中，可以出现错误
    	if(bean instanceof ValidationAware && ((ValidationAware) bean).hasErrors()){
    		if(IS_DEBUG_ENABLED){
    			log.debug("参数设置过程中出现错误，后续步骤不执行。");
    		}
    		return toModel((ValidationAware)bean);
    	}
    	
    	return proceed(bean);
	}
	
	/**
	 * 当 Scheme 实现了 Prepareable 接口时，在 do* 方法参数设置之后调用 prepare 方法。
	 * @param bean
	 */
	/*before(Prepareable bean): executionOfAllSchemeDoMethods() && this(bean){
		if(IS_DEBUG_ENABLED){
			log.debug("为 Scheme '" + bean + "' 准备数据");
		}
		bean.prepare();
	}*/
	
	Object around(Prepareable bean): executionOfAllSchemeDoMethods() && this(bean){
		if(IS_DEBUG_ENABLED){
			log.debug("为 Scheme '" + bean + "' 准备数据");
		}
		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
		//Method method = methodSignature.getMethod();
		String methodName = methodSignature.getName();
		
		if(IS_DEBUG_ENABLED){
			log.debug("准备方法：" + methodName);
		}
		
		try {
     		Method method = PrefixMethodInvocationUtil.getPrefixedMethod(new String[] { PREPARE_PREFIX, ALT_PREPARE_PREFIX }, methodName, bean);
     		if (method != null) {
    			method.invoke(bean);
    		}
     	}
     	catch(Exception e) {
     		// just in case there's an exception while doing reflection, 
     		// we still want prepare() to be able to get called.
     		log.warn("an exception occured while trying to execute prefixed method", e);
     	}
		
     	if (alwaysInvokePrepare) {
     		log.debug("执行prepare(): " + bean);
     		bean.prepare();
     	}
     	//准备数据的过程中，可以出现错误
    	if(bean instanceof ValidationAware && ((ValidationAware) bean).hasErrors()){
    		if(IS_DEBUG_ENABLED){
    			log.debug("数据准备过程中出现错误，后续步骤不执行。");
    		}
    		return toModel((ValidationAware)bean);
    	}
    	
    	return proceed(bean);
	}

	/**
	 * 当 Scheme 实现了 Validateable 接口时，在 do* 方法参数设置之后调用 validate 方法。
	 * @param bean
	 */
	/*before(Validateable bean): executionOfAllSchemeDoMethods() && this(bean){
		if(IS_DEBUG_ENABLED){
			log.debug("validate scheme: " + bean);
		}
		bean.validate();
	}*/
	Object around(Validateable bean): executionOfAllSchemeDoMethods() && this(bean){
		if(IS_DEBUG_ENABLED){
			log.debug("validate scheme: " + bean);
		}
		//bean.validate();
		
		// keep exception that might occured in validateXXX or validateDoXXX
    	Exception exception = null; 
    	
        Validateable validateable = (Validateable) bean;
        if (log.isDebugEnabled()) {
        	log.debug("Invoking validate() on scheme " + validateable);
        }
        
        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
		//Method method = methodSignature.getMethod();
		String methodName = methodSignature.getName();
		
		if(IS_DEBUG_ENABLED){
			log.debug("验证方法：" + methodName);
		}
        
        try {
        	Method method = PrefixMethodInvocationUtil.getPrefixedMethod(new String[] { VALIDATE_PREFIX, ALT_VALIDATE_PREFIX }, 
        			methodName, bean);
        	if(method != null){
        		method.invoke(bean);
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
        	if(exception instanceof RuntimeException){
        		throw (RuntimeException) exception;
        	}else{
        		throw new RuntimeException(exception);
        	}
        }
        
        if(bean instanceof ValidationAware && ((ValidationAware) bean).hasErrors()){
        	if(IS_DEBUG_ENABLED){
    			log.debug("方法验证出现错误，后续步骤不执行：" + methodName);
    		}
    		return toModel((ValidationAware)bean);
    	}
        
        return proceed(bean); 
	}
	

	@SuppressWarnings("unchecked")
	before(Map map): executionOfProcessMethods() && args(map, ..) && !this(com.opensymphony.xwork2.interceptor.NoParameters){
		Object wp = thisJoinPoint.getThis();
		
		if(IS_DEBUG_ENABLED){
			log.debug("为 WorkProcess '" + wp + "' 设置参数");
		}
		parametersSetter.setParameters(wp, map);
	}
//	@SuppressWarnings("unchecked")
//	@Before("executionOfProcessMethods() && args(map, ..) && !this(com.opensymphony.xwork2.interceptor.NoParameters)")
//	public void setParametersForProcesses(JoinPoint jp, Map map) {
//		Object wp = jp.getThis();
//		log.debug("为 WorkProcess '" + wp + "' 设置参数");
//		parametersSetter.setParameters(wp, map);
//	}

	
	/**
	 * 如果实现了验证接口，调用验证。
	 * 
	 */
	Object around(final ValidationAware scheme):executionOfAllSchemeDoMethods() && this(scheme){
		if(IS_DEBUG_ENABLED){
			log.debug("验证(ValidationAware) :" + scheme);
		}
		Callback action = new Callback(){
			public Object doInCallback() throws Throwable{
				return proceed(scheme);
			}
		};
		
		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
		Method method = methodSignature.getMethod();
		
		//return validator.validate(action, scheme, method.getName());
		try{
			return validator.validate(action, scheme, method.getName());
		}catch(Throwable t){
			//log.error(String.format("验证WorkScheme(%s#%s)出错", scheme, method.getName()), t);
			if(t instanceof RuntimeException){
        		throw (RuntimeException) t;
        	}else{
        		throw new RuntimeException(t);
        	}
		}
	}
	
//	@Around("executionOfAllSchemeDoMethods() && this(scheme)")
//	public Object validationSchemeMethods(ProceedingJoinPoint jp, ValidationAware scheme) throws Throwable{
//		log.debug("验证 :" + scheme);
//		Callback action = new Callback.JoinPointCallback(jp);
//		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
//		Method method = methodSignature.getMethod();
//	
//		return validator.validate(action, scheme, method.getName());
//	}
	
	
//	@AfterReturning(value="executionOfAllPrintableActionMethods(printable)", returning="returnValue")
//	public void preparePrintDataIfRequired(JoinPoint jp, Printable printable, String returnValue){
//		//System.out.println(jp + "," + printable);
//		printDataHandler.preparePrintDataIfRequired(returnValue, printable);
//	}
	
	
	/**
	 * WorkScheme 的业务驱动拦截器。
	 * 
	 */
	Object around(final WorkScheme ws): executionOfWorkSchemeDoMethods() && target(ws){
		Callback action = new Callback(){
			public Object doInCallback() throws Throwable{
				return proceed(ws);
			}
		};
		try{
			return taskWorkHandlerForWorkSchemeInterceptor.handle(action, ws);
		}catch(Throwable t){
			log.error(String.format("业务驱动(%s)出错", ws), t);
			if(t instanceof RuntimeException){
        		throw (RuntimeException) t;
        	}else{
        		throw new RuntimeException(t);
        	}
		}
	}
	
//	@Around("executionOfWorkSchemeDoMethods() && target(ws)")
//	public Object taskHandlerAroundWorkScheme(ProceedingJoinPoint jp, WorkScheme ws) throws Throwable{
//		Callback action = new Callback.JoinPointCallback(jp);
//		return taskWorkHandlerForWorkSchemeInterceptor.handle(action, ws);
//	}
	
	/**
	 * 打印相关的拦截器。
	 */
	after(Printable printable) returning(String returnValue):executionOfAllPrintableActionMethods(printable){
		if(printable instanceof ValidationAware && ((ValidationAware) printable).hasErrors()){
			if(IS_DEBUG_ENABLED){
				log.debug("当前对象有验证错误，不处理打印配置。");
			}
		}else{
			printDataHandler.preparePrintDataIfRequired(returnValue, printable);
		}
	}

	/**
	 * @param taskWorkHandlerForWorkSchemeInterceptor the taskWorkHandlerForWorkSchemeInterceptor to set
	 */
	public void setTaskWorkHandlerForWorkSchemeInterceptor(
			TaskWorkHandlerForWorkSchemeInterceptor taskWorkHandlerForWorkSchemeInterceptor) {
		this.taskWorkHandlerForWorkSchemeInterceptor = taskWorkHandlerForWorkSchemeInterceptor;
	}

	/**
	 * @param parametersSetter the parametersSetter to set
	 */
	public void setParametersSetter(ParametersSetter parametersSetter) {
		this.parametersSetter = parametersSetter;
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(XWorkValidator validator) {
		this.validator = validator;
	}

	/**
	 * @param printDataHandler the printDataHandler to set
	 */
	public void setPrintDataHandler(PrintDataHandler printDataHandler) {
		this.printDataHandler = printDataHandler;
	}
	
	public void setAlwaysInvokePrepare(boolean alwaysInvokePrepare){
		this.alwaysInvokePrepare = alwaysInvokePrepare;
	}
	
	public void setAlwaysInvokeValidate(boolean alwaysInvokeValidate){
		this.alwaysInvokeValidate = alwaysInvokeValidate;
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
}