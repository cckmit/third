/*
 * $Id: ObjectAdminService.java 5588 2012-05-03 01:52:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.commons.ObjectAdmin;
import cn.redflagsoft.base.bean.commons.ObjectArchive;
import cn.redflagsoft.base.bean.commons.ObjectCancel;
import cn.redflagsoft.base.bean.commons.ObjectFinish;
import cn.redflagsoft.base.bean.commons.ObjectInvalid;
import cn.redflagsoft.base.bean.commons.ObjectPause;
import cn.redflagsoft.base.bean.commons.ObjectPublish;
import cn.redflagsoft.base.bean.commons.ObjectShelve;
import cn.redflagsoft.base.bean.commons.ObjectTrans;
import cn.redflagsoft.base.bean.commons.ObjectUnarchive;
import cn.redflagsoft.base.bean.commons.ObjectWake;

/**
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ObjectAdminService {

	/**
	 * 对象取消。
	 *  
	 * @param object
	 * @param cancel
	 * @return
	 */
	ObjectCancel cancelObject(RFSObject object, ObjectCancel cancel);

	/**
	 * 对象作废。
	 * @param object
	 * @param invalid
	 * @return
	 */
	ObjectInvalid invalidObject(RFSObject object, ObjectInvalid invalid);

	/**
	 * 对象归档。
	 * @param object
	 * @param archive
	 * @return
	 */
	ObjectArchive archiveObject(RFSObject object, ObjectArchive archive);
	
	/**
	 * 对象退档
	 * @param object
	 * @param unarchive
	 * @return
	 */
	ObjectUnarchive unarchiveObject(RFSObject object,ObjectUnarchive unarchive);
	
	/**
	 * 对象发布
	 * @param object
	 * @param publish
	 * @return
	 */
	ObjectPublish publishObject(RFSObject object,ObjectPublish publish);
	
	/**
	 * 对象暂停
	 * @param object
	 * @param pause
	 * @return
	 */
	ObjectPause pauseObject(RFSObject object,ObjectPause pause);
	
	/**
	 * 对象恢复
	 * @param object
	 * @param wake
	 * @return
	 */
	ObjectWake wakeObject(RFSObject object,ObjectWake wake);
	
	/**
	 * 对象转交
	 * @param object
	 * @param trans
	 * @return
	 */
	ObjectTrans transObject(RFSObject object,ObjectTrans trans);
	
	/**
	 * 对象搁置
	 * @param object
	 * @param shelve
	 * @return
	 */
	ObjectShelve shelveObject(RFSObject object,ObjectShelve shelve);
	
	/**
	 * 对象结束
	 * @param object
	 * @param finish
	 * @return
	 */
	ObjectFinish finishObject(RFSObject object,ObjectFinish finish);
	
	
	<T extends ObjectAdmin> T getObjectAdmin(long objectAdminID);
	
}
