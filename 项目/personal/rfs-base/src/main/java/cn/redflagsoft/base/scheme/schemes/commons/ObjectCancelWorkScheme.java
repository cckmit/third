package cn.redflagsoft.base.scheme.schemes.commons;

import cn.redflagsoft.base.bean.commons.ObjectCancel;

/**
 * 
 * @author ZF
 *
 */
public class ObjectCancelWorkScheme extends AbstractObjectAdminWorkScheme {
	private ObjectCancel cancel;

	public ObjectCancel getCancel() {
		return cancel;
	}

	public void setCancel(ObjectCancel cancel) {
		this.cancel = cancel;
	}
	
	public Object doScheme(){
		return cancel(cancel);
	}
	
	/**
	 * 取消。
	 * 将该方法单独定义，便于在继承中重写，因为do方法不能重写。
	 * @param cancel
	 * @return
	 */
	public Object cancel(ObjectCancel cancel){
		ObjectCancel cancel2 = getObjectAdminService().cancelObject(getObject(), cancel);
		addRelatedItem(cancel2);
		
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "取消成功！";
	}
}
