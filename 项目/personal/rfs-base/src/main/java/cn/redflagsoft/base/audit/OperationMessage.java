package cn.redflagsoft.base.audit;




/**
 * �û�������־��������¼��
 * 
 * @author Alex Lin(alex@opoo.org)
 */
public interface OperationMessage extends AuditMessage {
	
	/**
	 * ����Ĳ��������硰ɾ�������ߡ�delete����
	 * @return
	 */
	String getOperation();
	
	/**
	 * ����˷����漰�Ķ���
	 * 
	 * @return
	 */
	DomainIdentifier getDoamin();
}
