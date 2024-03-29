/*
 * $Id: MattersHandlerWrapper.java 4607 2011-08-18 09:40:26Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import java.util.List;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class MattersHandlerWrapper implements MattersHandler {
	private final MattersHandler mattersHandler;
	public MattersHandlerWrapper(MattersHandler mattersHandler){
		this.mattersHandler = mattersHandler;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#acceptMatters(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long[])
	 */
	public void acceptMatters(Task task, Work work, RFSObject object,
			Long[] matterIds) {
		mattersHandler.acceptMatters(task, work, object, matterIds);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#acceptMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long)
	 */
	public void acceptMatter(Task task, Work work, RFSObject object,
			Long matterId) {
		mattersHandler.acceptMatter(task, work, object, matterId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#acceptMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long, short)
	 */
	public void acceptMatter(Task task, Work work, RFSObject object,
			Long matterId, short tag) {
		mattersHandler.acceptMatter(task, work, object, matterId, tag);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#readyMatters(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long[])
	 */
	public void readyMatters(Task task, Work work, RFSObject object,
			Long[] matterIds) {
		mattersHandler.readyMatters(task, work, object, matterIds);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#readyMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long)
	 */
	public void readyMatter(Task task, Work work, RFSObject object,
			Long matterId) {
		mattersHandler.readyMatter(task, work, object, matterId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#readyMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long, short)
	 */
	public void readyMatter(Task task, Work work, RFSObject object,
			Long matterId, short tag) {
		mattersHandler.readyMatter(task, work, object, matterId, tag);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#finishMatters(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long[])
	 */
	public void finishMatters(Task task, Work work, RFSObject object,
			Long[] matterIds) {
		mattersHandler.finishMatters(task, work, object, matterIds);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#finishMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long)
	 */
	public void finishMatter(Task task, Work work, RFSObject object,
			Long matterId) {
		mattersHandler.finishMatter(task, work, object, matterId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#finishMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long, short)
	 */
	public void finishMatter(Task task, Work work, RFSObject object,
			Long matterId, short tag) {
		mattersHandler.finishMatter(task, work, object, matterId, tag);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#finishMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long, short, java.lang.String)
	 */
	public void finishMatter(Task task, Work work, RFSObject object,
			Long matterId, short tag, String note) {
		mattersHandler.finishMatter(task, work, object, matterId, tag, note);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#finishMatters(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long[], java.lang.String)
	 */
	public void finishMatters(Task task, Work work, RFSObject object,
			Long[] matterIds, String note) {
		mattersHandler.finishMatters(task, work, object, matterIds, note);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#hang(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void hang(Work work, RFSObject object, List<Long> matterIds) {
		mattersHandler.hang(work, object, matterIds);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#hang(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void hang(Work work, RFSObject object, List<Long> matterIds,
			short tag) {
		mattersHandler.hang(work, object, matterIds, tag);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#wake(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void wake(Work work, RFSObject object, List<Long> matterIds) {
		mattersHandler.wake(work, object, matterIds);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#wake(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void wake(Work work, RFSObject object, List<Long> matterIds,
			short tag) {
		mattersHandler.wake(work, object, matterIds, tag);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#avoid(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void avoid(Work work, RFSObject object, List<Long> matterIds) {
		mattersHandler.avoid(work, object, matterIds);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#avoid(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void avoid(Work work, RFSObject object, List<Long> matterIds,
			short tag) {
		mattersHandler.avoid(work, object, matterIds, tag);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#cancel(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void cancel(Work work, RFSObject object, List<Long> matterIds) {
		mattersHandler.cancel(work, object, matterIds);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#cancel(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void cancel(Work work, RFSObject object, List<Long> matterIds,
			short tag) {
		mattersHandler.cancel(work, object, matterIds, tag);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#stop(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void stop(Work work, RFSObject object, List<Long> matterIds) {
		mattersHandler.stop(work, object, matterIds);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#stop(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void stop(Work work, RFSObject object, List<Long> matterIds,
			short tag) {
		mattersHandler.stop(work, object, matterIds, tag);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#withdraw(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void withdraw(Work work, RFSObject object, List<Long> matterIds) {
		mattersHandler.withdraw(work, object, matterIds);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#withdraw(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void withdraw(Work work, RFSObject object, List<Long> matterIds,	short tag) {
		mattersHandler.withdraw(work, object, matterIds, tag);		
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#reject(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void reject(Work work, RFSObject object, List<Long> matterIds) {
		mattersHandler.reject(work, object, matterIds);		
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#reject(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void reject(Work work, RFSObject object, List<Long> matterIds,short tag) {
		mattersHandler.reject(work, object, matterIds, tag);		
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#transfer(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void transfer(Work work, RFSObject object, List<Long> matterIds) {
		mattersHandler.transfer(work, object, matterIds);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#transfer(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void transfer(Work work, RFSObject object, List<Long> matterIds,
			short tag) {
		mattersHandler.transfer(work, object, matterIds, tag);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#undo(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void undo(Work work, RFSObject object, List<Long> matterIds) {
		mattersHandler.undo(work, object, matterIds);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#undo(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void undo(Work work, RFSObject object, List<Long> matterIds,
			short tag) {
		mattersHandler.undo(work, object, matterIds, tag);
	}
}
