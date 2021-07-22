package cn.redflagsoft.base.audit;

import java.net.InetAddress;
import java.util.List;

import org.opoo.ndao.Domain;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.security.UserClerk;



/**
 * AuditManager is a mechanism for logging calls to targeted parts of the api
 * for the purpose of tracking user (particular moderator and administrator)
 * behavior.
 *
 * A given class is associated with an {@link Auditor} instance which contains
 * the logic for formulating an {@link AuditMessage} describing relevant data
 * for a given method call. These messages can be retrieved through calls to
 * the {@link #getAuditMessages} methods of this interface.
 *
 * No api exists for manipulating or deleting AuditMessages once they are created
 * and stored.
 */
public interface AuditManager {

    /**
     * Audits a particular method call on an object.
     * 
     * @param call the MethodCall instance capturing details of the call.
     * @param auditor the Auditor to use for the call.
     * @throws NotAuditableException if no auditor is registered which can handle
     * the supplied method call.
     */
    void auditMethodCall(MethodCall call, Auditor auditor) throws NotAuditableException;

    /**
     * Audits a particular method call with individual data elements passed in.
     *
     * @param user The user calling the method.
     * @param address The host that the call was executed on.
     * @param details The details of the called method.
     * @param description The description of the called method.
     * @throws NotAuditableException if no auditor is registered which can handle
     * the supplied call data.
     */
    AuditMessage audit(UserClerk user, InetAddress address, String details, String description, 
    		DomainIdentifier domain, String operation) throws NotAuditableException;

    
    /**
     * Returns all audit messages in the system.
     *
     * @return all audit messages in the system.
     */
    Iterable<AuditMessage> getAuditMessages();


    /**
     * 查找指定用户的全部审核日志。
     * 
     * @param userId
     * @return
     */
    List<AuditMessage> findAuditMessagesByUser(long userId);
    
    /**
     * 查询指定对象的全部审核日志。
     * 
     * @param domain
     * @return
     */
    List<AuditMessage> findAuditMessagesByDomain(Domain<?> domain);
    
    /**
     * 查询指定对象的全部审核日志。
     * @param domain
     * @return
     */
    List<AuditMessage> findAuditMessagesByDomain(DomainIdentifier domain);
    
    /**
     * 根据条件查询审核日志。
     * 
     * @param filter
     * @return
     */
    List<AuditMessage> findAuditMessages(ResultFilter filter);
    
    
    /**
     * 根据条件查询审核日志。
     * 
     * @param filter
     * @return
     */
    PageableList<AuditMessage> findPageableAuditMessages(ResultFilter filter);
}
