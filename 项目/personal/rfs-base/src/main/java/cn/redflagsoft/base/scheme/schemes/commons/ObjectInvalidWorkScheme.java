package cn.redflagsoft.base.scheme.schemes.commons;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.commons.ObjectInvalid;

/**
 * 
 * @author ZF
 *
 */
public class ObjectInvalidWorkScheme extends AbstractObjectAdminWorkScheme {
	private ObjectInvalid invalid;
	
	public ObjectInvalid getInvalid() {
		return invalid;
	}

	public void setInvalid(ObjectInvalid invalid) {
		this.invalid = invalid;
	}
	
	public Object doScheme(){
		return invalid(invalid);
	}
	
	/**
	 * 作废。
	 * 将该方法单独定义，便于在继承中重写，因为do方法不能重写。
	 * @param invalid
	 * @return
	 */
	protected Object invalid(ObjectInvalid invalid){
		RFSObject rfsObject = getObject();
		ObjectInvalid invalid2 = getObjectAdminService().invalidObject(rfsObject, invalid);
		
		getRiskService().riskInvalid(rfsObject);
		getWorkService().workInvalid(rfsObject);
		getTaskService().taskInvalid(rfsObject);
		
		addRelatedItem(invalid2);
		
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "作废成功！";
	}
}
