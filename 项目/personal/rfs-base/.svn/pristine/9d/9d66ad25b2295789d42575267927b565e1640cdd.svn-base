package cn.redflagsoft.base.bean;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 信息项配置(InfoConfig)
 * @author lf
 *
 */
public class InfoConfig  extends VersionableBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final byte TYPE_TASK = 1;
	public static final byte TYPE_DATUM = 2;
	public static final byte TYPE_OBJECT_PROPERTY = 3;
	public static final byte TYPE_ITEM_PROPERTY = 4;
	
	public static final byte STATUS_VALID = 1;
	public static final byte STATUS_INVALID = 0;
	
	//private Long id;
	private String name;
	//private byte type;
	//private byte status;
	private int objectType;
	private String value;
	private short tag;
	private Long dutyEntityId;
	private String dutyEntityName;
	private int displayOrder = 0;
	private Long roleId;
	private String roleName;
	private int refTaskType; 					// 2013-03-25 所属的task值
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public short getTag() {
		return tag;
	}
	public void setTag(short tag) {
		this.tag = tag;
	}
	public Long getDutyEntityId() {
		return dutyEntityId;
	}
	public void setDutyEntityId(Long dutyEntityId) {
		this.dutyEntityId = dutyEntityId;
	}
	public String getDutyEntityName() {
		return dutyEntityName;
	}
	public void setDutyEntityName(String dutyEntityName) {
		this.dutyEntityName = dutyEntityName;
	}
	/**
	 * @return the displayOrder
	 */
	public int getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public int getRefTaskType() {
		return refTaskType;
	}
	public void setRefTaskType(int refTaskType) {
		this.refTaskType = refTaskType;
	}
	
	public String toString(){
		return new ToStringBuilder(this).append("id", getId())
				.append("name", name)
				.append("objectType", objectType)
				.append("value", value)
				.append("tag", tag)
				.append("roleId", roleId)
				.append("roleName", roleName)
				.append("dutyEntityId", dutyEntityId)
				.append("dutyEntityName", dutyEntityName)
				.append("type", getType())
				.append("displayOrder", displayOrder)
				.append("status", getStatus())
				.append("refTaskType",getRefTaskType())
				.toString();
	}
}
