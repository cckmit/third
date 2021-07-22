package cn.redflagsoft.base.scheme.schemes.commons;

import cn.redflagsoft.base.bean.commons.ObjectWake;

/**
 * 
 * @author ZF
 *
 */
public class ObjectWakeWorkScheme extends AbstractObjectAdminWorkScheme {
	public ObjectWake wake;

	public ObjectWake getWake() {
		return wake;
	}

	public void setWake(ObjectWake wake) {
		this.wake = wake;
	}
	
	public Object doScheme(){
		return wake(wake);
	}
	
	/**
	 * 恢复。
	 * 将该方法单独定义，便于在继承中重写，因为do方法不能重写。
	 * @param wake
	 * @return
	 */
	protected Object wake(ObjectWake wake){
		ObjectWake wake2 = getObjectAdminService().wakeObject(getObject(), wake);
		addRelatedItem(wake2);
		
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "恢复成功！";
	}
	
}
