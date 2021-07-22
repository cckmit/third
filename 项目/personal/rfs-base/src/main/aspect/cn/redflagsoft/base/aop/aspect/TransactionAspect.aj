package cn.redflagsoft.base.aop.aspect;

import java.util.Properties;

import org.springframework.transaction.aspectj.AbstractTransactionAspect;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSource;


/**
 * ÊÂÎñÇÐÃæ ¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public aspect TransactionAspect extends AbstractTransactionAspect {

	public TransactionAspect() {
		super(buildRequiredTransactionAttributeSource());
	}

	private pointcut executionOfSchemeDoMethods(): execution(public * cn.redflagsoft.base.scheme.Scheme+.do*());

	private pointcut executionOfSchemeViewMethods(): execution(public * cn.redflagsoft.base.scheme.Scheme+.view*());
	
	public pointcut executionOfProcessMethods(): 
		execution(public * cn.redflagsoft.base.process.WorkProcess+.execute(java.util.Map, boolean));
	
	public pointcut executionOfWorkSchemeDoMethods(): 
		execution(* cn.redflagsoft.base.scheme.WorkScheme+.do*(..));
	
	
	public pointcut executionOfAllTransactionalMethods(): 
		executionOfSchemeDoMethods() || executionOfProcessMethods() || executionOfWorkSchemeDoMethods();
	
	
	protected pointcut transactionalMethodExecution(Object txObject): executionOfAllTransactionalMethods() && this(txObject);
	
	
	
	/**
	 * 	<tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="add*" propagation="REQUIRED" />
            <tx:method name="delete*" propagation="REQUIRED" />
            <tx:method name="update*" propagation="REQUIRED" />
            <tx:method name="save*" propagation="REQUIRED" />
            <tx:method name="execute" propagation="REQUIRED" />
            <tx:method name="create*" propagation="REQUIRED" />
            <tx:method name="scheme" propagation="REQUIRED" />
            <tx:method name="do*" propagation="REQUIRED" />
            <!-- <tx:method name="*" propagation="true" /> -->
        </tx:attributes>
    </tx:advice>
	 * @return
	 */
	public static TransactionAttributeSource buildRequiredTransactionAttributeSource(){
		NameMatchTransactionAttributeSource tas = new NameMatchTransactionAttributeSource();
		
		Properties props = new Properties();
		props.setProperty("do*", "PROPAGATION_REQUIRED");
		props.setProperty("add*", "PROPAGATION_REQUIRED");
		props.setProperty("delete*", "PROPAGATION_REQUIRED");
		props.setProperty("update*", "PROPAGATION_REQUIRED");
		props.setProperty("save*", "PROPAGATION_REQUIRED");
		props.setProperty("execute", "PROPAGATION_REQUIRED");
		props.setProperty("scheme", "PROPAGATION_REQUIRED");
		
		tas.setProperties(props);
		
//		MethodMapTransactionAttributeSource source = new MethodMapTransactionAttributeSource();
//		source.addTransactionalMethod("dod", buildTransactionAttribute("PROPAGATION_REQUIRED"));
//		source.addTransactionalMethod("execute", buildTransactionAttribute("PROPAGATION_REQUIRED"));
//		source.addTransactionalMethod("add*", buildTransactionAttribute("PROPAGATION_REQUIRED"));
//		source.addTransactionalMethod("save*", buildTransactionAttribute("PROPAGATION_REQUIRED"));
		return tas;
	}
}