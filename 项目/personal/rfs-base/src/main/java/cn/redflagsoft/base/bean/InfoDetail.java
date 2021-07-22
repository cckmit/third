package cn.redflagsoft.base.bean;

import org.apache.commons.lang.builder.ToStringBuilder;

/***
 * 信息项统计明细（InfoDetail）
 * @author lf
 *
 */
public class InfoDetail extends VersionableBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final byte STATUS_NONE = 0;
	public static final byte STATUS_COMPLETE = 1;
	public static final byte STATUS_INCOMPLETE = 2;
	
	//private String name;
	private int objectType;
	private Long objectId;
	private Long infoConfigId;
	private Long dutyEntityId;
	private String dutyEntityName;
	
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public Long getInfoConfigId() {
		return infoConfigId;
	}
	public void setInfoConfigId(Long infoConfigId) {
		this.infoConfigId = infoConfigId;
	}
	
	/**
	 * @return the dutyEntityId
	 */
	public Long getDutyEntityId() {
		return dutyEntityId;
	}
	/**
	 * @param dutyEntityId the dutyEntityId to set
	 */
	public void setDutyEntityId(Long dutyEntityId) {
		this.dutyEntityId = dutyEntityId;
	}
	/**
	 * @return the dutyEntityName
	 */
	public String getDutyEntityName() {
		return dutyEntityName;
	}
	/**
	 * @param dutyEntityName the dutyEntityName to set
	 */
	public void setDutyEntityName(String dutyEntityName) {
		this.dutyEntityName = dutyEntityName;
	}
	public String toString(){
		return new ToStringBuilder(this)
			.append("id", getId())
			.append("objectType", objectType)
			.append("objectId", objectId)
			.append("infoConfigId", infoConfigId)
			.append("status", getStatus())
			.append("dutyEntityId", dutyEntityId)
			.append("dutyEntityName", dutyEntityName)
			.toString();
	}
}
