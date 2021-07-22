package cn.redflagsoft.base.bean.commons;

import cn.redflagsoft.base.ObjectTypes;

/**
 * 对象暂停日志
 * @author ZF
 *
 */
public class ObjectPause extends ObjectAdmin {
	private static final long serialVersionUID = -6454636187067766389L;

	public int getObjectType() {
		
		return ObjectTypes.OBJECT_PAUSE;
	}

}
