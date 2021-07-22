package cn.redflagsoft.base.bean;

import org.opoo.apps.bean.LongKeyBean;
import org.opoo.cache.Cacheable;
import org.opoo.cache.CacheSizes;

public class RoleMenuRemark extends LongKeyBean implements Cacheable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2855014176259996574L;

	private long roleId;
	private long menuId;
	private String name;
	private String label;
	private String remark1;
	private String remark2;
	/**
	 * @return the roleId
	 */
	public long getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
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
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the remark1
	 */
	public String getRemark1() {
		return remark1;
	}
	/**
	 * @param remark1 the remark1 to set
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	/**
	 * @return the remark2
	 */
	public String getRemark2() {
		return remark2;
	}
	/**
	 * @param remark2 the remark2 to set
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	/**
	 * @return the menuId
	 */
	public long getMenuId() {
		return menuId;
	}
	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
	public int getCachedSize() {
		int size = CacheSizes.sizeOfObject();
		size += CacheSizes.sizeOfLong();
		size += CacheSizes.sizeOfLong();
		size += CacheSizes.sizeOfString(name);
		size += CacheSizes.sizeOfString(label);
		size += CacheSizes.sizeOfString(remark1);
		size += CacheSizes.sizeOfString(remark2);
		return size;
	}
	
}
