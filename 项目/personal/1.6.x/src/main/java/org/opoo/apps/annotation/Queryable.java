package org.opoo.apps.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 指定某方法是否可以被通用查询调用。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Queryable {
	/**
	 * 被通用查询调用时，方法的各参数的名称。
	 * @return
	 */
	String[] argNames() default {};
	
	String name() default "";
}
