package cn.redflagsoft.base.bean;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.ObjectType;


@ObjectType(ObjectTypes.RISK)
public class RiskObject implements RFSEntityObject,RFSObjectable {
	public static final int OBJECT_TYPE = ObjectTypes.RISK;
	private Risk risk;
	
	/**
	 * @param risk
	 */
	public RiskObject(Risk risk) {
		super();
		this.risk = risk;
	}
	/**
	 * 
	 */
	public RiskObject() {
		super();
	}

	/**
	 * @return the risk
	 */
	public Risk getRisk() {
		return risk;
	}

	/**
	 * @param risk the risk to set
	 */
	public void setRisk(Risk risk) {
		this.risk = risk;
	}

	public Long getId() {
		return risk != null ? risk.getId() : null;
	}

	public int getObjectType() {
		return OBJECT_TYPE;
	}

	public String getName() {
		return risk != null ? risk.getName() : null;
	}
	
	public String getCode(){
		return risk != null ? risk.getCode() : null;
	}
}
