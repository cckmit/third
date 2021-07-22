package cn.redflagsoft.base.aop.aspect;


/**
 * 定义了所需的切面的织入顺序。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public aspect PrecedenceAspect {
	/**
	 * 切面织入顺序。
	 */
	declare precedence: AnnotationSecurityAspect, TransactionAspect, BaseAspect, AuditAspect;
}
