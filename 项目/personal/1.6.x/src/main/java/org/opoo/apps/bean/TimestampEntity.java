package org.opoo.apps.bean;

import java.io.Serializable;
import java.util.Date;

import org.opoo.ndao.domain.Entity;
import org.opoo.ndao.domain.Versionable;

/**
 * 带有创建、修改记录的实体类（超类）。
 * 应该根据实际应用场景来设计这个类。
 * 
 * @author alex
 *
 * @param <K>
 * @deprecated
 */
public abstract class TimestampEntity<K extends Serializable> extends Entity<K>
	implements VersionToken, Versionable{
	private static final long serialVersionUID = 1L;
	
	private Date creationTime;
	private Date modificationTime;
	private String createdBy;
	private String modifiedBy;
	
	public TimestampEntity(){
		
	}
	
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getModificationTime() {
		return modificationTime;
	}
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreateBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
