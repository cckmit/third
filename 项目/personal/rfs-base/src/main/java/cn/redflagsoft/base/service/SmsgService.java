package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.apps.bean.core.Attachment;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgAttachment;
import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.vo.SmsgReadVO;
import cn.redflagsoft.base.vo.SmsgVO;

/**
 * 消息管理。
 * 
 * @author lf
 * @author lcj
 *
 */
public interface SmsgService extends RFSObjectService<Smsg>{
	
	/**
	 * 查询待发送的消息。给消息网关使用。可以分批查询。
	 * 
	 * <p>查询规则是：
	 * 	“bizStatus == 已发布”
	 * 且“发送状态  != 全部发送”
	 * 且“发布时间（生效时间）<= NOW” 
	 * 且 “过期时间 == null || 过期时间 > NOW”
	 * 且“发送时间 == null”
	 * 且“接收人的发送状态 == 未发送”。
	 * 
	 * 其中“发送时间”和“接收人的发送状态”的条件基本上是冗余的，如果系统数据一致，一般是不需要
	 * 这些条件的。
	 * 
	 * 
	 * @param startIndex 对象索引号，从0开始
	 * @param maxResults 对象数量，全部可以使用Integer.MAX_VALUE
	 * @return Smsg的ID集合
	 */
	List<Long> findSendableSmsgIds(int startIndex, int maxResults);
	
	/**
	 * 查询待发送的消息的数量。
	 * @see SmsgService#findSendableSmsgIds(int, int)
	 * @return
	 */
	int getSendableSmsgCount();
	
	/**
	 * 审核消息。
	 * 
	 * <p>修改消息状态为已审核，设置审核时间为当前时间。
	 * <p>已拟写的消息才能审核，不满足条件则抛出运行时异常。
	 * 
	 * @param smsgId 被审核的消息的ID
	 */
	void checkSmsg(Long smsgId);
		
	/**
	 * 审核消息。
	 * 
	 * <p>修改消息状态为已审核，设置审核时间为当前时间。
	 * <p>已拟写的消息才能审核，不满足条件则抛出运行时异常。
	 * @param smsg 被审核的消息
	 */
	void checkSmsg(Smsg smsg);
	
	/**
	 * 批准消息。
	 * 
	 * <p>修改消息状态为已批准，设置批准时间为当前时间。
	 * <p>已经审核的消息才能批准，不满足条件则抛出运行时异常。
	 * 
	 * @param smsgId 被批准的消息的ID
	 */
	void approveSmsg(Long smsgId);
	
	/**
	 * 批准消息。
	 * 
	 * <p>修改消息状态为已批准，设置批准时间为当前时间。
	 * <p>已经审核的消息才能批准，不满足条件则抛出运行时异常。
	 * @param smsg 被批准消息
	 */
	void approveSmsg(Smsg smsg);
	
	/**
	 * 消息发布。
	 * 
	 * <p>改变消息状态(<code>bizStatus</code>)为“<code>BIZ_STATUS_已发布</code>”，
	 * 如果发布时间为空则设置为当前时间，不为空则表示消息已经设置过生效时间。
	 * 
	 * <p>消息发布后，消息网关将扫描并发送消息。扫描的规则见 {@link #findSendableMsg(int, int)}.
	 * 
	 * <p>发布功能的执行条件如下，不满足以下条件的应该抛出运行时异常。
	 * <ul>
	 * <li>如果消息需要审核，则bizStatus为“已批准”的消息才能进行发布。
	 * <li>如果消息不需要审核，则bizStatus为“已拟写”的消息才能进行发布。
	 * <li>消息接收人集合必须不为空，且接收人的地址集合不能为空。
	 * </ul>
	 * 
	 * @param smsgId 被发布的消息的ID
	 */
	void publishSmsg(Long smsgId);
	
	/**
	 * 消息发布。
	 * 
	 * <p>改变消息状态(<code>bizStatus</code>)为“<code>BIZ_STATUS_已发布</code>”，
	 * 如果发布时间为空则设置为当前时间，不为空则表示消息已经设置过生效时间。
	 * 
	 * <p>消息发布后，消息网关将扫描并发送消息。扫描的规则见 {@link #findSendableMsg(int, int)}.
	 * 
	 * <p>发布功能的执行条件如下，不满足以下条件的应该抛出运行时异常。
	 * <ul>
	 * <li>如果消息需要审核，则bizStatus为“已批准”的消息才能进行发布。
	 * <li>如果消息不需要审核，则bizStatus为“已拟写”的消息才能进行发布。
	 * <li>消息接收人集合必须不为空，且接收人的地址集合不能为空。
	 * </ul>
	 * 
	 * @param smsg 被发布的消息
	 */
	void publishSmsg(Smsg smsg);
	
	/***
	 * 根据ID撤销对应的SMSG。
	 * 
	 * <p>将各接收人发送状态设置为“SEND_STATUS_已撤销”，再将消息的状态（bizStatus)
	 * 设置为“BIZ_STATUS_已撤销”，撤销时间设置为当前时间。
	 *
	 * <p>已发布的消息才需要撤销，不满足条件的操作应该抛出运行时异常。
	 * 
	 * @param smsgId
	 */
	void cancelSmsg(Long smsgId);
	
	/**
	 * 撤销已经发布的消息。
	 * 
	 * <p>将各接收人发送状态设置为“SEND_STATUS_已撤销”，再将消息的状态（bizStatus)
	 * 设置为“BIZ_STATUS_已撤销”，撤销时间设置为当前时间。
	 *
	 * <p>已发布的消息才需要撤销，不满足条件的操作应该抛出运行时异常。
	 * 
	 * @param smsg 被撤销的消息
	 */
	void cancelSmsg(Smsg smsg);
	
	/**
	 * 撤销指定接收人。
	 * 
	 * 将接收人的发送状态设置为“SEND_STATUS_已撤销”。
	 * @param r 指定接收人
	 */
	void cancelSmsgReceiver(SmsgReceiver r);
	
	/**
	 * 如果某接收人发送失败需要重新发送，可调用这个方法重置
	 * 接收人为待发送状态。
	 * 将尝试发送次数清零，将有效状态设置为有效，将发送状态设置
	 * 为待发送。
	 * @param r
	 */
	void resetSmsgReceiver(SmsgReceiver r);
	
	/***
	 * 根据ID删除对应的SMSG。
	 * 
	 * <p>将消息状态（bizStatus）设置为“已删除”，删除时间设置为当前时间。已删除的消息只
	 * 能在“已删除消息”菜单查看。
	 * 
	 * @param smsgId
	 */
	void deleteSmsg(Long smsgId);
	
	/**
	 * 删除消息。
	 * 
	 * <p>将消息状态（bizStatus）设置为“已删除”，删除时间设置为当前时间。已删除的消息只
	 * 能在“已删除消息”菜单查看。
	 * 
	 * @param smsg 被删除的消息对象
	 */
	void deleteSmsg(Smsg smsg);
	
//	/**
//	 * 发送信息 (发送到消息对应的所有人)。
//	 * 
//	 * 
//	 * @param smsgId
//	 */
//	void sendSmsg(Long smsgId);
	
	/**
	 * 查询指定消息对应的接收人列表。
	 * @param smsgId
	 * @return
	 */
	List<SmsgReceiver> findReceivers(long smsgId);
	
	/****
	 * 	信息登记.
	 * @param smsg
	 * @param smsgReclist
	 * @param attachments
	 */
	Smsg createSmsg(Smsg smsg, List<SmsgReceiver> smsgReclist, List<SmsgAttachment> attachments);
	
	/**
	 * 消息登记。
	 * 
	 * @param smsg 被登记的消息
	 * @param receiverIds 接收人ID集合
	 * @param attachedFileIds 附件文件ID集合
	 * @return 登记后的Smsg对象
	 */
	Smsg createMsg(Smsg smsg, List<Long> receiverIds, List<Long> attachedFileIds);

	/**
	 * 更新消息。
	 * 注意，消息的类型（kind）不能被修改。
	 * 
	 * @param smsg 被修改的消息
	 * @param smsgReclist 接收人集合
	 * @param attachments 附件集合
	 * @return 修改后的Smsg对象
	 */
	Smsg updateSmsg(Smsg smsg, List<SmsgReceiver> smsgReclist, List<SmsgAttachment> attachments);
	
	/**
	 * 更新消息。
	 * 
	 * @param smsg 原来的消息
	 * @param param 要修改的属性组成的参数对象
	 * @param newReceiverIds 修改后的接收者ID集合
	 * @param attachedFileIds 消息附件ID集合
	 * @return 修改后的Smsg对象
	 */
	Smsg updateMsg(Smsg smsg, Smsg param, List<Long> newReceiverIds, List<Long> attachedFileIds);
	
	/**
	 * 信息阅读
	 * @return
	 */
//	Map<String,String> smsgRead(Long smsgId);
	
	/***
	 * 	信息阅读(接收人)
	 * @param smsgId
	 * @param clerk
	 * @return
	 */
	SmsgReadVO readSmsg(long smsgId, Clerk clerk);
	
	/***
	 * 	信息阅读(接收人)
	 * @param smsg 必须时持久化对象，而不是前台传递的参数对象。
	 * @param clerk
	 * @return
	 */
	SmsgReadVO readSmsg(Smsg smsg, Clerk clerk);
	
	/***
	 * 根据ID获取对应的SMSG记录
	 * @param smsgId
	 * @return
	 */
	Smsg getSmsg(Long smsgId);
	
	/**
	 * 根据ID获取对应的SMSGVO对象。
	 * 
	 * @param smsgId
	 * @return
	 */
	SmsgVO getSmsgVO(long smsgId);
	
	/**
	 * 查询消息的附件集合。
	 * 
	 * @param smsgId
	 * @return
	 */
	List<Attachment> findSmsgAttachments(Long smsgId);
	
	/**
	 * 根据附件ID删除所有关联的SmsgAttachment对象。
	 * @param attachmentId
	 */
	void removeSmsgAttachmentsByFileId(Long attachmentId);
	
	/**
	 * 查询指定人的消息数量。
	 * 
	 * @param userId 人员、用户ID 
	 * @param msgKind 消息类型
	 * @param readStatus 阅读状态，如果阅读状态<0，则表示忽略该条件
	 * @return 消息数量
	 */
	int getUserSmsgCount(long userId, byte msgKind, byte readStatus);
	
	/**
	 * 获取指定用户的未读消息，有缓存，不是特别精确，用于提醒。
	 * 这里主要指内部消息，只有内部消息才能在系统中阅读。
	 * @param userId
	 * @return
	 */
	int getUserUnreadSmsgCount(long userId);

//	/**
//	 * 
//	 * @param userId
//	 * @param readStatus
//	 * @return
//	 */
//	List<SmsgVO> findUserSmsgs(long userId, byte readStatus);
	
	/**
	 * 获取用户消息数量。这里主要指内部消息，只有内部消息才能在系统中阅读。
	 * @param userId
	 * @return
	 */
	UserSmsgCounts getUserSmsgCounts(long userId);
	
	/**
	 * 用户消息数量。
	 *
	 */
	public static class UserSmsgCounts{
		private long userId;
		private int unreadCount;
		private int readCount;
		
		/**
		 * @param userId
		 * @param unreadCount
		 * @param readCount
		 */
		public UserSmsgCounts(long userId, int unreadCount,	int readCount) {
			super();
			this.userId = userId;
			this.unreadCount = unreadCount;
			this.readCount = readCount;
		}
		/**
		 * 
		 */
		public UserSmsgCounts() {
			super();
		}
		/**
		 * @return the userId
		 */
		public long getUserId() {
			return userId;
		}
		/**
		 * @param userId the userId to set
		 */
		public void setUserId(long userId) {
			this.userId = userId;
		}
		
		/**
		 * @return the unreadCount
		 */
		public int getUnreadCount() {
			return unreadCount;
		}
		/**
		 * @param unreadCount the unreadCount to set
		 */
		public void setUnreadCount(int unreadCount) {
			this.unreadCount = unreadCount;
		}
		/**
		 * @return the readCount
		 */
		public int getReadCount() {
			return readCount;
		}
		/**
		 * @param readCount the readCount to set
		 */
		public void setReadCount(int readCount) {
			this.readCount = readCount;
		}
		public int getTotalCount(){
			return this.readCount + this.unreadCount;
		}
	}
}
