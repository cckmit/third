/*
 * $Id: ObjectAdminDao.java 6313 2013-11-01 06:13:54Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.dao;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.commons.ObjectAdmin;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ObjectAdminDao extends Dao<ObjectAdmin, Long> {
	
	<T extends ObjectAdmin> T get(long id, Class<T> clazz);
	
	<T extends ObjectAdmin> T getLast(RFSEntityObject object, Class<T> clazz);
	
	<T extends ObjectAdmin> T getLast(int objType, long objId, Class<T> clazz);
}
