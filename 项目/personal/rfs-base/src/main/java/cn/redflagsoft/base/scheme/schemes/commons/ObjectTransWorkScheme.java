package cn.redflagsoft.base.scheme.schemes.commons;

import cn.redflagsoft.base.bean.commons.ObjectTrans;

/**
 * 
 * @author ZF
 *
 */
public class ObjectTransWorkScheme extends AbstractObjectAdminWorkScheme {
	public ObjectTrans trans;

	public ObjectTrans getTrans() {
		return trans;
	}

	public void setTrans(ObjectTrans trans) {
		this.trans = trans;
	}
	
	public Object doScheme(){
		return trans(trans);
	}
	
	/**
	 * 转交。
	 * 将该方法单独定义，便于在继承中重写，因为do方法不能重写。
	 * @param trans
	 * @return
	 */
	protected Object trans(ObjectTrans trans){
		ObjectTrans trans2 = getObjectAdminService().transObject(getObject(), trans);
		addRelatedItem(trans2);
		
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "转交成功！";
	}
}
