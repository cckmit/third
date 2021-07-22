package org.opoo.apps.security;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.springframework.security.GrantedAuthority;

/**
 * 登录用户。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SecurityUser extends org.springframework.security.userdetails.User implements MutableUser, User, Principal {
//	private static final Log log = LogFactory.getLog(SecurityUser.class);

	private static final long serialVersionUID = 1089022947596407741L;
	
	//private UserTemplate userTemplate;
	
//	private GrantedAuthority[] authorities;
//	private String password;
//	//也是用户ID
//	private String username;
//	private boolean accountNonExpired = true;
//	private boolean accountNonLocked;
//	private boolean credentialsNonExpired = true;
	
//	private boolean enabled;
	
	
	//最后登录时间
	private Date lastLoginTime;
	private Date loginTime;
	
	private String loginAddress;
	
	//登录次数
	private int loginCount = 0;
	//尝试登录次数。
	private int tryLoginCount = 0;
	//职员ID
	private Long userId;
	
	private Date modificationTime;
	private Date credentialsExpireTime;
	private Date accountExpireTime;
	private Date creationTime;
	private String creator;
	private String modifier;
	
	@SuppressWarnings("unused")
	private OnlineStatus onlineStatus = OnlineStatus.OFFLINE;
	
	private Map<String, String> properties;

	private String name;
	private String externalUserId;
	
	/**
	 * 用户。
	 * 
	 * 
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param enabled 有效？
	 * @param accountExpireTime 帐号过期时间，null永不过期
	 * @param credentialsExpireTime 密码过期时间，null永不过期
	 * @param accountNonLocked 帐号被锁？
	 * @param userId 用户id，用于关联应用系统中与用户相关的相信，例如用户详情
	 * @param authorities 权限
	 * @throws IllegalArgumentException 如果用户名或者密码的字符串无效，则抛出异常
	 */
	public SecurityUser(String username, String password, boolean enabled,
			Date accountExpireTime, Date credentialsExpireTime,
			boolean accountNonLocked, Long userId,
			GrantedAuthority[] authorities,	Map<String, String> properties)
			throws IllegalArgumentException {
		
		super(username, password, enabled, 
				accountExpireTime == null || accountExpireTime.getTime() >= System.currentTimeMillis(),
				credentialsExpireTime == null || credentialsExpireTime.getTime() >= System.currentTimeMillis(),
				accountNonLocked, authorities);
		if(userId == null){
			throw new IllegalArgumentException("userId must be set.");
		}

		this.accountExpireTime = (accountExpireTime);
		this.credentialsExpireTime = (credentialsExpireTime);
		//this.accountNonExpired = accountNonExpired;
		//this.credentialsNonExpired = credentialsNonExpired;
		this.userId = userId;
		this.properties = properties;
		
	}
	
	public SecurityUser(String username, String password, boolean enabled,
			Date accountExpireTime, Date credentialsExpireTime,
			boolean accountNonLocked, Long userId,
			GrantedAuthority[] authorities,	Map<String, String> properties,String externalUserId)
			throws IllegalArgumentException {
		
		super(username, password, enabled, 
				accountExpireTime == null || accountExpireTime.getTime() >= System.currentTimeMillis(),
				credentialsExpireTime == null || credentialsExpireTime.getTime() >= System.currentTimeMillis(),
				accountNonLocked, authorities);
		if(userId == null){
			throw new IllegalArgumentException("userId must be set.");
		}

		this.accountExpireTime = (accountExpireTime);
		this.credentialsExpireTime = (credentialsExpireTime);
		//this.accountNonExpired = accountNonExpired;
		//this.credentialsNonExpired = credentialsNonExpired;
		this.userId = userId;
		this.properties = properties;
		this.externalUserId = externalUserId;
	}
	
	SecurityUser(User user, GrantedAuthority[] combinedAuthorities, Map<String, String> combinedProperties){
		this(user.getUsername(), user.getPassword(), user.isEnabled(),
				user.getAccountExpireTime(), user.getCredentialsExpireTime(),
				user.isAccountNonLocked(), user.getUserId(), 
				combinedAuthorities, combinedProperties);
		this.creationTime = user.getCreationTime();
		this.creator = user.getCreator();
		this.onlineStatus = user.getOnlineStatus();
		this.lastLoginTime = user.getLastLoginTime();
		this.loginTime = user.getLoginTime();
		this.loginAddress = user.getLoginAddress();
		this.loginCount = user.getLoginCount();
		this.modificationTime = user.getModificationTime();
		this.modifier = user.getModifier();
		this.name = user.getUsername();
		this.tryLoginCount = user.getTryLoginCount();
		this.externalUserId = user.getExternalUserId();
	}
	
	
	
	// ~ Methods
	// ========================================================================================================

	public boolean equals(Object rhs) {
		
		if (!(rhs instanceof SecurityUser) || (rhs == null)) {
			return false;
		}
		//
		boolean equals = super.equals(rhs);
		
		if(!equals){
			return false;
		}
		
		SecurityUser user = (SecurityUser) rhs;

		// 判断扩展属性。
		return ( (this.getOnlineStatus() == user.getOnlineStatus())
				&& this.getUsername().equals(user.getUsername())
				&& (this.getUserId().longValue() == user.getUserId().longValue())
		);
	}

	public int hashCode() {
		int code = super.hashCode();
        return code;
	}


	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append("UserId: ").append(userId).append("; ");
		sb.append("AccountExpireTime: ").append(this.accountExpireTime).append("; ");
		sb.append("CredentialsExpireTime: ").append(this.credentialsExpireTime).append("; ");
		return sb.toString();
	}

	public boolean isAccountNonExpired() {
		return (accountExpireTime == null || accountExpireTime.getTime() >= System.currentTimeMillis());
	}

	
	public boolean isCredentialsNonExpired() {
		return (credentialsExpireTime == null || credentialsExpireTime.getTime() >= System.currentTimeMillis());
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Date getAccountExpireTime() {
		return accountExpireTime;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public String getCreator() {
		return creator;
	}

	public Date getCredentialsExpireTime() {
		return credentialsExpireTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public String getLoginAddress() {
		return loginAddress;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public String getModifier() {
		return modifier;
	}

	public OnlineStatus getOnlineStatus() {
		//log.debug(this.onlineStatus);
		List<Presence> presences = Application.getContext().getPresenceManager().getPresences(this);
		Presence presence = presences != null && !presences.isEmpty() ? presences.get(0) : null;//Application.getContext().getPresenceManager().getPresence(this);
		if(presence != null){
//			if(Presence.STATUS_ONLINE == presence.getStatus()){
//				Date time = presence.getLastUpdateTime();
//				int timeToIdleSeconds = AppsGlobals.getProperty("Presence.timeToIdleSeconds", 5 * 60);
//				if(time != null){
//					if(System.currentTimeMillis() - time.getTime() > timeToIdleSeconds * 1000){
//						log.info("将空闲时间超过 " + timeToIdleSeconds + " 秒的用户 '"
//								+ getUsername() + "' 状态设置为空闲。");
//						presence.setStatus(Presence.STATUS_IDLE);
//					}
//				}
//			}
			
			switch(presence.getStatus()){
				case Presence.STATUS_ONLINE:
					return OnlineStatus.ONLINE;
				case Presence.STATUS_AWAY: 
					return OnlineStatus.OFFLINE;
				case Presence.STATUS_IDLE:
					return OnlineStatus.IDLE;
				case Presence.STATUS_INVISIBLE:
					return OnlineStatus.INVISIBLE;
				default:
					return OnlineStatus.OFFLINE;
			}
		}
		return OnlineStatus.OFFLINE;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public int getTryLoginCount() {
		return tryLoginCount;
	}

	public Long getUserId() {
		return userId;
	}
	
	
	

	public boolean isOnline(){
		//OnlineStatus os = getOnlineStatus();
		//return os != null && (os == OnlineStatus.ONLINE || os == OnlineStatus.IDLE);
		//return OnlineStatus.isOnline(os);
		
		return Application.getContext().getPresenceManager().isOnline(this);
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public void setOnlineStatus(OnlineStatus onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public void setTryLoginCount(int tryLoginCount) {
		this.tryLoginCount = tryLoginCount;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getExternalUserId() {
		return externalUserId;
	}

	public void setExternalUserId(String externalUserId) {
		this.externalUserId = externalUserId;
	}


//	public Date getLoginTime() {
////		Presence presence = Application.getContext().getPresenceManager().getPresence(this);
////		if(presence != null){
////			return presence.getLoginTime();
////		}
//		
//		List<Presence> presences = Application.getContext().getPresenceManager().getPresences(this);
//		if(presences != null && !presences.isEmpty()){
//			return presences.get(0).getLoginTime();
//		}
//		return null;
//	}
	
}
