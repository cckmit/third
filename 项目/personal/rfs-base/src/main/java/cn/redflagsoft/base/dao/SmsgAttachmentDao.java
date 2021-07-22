/*
 * $Id: SmsgAttachmentDao.java 4991 2011-10-28 06:47:17Z lf $
 * RiskDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.apps.bean.core.Attachment;
import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.SmsgAttachment;


/**
 * @author py
 *
 */
public interface SmsgAttachmentDao extends Dao<SmsgAttachment,Long> {
	
	List<Attachment> findSmsgAttachments(Long smsgId);
}
