package cn.redflagsoft.base.bean;

import org.opoo.cache.Cacheable;
import org.opoo.cache.CacheSizes;


/**
 * 操作。应用，服务。
 * 菜单对应的服务。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class Action  extends BaseBean implements Cacheable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4910510883304811909L;
	
	private String uid;
	private String name;
	private String location;
	private String icon;
	private String logo;



	public Action(Long id) {
		setId(id);
	}
	
	public Action(){
		
	}



	/**
	 * 唯一标识。
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}



	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}



	/**
	 * 名称。
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
	 * 应用路径、地址等。
	 * 以 URL 或者文件路径等形式展现。
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}



	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	public int getCachedSize() {
		int size = CacheSizes.sizeOfObject();
		size += CacheSizes.sizeOfLong();
		size += CacheSizes.sizeOfString(uid);
		size += CacheSizes.sizeOfString(name);
		size += CacheSizes.sizeOfString(location);
		size += CacheSizes.sizeOfString(icon);
		size += CacheSizes.sizeOfString(logo);
		return size;
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
