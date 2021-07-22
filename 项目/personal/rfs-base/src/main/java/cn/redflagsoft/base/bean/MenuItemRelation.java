package cn.redflagsoft.base.bean;

import org.opoo.apps.bean.LongKeyBean;
import org.springframework.core.Ordered;

public class MenuItemRelation extends LongKeyBean implements Ordered {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7099512193953546521L;
	private long supId;
	private long subId;
	private int displayOrder;
	
	
	public int getOrder() {
		return displayOrder;
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
	 * @return the supId
	 */
	public long getSupId() {
		return supId;
	}





	/**
	 * @param supId the supId to set
	 */
	public void setSupId(long supId) {
		this.supId = supId;
	}





	/**
	 * @return the subId
	 */
	public long getSubId() {
		return subId;
	}





	/**
	 * @param subId the subId to set
	 */
	public void setSubId(long subId) {
		this.subId = subId;
	}
}
