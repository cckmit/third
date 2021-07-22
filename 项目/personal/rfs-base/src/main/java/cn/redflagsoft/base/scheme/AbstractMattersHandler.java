package cn.redflagsoft.base.scheme;


import java.util.List;

import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

public abstract class AbstractMattersHandler implements MattersHandler {
	public void acceptMatter(Task task, Work work, RFSObject object, Long matterId) {
		acceptMatter( task, work,  object, matterId,(short)0) ;		
	}
	
	public void acceptMatter(Task task, Work work, RFSObject object, Long matterId,short tag) {
		//processWorkAffair(task, work, matterId, MatterAffair.ACTION_ACCEPT_MATTER, object);
		
		//处理task的关联操作
		//processTaskAffair(task, work, matterId, MatterAffair.ACTION_ACCEPT_MATTER, object);
		//处理work的辅助关联操作
		//processWorkAffair(task, work, matterId, MatterAffair.ACTION_ACCEPT_MATTER, object);
		
		processWorkAffair(work, matterId, MatterAffair.ACTION_ACCEPT_MATTER, object, tag, null, null);
		
		processTaskAffair(task, matterId, MatterAffair.ACTION_ACCEPT_MATTER, object,tag);		
	}

	public void acceptMatters(Task task, Work work, RFSObject object,
			Long[] matterIds) {
		if(matterIds != null){
			for(Long matterId : matterIds){
				if(matterId == null) continue;
				else acceptMatter(task, work, object, matterId);
			}
		}
	}

	public void finishMatter(Task task, Work work, RFSObject object,
			Long matterId) {
		finishMatter(task, work, object,matterId,(short)0);
	}
	public void finishMatter(Task task, Work work, RFSObject object,
			Long matterId,short tag) {
		processWorkAffair(work, matterId, MatterAffair.ACTION_END_MATTER, object,tag,null, null);
	}

	public void finishMatters(Task task, Work work, RFSObject object,
			Long[] matterIds) {
		if(matterIds != null){
			for(Long matterId : matterIds){
				if(matterId == null) continue;
				else finishMatter(task, work, object, matterId);
			}
		}
	}
	
	public void finishMatters(Task task, Work work, RFSObject object,
			Long[] matterIds,String note) {
		if(matterIds != null){
			for(Long matterId : matterIds){
				if(matterId == null) continue;
				else finishMatter(task, work, object, matterId,(short)0,note);
			}
		}
	}
	
//	public void finishMatters(Task task, Work work, RFSObject object,
//			Long[] matterIds, short tag, String note,
//			List<RFSItemable> relatedItems) {
//		if (matterIds != null) {
//			for (Long matterId : matterIds) {
//				if (matterId == null) {
//					continue;
//				} else {
//					// finishMatter(task, work, object, matterId,tag,note);
//					processWorkAffair(work, matterId,
//							MatterAffair.ACTION_END_MATTER, object, tag, note,
//							relatedItems);
//				}
//			}
//		}
//	}
	
	public void finishMatter(Task task, Work work, RFSObject object,
			Long matterId,short tag,String note) {
		processWorkAffair(work, matterId, MatterAffair.ACTION_END_MATTER, object,tag,note,null);
	}
	
	
	
	public void readyMatter(Task task, Work work, RFSObject object,	Long matterId) {
		readyMatter(task,work, object,matterId,(short)0) ;
	}
	public void readyMatter(Task task, Work work, RFSObject object,	Long matterId,short tag) {
		processWorkAffair(work, matterId, MatterAffair.ACTION_DO_END_MATTER, object,tag,null,null);
	}

	public void readyMatters(Task task, Work work, RFSObject object,
			Long[] matterIds) {
		if(matterIds != null){
			for(Long matterId : matterIds){
				if(matterId == null) continue;
				else readyMatter(task, work, object, matterId);
			}
		}
	}
	
	/**
	 * 
	 * @param work
	 * @param object
	 * @param matterId
	 */
	public void hang(Work work, RFSObject object, Long matterId){
		hang(work, object,matterId,(short)0);
	}
	public void hang(Work work, RFSObject object, Long matterId,short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_HANG, object,tag,null,null);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#hang(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void hang(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			hang(work, object, matterId);
		}
	}
	
	public void hang(Work work, RFSObject object, List<Long> matterIds,short tag) {
		for(Long matterId: matterIds){
			hang(work, object, matterId,tag);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#wake(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void wake(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			wake(work, object, matterId);
		}
	}	
	
	public void wake(Work work, RFSObject object, List<Long> matterIds,short tag) {
		for(Long matterId: matterIds){
			wake(work, object, matterId,tag);
		}
	}	
	/**
	 * 
	 * @param work
	 * @param object
	 * @param matterId
	 */
	public void wake(Work work, RFSObject object, Long matterId){
		wake(work,object,matterId,(short)0);
	}

	public void wake(Work work, RFSObject object, Long matterId, short tag) {
		processWorkAffair(work, matterId, MatterAffair.ACTION_WAKE, object, tag, null,null);
	}
	
	
	
	public void avoid(Work work, RFSObject object, Long matterId){
		avoid(work, object,matterId,(short)0);
	}
	public void avoid(Work work, RFSObject object, Long matterId,short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_AVOID, object,tag,null, null);
	}
	public void avoid(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			avoid(work, object, matterId);
		}
	}
	public void avoid(Work work, RFSObject object, List<Long> matterIds,short tag) {
		for(Long matterId: matterIds){
			avoid(work, object, matterId,tag);
		}
	}
	
	public void cancel(Work work, RFSObject object, Long matterId){
		cancel(work, object,matterId,(short)0);
	}
	public void cancel(Work work, RFSObject object, Long matterId,short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_CANCEL, object,tag,null, null);
	}
	public void cancel(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			cancel(work, object, matterId);
		}
	}
	public void cancel(Work work, RFSObject object, List<Long> matterIds,short tag) {
		for(Long matterId: matterIds){
			cancel(work, object, matterId,tag);
		}
	}
	
	
	public void stop(Work work, RFSObject object, Long matterId){
		stop(work, object,matterId,(short)0);
	}
	public void stop(Work work, RFSObject object, Long matterId,short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_STOP, object,tag,null,null);
	}
	public void stop(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			stop(work, object, matterId);
		}
	}
	public void stop(Work work, RFSObject object, List<Long> matterIds,short tag) {
		for(Long matterId: matterIds){
			stop(work, object, matterId,tag);
		}
	}
	
	/**
	 * 
	 * @param work
	 * @param bizId
	 * @param bizAction
	 * @param object
	 * @param tag
	 * @param note
	 * @param relatedItems
	 */
	protected abstract void processWorkAffair(Work work, Long bizId, Byte bizAction, RFSObject object, short tag, String note, WorkScheme ws/*List<RFSItemable> relatedItems*/);
	
	/**
	 * 本级为任务，上级为作业
	 * @param task
	 * @param work
	 * @param matterId
	 * @param bizAction
	 * @param object
	 * @param tag
	 */
	protected abstract void processTaskAffair(Task task, Long bizId, Byte bizAction, RFSObject object,short tag);

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#withdraw(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void withdraw(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			if(matterId != null){
				withdraw(work, object, matterId);
			}
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#withdraw(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void withdraw(Work work, RFSObject object, List<Long> matterIds,	short tag) {
		for(Long matterId: matterIds){
			if(matterId != null){
				withdraw(work, object, matterId, tag);
			}
		}
	}
	public void withdraw(Work work, RFSObject object, Long matterId, short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_WITHDRAW, object, tag, null, null);
	}
	
	public void withdraw(Work work, RFSObject object, Long matterId){
		withdraw(work, object, matterId, (short)0);
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#reject(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void reject(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			if(matterId != null){
				reject(work, object, matterId);
			}
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#reject(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void reject(Work work, RFSObject object, List<Long> matterIds, short tag) {
		for(Long matterId: matterIds){
			if(matterId != null){
				reject(work, object, matterId, tag);
			}
		}
	} 
	
	public void reject(Work work, RFSObject object, Long matterId, short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_REJECT, object, tag, null, null);
	}
	
	public void reject(Work work, RFSObject object, Long matterId){
		reject(work, object, matterId, (short)0);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#transfer(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void transfer(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			if(matterId != null){
				transfer(work, object, matterId);
			}
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#transfer(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
	 */
	public void transfer(Work work, RFSObject object, List<Long> matterIds,
			short tag) {
		for(Long matterId: matterIds){
			if(matterId != null){
				transfer(work, object, matterId, tag);
			}
		}
	}
	public void transfer(Work work, RFSObject object, Long matterId, short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_TRANSFER, object, tag, null, null);
	}
	
	public void transfer(Work work, RFSObject object, Long matterId){
		transfer(work, object, matterId, (short)0);
	}
	
	
	public void undo(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			if(matterId != null){
				undo(work, object, matterId);
			}
		}
	}

	public void undo(Work work, RFSObject object, List<Long> matterIds,
			short tag) {
		for(Long matterId: matterIds){
			if(matterId != null){
				undo(work, object, matterId, tag);
			}
		}
	}
	public void undo(Work work, RFSObject object, Long matterId, short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_UNDO_MATTER, object, tag, null, null);
	}
	
	public void undo(Work work, RFSObject object, Long matterId){
		undo(work, object, matterId, (short)0);
	}
	

//	/**
//	 * 本级为作业，上级也为作业
//	 * @param job
//	 * @param bizId
//	 * @param bizAction
//	 * @param object
//	 * @param tag
//	 */
//	protected abstract void processJobAffair(Job job, Long bizId, Byte bizAction, RFSObject object,short tag);

	
}
