package org.opoo.apps.aop.audit;

/**
 * 方法审核。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public @interface Audit {
	/**
	 * 审核者
	 * @return
	 */
	Class<?> auditor();
    
	/**
	 * 是否允许匿名访问。默认false。
	 * @return
	 */
	boolean allowAnonymousCalls() default false;
}
