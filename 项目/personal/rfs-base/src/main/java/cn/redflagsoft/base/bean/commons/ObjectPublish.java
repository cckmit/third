package cn.redflagsoft.base.bean.commons;

import cn.redflagsoft.base.ObjectTypes;

/**
 * 对象发布日志
 * @author ZF
 *
 */
public class ObjectPublish extends ObjectAdmin {

	private static final long serialVersionUID = 2184456694601615617L;

	public int getObjectType() {
		return ObjectTypes.OBJECT_PUBLISH;
	}

}
