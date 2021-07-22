/*
 * $Id: ObjectAdminDao.java 6313 2013-11-01 06:13:54Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
