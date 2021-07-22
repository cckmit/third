package cn.redflagsoft.base.process.impl;

import java.util.Map;



import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ObjectService;


/**
 * 查询对象风险信息。
 * 
 * @author Alex Lin
 *
 */
@ProcessType(ObjectRiskSummaryProcess.TYPE)
public class ObjectRiskSummaryProcess  extends AbstractWorkProcess {
	public static final int TYPE = 3301;
	private Integer objectType;
	private Integer riskType;
	private Short objectStatus;
	private Short year;
	private int key;
	private int eps;
	
	

	public int getEps() {
		return eps;
	}

	public void setEps(int eps) {
		this.eps = eps;
	}

	private ObjectService<RFSObject> objectService;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public Integer getRiskType() {
		return riskType;
	}

	public void setRiskType(Integer riskType) {
		this.riskType = riskType;
	}

	public Short getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(Short objectStatus) {
		this.objectStatus = objectStatus;
	}

	public ObjectService<RFSObject> getObjectService() {
		return objectService;
	}

	public void setObjectService(ObjectService<RFSObject> objectService) {
		this.objectService = objectService;
	}

	public Object execute(Map parameters, boolean needLog) {
		Long entityID=null;
		if(eps==1){
			Clerk c=UserClerkHolder.getClerk();
			entityID=c.getEntityID();
		}
		return objectService.getObjectRiskSummaryByManager(objectType, riskType, objectStatus, year,key,entityID);
	}

	/**
	 * @return the year
	 */
	public Short getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Short year) {
		this.year = year;
	}
}
