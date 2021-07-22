package cn.redflagsoft.base.bean;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.ObjectType;


@ObjectType(ObjectTypes.CAUTION)
@Deprecated
public class CautionObject implements RFSEntityObject, RFSObjectable {
	public static final int OBJECT_TYPE = ObjectTypes.CAUTION;
	private Caution caution;
	/**
	 * 
	 */
	public CautionObject() {
		super();
	}

	/**
	 * @param caution
	 */
	public CautionObject(Caution caution) {
		super();
		this.caution = caution;
	}

	public String getName() {
		return caution != null ? caution.getName() : null;
	}

	public Long getId() {
		return caution != null ? caution.getId() : null;
	}

	public int getObjectType() {
		return OBJECT_TYPE;
	}

	/**
	 * @return the caution
	 */
	public Caution getCaution() {
		return caution;
	}

	/**
	 * @param caution the caution to set
	 */
	public void setCaution(Caution caution) {
		this.caution = caution;
	}
}
