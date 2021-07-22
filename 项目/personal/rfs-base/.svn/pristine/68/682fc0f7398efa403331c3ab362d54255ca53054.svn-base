package cn.redflagsoft.base.scheme.schemes.commons;

import cn.redflagsoft.base.bean.commons.ObjectPause;

/**
 * 
 * @author ZF
 *
 */
public class ObjectPauseWorkScheme extends AbstractObjectAdminWorkScheme {
	public ObjectPause pause;

	public ObjectPause getPause() {
		return pause;
	}

	public void setPause(ObjectPause pause) {
		this.pause = pause;
	}
	
	public Object doScheme(){
		return pause(pause);
	}
	
	/**
	 * 暂停。
	 * 将该方法单独定义，便于在继承中重写，因为do方法不能重写。
	 * @param pause
	 * @return
	 */
	protected Object pause(ObjectPause pause){
		ObjectPause pause2 = getObjectAdminService().pauseObject(getObject(), pause);
		addRelatedItem(pause2);
		
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "暂停成功！";
	}
}
