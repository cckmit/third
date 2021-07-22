package cn.redflagsoft.base.audit;


import java.net.InetAddress;
import java.util.Date;

import org.opoo.ndao.Domain;

import cn.redflagsoft.base.security.UserClerk;


/**
 * AuditMessage implementation which allows get and set operations on all fields.
 */
public class MutableAuditMessage implements OperationMessage, AuditMessage {
	private static final long serialVersionUID = 578569514754402881L;
	private long id = -1;
    private UserClerk user;
    private Date timestamp;
    private InetAddress node;
    private String details;
    private String description;
    private boolean ignore;
    private String operation;
	private DomainIdentifier domain;

    public UserClerk getUser() {
        return user;
    }

    public void setUser(UserClerk user) {
        this.user = user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public InetAddress getNode() {
        return node;
    }

    public void setNode(InetAddress node) {
        this.node = node;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public DomainIdentifier getDoamin() {
		return domain;
	}
	
	public void setDomain(DomainIdentifier domain){
		this.domain = domain;
	}
	
	public void setDomain(Domain<?> domain){
		if(domain != null && domain.getId() != null){
			this.domain = new DomainIdentifier(domain.getId().toString(), domain.getClass().getName());
		}
	}
}
