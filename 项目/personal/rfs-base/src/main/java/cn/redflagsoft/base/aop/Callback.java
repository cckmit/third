package cn.redflagsoft.base.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;



/**
 * AOP �����еĻص��ӿڡ�
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface Callback {
	/**
	 * �ڻص���ִ�еķ�����
	 * 
	 * @return
	 * @throws Throwable
	 */
	Object doInCallback() throws Throwable;
	
	
	public static class MethodCallback implements Callback{
		private MethodInvocation invocation;
		public MethodCallback(MethodInvocation invocation){
			this.invocation = invocation;
		}
		public Object doInCallback() throws Throwable {
			return invocation.proceed();
		}
		
		public String toString(){
			return "MethodCallback(" + invocation.getMethod().getName() + ")@" + Integer.toHexString(hashCode());
		}
	}
	
	public static class AspectJCallback implements Callback{
		private ProceedingJoinPoint pjp;
		public AspectJCallback(ProceedingJoinPoint pjp){
			this.pjp = pjp;
		}
		public Object doInCallback() throws Throwable {
			return pjp.proceed();
		}
		
		private String getMethodName(){
			Signature signature = pjp.getSignature();
			if(signature instanceof MethodSignature){
				return ((MethodSignature)signature).getMethod().getName();
			}
			return "none";
		}
		
		public String toString(){
			return "AspectJCallback(" + getMethodName() + ")@" + Integer.toHexString(hashCode());
		}
	}
}
