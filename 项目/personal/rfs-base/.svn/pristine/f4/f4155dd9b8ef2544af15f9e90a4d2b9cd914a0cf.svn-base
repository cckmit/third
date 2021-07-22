package cn.redflagsoft.base.aop.aspect;

import cn.redflagsoft.base.bizlog.BizLogService;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.lifecycle.ApplicationState;
import org.opoo.ndao.Domain;
import org.springframework.security.AccessDeniedException;

import cn.redflagsoft.base.aop.annotation.Audit;
import cn.redflagsoft.base.audit.AuditManager;
import cn.redflagsoft.base.audit.Auditor;
import cn.redflagsoft.base.audit.NotAuditableException;
import cn.redflagsoft.base.audit.SimpleMethodCall;
import cn.redflagsoft.base.security.UserClerk;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.aop.annotation.Operation;
import cn.redflagsoft.base.aop.annotation.AfterOperation;
import cn.redflagsoft.base.audit.impl.OperationAuditor;
import cn.redflagsoft.base.aop.annotation.BizLog;
import cn.redflagsoft.base.aop.Callback;

/**
 * 方法审核切面。
 * @since 1.5.2
 */
public aspect AuditAspect{
	private static final Log log = LogFactory.getLog(AuditAspect.class);
	//private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	private AuditManager auditManager;
	private BizLogService bizLogService;
	
	/**
     * Matches the execution of any method with the Audit annotation.
     */
    private pointcut executionOfAuditMethod1() : execution(* *(..)) && @annotation(Audit);
    
    private pointcut executionOfAuditMethod2() : execution(* *(..)) && @annotation(Operation);
    
    private pointcut executionOfAuditMethod3() : execution(* *(..)) && @annotation(AfterOperation);
    
    private pointcut executionOfBizLogMethod() : execution(* *(..)) && @annotation(BizLog);
    
    /*
    after() returning(Object returnValue):executionOfBizLogMethod(){
    	System.out.println( returnValue);
    }*/
    
    @SuppressAjWarnings("adviceDidNotMatch")
    Object around():executionOfBizLogMethod(){
    	Callback action = new Callback(){
			public Object doInCallback() throws Throwable{
				return proceed();
			}
		};
		UserClerk user = UserClerkHolder.getNullableUserClerk();
		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
 		Method method = methodSignature.getMethod();
 		BizLog bizLog = method.getAnnotation(BizLog.class);
 		
 		if(log.isDebugEnabled()){
 			log.debug(method + "");
 			log.debug("Annotations.length: " + method.getAnnotations().length);
 		}
 		if(bizLog == null){
 			throw new RuntimeException("BizLog注解为空。");
 		}
 		
 		SimpleMethodCall mcall = new SimpleMethodCall();
        mcall.setCaller(user);
        mcall.setMethod(method);
        mcall.setParameterValues(thisJoinPoint.getArgs());
        mcall.setTargetObject(thisJoinPoint.getTarget());
 		//mcall.setResult(returnValue);
 		
        try{
        	return bizLogService.processBizLog(mcall, bizLog, action);
        }catch(Throwable e){
 			if(e instanceof RuntimeException){
 				throw (RuntimeException) e;
 			}else{
 				throw new RuntimeException(e);
 			}
 		}
    }
    

    // 后置通知
   // Object around() : pointcut_expression {}
   //after() returning() : executionOfAuditMethod2(){
   @SuppressAjWarnings("adviceDidNotMatch")
 	after() returning(Object returnValue):executionOfAuditMethod3(){
    	if(Application.getApplicationState() == ApplicationState.RUNNING){
    		UserClerk user = UserClerkHolder.getNullableUserClerk();
    		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
     		Method method = methodSignature.getMethod();
     		AfterOperation annotation = method.getAnnotation(AfterOperation.class);
     		if(user == null){
     			if(annotation.allowAnonymousCalls()){
     				log.debug("No authentication available in AuditAspect");
     			}else{
     				throw new AccessDeniedException("未登录用户不允许执行该操作");
     			}
     			//log.error("No authentication available in AuditAspect: Unable to log method call");
     			//return;
     		}     		
     		SimpleMethodCall mcall = new SimpleMethodCall();
            mcall.setCaller(user);
            mcall.setMethod(method);
            mcall.setParameterValues(thisJoinPoint.getArgs());
            mcall.setTargetObject(thisJoinPoint.getTarget());
     		mcall.setResult(returnValue);
            auditManager.auditMethodCall(mcall, buildAuditor(annotation.auditor(), mcall.getTargetClass()));
     	}
    }
   
   @SuppressAjWarnings("adviceDidNotMatch")
   before():executionOfAuditMethod2(){
    	if(Application.getApplicationState() == ApplicationState.RUNNING){
    		UserClerk user = UserClerkHolder.getNullableUserClerk();
    		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
     		Method method = methodSignature.getMethod();
     		Operation annotation = method.getAnnotation(Operation.class);
     		if(user == null){
     			if(annotation.allowAnonymousCalls()){
     				log.debug("No authentication available in AuditAspect");
     			}else{
     				throw new AccessDeniedException("未登录用户不允许执行该操作");
     			}
     			//log.error("No authentication available in AuditAspect: Unable to log method call");
     			//return;
     		}     		
     		SimpleMethodCall mcall = new SimpleMethodCall();
            mcall.setCaller(user);
            mcall.setMethod(method);
            mcall.setParameterValues(thisJoinPoint.getArgs());
            mcall.setTargetObject(thisJoinPoint.getTarget());
     		
            auditManager.auditMethodCall(mcall, buildAuditor(annotation.auditor(), mcall.getTargetClass()));
     	}
    }
    
    // 前置通知
    @SuppressAjWarnings("adviceDidNotMatch")
    before():executionOfAuditMethod1(){
    	if(Application.getApplicationState() == ApplicationState.RUNNING){
    		UserClerk user = UserClerkHolder.getNullableUserClerk();
    		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
     		Method method = methodSignature.getMethod();
     		Audit annotation = method.getAnnotation(Audit.class);
     		if(user == null){
     			if(annotation.allowAnonymousCalls()){
     				log.debug("No authentication available in AuditAspect");
     			}else{
     				throw new AccessDeniedException("未登录用户不允许执行该操作");
     			}
     			//log.error("No authentication available in AuditAspect: Unable to log method call");
     			//return;
     		}
     		SimpleMethodCall mcall = new SimpleMethodCall();
            mcall.setCaller(user);
            mcall.setMethod(method);
            mcall.setParameterValues(thisJoinPoint.getArgs());
            mcall.setTargetObject(thisJoinPoint.getTarget());
            
            auditManager.auditMethodCall(mcall, buildAuditor(annotation.auditor(), mcall.getTargetClass()));
    	}
    }
    
	private Auditor buildAuditor(Class<? extends Auditor> auditorClass, Class<?> targetClass) throws NotAuditableException {
		try {
			Auditor auditor = auditorClass.newInstance();
			auditor.setAuditedClass(targetClass);
			return auditor;
		} catch (InstantiationException e) {
			throw new NotAuditableException(e);
		} catch (IllegalAccessException e) {
			throw new NotAuditableException(e);
		}
	}
	
	public void setAuditManager(AuditManager auditManager){
		this.auditManager = auditManager;
	}
	
	public AuditManager getAuditManager() {
		return auditManager;
	}
	
	public void setBizLogService(BizLogService bizLogService){
		this.bizLogService = bizLogService;
	}
	public BizLogService getBizLogService(){
		return bizLogService;
	}
}