package cn.redflagsoft.base.audit.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserManager;
import org.opoo.ndao.Domain;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.redflagsoft.base.audit.AuditManager;
import cn.redflagsoft.base.audit.AuditMessage;
import cn.redflagsoft.base.audit.Auditor;
import cn.redflagsoft.base.audit.DomainIdentifier;
import cn.redflagsoft.base.audit.MethodCall;
import cn.redflagsoft.base.audit.MutableAuditMessage;
import cn.redflagsoft.base.audit.NotAuditableException;
import cn.redflagsoft.base.bean.AuditLog;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.dao.AuditLogDao;
import cn.redflagsoft.base.security.UserClerk;
import cn.redflagsoft.base.service.ClerkService;

/**
 * Default implementation of AuditManager which uses the database for persistence.
 */
public class AuditManagerImpl implements AuditManager {

    private AuditLogDao auditLogDao;
    private Auditor cactchall = new CatchAllAuditor();
    private volatile boolean asyncMode = true;
    private Executor executor;
    
    private static final Log log = LogFactory.getLog(AuditManagerImpl.class);

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {NotAuditableException.class})
    public void auditMethodCall(MethodCall call, Auditor auditor) throws NotAuditableException {
        MutableAuditMessage message = null;

        if (auditor != null) {
            if (auditor.methodIsAuditable(call.getMethod())) {
                message = auditor.auditMethodCall(call);
            }
            else {
                log.warn("Unable to audit method " + call.getMethod() + " with auditor " + auditor.getClass().getName()
                        + " using default auditor.");
            }
        }

        if (message != null && message.isIgnore()) {
            return;
        }

        if (message == null) {
            message = cactchall.auditMethodCall(call);
        }
        saveAuditMessage(message);
    }

	public AuditMessage audit(UserClerk user, InetAddress address, String details, String description,
			DomainIdentifier domain, String operation) throws NotAuditableException {
		MutableAuditMessage message = new MutableAuditMessage();
		message.setDescription(description);
		message.setDetails(details);
		message.setDomain(domain);
		message.setNode(address);
		message.setOperation(operation);
		message.setTimestamp(new Date());
		message.setUser(user);
		saveAuditMessage(message);
		return message;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void saveAuditMessage(final MutableAuditMessage message) {
		final AuditLog msg = messageToBean(message);
		msg.setId(null);
		if (asyncMode) {
			executor.execute(new Runnable() {
				public void run() {
					try {
						AuditLog saved = auditLogDao.save(msg);
						message.setId(saved.getId());
					} catch (Exception daoe) {
						log.error("Unable to add audit message: " + message, daoe);
					}
				}
			});
		} else {
			try {
				AuditLog saved = auditLogDao.save(msg);
				message.setId(saved.getId());
			} catch (Exception daoe) {
				log.error("Unable to add audit message: " + message, daoe);
			}
		}
	}
    
    protected AuditLog messageToBean(MutableAuditMessage message){
    	AuditLog msg = new AuditLog();
    	msg.setDescription(message.getDescription());
    	msg.setDetails(message.getDetails());
   		msg.setDomain(message.getDoamin());
    	msg.setId(message.getId());
    	msg.setNode(message.getNode() != null ? message.getNode().getHostAddress()  : "undefined");
    	msg.setOperation(message.getOperation());
    	msg.setTimestamp(message.getTimestamp());
    	msg.setUserId(message.getUser() != null && message.getUser().getUser() != null 
    			? message.getUser().getUser().getUserId() : -100L);
    	msg.setUsername(message.getUser() != null && message.getUser().getClerk() != null 
    			? message.getUser().getClerk().getName() : "未知");
    	return msg;
    }
    
    
    protected MutableAuditMessage beanToMessage(final AuditLog msg){
    	MutableAuditMessage message = new MutableAuditMessage();
    	message.setDescription(msg.getDescription());
    	message.setDetails(msg.getDetails());
		if (msg.getDomain() != null && msg.getDomain().getId() != null && msg.getDomain().getType() != null) {
			message.setDomain(msg.getDomain());
		}
    	message.setId(msg.getId());
    	try {
    		message.setNode(InetAddress.getByName(msg.getNode()));
		} catch (UnknownHostException e) {
			log.error(e.getMessage());
		}
    	message.setOperation(msg.getOperation());
    	message.setTimestamp(msg.getTimestamp());
    	message.setUser(loadUser(msg.getUserId()));
    	return message;
    }

    private UserClerk loadUser(long userId) {
    	if(userId < 0){
    		return null;
    	}
    	UserManager userManager = Application.getContext().getUserManager();
    	ClerkService clerkService = Application.getContext().get("clerkService", ClerkService.class);
    	User user = null;// (User) userManager.loadUserByUserId(userId);
    	try {
			user = (User) userManager.loadUserByUserId(userId);
		} catch (Exception e) {
			//ignore e
		}
    	if(user != null){
	    	Clerk clerk = clerkService.getClerk(userId);
	    	return new UserClerk(user, clerk);
    	}
    	
   		return null;
	}

	/**
	 * 获取所有日志。
	 */
	public Iterable<AuditMessage> getAuditMessages() {
		List<AuditLog> list = auditLogDao.find(new ResultFilter(null, Order.desc("timestamp")));
		return beanListToMessageList(list);
    }
	/**
	 * 获取指定用户的操作日志。
	 * @param userId
	 * @return
	 */
	@Queryable(argNames="userId", name="findAuditMessagesByUser")
	public List<AuditMessage> findAuditMessagesByUser(long userId){
		return findAuditMessages(new ResultFilter(Restrictions.eq("userId", userId), Order.desc("timestamp")));
	}
	
	public List<AuditMessage> findAuditMessagesByDomain(Domain<?> domain){
		Assert.notNull(domain, "域对象不能为空");
		Assert.notNull(domain.getId(), "对象ID不能为空");
		Logic c = Restrictions.logic(Restrictions.eq("domain.id", domain.getId().toString()))
			.and(Restrictions.eq("domain.type", domain.getClass().getName()));
		return findAuditMessages(new ResultFilter(c, Order.desc("timestamp")));
	}
	
	@Queryable
	public List<AuditMessage> findAuditMessages(ResultFilter filter) {
		return beanListToMessageList(auditLogDao.find(filter));
	}
	
	protected List<AuditMessage> beanListToMessageList(List<AuditLog> list){
		if(list == null){
			return Collections.emptyList();
		}
		List<AuditMessage> result = new ArrayList<AuditMessage>(list.size());
		for(AuditLog bean: list){
			result.add(beanToMessage(bean));
		}
		return result;
	}
	

    public boolean isAsyncMode() {
        return asyncMode;
    }

    public void setAsyncMode(boolean asyncMode) {
        this.asyncMode = asyncMode;
    }

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public AuditLogDao getAuditLogDao() {
		return auditLogDao;
	}

	public void setAuditLogDao(AuditLogDao auditLogDao) {
		this.auditLogDao = auditLogDao;
	}

	public List<AuditMessage> findAuditMessagesByDomain(DomainIdentifier domain) {
		Assert.notNull(domain, "域对象不能为空");
		Assert.notNull(domain.getId(), "对象ID不能为空");
		Logic c = Restrictions.logic(Restrictions.eq("domain.id", domain.getId()))
			.and(Restrictions.eq("domain.type", domain.getType()));
		return findAuditMessages(new ResultFilter(c, Order.desc("timestamp")));
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.audit.AuditManager#findPageableAuditMessages(org.opoo.ndao.support.ResultFilter)
	 */
	public PageableList<AuditMessage> findPageableAuditMessages(ResultFilter filter) {
		PageableList<AuditLog> list = auditLogDao.findPageableList(filter);
		return new PageableList<AuditMessage>(beanListToMessageList(list), list.getStartIndex(), list.getPageSize(), list.getItemCount());
	}
}
