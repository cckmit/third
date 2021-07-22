package cn.redflagsoft.base.audit;

import java.io.Serializable;

import org.opoo.ndao.Domain;

public class DomainIdentifier implements Serializable {
	private static final long serialVersionUID = -894703027528110115L;
	private String id;
	private String type;
	
	public DomainIdentifier() {
		super();
	}
	public DomainIdentifier(Domain<?> domain){
		if(domain == null || domain.getId() == null){
			throw new IllegalArgumentException("domain null");
		}
		this.id = domain.getId().toString();
		this.type = domain.getClass().getName();
	}
	public DomainIdentifier(String id, String type) {
		this.id = id;
		this.type = type;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
