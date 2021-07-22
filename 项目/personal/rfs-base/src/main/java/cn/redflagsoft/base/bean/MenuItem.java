package cn.redflagsoft.base.bean;

import org.opoo.cache.Cacheable;

public class MenuItem extends VersionableBean implements Cacheable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6975237481496393504L;
	private String name;
	private String label;
	private Long parentId;
	private Long actionId = null;
	private String icon;
	private String logo;

	
	public int getCachedSize() {
		return 0;
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
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}


	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	/**
	 * @return the actionId
	 */
	public Long getActionId() {
		return actionId;
	}


	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}


	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}


	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}


	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}


	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

}
