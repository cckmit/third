package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.apps.bean.StringKeyBean;
import org.opoo.ndao.domain.Versionable;
import org.springframework.core.Ordered;

public class DistrictBean extends StringKeyBean implements Versionable, Ordered, VersionLogable{
	
	private static final long serialVersionUID = 3225906546363555559L;
	
	private String code;
	private String name;
	private String parentCode;
	private int displayOrder;
	private byte status;
	private int type;
	private Long creator;
	private Date creationTime;
	private Long modifier;
	private Date modificationTime;
	private String remark;
	
	public DistrictBean(){
	}
	/**
	 * @param district
	 */
	public DistrictBean(District district) {
		this.code = district.getCode();
		this.displayOrder = district.getDisplayOrder();
		this.name = district.getName();
		this.parentCode = district.getParent() != null ? district.getParent().getCode() : null;
		this.remark = district.getRemark();
		this.status = district.getStatus();
		this.type = district.getType();
	}
	
	
	@Override
	public String getId() {
		return getCode();
	}
	@Override
	public String getKey() {
		return getCode();
	}
	@Override
	public void setId(String id) {
		setCode(id);
	}
	@Override
	public void setKey(String id) {
		setCode(id);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Long getModifier() {
		return modifier;
	}
	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}
	public Date getModificationTime() {
		return modificationTime;
	}
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/* (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	public int getOrder() {
		return getDisplayOrder();
	}
}
