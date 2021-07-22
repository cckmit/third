package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.AccessDeniedException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgAttachment;
import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.codegenerator.CodeGeneratorProvider;
import cn.redflagsoft.base.dao.ObjectsDao;
import cn.redflagsoft.base.dao.SmsgAttachmentDao;
import cn.redflagsoft.base.dao.SmsgDao;
import cn.redflagsoft.base.dao.SmsgReceiverDao;
import cn.redflagsoft.base.event2.SmsgEvent;
import cn.redflagsoft.base.event2.SmsgSendEvent;
import cn.redflagsoft.base.event2.SmsgSendEvent.Type;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.SmsgException;
import cn.redflagsoft.base.service.SmsgManager;
import cn.redflagsoft.base.service.SmsgNoSupportedSenderException;
import cn.redflagsoft.base.service.SmsgNotSendableException;
import cn.redflagsoft.base.service.SmsgRuntimeException;
import cn.redflagsoft.base.service.SmsgSender;
import cn.redflagsoft.base.service.SmsgService;
import cn.redflagsoft.base.service.TaskService;
import cn.redflagsoft.base.vo.SmsgReadVO;
import cn.redflagsoft.base.vo.SmsgVO;

import com.google.common.collect.Lists;

/**
 * 消息管理（服务）。
 * @author lf 
 * @author Alex Lin(lcql@msn.com)
 */
public class SmsgServiceImpl extends AbstractRFSObjectService<Smsg> implements SmsgService, SmsgManager, ApplicationContextAware{
	private static final Log log = LogFactory.getLog(SmsgServiceImpl.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	private ObjectsDao objectsDao;
	private TaskService taskService;
	private SmsgReceiverDao smsgReceiverDao;
	private ClerkService clerkService;
	private SmsgAttachmentDao smsgAttachmentDao;
	private CodeGeneratorProvider codeGeneratorProvider;
	private List<SmsgSender> senders = new ArrayList<SmsgSender>();
	//private Cache<Long, Integer> userUnreadSmsgCountCache = new TimedExpirationMap<Long, Integer>("userUnreadSmsgCountCache", 20 * 60 * 1000L, 5000L);
	private AtomicBoolean isSendingAvailabelMsgs = new AtomicBoolean(false);
	
	public SmsgAttachmentDao getSmsgAttachmentDao() {
		return smsgAttachmentDao;
	}

	public void setSmsgAttachmentDao(SmsgAttachmentDao smsgAttachmentDao) {
		this.smsgAttachmentDao = smsgAttachmentDao;
	}
	
	/**
	 * @return the codeGeneratorProvider
	 */
	public CodeGeneratorProvider getCodeGeneratorProvider() {
		return codeGeneratorProvider;
	}

	/**
	 * @param codeGeneratorProvider the codeGeneratorProvider to set
	 */
	public void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider) {
		this.codeGeneratorProvider = codeGeneratorProvider;
	}

	/**
	 * @return the clerkService
	 */
	public ClerkService getClerkService() {
		return clerkService;
	}

	/**
	 * @param clerkService the clerkService to set
	 */
	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	public SmsgReceiverDao getSmsgReceiverDao() {
		return smsgReceiverDao;
	}

	public void setSmsgReceiverDao(SmsgReceiverDao smsgReceiverDao) {
		this.smsgReceiverDao = smsgReceiverDao;
	}
	
	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public ObjectsDao getObjectsDao() {
		return objectsDao;
	}

	public void setObjectsDao(ObjectsDao objectsDao) {
		this.objectsDao = objectsDao;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.AbstractRFSObjectService#deleteObject(cn.redflagsoft.base.bean.RFSObject)
	 */
	@Override
	public void deleteObject(Smsg object) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.AbstractRFSObjectService#removeObject(java.lang.Long)
	 */
	@Override
	public void removeObject(Long id) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.AbstractRFSObjectService#updateObject(cn.redflagsoft.base.bean.RFSObject)
	 */
	@Override
	public Smsg updateObject(Smsg object) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 仅修改状态等不影响业务数据的更新可以调用这个方法，
	 * 否则必须调用 {@link #updateMsg(Smsg, Smsg, List, List)}方法。
	 * 
	 * @see cn.redflagsoft.base.service.impl.AbstractRFSObjectService#updateObject(cn.redflagsoft.base.bean.RFSObject, cn.redflagsoft.base.bean.RFSObject)
	 */
	public Smsg updateObject(Smsg oldObject, Smsg newObject) {
		//throw new UnsupportedOperationException();
		return super.updateObject(oldObject, newObject);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.AbstractRFSObjectService#saveObject(cn.redflagsoft.base.bean.RFSObject)
	 */
	@Override
	public Smsg saveObject(Smsg object) {
		throw new UnsupportedOperationException();
	}

	public void publishSmsg(Long smsgId) {
		Smsg smsg = getSmsg(smsgId);
		publishSmsg(smsg);
	}
	
	public void publishSmsg(Smsg smsg) {
		if(smsg == null){
			throw new NullPointerException("smsg");
		}
		if(smsg.getBizStatus() != Smsg.BIZ_STATUS_已拟定  
				&& smsg.getBizStatus() != Smsg.BIZ_STATUS_已批准){
			throw new SmsgRuntimeException("消息不能发布。");
		}
		
		smsg.setBizStatus(Smsg.BIZ_STATUS_已发布);
		//如果没有设置生效时间（发布时间）则取当前时间，否则使用生效时间为发布时间。
		if(smsg.getPublishTime() == null){
			smsg.setPublishTime(new Date());
		}
		
		Smsg smsg2 = getObjectDao().update(smsg);
		List<SmsgReceiver> smsgReceiverList = findReceivers(smsg.getId());
		for (SmsgReceiver smsgReceiver : smsgReceiverList) {
			smsgReceiver.setStatus(SmsgReceiver.STATUS_有效);
			smsgReceiverDao.update(smsgReceiver);
		}
		
		if(Smsg.KIND_内部消息 == smsg.getKind()){
			sendMsg(smsg2, smsgReceiverList);
		}
	}

	public Smsg getSmsg(Long smsgId) {
		Smsg smsg = getObjectDao().get(smsgId);
		return smsg;
	}
	

	public void cancelSmsg(Long smsgId) {
		Smsg smsgResult = getSmsg(smsgId);
		cancelSmsg(smsgResult);
	}
	
	public void cancelSmsg(Smsg smsgResult) {
		if(smsgResult != null && smsgResult.getBizStatus() == Smsg.BIZ_STATUS_已发布){
			List<SmsgReceiver> list = findReceivers(smsgResult.getId());
			for (SmsgReceiver smsgReceiver : list) {
				smsgReceiver.setSendStatus(SmsgReceiver.SEND_STATUS_已撤销);
				smsgReceiverDao.update(smsgReceiver);
				
				//清理缓存
//				userUnreadSmsgCountCache.remove(smsgReceiver.getToId());
			}
			//修改SMSG的状态
			smsgResult.setBizStatus(Smsg.BIZ_STATUS_已撤销);
			smsgResult.setCancelTime(new Date());
			getObjectDao().update(smsgResult);
		}else{
			throw new SmsgRuntimeException("消息不能撤销");
		}
	}

	public void deleteSmsg(Long smsgId) {
		Smsg smsgResult = getSmsg(smsgId);
		deleteSmsg(smsgResult);
	}
	
	public void deleteSmsg(Smsg smsgResult) {
		if(smsgResult != null){
			if(smsgResult.getBizStatus() == Smsg.BIZ_STATUS_拟写中
					|| smsgResult.getBizStatus() == Smsg.BIZ_STATUS_已拟定)
			{
				//物理删除
				smsgReceiverDao.remove(Restrictions.eq("smsgId", smsgResult.getId()));
				getObjectDao().delete(smsgResult);
				
				//清理缓存
//				userUnreadSmsgCountCache.clear();
			}else{
				//逻辑删除
				List<SmsgReceiver> list = findReceivers(smsgResult.getId());
				for (SmsgReceiver smsgReceiver : list) {
					smsgReceiver.setStatus(SmsgReceiver.STATUS_无效);
					smsgReceiverDao.update(smsgReceiver);
					
					//清理缓存
//					userUnreadSmsgCountCache.remove(smsgReceiver.getToId());
				}
				smsgResult.setBizStatus(Smsg.BIZ_STATUS_已删除);
				smsgResult.setDeleteTime(new Date());
				getObjectDao().update(smsgResult);
			}
		}
	}

	public Smsg createSmsg(Smsg smsg, List<SmsgReceiver> smsgReclist, List<SmsgAttachment> attachments) {
		Assert.notNull(smsgReclist);
		Assert.notNull(smsg);
		if(smsg.getWriteTime() == null){
			smsg.setWriteTime(new Date());
		}
		if(smsg.getExpirationTime() != null){
			//smsg.setExpirationTime(DateUtils.toEndOfDay(smsg.getExpirationTime()));
		}
		if(smsg.getBizStatus() == 0){
			smsg.setBizStatus(Smsg.BIZ_STATUS_拟写中);
		}
		
		//去掉重复的接收人
		if(smsgReclist != null){
			smsgReclist = new ArrayList<SmsgReceiver>(new HashSet<SmsgReceiver>(smsgReclist));
		}
		
		//如果消息基本完整，则改为已写状态
		if(isComplete(smsg, smsgReclist)){
			smsg.setBizStatus(Smsg.BIZ_STATUS_已拟定);
		}
		smsg.setAttached(attachments != null ? attachments.size() : 0);
		smsg.setSendNum(smsgReclist.size());
//		private int sentNum=0;			//已发人数
//		private int readNum=0;			//阅读人数
		
		
		if(smsg.getCode() == null){
			String code = codeGeneratorProvider.generateCode(smsg);
			smsg.setCode(code);
		}
		Smsg saveSmsg = getObjectDao().save(smsg);
		if (saveSmsg != null) {
			for (SmsgReceiver smsgReceiver : smsgReclist) {
				smsgReceiver.setSmsgId(saveSmsg.getId());
				smsgReceiver.setSmsgCode(saveSmsg.getCode());
				if(smsgReceiver.getExpirationTime() == null){
					smsgReceiver.setExpirationTime(smsg.getExpirationTime());
				}
				smsgReceiverDao.save(smsgReceiver);
			}
			if(attachments != null){
				for (SmsgAttachment sa : attachments) {
					sa.setSmsgId(saveSmsg.getId());
					sa.setSmsgCode(saveSmsg.getCode());
					smsgAttachmentDao.save(sa);
				}
			}
			return saveSmsg;
		}
		return null;
	}
	
	protected boolean isComplete(Smsg msg, List<SmsgReceiver> smsgReclist){
		return StringUtils.isNotBlank(msg.getTitle())
				&& StringUtils.isNotBlank(msg.getContent())
				&& smsgReclist != null
				&& smsgReclist.size() > 0;
	}
	
	public SmsgReadVO readSmsg(long smsgId, Clerk clerk) {
		Smsg smsg = getSmsg(smsgId);
		if(clerk == null){
			clerk = UserClerkHolder.getClerk();
		}
		return readSmsg(smsg, clerk);
	}
	
	public SmsgReadVO readSmsg(Smsg smsg, Clerk clerk) {
		Assert.notNull(smsg, "消息对象不能为空");
		Assert.notNull(clerk, "消息阅读者不能为空");
		Assert.notNull(smsg.getId(), "消息对象无效");
		Assert.notNull(clerk.getId(), "消息阅读者无效");
		List<SmsgReceiver> receivers = findReceivers(smsg.getId());
		SmsgReceiver receiver = null;
		long clerkId = clerk.getId().longValue();
		for(SmsgReceiver r: receivers){
			if(r.getToId().longValue() == clerkId){
				receiver = r;
				break;
			}
		}
		
		if(receiver == null){
			throw new AccessDeniedException("该用户无权限阅读此消息：" + clerk.getName());
		}
		
		if(receiver.getReadStatus() == SmsgReceiver.READ_STATUS_未读 && receiver.getStatus() == SmsgReceiver.STATUS_有效){
			receiver.setReadStatus(SmsgReceiver.READ_STATUS_已读);
			receiver.setReadTime(new Date());
			smsgReceiverDao.update(receiver);
			// 阅读完成以后,更新消息阅读人数
			smsg.setReadNum(smsg.getReadNum() + 1);
			getObjectDao().update(smsg);
			
			//清理新消息的缓存
//			long userId = receiver.getToId();
//			if(userUnreadSmsgCountCache.containsKey(userId)){
//				userUnreadSmsgCountCache.remove(userId);
//			}
			
			EventDispatcher dispatcher = getEventDispatcher();
			if(dispatcher != null){
				dispatcher.dispatchEvent(new SmsgEvent(SmsgEvent.Type.READ, smsg, Lists.newArrayList(receiver)));
			}
		}
		
		return new SmsgReadVO(smsg, receiver);
	}

	public SmsgVO getSmsgVO(long smsgId) {
		Smsg smsg = getSmsg(smsgId);
		if(smsg != null){
			List<SmsgReceiver> list = findReceivers(smsgId);
			return new SmsgVO(smsg, list);
		}
		return null;
	}

	public List<SmsgReceiver> findReceivers(long smsgId) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.eq("smsgId", smsgId));
		List<SmsgReceiver> list = smsgReceiverDao.find(filter);
		return list;
	}

	public Smsg updateSmsg(Smsg smsg, List<SmsgReceiver> smsgReclist, List<SmsgAttachment> attachments) {
		Assert.notNull(smsgReclist);
		Assert.notNull(smsg);
		if(smsg.getBizStatus() != Smsg.BIZ_STATUS_已拟定 
				&& smsg.getBizStatus() != Smsg.BIZ_STATUS_拟写中){
			throw new SmsgRuntimeException("消息不可编辑");
		}
		if(smsg.getWriteTime() == null){
			smsg.setWriteTime(new Date());
		}
		if(smsg.getExpirationTime() != null){
			//smsg.setExpirationTime(DateUtils.toEndOfDay(smsg.getExpirationTime()));
		}
		smsg.setBizStatus(Smsg.BIZ_STATUS_拟写中);
		
		//去掉重复的接收人
		if(smsgReclist != null){
			smsgReclist = new ArrayList<SmsgReceiver>(new HashSet<SmsgReceiver>(smsgReclist));
		}
				
		//如果消息基本完整，则改为已写状态
		if(isComplete(smsg, smsgReclist)){
			smsg.setBizStatus(Smsg.BIZ_STATUS_已拟定);
		}
		smsg.setAttached(attachments != null ? attachments.size() : 0);
		smsg.setSendNum(smsgReclist.size());
		Smsg updateSmsg = getObjectDao().update(smsg);
		if(updateSmsg != null){
			//先删除之前的接收人
			smsgReceiverDao.remove(Restrictions.eq("smsgId", updateSmsg.getId()));
			
			//再保存新的接收人
			for (SmsgReceiver smsgReceiver : smsgReclist) {
				smsgReceiver.setSmsgId(updateSmsg.getId());
				smsgReceiver.setSmsgCode(updateSmsg.getCode());
				if(smsgReceiver.getExpirationTime() == null){
					smsgReceiver.setExpirationTime(smsg.getExpirationTime());
				}
				smsgReceiverDao.save(smsgReceiver);
			}
			
			// 先删除掉 该消息所对应的所有 附件.
			smsgAttachmentDao.remove(Restrictions.eq("smsgId", updateSmsg.getId()));
			
			if(attachments != null){
				for (SmsgAttachment sa : attachments) {
					sa.setSmsgId(updateSmsg.getId());
					sa.setSmsgCode(updateSmsg.getCode());
					smsgAttachmentDao.save(sa);
				}
			}
		}
		
		//清理缓存
		//userUnreadSmsgCountCache.clear();
		return updateSmsg;
	}
	
	public List<Long> findSendableSmsgIds(int startIndex, int maxResults) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(buildSendableMsgCriterion());
		filter.setFirstResult(startIndex);
		filter.setMaxResults(maxResults);
		return findObjectIds(filter);
	}

	public int getSendableSmsgCount() {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(buildSendableMsgCriterion());
		return getObjectCount(filter);
	}
	
	protected Criterion buildSendableMsgCriterion(){
		Date now = new Date();
		// “bizStatus == 已发布” and “发送状态  != 全部发送”
		SimpleExpression eq = Restrictions.eq("bizStatus",Smsg.BIZ_STATUS_已发布);
		SimpleExpression ne = Restrictions.ne("sendStatus",Smsg.SEND_STATUS_全部发送);
		
		// “发布时间（生效时间）< NOW” or 发布时间（生效时间） = NOW
		SimpleExpression le = Restrictions.le("publishTime", now);
		SimpleExpression ge = Restrictions.ge("publishTime + " + getSendExpirationDays(), now);
		
		// “过期时间 == null or 过期时间 > NOW”
		Logic or2 = Restrictions.logic(Restrictions.isNull("expirationTime")).or(Restrictions.gt("expirationTime", now));
		
		//“发送时间 == null”
		Criterion isNull = Restrictions.isNull("sendTime");
		
		Logic logic = Restrictions.logic(eq).and(ne).and(le).and(ge).and(or2).and(isNull);
		
		if(log.isDebugEnabled()){
			log.debug("构建可发送消息的查询条件：" + logic.toString());
		}
		return logic;
	}

	public void checkSmsg(Long smsgId) {
		Smsg smsg = getSmsg(smsgId);
		checkSmsg(smsg);
	}
	
	public void checkSmsg(Smsg smsg) {
		if(smsg.getBizStatus() == Smsg.BIZ_STATUS_已拟定){
			smsg.setBizStatus(Smsg.BIZ_STATUS_已审核);
			smsg.setCheckTime(new Date());
			updateObject(smsg);
		}else{
			throw new SmsgRuntimeException("消息不能被审核,核对消息状态");
		}
	}


	public void approveSmsg(Long smsgId) {
		Smsg smsg = getSmsg(smsgId);
		approveSmsg(smsg);
	}
	public void approveSmsg(Smsg smsg) {
		if(smsg.getBizStatus() == Smsg.BIZ_STATUS_已审核){
			smsg.setBizStatus(Smsg.BIZ_STATUS_已批准);
			smsg.setApproveTime(new Date());
			updateObject(smsg);
		}else{
			throw new SmsgRuntimeException("消息不能被批准,核对消息状态");
		}
	}
	
	
	public Smsg createMsg(Smsg smsg, List<Long> receiverIds, List<Long> attachmentIds) {
		List<SmsgReceiver> smsgReceiverList = parseReceivers(smsg, receiverIds);
		List<SmsgAttachment> attachments = parseAttachments(smsg, attachmentIds);
		setFromInfoIfRequired(smsg);
		return createSmsg(smsg, smsgReceiverList, attachments);
	}

	private List<SmsgAttachment> parseAttachments(Smsg smsg,
			List<Long> attachmentIds) {
		List<SmsgAttachment> smsgAttachments = new ArrayList<SmsgAttachment>();
		if(smsg == null){
			return null;
		}
		if(attachmentIds != null && !attachmentIds.isEmpty()){
			for (Long fileId : attachmentIds) {
				SmsgAttachment smsgAttachment = new SmsgAttachment();
				smsgAttachment.setFileId(fileId);
				smsgAttachments.add(smsgAttachment);
			}
		}
		return smsgAttachments;
	}

	public Smsg updateMsg(Smsg smsg, Smsg param, List<Long> newReceiverIds, List<Long> newFileIds) {
		Smsg oldMsg = smsg;
		Smsg newMsg = param;
		
		List<SmsgReceiver> smsgReceiverList = parseReceivers(smsg, newReceiverIds);
		List<SmsgAttachment> attachments = parseAttachments(smsg, newFileIds);
		setFromInfoIfRequired(newMsg);
		
		oldMsg.setTitle(newMsg.getTitle());
		oldMsg.setContent(newMsg.getContent());
		oldMsg.setKeyword(newMsg.getKeyword());
		//消息类型不能被修改
//		oldMsg.setKind(newMsg.getKind());
		oldMsg.setPublishTime(newMsg.getPublishTime());
		oldMsg.setExpirationTime(newMsg.getExpirationTime());

		return updateSmsg(oldMsg, smsgReceiverList, attachments);
	}
	
	private List<SmsgReceiver> parseReceivers(Smsg msg, List<Long> receiverIds){
		if(receiverIds == null){
			receiverIds = new ArrayList<Long>();
		}
		List<SmsgReceiver> smsgReceiverList = new ArrayList<SmsgReceiver>();
		for(Long id: receiverIds){
			Clerk ccc = getClerkService().getClerk(id);
			SmsgReceiver sr = buildSmsgReceiver(msg, ccc);
			if(sr != null){
				smsgReceiverList.add(sr);
			}
		}
		return smsgReceiverList;
	}
	
	private SmsgReceiver buildSmsgReceiver(Smsg msg, Clerk c){
		if(c == null){
			return null;
		}
		SmsgReceiver sr = new SmsgReceiver();
		sr.setToAddr(getAddressOfReceiver(c, msg.getKind()));
		if(StringUtils.isBlank(sr.getToAddr())){
			//log.warn("无有效接收地址：" + c.getName());
			return null;
		}
		sr.setToId(c.getId());
		sr.setToName(c.getName());
		return sr;
	}
	
	private void setFromInfoIfRequired(Smsg smsg){
		if(smsg.getFrId() == null){
//			Clerk c = getClerk();//UserClerkHolder.getClerk();
			Clerk c = UserClerkHolder.getNullableClerk();
			if(c != null){
				smsg.setFrId(c.getId());
				smsg.setFrAddr(getAddressOfReceiver(c, smsg.getKind()));
				smsg.setFrName(c.getName());
				smsg.setFrOrgId(c.getEntityID());
				smsg.setFrOrgName(c.getEntityName());
			}else{
				log.warn("无法设置消息的发送人信息，系统中没有合适的登录用户。");
			}
		}
	}
	
	/**
	 * 获取接收人的地址，根据消息类型从Clerk对象中提取。
	 * @param c clerk对象
	 * @param smsgKind 消息类型
	 * @return 接收地址
	 */
	private String getAddressOfReceiver(Clerk c, byte smsgKind){
		if(smsgKind == Smsg.KIND_电子邮件){
			return c.getEmailAddr();
		}else if(smsgKind == Smsg.KIND_内部消息){
			return "无";
		}else if(smsgKind == Smsg.KIND_手机短信){
			return c.getMobNo();
		}
		return null;
	}
	
	
	
	
	
	/**
	 * “bizStatus == 已发布”
	 * 且“发送状态  != 全部发送”
	 * 且“发布时间（生效时间）<= NOW” 
	 * 且 “过期时间 == null || 过期时间 > NOW”
	 * 且“发送时间 == null”
	 * 且“接收人的发送状态 == 未发送”。
	 * 
	 * @param smsg
	 * @return
	 */
	public boolean isSendable(Smsg smsg){
		long now = System.currentTimeMillis();
		byte bizStatus = smsg.getBizStatus();
		byte sendStatus = smsg.getSendStatus();
		Date publishTime = smsg.getPublishTime();
		Date expirationTime = smsg.getExpirationTime();
		return Smsg.BIZ_STATUS_已发布 == bizStatus
				&& (Smsg.SEND_STATUS_尚未发送 == sendStatus || Smsg.SEND_STATUS_部分发送 == sendStatus)
				&& (publishTime != null && publishTime.getTime() <= now)
				&& (expirationTime == null || expirationTime.getTime() > now)
				&& smsg.getSendNum() > 0;
	}
	
	/**
	 * 
	 * @param r
	 * @return
	 */
	public boolean isSendable(SmsgReceiver r){
		byte sendStatus = r.getSendStatus();
		byte status = r.getStatus();
		int count = r.getTrySendCount();
		Date expirationTime = r.getExpirationTime();
		int maxRetryLimit = getMaxSendRetryLimit();//AppsGlobals.getProperty("smsg.send.retry_limit", 10);
		long now = System.currentTimeMillis();
		return SmsgReceiver.SEND_STATUS_未发送 == sendStatus 
				&& SmsgReceiver.STATUS_有效 == status
				&& (expirationTime == null || expirationTime.getTime() > now)
				&& (count < maxRetryLimit);
	}
	
	int getMaxSendRetryLimit(){
		int maxRetryLimit = AppsGlobals.getProperty("smsg.send.retry_limit", 10);
		return maxRetryLimit;
	}
	
	int getSendExpirationDays(){
		return AppsGlobals.getProperty("smsg.send.expiration_days", 2);
	}
	
	/**
	 * @return the senders
	 */
	public List<SmsgSender> getSenders() {
		return senders;
	}

	/**
	 * @param senders the senders to set
	 */
	public void setSenders(List<SmsgSender> senders) {
		this.senders = senders;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		//自动设置Senders，如果已经手工设置，则自动设置无效
		//if(getSenders() == null){
		@SuppressWarnings("unchecked")
		Map<String, SmsgSender> map = applicationContext.getBeansOfType(SmsgSender.class);
		if(!map.isEmpty()){
			List<SmsgSender> beans = new ArrayList<SmsgSender>(map.values());
			setSenders(beans);
		}
		//}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public int sendMsg(long smsgId){
		Smsg smsgResult = getSmsg(smsgId);
		return sendMsg(smsgResult);
	}

	protected int sendMsg(Smsg smsg) {
		if(isSendable(smsg)){
			List<SmsgReceiver> receivers = findReceivers(smsg.getId());
			return sendMsg(smsg, receivers);
		}
		return 0;
	}
	
	private int sendMsg(Smsg smsg, List<SmsgReceiver> receivers) {
		int n = 0;
		for (SmsgReceiver r : receivers) {
			try {
				n += sendSmsgToSingleReceiver(smsg, r);
			}catch(SmsgNoSupportedSenderException e){
				log.debug("没有可用的消息发送器: " + smsg + " -> " + r, e);
			} catch(SmsgNotSendableException e){
				log.debug("消息不可发送: " + smsg + " -> " + r, e);
			}catch (SmsgException e) {
				log.debug("消息发给指定人时错误: " + smsg + " -> " + r, e);
			}
		}
		return n;
	}
	
	private int sendSmsgToSingleReceiver(Smsg smsg, SmsgReceiver r) throws SmsgException{
		if(isSendable(smsg) && isSendable(r)){
			for(SmsgSender sender: senders){
				if(sender.supports(smsg, r)){
					return sender.send(smsg, r);
				}
			}
			String s = String.format("%s 到 %s(%s, %s)", smsg.getKindName(), r.getToName(), r.getToId(), r.getToAddr());
			throw new SmsgNoSupportedSenderException(s);
		}
		String s = String.format("%s 到 %s(%s, %s)", smsg.getKindName(), r.getToName(), r.getToId(), r.getToAddr());
		throw new SmsgNotSendableException(s);
	}
	
	
	protected SmsgVO sendMsg2(Smsg smsg, List<SmsgReceiver> receivers) {
		//List<SmsgReceiver> receivers = findReceivers(smsg.getId());
		List<SmsgReceiver> sentList = new ArrayList<SmsgReceiver>(receivers.size());
		for (SmsgReceiver r : receivers) {
			try {
				SmsgReceiver receiver = sendSmsgToSingleReceiver2(smsg, r);
				if(receiver != null){
					smsg.setSentNum(smsg.getSentNum() + 1);
					smsg.setSendStatus(Smsg.SEND_STATUS_部分发送);
					sentList.add(receiver);
				}else{
					log.debug("消息发送可能失败了");
				}
			}catch(SmsgNoSupportedSenderException e){
				log.debug("没有可用的消息发送器: " + smsg + " -> " + r, e);
			} catch(SmsgNotSendableException e){
				log.debug("消息不可发送: " + smsg + " -> " + r, e);
			}catch (SmsgException e) {
				log.debug("消息发给指定人时错误: " + smsg + " -> " + r, e);
			}
		}
		if(smsg.getSentNum() >= smsg.getSendNum()){
			smsg.setSentNum(smsg.getSendNum());
			smsg.setSendStatus(Smsg.SEND_STATUS_全部发送);
			smsg.setSendTime(new Date());
		}
		
		getObjectDao().update(smsg);
		
		//事件处理
		EventDispatcher dispatcher = getEventDispatcher();
		if(dispatcher != null){
			dispatcher.dispatchEvent(new SmsgEvent(SmsgEvent.Type.SENT, smsg, sentList));
		}
		return new SmsgVO(smsg, sentList);
	}
	
	/**
	 * 
	 * @param smsg
	 * @param r
	 * @return
	 * @throws Exception 
	 */
	protected SmsgReceiver sendSmsgToSingleReceiver2(Smsg smsg, SmsgReceiver r) throws SmsgException{
		if(isSendable(smsg) && isSendable(r)){
			for(SmsgSender sender: senders){
				if(sender.supports(smsg, r)){
					sender.send(smsg, r);
					return r;
				}
			}
			String s = String.format("%s 发送到 %s(%s, %s)", smsg.getKindName(), r.getToName(), r.getToId(), r.getToAddr());
			throw new SmsgNoSupportedSenderException(s);
		}
		String s = String.format("%s 发送到 %s(%s, %s)", smsg.getKindName(), r.getToName(), r.getToId(), r.getToAddr());
		throw new SmsgNotSendableException(s);
	}

	public int sendAvailableMsgs() {
		if(isSendingAvailabelMsgs.compareAndSet(false, true)){
			try{
				return sendAvailableMsgsInternal();
			}finally{
				isSendingAvailabelMsgs.set(false);
			}
		}else{
			log.info("另一线程正在发送消息，取消当前发送操作。");
			return 0;
		}
	}
	
	public void setSendAvailableMsgsStatus(boolean bool){
		log.info("通过外部强制设置 isSendingAvailabelMsgs 的值为： " + bool);
		isSendingAvailabelMsgs.set(bool);
	}
	
	private int sendAvailableMsgsInternal(){
		//每次处理的最大消息条数
		int batchSize = AppsGlobals.getProperty("smsg.send.batch_size", 100);
		//批量处理可能会有问题：发送器可能会修改发送状态导致再次查询结果集变化，或者发送器完全不该状态

		int total = getSendableSmsgCount();
		if(total > 0){
			int count = 0;
			int n = 0;
					
			/*
			for(int i = 0; i < total; i += batchSize){
				List<Long> ids = findSendableSmsgIds(i, batchSize);
				for(Long smsgId: ids){
					n += sendMsg(smsgId);
					count++;
				}
			}*/
			
			boolean moreThanBatchSize = total > batchSize;
			int maxResults = moreThanBatchSize ? batchSize : total;
			List<Long> ids = findSendableSmsgIds(0, maxResults);
			for(Long smsgId:ids){
				n += sendMsg(smsgId);
				count++;
			}
			log.info("本次共查询可发送消息 " + total + " 条，循环 " + count + " 次，实际提交或者发送消息" + n + " 条。");
			
			if(moreThanBatchSize){
				log.warn("待处理消息数量" + total + "条，本次只处理了" + maxResults + "条，其余留待下次处理或者手工处理。");
			}
			return maxResults;
		}else{
			log.debug("没有可发送的消息");
			return 0;
		}
	}
	
	
	
	@Queryable(argNames={"smsgId"}, name="findSmsgAttachments")
	public List<Attachment> findSmsgAttachments(Long smsgId) {
		return smsgAttachmentDao.findSmsgAttachments(smsgId);
	}

	public void removeSmsgAttachmentsByFileId(Long attachmentId) {
		smsgAttachmentDao.remove(Restrictions.eq("fileId", attachmentId));
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SmsgService#getUserSmsgCount(long, byte, byte)
	 */
	public int getUserSmsgCount(long userId, byte msgKind, byte readStatus) {
		return ((SmsgDao)getObjectDao()).getUserSmsgCount(userId, msgKind, readStatus);
	}
	
	public int getUserUnreadSmsgCount(long userId){
//		Integer count = userUnreadSmsgCountCache.get(userId);
//		if(count == null){
//			count = getUserSmsgCount(userId, SmsgReceiver.READ_STATUS_未读);
//			userUnreadSmsgCountCache.put(userId, count);
//		}else{
//			if(IS_DEBUG_ENABLED){
//				log.debug("Get unread smsg count from cache: " + count);
//			}
//		}
//		return count;
		
		return getUserSmsgCount(userId, Smsg.KIND_内部消息, (byte)-10/*SmsgReceiver.READ_STATUS_未读*/);
	}
	

	////////////////////////////////////////
	// 事件处理机制
	///////////////////
	
	private void handleSendStart(SmsgSender sender, Smsg smsg, SmsgReceiver r){
		/*
		r = extractSmsgReceiver(sender, r);
		r.setSendStatus(SmsgReceiver.SEND_STATUS_正在发送);
		r.setSendTime(new Date());
		smsgReceiverDao.update(r);
		
		if(IS_DEBUG_ENABLED){
			log.debug("消息开始发送：" + r);
		}
		*/
		//FIXME 以上代码修改发送状态后，如果发送失败没有将状态改回来，则不再尝试下次发送，
		//可能会似的消息还没有达到最大尝试次数就无法再次发送，所以注释掉。
		log.info("开始发送消息：" + r);
	}
	
	private void handleSendFail(SmsgSender sender, Smsg smsg, SmsgReceiver r){
		r = extractSmsgReceiver(sender, r);
		int retryLimit = getMaxSendRetryLimit();
		
		r.setTrySendCount(r.getTrySendCount() + 1);
		if(r.getTrySendCount() > retryLimit){
			String s = String.format("消息已经尝试发送 %s 次，超过最大次数 %s，设置消息为发送失败: %s", 	r.getTrySendCount(), retryLimit, r);
			log.warn(s);
			
			r.setSendStatus(SmsgReceiver.SEND_STATUS_发送失败);
		}else{
			r.setSendStatus(SmsgReceiver.SEND_STATUS_未发送);
		}
		r.setSendTime(new Date());
		smsgReceiverDao.update(r);
		
		if(IS_DEBUG_ENABLED){
			log.debug("消息发送失败：" + r);
		}
	}
	
	private void handleSendSuccess(SmsgSender sender, Smsg smsg, SmsgReceiver r){
		r = extractSmsgReceiver(sender, r);
		smsg = extractSmsg(sender, smsg);
		
		r.setSendStatus(SmsgReceiver.SEND_STATUS_已发送);
		r.setSendTime(new Date());
		//r.setTrySendCount(0);
		smsgReceiverDao.update(r);
		
		if(IS_DEBUG_ENABLED){
			log.debug("消息发送成功：" + r);
		}
		
		smsg.setSentNum(smsg.getSentNum() + 1);
		smsg.setSendStatus(Smsg.SEND_STATUS_部分发送);
		
		if(smsg.getSentNum() >= smsg.getSendNum()){
			smsg.setSentNum(smsg.getSendNum());
			smsg.setSendStatus(Smsg.SEND_STATUS_全部发送);
			smsg.setSendTime(new Date());
			
			if(IS_DEBUG_ENABLED){
				log.debug("消息的所有接收者均发送成功: " + smsg.getId() + ", " + smsg.getContent());
			}
		}
		
		getObjectDao().update(smsg);
		
		//事件处理
		EventDispatcher dispatcher = getEventDispatcher();
		if(dispatcher != null){
			dispatcher.dispatchEvent(new SmsgEvent(SmsgEvent.Type.SENT, smsg, Lists.newArrayList(r)));
		}
	}
	
	private Smsg extractSmsg(SmsgSender sender, Smsg smsg){
		if(sender instanceof IntenalSmsgSender){
			return smsg;
		}else{
			//如果不是内部消息发送器，为了保证异步发送器也能拿到最新的
			//Smsg对象，所以在这里重新查询对象
			return getSmsg(smsg.getId());
		}
	}
	
	private SmsgReceiver extractSmsgReceiver(SmsgSender sender, SmsgReceiver r){
		if(sender instanceof IntenalSmsgSender){
			return r;
		}else{
			return smsgReceiverDao.get(r.getId());
		}
	}
	
	/**
	 * 消息发送事件监听器。
	 */
	public static class SmsgSendEventListener implements EventListener<SmsgSendEvent>{
		private final SmsgServiceImpl smsgService;
		public SmsgSendEventListener(SmsgServiceImpl smsgService) {
			this.smsgService = smsgService;
		}
		/* (non-Javadoc)
		 * @see org.opoo.apps.event.v2.EventListener#handle(org.opoo.apps.event.v2.Event)
		 */
		public void handle(SmsgSendEvent event) {
			Type type = event.getType();
			if(SmsgSendEvent.Type.START == type){
				smsgService.handleSendStart(event.getSource(), event.getMsg(), event.getReceiver());
			}else if(SmsgSendEvent.Type.FAIL == type){
				smsgService.handleSendFail(event.getSource(), event.getMsg(), event.getReceiver());
			}else if(SmsgSendEvent.Type.SUCCESS == type){
				smsgService.handleSendSuccess(event.getSource(), event.getMsg(), event.getReceiver());
			}
		}
	}
	
	/**
	 * 内部消息发送器。
	 */
	public static class IntenalSmsgSender implements SmsgSender{
		private final SmsgServiceImpl smsgService;
		public IntenalSmsgSender(SmsgServiceImpl smsgService) {
			this.smsgService = smsgService;
		}
		public boolean supports(Smsg msg, SmsgReceiver r) {
			return Smsg.KIND_内部消息 == msg.getKind();
		}

		public int send(Smsg msg, SmsgReceiver r) throws SmsgException {
//			r.setSendStatus(SmsgReceiver.SEND_STATUS_已发送);
//			r.setSendTime(new Date());
//			r.setTrySendCount(0);
//			return smsgReceiverDao.update(r);
			smsgService.handleSendSuccess(this, msg, r);
			return 1;
		}

		/* (non-Javadoc)
		 * @see org.springframework.core.Ordered#getOrder()
		 */
		public int getOrder() {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SmsgService#getUserSmsgCounts(long)
	 */
	public UserSmsgCounts getUserSmsgCounts(long userId) {
		Map<Byte, Integer> map = ((SmsgDao) getObjectDao()).getUserSmsgCount(userId, (byte)-1/*Smsg.KIND_内部消息*/);
		Integer unreadCount = map.get(SmsgReceiver.READ_STATUS_未读);
		Integer readCount = map.get(SmsgReceiver.READ_STATUS_已读);
		int uc = unreadCount != null ? unreadCount.intValue() : 0;
		int rc = readCount != null ? readCount.intValue() : 0;
		return new UserSmsgCounts(userId, uc, rc);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SmsgService#cancelSmsgReceiver(cn.redflagsoft.base.bean.SmsgReceiver)
	 */
	public void cancelSmsgReceiver(SmsgReceiver r) {
		if(r != null){
			SmsgReceiver smsgReceiver = smsgReceiverDao.get(r.getId());
			smsgReceiver.setSendStatus(SmsgReceiver.SEND_STATUS_已撤销);
			smsgReceiverDao.update(smsgReceiver);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SmsgService#resetSmsgReceiver(cn.redflagsoft.base.bean.SmsgReceiver)
	 */
	public void resetSmsgReceiver(SmsgReceiver r) {
		if(r != null){
			SmsgReceiver smsgReceiver = smsgReceiverDao.get(r.getId());
			smsgReceiver.setTrySendCount(0);
			smsgReceiver.setStatus(SmsgReceiver.STATUS_有效);
			smsgReceiver.setSendStatus(SmsgReceiver.SEND_STATUS_未发送);
			smsgReceiverDao.update(smsgReceiver);
		}
	}
}
