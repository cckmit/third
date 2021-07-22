package org.opoo.apps.module.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.opoo.apps.module.ModuleMetaData;
import org.opoo.ndao.domain.Versionable;

/**
 * Ä£¿é Bean¡£
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleBean implements Serializable, Versionable {

	private static final long serialVersionUID = -8307298949017015118L;
	private String name;
	private Date creationTime;
	private Date modificationTime;
	private ModuleMetaData.Scope scope = ModuleMetaData.Scope.cluster;
	
	public ModuleBean(String name) {
		super();
		this.name = name;
	}
	public ModuleBean() {
		super();
	}
	
	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	/**
	 * @return the modificationTime
	 */
	public Date getModificationTime() {
		return modificationTime;
	}
	/**
	 * @param modificationTime the modificationTime to set
	 */
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

    public int hashCode()
    {
        return (new HashCodeBuilder(13, 11)).append(name).toHashCode();
    }

    public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) {
			return false;
		} else {
			ModuleBean other = (ModuleBean) obj;
			return (new EqualsBuilder()).append(name, other.name).isEquals();
		}
	}

	/**
	 * @return the scope
	 */
	public ModuleMetaData.Scope getScope() {
		return scope;
	}
	/**
	 * @param scope the scope to set
	 */
	public void setScope(ModuleMetaData.Scope scope) {
		this.scope = scope;
	}
	
	
    public String toString()
    {
        return (new ToStringBuilder(this)).append("name", name)
        	.append("creationTime", creationTime)
        	.append("modificationTime", modificationTime)
        	.append("scope", scope)
        	.toString();
    }
}
