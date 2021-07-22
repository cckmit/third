package cn.redflagsoft.base.aop.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.redflagsoft.base.audit.Auditor;
import cn.redflagsoft.base.audit.impl.CatchAllAuditor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Audit {
    
	/**
	 * 审核者。
	 * @return
	 */
    Class<? extends Auditor> auditor() default CatchAllAuditor.class;

    /**
     * 是否允许匿名访问。
     * @return
     */
    boolean allowAnonymousCalls() default false;

}
