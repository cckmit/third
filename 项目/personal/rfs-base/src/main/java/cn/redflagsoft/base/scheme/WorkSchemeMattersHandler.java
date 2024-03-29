/*
 * $Id: WorkSchemeMattersHandler.java 5165 2011-12-02 08:11:55Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/**
 * @author Alex Lin
 *
 */
class WorkSchemeMattersHandler extends AbstractMattersHandler implements MattersHandler {
	private final AbstractMattersHandler mattersHandler;
	private final WorkScheme ws;
	
	public WorkSchemeMattersHandler(AbstractMattersHandler mattersHandler, WorkScheme ws){
		this.mattersHandler = mattersHandler;
		this.ws = ws;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.AbstractMattersHandler#processWorkAffair(cn.redflagsoft.base.bean.Work, java.lang.Long, java.lang.Byte, cn.redflagsoft.base.bean.RFSObject, short, java.lang.String, java.util.List)
	 */
	@Override
	protected void processWorkAffair(Work work, Long bizId, Byte bizAction,
			RFSObject object, short tag, String note,
			WorkScheme workScheme) {
		mattersHandler.processWorkAffair(work, bizId, bizAction, object, tag, note, ws);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.AbstractMattersHandler#processTaskAffair(cn.redflagsoft.base.bean.Task, java.lang.Long, java.lang.Byte, cn.redflagsoft.base.bean.RFSObject, short)
	 */
	@Override
	protected void processTaskAffair(Task task, Long bizId, Byte bizAction,	RFSObject object, short tag) {
		mattersHandler.processTaskAffair(task, bizId, bizAction, object, tag);
	}
}
