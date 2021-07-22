package org.opoo.apps.security;

import java.util.Date;
import java.util.Map;

import org.springframework.security.userdetails.UserDetails;



/**
 * 用户接口。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface User extends UserDetails {
	
	/**
	 * 获取帐号过期时间。
	 * 为空则永不过期。
	 * @return <code>null</code> if never expire
	 */
	Date getAccountExpireTime();
	/**
	 * 获取密码过期时间。为空则永不过期。
	 * @return <code>null</code> if never expire
	 */
	Date getCredentialsExpireTime();
	/**
	 * 用户上次登录时间。
	 * @return <code>null</code> if never login
	 */
	Date getLastLoginTime();
	
	/**
	 * 外部系统用户id
	 * @return
	 */
	String getExternalUserId();
	/**
	 * 当前登录时间。
	 * 
	 * @return
	 * @since 1.5
	 */
	Date getLoginTime();
	
	/**
	 * 最后登录使用的地址，例如web程序记录IP地址，c/s记录客户端id等等。
	 * @return
	 */
	String getLoginAddress();
	
	/**
	 * 获取用户最后一次登录时尝试次数，登录成功则清零。
	 * 可根据这个数据来对用户进行登录控制，比如失败次数超过3次
	 * 需要输入验证码，超过10次锁定帐户等等。
	 * @return <code>0</code> if login success
	 */
	int getTryLoginCount();
	
	/**
	 * 用户总登录次数。
	 * 
	 * @return
	 */
	int getLoginCount();
	/**
	 * 用户创建时间。
	 * @return
	 */
	Date getCreationTime();
	/**
	 * 用户修改时间。
	 * @return 
	 */
	Date getModificationTime();
	
	/**
	 * 创建人用户名。
	 * @return
	 */
	String getCreator();
	/**
	 * 修改人用户名。
	 * @return
	 */
	String getModifier();
	
	/**
	 * 
	 * @return
	 */
	Long getUserId();
	
	
	
//	/**
//	 * 用户状态
//	 * @return
//	 */
//	Status getStatus();
	
	/**
	 * 在线状态
	 * @return
	 * 
	 */
	OnlineStatus getOnlineStatus();
	
	/**
	 * 用户属性。
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
