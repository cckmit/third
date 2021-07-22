package cn.redflagsoft.base.audit.proxy;

import java.net.InetAddress;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.lifecycle.ApplicationState;
import org.opoo.ndao.Domain;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.audit.AuditManager;
import cn.redflagsoft.base.audit.AuditMessage;
import cn.redflagsoft.base.audit.Auditor;
import cn.redflagsoft.base.audit.DomainIdentifier;
import cn.redflagsoft.base.audit.MethodCall;
import cn.redflagsoft.base.audit.NotAuditableException;
import cn.redflagsoft.base.security.UserClerk;

/**
 * 用户操作审计管理代理类。
 *
 */
public class AuditManagerProxy implements AuditManager {
	public static final String PROPERTY_AUDIT_ENABLED = "audit.enabled";
	
	private static final Log log = LogFactory.getLog(AuditManagerProxy.class);
	
	public static boolean isAuditEnabledInProperties(){
		return AppsGlobals.getProperty(PROPERTY_AUDIT_ENABLED, false);
	}
	
	/**
	 * 系统配置中，审计功能是否打开，默认为不打开。
	 * @return
	 */
	private static boolean isAuditEnabled() {
		return Application.getApplicationState() == ApplicationState.RUNNING
				&& isAuditEnabledInProperties();//AppsGlobals.getProperty(PROPERTY_AUDIT_ENABLED, false);
	}
	private AuditManager getAuditManagerImpl(){
		return Application.getContext().get("auditManagerImpl", AuditManager.class);
	}
	
	public AuditMessage audit(UserClerk user, InetAddress address, String details, String description,
			DomainIdentifier domain, String operation) throws NotAuditableException {
		if(isAuditEnabled()){
			return getAuditManagerImpl().audit(user, address, details, description, domain, operation);
		}else{
			log.debug("审计功能未开放。");
			return null;
		}
	}

	public void auditMethodCall(MethodCall call, Auditor auditor) throws NotAuditableException {
		if(isAuditEnabled()){
			getAuditManagerImpl().auditMethodCall(call, auditor);
		}else{
			log.debug("Audit is disabled, skip audit method " + call.getMethod());
		}
	}

	public Iterable<AuditMessage> getAuditMessages() {
		if(isAuditEnabled()){
			return getAuditManagerImpl().getAuditMessages();
		}else{
			log.debug("Audit is disabled, return empty AuditMessages");
			return Collections.emptyList();
		}
	}

	public List<AuditMessage> findAuditMessages(ResultFilter filter) {
		if(isAuditEnabled()){
			return getAuditManagerImpl().findAuditMessages(filter);
		}else{
			log.debug("Audit is disabled, return empty AuditMessages");
			return Collections.emptyList();
		}
	}

	public List<AuditMessage> findAuditMessagesByDomain(Domain<?> domain) {
		if(isAuditEnabled()){
			return getAuditManagerImpl().findAuditMessagesByDomain(domain);
		}else{
			log.debug("Audit is disabled, return empty AuditMessages");
			return Collections.emptyList();
		}
	}

	public List<AuditMessage> findAuditMessagesByUser(long userId) {
		if(isAuditEnabled()){
			return getAuditManagerImpl().findAuditMessagesByUser(userId);
		}else{
			log.debug("Audit is disabled, return empty AuditMessages");
			return Collections.emptyList();
		}
	}

	public List<AuditMessage> findAuditMessagesByDomain(DomainIdentifier domain) {
		if(isAuditEnabled()){
			return getAuditManagerImpl().findAuditMessagesByDomain(domain);
		}else{
			log.debug("Audit is disabled, return empty AuditMessages");
			return Collections.emptyList();
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.audit.AuditManager#findPageableAuditMessages(org.opoo.ndao.support.ResultFilter)
	 */
	@Queryable(name="findPageableAuditMessages")
	public PageableList<AuditMessage> findPageableAuditMessages(ResultFilter filter) {
		if(isAuditEnabled()){
			return getAuditManagerImpl().findPageableAuditMessages(filter);
		}else{
			log.debug("Audit is disabled, return empty AuditMessages");
			List<AuditMessage> list = Collections.emptyList();
			return new PageableList<AuditMessage>(list, 0, 15, 0);
		}
	}
}
