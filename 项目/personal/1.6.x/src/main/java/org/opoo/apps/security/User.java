package org.opoo.apps.security;

import java.util.Date;
import java.util.Map;

import org.springframework.security.userdetails.UserDetails;



/**
 * �û��ӿڡ�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface User extends UserDetails {
	
	/**
	 * ��ȡ�ʺŹ���ʱ�䡣
	 * Ϊ�����������ڡ�
	 * @return <code>null</code> if never expire
	 */
	Date getAccountExpireTime();
	/**
	 * ��ȡ�������ʱ�䡣Ϊ�����������ڡ�
	 * @return <code>null</code> if never expire
	 */
	Date getCredentialsExpireTime();
	/**
	 * �û��ϴε�¼ʱ�䡣
	 * @return <code>null</code> if never login
	 */
	Date getLastLoginTime();
	
	/**
	 * �ⲿϵͳ�û�id
	 * @return
	 */
	String getExternalUserId();
	/**
	 * ��ǰ��¼ʱ�䡣
	 * 
	 * @return
	 * @since 1.5
	 */
	Date getLoginTime();
	
	/**
	 * ����¼ʹ�õĵ�ַ������web�����¼IP��ַ��c/s��¼�ͻ���id�ȵȡ�
	 * @return
	 */
	String getLoginAddress();
	
	/**
	 * ��ȡ�û����һ�ε�¼ʱ���Դ�������¼�ɹ������㡣
	 * �ɸ���������������û����е�¼���ƣ�����ʧ�ܴ�������3��
	 * ��Ҫ������֤�룬����10�������ʻ��ȵȡ�
	 * @return <code>0</code> if login success
	 */
	int getTryLoginCount();
	
	/**
	 * �û��ܵ�¼������
	 * 
	 * @return
	 */
	int getLoginCount();
	/**
	 * �û�����ʱ�䡣
	 * @return
	 */
	Date getCreationTime();
	/**
	 * �û��޸�ʱ�䡣
	 * @return 
	 */
	Date getModificationTime();
	
	/**
	 * �������û�����
	 * @return
	 */
	String getCreator();
	/**
	 * �޸����û�����
	 * @return
	 */
	String getModifier();
	
	/**
	 * 
	 * @return
	 */
	Long getUserId();
	
	
	
//	/**
//	 * �û�״̬
//	 * @return
//	 */
//	Status getStatus();
	
	/**
	 * ����״̬
	 * @return
	 * 
	 */
	OnlineStatus getOnlineStatus();
	
	/**
	 * �û����ԡ�
	 * @return
	 */
	Map<String, String> getProperties();
	
	
//	public static enum Status{
//		none,awaiting_moderation,approved,rejected,awaiting_validation,validated,registered;
//		public static Status valueOf(int ordinal){
//			for(Status status: Status.values()){
//				if(status.ordinal() == ordinal){
//					return status;
//				}
//			}
//			throw new IllegalArgumentException("Specified ordinal does not relate to a valid status");
//		}
//	}
	
	public static enum OnlineStatus{
		OFFLINE, ONLINE, IDLE, INVISIBLE;
		
		public static OnlineStatus valueOf(int ordinal){
			for(OnlineStatus status: OnlineStatus.values()){
				if(status.ordinal() == ordinal){
					return status;
				}
			}
			throw new IllegalArgumentException("Specified ordinal does not relate to a valid online status");
		}
		
		/**
		 * 
		 * @param status
		 * @return
		 */
		public static boolean isOnline(OnlineStatus status){
			return ONLINE.equals(status) || IDLE.equals(status);
		}
	}
}
