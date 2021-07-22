package cn.redflagsoft.base.aop.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.lifecycle.ApplicationState;
import org.springframework.security.AccessDeniedException;

import cn.redflagsoft.base.aop.annotation.AfterOperation;
import cn.redflagsoft.base.aop.annotation.Audit;
import cn.redflagsoft.base.aop.annotation.Operation;
import cn.redflagsoft.base.audit.AuditManager;
import cn.redflagsoft.base.audit.Auditor;
import cn.redflagsoft.base.audit.NotAuditableException;
import cn.redflagsoft.base.audit.SimpleMethodCall;
import cn.redflagsoft.base.security.UserClerk;
import cn.redflagsoft.base.security.UserClerkHolder;

public class AuditInterceptor implements MethodInterceptor {
	private static final Log log = LogFactory.getLog(AuditInterceptor.class);
	private AuditManager auditManager;
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		boolean before = false;
		if(Application.getApplicationState() == ApplicationState.RUNNING){
			Class<? extends Auditor> auditorClass = null;
			boolean allowAnonymousCalls = false;
			
			Method method = invocation.getMethod();
	 		Audit ad = method.getAnnotation(Audit.class);
	 		if(ad != null){
	 			auditorClass = ad.auditor();
	 			allowAnonymousCalls = ad.allowAnonymousCalls();
	 		}else{
	 			Operation op = method.getAnnotation(Operation.class);
	 			if(op != null){
	 				auditorClass = op.auditor();
	 				allowAnonymousCalls = op.allowAnonymousCalls();
	 			}
	 		}
	 		before = auditorClass != null;
	 		
	 		if(before){
				UserClerk user = UserClerkHolder.getNullableUserClerk();
		 		if(user == null){
		 			//return invocation.proceed();
		 			if(!allowAnonymousCalls){
		 				throw new AccessDeniedException("未登录用户不允许执行该操作");
		 			}else{
		 				log.error("No authentication available in AuditAspect: Unable to log method call");
		 			}
		 		}
		 		SimpleMethodCall mcall = new SimpleMethodCall();
		        mcall.setCaller(user);
		        mcall.setMethod(method);
		        mcall.setParameterValues(invocation.getArguments());
		        mcall.setTargetObject(invocation.getThis());
		        auditManager.auditMethodCall(mcall, buildAuditor(auditorClass, mcall.getTargetClass()));
	 		}
		}
		Object result = invocation.proceed();
		if(Application.getApplicationState() == ApplicationState.RUNNING){
			Method method = invocation.getMethod();
	 		AfterOperation ao = method.getAnnotation(AfterOperation.class);
	 		if(ao != null){
	 			UserClerk user = UserClerkHolder.getNullableUserClerk();
		 		if(user == null){
		 			//return invocation.proceed();
		 			if(!ao.allowAnonymousCalls()){
		 				throw new AccessDeniedException("未登录用户不允许执行该操作");
		 			}else{
		 				log.error("No authentication available in AuditAspect: Unable to log method call");
		 			}
		 		}
		 		SimpleMethodCall mcall = new SimpleMethodCall();
		        mcall.setCaller(user);
		        mcall.setMethod(method);
		        mcall.setParameterValues(invocation.getArguments());
		        mcall.setTargetObject(invocation.getThis());
		        mcall.setResult(result);
		        auditManager.auditMethodCall(mcall, buildAuditor(ao.auditor(), mcall.getTargetClass()));
	 		}else{
	 			if(!before){
	 				log.warn("该方法被拦截，但未标注Audit,Operation,AfterOperation中任何一个，请检查配置："+ method);
	 			}
	 		}
		}
		return result;
	}
	
//	private Auditor buildAuditor(Audit annotation, Class<?> targetClass) throws NotAuditableException {
//		try {
//			Auditor auditor = (Auditor) annotation.auditor().newInstance();
//			auditor.setAuditedClass(targetClass);
//			return auditor;
//		} catch (InstantiationException e) {
//			throw new NotAuditableException(e);
//		} catch (IllegalAccessException e) {
//			throw new NotAuditableException(e);
//		}
//	}
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

	public AuditManager getAuditManager() {
		return auditManager;
	}

	public void setAuditManager(AuditManager auditManager) {
		this.auditManager = auditManager;
	}
	
}
