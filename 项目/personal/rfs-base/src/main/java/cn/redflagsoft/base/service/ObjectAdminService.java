/*
 * $Id: ObjectAdminService.java 5588 2012-05-03 01:52:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
	 * ����ȡ����
	 *  
	 * @param object
	 * @param cancel
	 * @return
	 */
	ObjectCancel cancelObject(RFSObject object, ObjectCancel cancel);

	/**
	 * �������ϡ�
	 * @param object
	 * @param invalid
	 * @return
	 */
	ObjectInvalid invalidObject(RFSObject object, ObjectInvalid invalid);

	/**
	 * ����鵵��
	 * @param object
	 * @param archive
	 * @return
	 */
	ObjectArchive archiveObject(RFSObject object, ObjectArchive archive);
	
	/**
	 * �����˵�
	 * @param object
	 * @param unarchive
	 * @return
	 */
	ObjectUnarchive unarchiveObject(RFSObject object,ObjectUnarchive unarchive);
	
	/**
	 * ���󷢲�
	 * @param object
	 * @param publish
	 * @return
	 */
	ObjectPublish publishObject(RFSObject object,ObjectPublish publish);
	
	/**
	 * ������ͣ
	 * @param object
	 * @param pause
	 * @return
	 */
	ObjectPause pauseObject(RFSObject object,ObjectPause pause);
	
	/**
	 * ����ָ�
	 * @param object
	 * @param wake
	 * @return
	 */
	ObjectWake wakeObject(RFSObject object,ObjectWake wake);
	
	/**
	 * ����ת��
	 * @param object
	 * @param trans
	 * @return
	 */
	ObjectTrans transObject(RFSObject object,ObjectTrans trans);
	
	/**
	 * �������
	 * @param object
	 * @param shelve
	 * @return
	 */
	ObjectShelve shelveObject(RFSObject object,ObjectShelve shelve);
	
	/**
	 * �������
	 * @param object
	 * @param finish
	 * @return
	 */
	ObjectFinish finishObject(RFSObject object,ObjectFinish finish);
	
	
	<T extends ObjectAdmin> T getObjectAdmin(long objectAdminID);
	
}
