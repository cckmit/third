package cn.redflagsoft.base.bean.commons;

import cn.redflagsoft.base.ObjectTypes;

/**
 * 对象转交日志
 * @author ZF
 *
 */
public class ObjectTrans extends ObjectAdmin {

	private static final long serialVersionUID = -7318354761628337853L;

	public int getObjectType() {
		
		return ObjectTypes.OBJECT_TRANS;
	}

}
