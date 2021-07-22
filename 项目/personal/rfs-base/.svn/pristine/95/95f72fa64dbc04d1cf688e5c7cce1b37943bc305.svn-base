package cn.redflagsoft.base.audit;




/**
 * 用户操作日志，操作记录。
 * 
 * @author Alex Lin(alex@opoo.org)
 */
public interface OperationMessage extends AuditMessage {
	
	/**
	 * 具体的操作。例如“删除”或者“delete”。
	 * @return
	 */
	String getOperation();
	
	/**
	 * 被审核方法涉及的对象。
	 * 
	 * @return
	 */
	DomainIdentifier getDoamin();
}
