package cn.redflagsoft.base.audit;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Convenience class to make auditor implementation easier.
 */
public abstract class AbstractAuditor implements Auditor {
    protected static final Logger log = LogManager.getLogger(AbstractAuditor.class.getName());

    public MutableAuditMessage auditMethodCall(MethodCall call) throws NotAuditableException {
        MutableAuditMessage message = new MutableAuditMessage();
        message.setUser(call.getCaller());
        message.setTimestamp(new Date());
        try {
            message.setNode(InetAddress.getLocalHost());
        }
        catch (UnknownHostException e) {
            log.warn("Host name unresolvable. Audit will not contain node information unless the host name can be " 
                    + "resolved via DNS.");
            message.setNode(null);
        }
        message.setDetails(buildDetails(call, message));
        message.setDescription(buildDescription(call, message));
        message.setOperation(buildOperation(call, message));
        message.setDomain(buildDomain(call, message));

        if ((message.getDetails() == null || message.getDescription() == null)/* && !message.isIgnore()*/) {
            return null;
        }
        else {
            return message;
        }
    }


    protected DomainIdentifier buildDomain(MethodCall call, MutableAuditMessage message) {
		return null;
	}

	protected String buildOperation(MethodCall call, MutableAuditMessage message) {
		return null;
	}




	/**
     * Returns data used in detail property of {@link AuditMessage}.
     *
     * @param call the method call to return details for.
     * @return data used in detail property of {@link AuditMessage}.
     */
    public abstract String buildDetails(MethodCall call, MutableAuditMessage auditMessage);

    /**
     * Returns data used in description property of {@link AuditMessage}.
     *
     * @param call the method call to return description for.
     * @return data used in description property of {@link AuditMessage}.
     */
    public abstract String buildDescription(MethodCall call, MutableAuditMessage message);

}


