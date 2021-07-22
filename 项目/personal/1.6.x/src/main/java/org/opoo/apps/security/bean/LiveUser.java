package org.opoo.apps.security.bean;

import java.util.Date;
import java.util.Map;

import org.opoo.apps.security.MutableUser;
import org.opoo.apps.security.User;
import org.opoo.ndao.Domain;
import org.opoo.ndao.domain.Versionable;
import org.springframework.security.GrantedAuthority;


/**
 * 用户对象。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class LiveUser implements Versionable, MutableUser, User, Domain<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2323689585640668185L;
	//也是用户ID
	private String username;
	private String password;
	private boolean enabled;
	
	
	private Date accountExpireTime;
	//private boolean accountNonExpired = true;
	private boolean accountNonLocked;
	private Date credentialsExpireTime;
	//private boolean credentialsNonExpired = true;
	
	//最后登录时间
	private Date lastLoginTime;
	//本次登录时间
	private Date loginTime;
	
	//登录次数
	private int loginCount = 0;
	//尝试登录次数。
	private int tryLoginCount = 0;
	//职员ID
	private Long userId;
	//对应外部用户ID
	private String externalUserId;
	
	private String loginAddress;
	private OnlineStatus onlineStatus = OnlineStatus.OFFLINE;
	
	private Date creationTime;
	private String creator;
	
	private Date modificationTime;
	private String modifier;

	/**
	 * 用户属性，不包含用户所属组的属性。
	 */
	private Map<String, String> properties;
	/**
	 * 用户权限，不包含前缀和用户所属组的权限。
	 */
	private GrantedAuthority[] authorities;
	
	/**
	 * 根据指定的属性构建用户对象。
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param enabled 是否有效
	 * @param accountExpireTime 账户过期时间
	 * @param accountNonLocked 账户是否未锁定
	 * @param credentialsExpireTime 凭证过期时间
	 * @param lastLoginTime 最后登录时间
	 * @param loginTime 本次登录时间
	 * @param loginCount 累计登录次数
	 * @param tryLoginCount 尝试登录（失败）次数
	 * @param userId 用户ID
	 * @param loginAddress 登录地址，客户端IP
	 * @param onlineStatus 在线状态
	 * @param creationTime 创建时间
	 * @param creator 创建者ID
	 * @param modificationTime 更新时间
	 * @param modifier 更新者ID
	 * @param properties 属性集合
	 * @param authorities 权限集合
	 */
	public LiveUser(String username, String password, boolean enabled,
			Date accountExpireTime, boolean accountNonLocked,
			Date credentialsExpireTime, Date lastLoginTime, Date loginTime, int loginCount,
			int tryLoginCount, Long userId, String loginAddress,
			OnlineStatus onlineStatus, Date creationTime, String creator,
			Date modificationTime, String modifier,
			Map<String, String> properties, GrantedAuthority[] authorities) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountExpireTime = accountExpireTime;
		this.accountNonLocked = accountNonLocked;
		this.credentialsExpireTime = credentialsExpireTime;
		this.lastLoginTime = lastLoginTime;
		this.loginTime = loginTime;
		this.loginCount = loginCount;
		this.tryLoginCount = tryLoginCount;
		this.userId = userId;
		this.loginAddress = loginAddress;
		this.onlineStatus = onlineStatus;
		this.creationTime = creationTime;
		this.creator = creator;
		this.modificationTime = modificationTime;
		this.modifier = modifier;
		this.properties = properties;
		this.authorities = authorities;
	}
	/**
	 * 构建用户对象。并指定用户名。
	 * @param username 用户名
	 */
	public LiveUser(String username) {
		super();
		this.username = username;
	}
	/**
	 * 构建用户对象。
	 */
	public LiveUser() {
		super();
	}
	/**
	 * 根据用户对象构建新的用户对象。
	 * @param user 用户对象
	 */
	public LiveUser(User user){
		super();
//		this.username = user.getUsername();
//		this.password = user.getPassword();
//		this.enabled = user.isEnabled();
//		this.accountExpireTime = user.getAccountExpireTime();
//		this.accountNonLocked = user.isAccountNonLocked();
//		this.credentialsExpireTime = user.getCredentialsExpireTime();
//		this.lastLoginTime = user.getLastLoginTime();
//		this.loginCount = user.getLoginCount();
//		this.tryLoginCount = user.getTryLoginCount();
//		this.userId = user.getUserId();
//		this.loginAddress = user.getLoginAddress();
//		this.onlineStatus = user.getOnlineStatus();
//		this.creationTime = user.getCreationTime();
//		this.creator = user.getCreator();
//		this.modificationTime = user.getModificationTime();
//		this.modifier = user.getModifier();
//		this.properties = user.getProperties();
//		this.authorities = user.getAuthorities();
		
		this.copyFrom(user);
	}
	/**
	 * 从指定用户对象复制属性到当前用户对象。
	 * @param user 指定用户对象
	 */
	public void copyFrom(User user){
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.enabled = user.isEnabled();
		this.accountExpireTime = user.getAccountExpireTime();
		this.accountNonLocked = user.isAccountNonLocked();
		this.credentialsExpireTime = user.getCredentialsExpireTime();
		this.lastLoginTime = user.getLastLoginTime();
		this.loginTime = user.getLoginTime();
		this.loginCount = user.getLoginCount();
		this.tryLoginCount = user.getTryLoginCount();
		this.userId = user.getUserId();
		this.loginAddress = user.getLoginAddress();
		this.onlineStatus = user.getOnlineStatus();
		this.creationTime = user.getCreationTime();
		this.creator = user.getCreator();
		this.modificationTime = user.getModificationTime();
		this.modifier = user.getModifier();
		this.properties = user.getProperties();
		this.authorities = user.getAuthorities();
	}
	
	public String getExternalUserId() {
		return externalUserId;
	}
	public void setExternalUserId(String externalUserId) {
		this.externalUserId = externalUserId;
	}
	public String getId() {
		return username;
	}
	public void setId(String k) {
		this.username = k;		
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the accountExpireTime
	 */
	public Date getAccountExpireTime() {
		return accountExpireTime;
	}
	/**
	 * @param accountExpireTime the accountExpireTime to set
	 */
	public void setAccountExpireTime(Date accountExpireTime) {
		this.accountExpireTime = accountExpireTime;
	}
//	/**
//	 * @return the accountNonExpired
//	 */
//	public boolean isAccountNonExpired() {
//		return accountNonExpired;
//	}
//	/**
//	 * @param accountNonExpired the accountNonExpired to set
//	 */
//	public void setAccountNonExpired(boolean accountNonExpired) {
//		this.accountNonExpired = accountNonExpired;
//	}
	/**
	 * @return the accountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	/**
	 * @return the credentialsExpireTime
	 */
	public Date getCredentialsExpireTime() {
		return credentialsExpireTime;
	}
	/**
	 * @param credentialsExpireTime the credentialsExpireTime to set
	 */
	public void setCredentialsExpireTime(Date credentialsExpireTime) {
		this.credentialsExpireTime = credentialsExpireTime;
	}
//	/**
//	 * @return the credentialsNonExpired
//	 */
//	public boolean isCredentialsNonExpired() {
//		return credentialsNonExpired;
//	}
//	/**
//	 * @param credentialsNonExpired the credentialsNonExpired to set
//	 */
//	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
//		this.credentialsNonExpired = credentialsNonExpired;
//	}
	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	/**
	 * @return the loginCount
	 */
	public int getLoginCount() {
		return loginCount;
	}
	/**
	 * @param loginCount the loginCount to set
	 */
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	/**
	 * @return the tryLoginCount
	 */
	public int getTryLoginCount() {
		return tryLoginCount;
	}
	/**
	 * @param tryLoginCount the tryLoginCount to set
	 */
	public void setTryLoginCount(int tryLoginCount) {
		this.tryLoginCount = tryLoginCount;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the loginAddress
	 */
	public String getLoginAddress() {
		return loginAddress;
	}
	/**
	 * @param loginAddress the loginAddress to set
	 */
	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * @return the modificationTime
	 */
	public Date getModificationTime() {
		return modificationTime;
	}
	/**
	 * @param modificationTime the modificationTime to set
	 */
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
	/**
	 * @return the modifier
	 */
	public String getModifier() {
		return modifier;
	}
	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public boolean isAccountNonExpired() {
		return (accountExpireTime == null || accountExpireTime.getTime() >= System.currentTimeMillis());
	}

	
	public boolean isCredentialsNonExpired() {
		return (credentialsExpireTime == null || credentialsExpireTime.getTime() >= System.currentTimeMillis());
	}
	
	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}
	/**
	 * @param onlineStatus the onlineStatus to set
	 */
	public void setOnlineStatus(OnlineStatus onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	/**
	 * @return the onlineStatus
	 */
	public OnlineStatus getOnlineStatus() {
		return onlineStatus;
	}
	
	public Date getLoginTime() {
		return loginTime;
	}
	
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
}
