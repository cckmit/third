package cn.redflagsoft.base.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.opoo.ndao.Domain;

import cn.redflagsoft.base.audit.Auditor;
import cn.redflagsoft.base.audit.impl.OperationAuditor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operation {

	/**
	 * 对象名
	 * 
	 * @return
	 */
	String targetName() default "";

	/**
	 * 操作方式
	 * 
	 * @return
	 */
	String operation();

	/***
	 * 被操作对象在参数中的下标.
	 * 如果该值为-1,则表示domain是方法的返回值。
	 * 
	 * @return
	 */
	int domainArgIndex() default -100;

	/**
	 * 被操作对象的类型
	 * 
	 * @return
	 */
	String domainType() default "";
	
	/**
	 * 被操作对象的类型。
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	Class<? extends Domain> domainClass() default Domain.class;

	/**
	 * 被操作对象的ID 在参数中的 下标
	 * 
	 * @return
	 */
	int domainIdArgIndex() default -1;

	/**
	 * 审核者。
	 * @return
	 */
	Class<? extends Auditor> auditor() default OperationAuditor.class;

	/**
	 * 是都允许匿名访问。默认false。
	 * 
	 * @return
	 */
	boolean allowAnonymousCalls() default false;
	
	/**
	 * 详情。
	 * @return
	 */
	String details() default "";
	
	/**
	 * 描述。
	 */
	String description() default "";
}
