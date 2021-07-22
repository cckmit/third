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
 * ��Ϣ�������񣩡�
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
	 * ���޸�״̬�Ȳ�Ӱ��ҵ�����ݵĸ��¿��Ե������������
	 * ���������� {@link #updateMsg(Smsg, Smsg, List, List)}������
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
		if(smsg.getBizStatus() != Smsg.BIZ_STATUS_���ⶨ  
				&& smsg.getBizStatus() != Smsg.BIZ_STATUS_����׼){
			throw new SmsgRuntimeException("��Ϣ���ܷ�����");
		}
		
		smsg.setBizStatus(Smsg.BIZ_STATUS_�ѷ���);
		//���û��������Чʱ�䣨����ʱ�䣩��ȡ��ǰʱ�䣬����ʹ����Чʱ��Ϊ����ʱ�䡣
		if(smsg.getPublishTime() == null){
			smsg.setPublishTime(new Date());
		}
		
		Smsg smsg2 = getObjectDao().update(smsg);
		List<SmsgReceiver> smsgReceiverList = findReceivers(smsg.getId());
		for (SmsgReceiver smsgReceiver : smsgReceiverList) {
			smsgReceiver.setStatus(SmsgReceiver.STATUS_��Ч);
			smsgReceiverDao.update(smsgReceiver);
		}
		
		if(Smsg.KIND_�ڲ���Ϣ == smsg.getKind()){
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
		if(smsgResult != null && smsgResult.getBizStatus() == Smsg.BIZ_STATUS_�ѷ���){
			List<SmsgReceiver> list = findReceivers(smsgResult.getId());
			for (SmsgReceiver smsgReceiver : list) {
				smsgReceiver.setSendStatus(SmsgReceiver.SEND_STATUS_�ѳ���);
				smsgReceiverDao.update(smsgReceiver);
				
				//������
//				userUnreadSmsgCountCache.remove(smsgReceiver.getToId());
			}
			//�޸�SMSG��״̬
			smsgResult.setBizStatus(Smsg.BIZ_STATUS_�ѳ���);
			smsgResult.setCancelTime(new Date());
			getObjectDao().update(smsgResult);
		}else{
			throw new SmsgRuntimeException("��Ϣ���ܳ���");
		}
	}

	public void deleteSmsg(Long smsgId) {
		Smsg smsgResult = getSmsg(smsgId);
		deleteSmsg(smsgResult);
	}
	
	public void deleteSmsg(Smsg smsgResult) {
		if(smsgResult != null){
			if(smsgResult.getBizStatus() == Smsg.BIZ_STATUS_��д��
					|| smsgResult.getBizStatus() == Smsg.BIZ_STATUS_���ⶨ)
			{
				//����ɾ��
				smsgReceiverDao.remove(Restrictions.eq("smsgId", smsgResult.getId()));
				getObjectDao().delete(smsgResult);
				
				//������
//				userUnreadSmsgCountCache.clear();
			}else{
				//�߼�ɾ��
				List<SmsgReceiver> list = findReceivers(smsgResult.getId());
				for (SmsgReceiver smsgReceiver : list) {
					smsgReceiver.setStatus(SmsgReceiver.STATUS_��Ч);
					smsgReceiverDao.update(smsgReceiver);
					
					//������
//					userUnreadSmsgCountCache.remove(smsgReceiver.getToId());
				}
				smsgResult.setBizStatus(Smsg.BIZ_STATUS_��ɾ��);
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
			smsg.setBizStatus(Smsg.BIZ_STATUS_��д��);
		}
		
		//ȥ���ظ��Ľ�����
		if(smsgReclist != null){
			smsgReclist = new ArrayList<SmsgReceiver>(new HashSet<SmsgReceiver>(smsgReclist));
		}
		
		//�����Ϣ�������������Ϊ��д״̬
		if(isComplete(smsg, smsgReclist)){
			smsg.setBizStatus(Smsg.BIZ_STATUS_���ⶨ);
		}
		smsg.setAttached(attachments != null ? attachments.size() : 0);
		smsg.setSendNum(smsgReclist.size());
//		private int sentNum=0;			//�ѷ�����
//		private int readNum=0;			//�Ķ�����
		
		
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
		Assert.notNull(smsg, "��Ϣ������Ϊ��");
		Assert.notNull(clerk, "��Ϣ�Ķ��߲���Ϊ��");
		Assert.notNull(smsg.getId(), "��Ϣ������Ч");
		Assert.notNull(clerk.getId(), "��Ϣ�Ķ�����Ч");
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
			throw new AccessDeniedException("���û���Ȩ���Ķ�����Ϣ��" + clerk.getName());
		}
		
		if(receiver.getReadStatus() == SmsgReceiver.READ_STATUS_δ�� && receiver.getStatus() == SmsgReceiver.STATUS_��Ч){
			receiver.setReadStatus(SmsgReceiver.READ_STATUS_�Ѷ�);
			receiver.setReadTime(new Date());
			smsgReceiverDao.update(receiver);
			// �Ķ�����Ժ�,������Ϣ�Ķ�����
			smsg.setReadNum(smsg.getReadNum() + 1);
			getObjectDao().update(smsg);
			
			//��������Ϣ�Ļ���
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
		if(smsg.getBizStatus() != Smsg.BIZ_STATUS_���ⶨ 
				&& smsg.getBizStatus() != Smsg.BIZ_STATUS_��д��){
			throw new SmsgRuntimeException("��Ϣ���ɱ༭");
		}
		if(smsg.getWriteTime() == null){
			smsg.setWriteTime(new Date());
		}
		if(smsg.getExpirationTime() != null){
			//smsg.setExpirationTime(DateUtils.toEndOfDay(smsg.getExpirationTime()));
		}
		smsg.setBizStatus(Smsg.BIZ_STATUS_��д��);
		
		//ȥ���ظ��Ľ�����
		if(smsgReclist != null){
			smsgReclist = new ArrayList<SmsgReceiver>(new HashSet<SmsgReceiver>(smsgReclist));
		}
				
		//�����Ϣ�������������Ϊ��д״̬
		if(isComplete(smsg, smsgReclist)){
			smsg.setBizStatus(Smsg.BIZ_STATUS_���ⶨ);
		}
		smsg.setAttached(attachments != null ? attachments.size() : 0);
		smsg.setSendNum(smsgReclist.size());
		Smsg updateSmsg = getObjectDao().update(smsg);
		if(updateSmsg != null){
			//��ɾ��֮ǰ�Ľ�����
			smsgReceiverDao.remove(Restrictions.eq("smsgId", updateSmsg.getId()));
			
			//�ٱ����µĽ�����
			for (SmsgReceiver smsgReceiver : smsgReclist) {
				smsgReceiver.setSmsgId(updateSmsg.getId());
				smsgReceiver.setSmsgCode(updateSmsg.getCode());
				if(smsgReceiver.getExpirationTime() == null){
					smsgReceiver.setExpirationTime(smsg.getExpirationTime());
				}
				smsgReceiverDao.save(smsgReceiver);
			}
			
			// ��ɾ���� ����Ϣ����Ӧ������ ����.
			smsgAttachmentDao.remove(Restrictions.eq("smsgId", updateSmsg.getId()));
			
			if(attachments != null){
				for (SmsgAttachment sa : attachments) {
					sa.setSmsgId(updateSmsg.getId());
					sa.setSmsgCode(updateSmsg.getCode());
					smsgAttachmentDao.save(sa);
				}
			}
		}
		
		//������
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
		// ��bizStatus == �ѷ����� and ������״̬  != ȫ�����͡�
		SimpleExpression eq = Restrictions.eq("bizStatus",Smsg.BIZ_STATUS_�ѷ���);
		SimpleExpression ne = Restrictions.ne("sendStatus",Smsg.SEND_STATUS_ȫ������);
		
		// ������ʱ�䣨��Чʱ�䣩< NOW�� or ����ʱ�䣨��Чʱ�䣩 = NOW
		SimpleExpression le = Restrictions.le("publishTime", now);
		SimpleExpression ge = Restrictions.ge("publishTime + " + getSendExpirationDays(), now);
		
		// ������ʱ�� == null or ����ʱ�� > NOW��
		Logic or2 = Restrictions.logic(Restrictions.isNull("expirationTime")).or(Restrictions.gt("expirationTime", now));
		
		//������ʱ�� == null��
		Criterion isNull = Restrictions.isNull("sendTime");
		
		Logic logic = Restrictions.logic(eq).and(ne).and(le).and(ge).and(or2).and(isNull);
		
		if(log.isDebugEnabled()){
			log.debug("�����ɷ�����Ϣ�Ĳ�ѯ������" + logic.toString());
		}
		return logic;
	}

	public void checkSmsg(Long smsgId) {
		Smsg smsg = getSmsg(smsgId);
		checkSmsg(smsg);
	}
	
	public void checkSmsg(Smsg smsg) {
		if(smsg.getBizStatus() == Smsg.BIZ_STATUS_���ⶨ){
			smsg.setBizStatus(Smsg.BIZ_STATUS_�����);
			smsg.setCheckTime(new Date());
			updateObject(smsg);
		}else{
			throw new SmsgRuntimeException("��Ϣ���ܱ����,�˶���Ϣ״̬");
		}
	}


	public void approveSmsg(Long smsgId) {
		Smsg smsg = getSmsg(smsgId);
		approveSmsg(smsg);
	}
	public void approveSmsg(Smsg smsg) {
		if(smsg.getBizStatus() == Smsg.BIZ_STATUS_�����){
			smsg.setBizStatus(Smsg.BIZ_STATUS_����׼);
			smsg.setApproveTime(new Date());
			updateObject(smsg);
		}else{
			throw new SmsgRuntimeException("��Ϣ���ܱ���׼,�˶���Ϣ״̬");
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
		//��Ϣ���Ͳ��ܱ��޸�
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
			//log.warn("����Ч���յ�ַ��" + c.getName());
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
				log.warn("�޷�������Ϣ�ķ�������Ϣ��ϵͳ��û�к��ʵĵ�¼�û���");
			}
		}
	}
	
	/**
	 * ��ȡ�����˵ĵ�ַ��������Ϣ���ʹ�Clerk��������ȡ��
	 * @param c clerk����
	 * @param smsgKind ��Ϣ����
	 * @return ���յ�ַ
	 */
	private String getAddressOfReceiver(Clerk c, byte smsgKind){
		if(smsgKind == Smsg.KIND_�����ʼ�){
			return c.getEmailAddr();
		}else if(smsgKind == Smsg.KIND_�ڲ���Ϣ){
			return "��";
		}else if(smsgKind == Smsg.KIND_�ֻ�����){
			return c.getMobNo();
		}
		return null;
	}
	
	
	
	
	
	/**
	 * ��bizStatus == �ѷ�����
	 * �ҡ�����״̬  != ȫ�����͡�
	 * �ҡ�����ʱ�䣨��Чʱ�䣩<= NOW�� 
	 * �� ������ʱ�� == null || ����ʱ�� > NOW��
	 * �ҡ�����ʱ�� == null��
	 * �ҡ������˵ķ���״̬ == δ���͡���
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
		return Smsg.BIZ_STATUS_�ѷ��� == bizStatus
				&& (Smsg.SEND_STATUS_��δ���� == sendStatus || Smsg.SEND_STATUS_���ַ��� == sendStatus)
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
		return SmsgReceiver.SEND_STATUS_δ���� == sendStatus 
				&& SmsgReceiver.STATUS_��Ч == status
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
		//�Զ�����Senders������Ѿ��ֹ����ã����Զ�������Ч
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
				log.debug("û�п��õ���Ϣ������: " + smsg + " -> " + r, e);
			} catch(SmsgNotSendableException e){
				log.debug("��Ϣ���ɷ���: " + smsg + " -> " + r, e);
			}catch (SmsgException e) {
				log.debug("��Ϣ����ָ����ʱ����: " + smsg + " -> " + r, e);
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
			String s = String.format("%s �� %s(%s, %s)", smsg.getKindName(), r.getToName(), r.getToId(), r.getToAddr());
			throw new SmsgNoSupportedSenderException(s);
		}
		String s = String.format("%s �� %s(%s, %s)", smsg.getKindName(), r.getToName(), r.getToId(), r.getToAddr());
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
					smsg.setSendStatus(Smsg.SEND_STATUS_���ַ���);
					sentList.add(receiver);
				}else{
					log.debug("��Ϣ���Ϳ���ʧ����");
				}
			}catch(SmsgNoSupportedSenderException e){
				log.debug("û�п��õ���Ϣ������: " + smsg + " -> " + r, e);
			} catch(SmsgNotSendableException e){
				log.debug("��Ϣ���ɷ���: " + smsg + " -> " + r, e);
			}catch (SmsgException e) {
				log.debug("��Ϣ����ָ����ʱ����: " + smsg + " -> " + r, e);
			}
		}
		if(smsg.getSentNum() >= smsg.getSendNum()){
			smsg.setSentNum(smsg.getSendNum());
			smsg.setSendStatus(Smsg.SEND_STATUS_ȫ������);
			smsg.setSendTime(new Date());
		}
		
		getObjectDao().update(smsg);
		
		//�¼�����
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
			String s = String.format("%s ���͵� %s(%s, %s)", smsg.getKindName(), r.getToName(), r.getToId(), r.getToAddr());
			throw new SmsgNoSupportedSenderException(s);
		}
		String s = String.format("%s ���͵� %s(%s, %s)", smsg.getKindName(), r.getToName(), r.getToId(), r.getToAddr());
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
			log.info("��һ�߳����ڷ�����Ϣ��ȡ����ǰ���Ͳ�����");
			return 0;
		}
	}
	
	public void setSendAvailableMsgsStatus(boolean bool){
		log.info("ͨ���ⲿǿ������ isSendingAvailabelMsgs ��ֵΪ�� " + bool);
		isSendingAvailabelMsgs.set(bool);
	}
	
	private int sendAvailableMsgsInternal(){
		//ÿ�δ���������Ϣ����
		int batchSize = AppsGlobals.getProperty("smsg.send.batch_size", 100);
		//����������ܻ������⣺���������ܻ��޸ķ���״̬�����ٴβ�ѯ������仯�����߷�������ȫ����״̬

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
			log.info("���ι���ѯ�ɷ�����Ϣ " + total + " ����ѭ�� " + count + " �Σ�ʵ���ύ���߷�����Ϣ" + n + " ����");
			
			if(moreThanBatchSize){
				log.warn("��������Ϣ����" + total + "��������ֻ������" + maxResults + "�������������´δ�������ֹ�����");
			}
			return maxResults;
		}else{
			log.debug("û�пɷ��͵���Ϣ");
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
//			count = getUserSmsgCount(userId, SmsgReceiver.READ_STATUS_δ��);
//			userUnreadSmsgCountCache.put(userId, count);
//		}else{
//			if(IS_DEBUG_ENABLED){
//				log.debug("Get unread smsg count from cache: " + count);
//			}
//		}
//		return count;
		
		return getUserSmsgCount(userId, Smsg.KIND_�ڲ���Ϣ, (byte)-10/*SmsgReceiver.READ_STATUS_δ��*/);
	}
	

	////////////////////////////////////////
	// �¼��������
	///////////////////
	
	private void handleSendStart(SmsgSender sender, Smsg smsg, SmsgReceiver r){
		/*
		r = extractSmsgReceiver(sender, r);
		r.setSendStatus(SmsgReceiver.SEND_STATUS_���ڷ���);
		r.setSendTime(new Date());
		smsgReceiverDao.update(r);
		
		if(IS_DEBUG_ENABLED){
			log.debug("��Ϣ��ʼ���ͣ�" + r);
		}
		*/
		//FIXME ���ϴ����޸ķ���״̬���������ʧ��û�н�״̬�Ļ��������ٳ����´η��ͣ�
		//���ܻ��Ƶ���Ϣ��û�дﵽ����Դ������޷��ٴη��ͣ�����ע�͵���
		log.info("��ʼ������Ϣ��" + r);
	}
	
	private void handleSendFail(SmsgSender sender, Smsg smsg, SmsgReceiver r){
		r = extractSmsgReceiver(sender, r);
		int retryLimit = getMaxSendRetryLimit();
		
		r.setTrySendCount(r.getTrySendCount() + 1);
		if(r.getTrySendCount() > retryLimit){
			String s = String.format("��Ϣ�Ѿ����Է��� %s �Σ����������� %s��������ϢΪ����ʧ��: %s", 	r.getTrySendCount(), retryLimit, r);
			log.warn(s);
			
			r.setSendStatus(SmsgReceiver.SEND_STATUS_����ʧ��);
		}else{
			r.setSendStatus(SmsgReceiver.SEND_STATUS_δ����);
		}
		r.setSendTime(new Date());
		smsgReceiverDao.update(r);
		
		if(IS_DEBUG_ENABLED){
			log.debug("��Ϣ����ʧ�ܣ�" + r);
		}
	}
	
	private void handleSendSuccess(SmsgSender sender, Smsg smsg, SmsgReceiver r){
		r = extractSmsgReceiver(sender, r);
		smsg = extractSmsg(sender, smsg);
		
		r.setSendStatus(SmsgReceiver.SEND_STATUS_�ѷ���);
		r.setSendTime(new Date());
		//r.setTrySendCount(0);
		smsgReceiverDao.update(r);
		
		if(IS_DEBUG_ENABLED){
			log.debug("��Ϣ���ͳɹ���" + r);
		}
		
		smsg.setSentNum(smsg.getSentNum() + 1);
		smsg.setSendStatus(Smsg.SEND_STATUS_���ַ���);
		
		if(smsg.getSentNum() >= smsg.getSendNum()){
			smsg.setSentNum(smsg.getSendNum());
			smsg.setSendStatus(Smsg.SEND_STATUS_ȫ������);
			smsg.setSendTime(new Date());
			
			if(IS_DEBUG_ENABLED){
				log.debug("��Ϣ�����н����߾����ͳɹ�: " + smsg.getId() + ", " + smsg.getContent());
			}
		}
		
		getObjectDao().update(smsg);
		
		//�¼�����
		EventDispatcher dispatcher = getEventDispatcher();
		if(dispatcher != null){
			dispatcher.dispatchEvent(new SmsgEvent(SmsgEvent.Type.SENT, smsg, Lists.newArrayList(r)));
		}
	}
	
	private Smsg extractSmsg(SmsgSender sender, Smsg smsg){
		if(sender instanceof IntenalSmsgSender){
			return smsg;
		}else{
			//��������ڲ���Ϣ��������Ϊ�˱�֤�첽������Ҳ���õ����µ�
			//Smsg�����������������²�ѯ����
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
	 * ��Ϣ�����¼���������
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
	 * �ڲ���Ϣ��������
	 */
	public static class IntenalSmsgSender implements SmsgSender{
		private final SmsgServiceImpl smsgService;
		public IntenalSmsgSender(SmsgServiceImpl smsgService) {
			this.smsgService = smsgService;
		}
		public boolean supports(Smsg msg, SmsgReceiver r) {
			return Smsg.KIND_�ڲ���Ϣ == msg.getKind();
		}

		public int send(Smsg msg, SmsgReceiver r) throws SmsgException {
//			r.setSendStatus(SmsgReceiver.SEND_STATUS_�ѷ���);
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
		Map<Byte, Integer> map = ((SmsgDao) getObjectDao()).getUserSmsgCount(userId, (byte)-1/*Smsg.KIND_�ڲ���Ϣ*/);
		Integer unreadCount = map.get(SmsgReceiver.READ_STATUS_δ��);
		Integer readCount = map.get(SmsgReceiver.READ_STATUS_�Ѷ�);
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
			smsgReceiver.setSendStatus(SmsgReceiver.SEND_STATUS_�ѳ���);
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
			smsgReceiver.setStatus(SmsgReceiver.STATUS_��Ч);
			smsgReceiver.setSendStatus(SmsgReceiver.SEND_STATUS_δ����);
			smsgReceiverDao.update(smsgReceiver);
		}
	}
}
