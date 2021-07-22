package org.opoo.apps.security;

import java.util.Date;
import java.util.Map;

import org.springframework.security.GrantedAuthority;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class UserWrapper implements User {
	private static final long serialVersionUID = -5221148583964860358L;

	private final User user;
	public UserWrapper(User user){
		this.user = user;
	}

	public Date getAccountExpireTime() {
		return user.getAccountExpireTime();
	}

	public Date getCreationTime() {
		return user.getCreationTime();
	}

	public String getCreator() {
		return user.getCreator();
	}

	public Date getCredentialsExpireTime() {
		return user.getCredentialsExpireTime();
	}

	public Date getLastLoginTime() {
		return user.getLastLoginTime();
	}

	public String getLoginAddress() {
		return user.getLoginAddress();
	}

	public int getLoginCount() {
		return user.getLoginCount();
	}

	public Date getModificationTime() {
		return user.getModificationTime();
	}

	public String getModifier() {
		return user.getModifier();
	}

	public OnlineStatus getOnlineStatus() {
		return user.getOnlineStatus();
	}

	public int getTryLoginCount() {
		return user.getTryLoginCount();
	}


	public GrantedAuthority[] getAuthorities() {
		return user.getAuthorities();
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getUsername() {
		return user.getUsername();
	}

	public boolean isAccountNonExpired() {
		return user.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
		return user.isCredentialsNonExpired();
	}

	public boolean isEnabled() {
		return user.isEnabled();
	}

	public Map<String, String> getProperties() {
		return user.getProperties();
	}

	public Long getUserId() {
		return user.getUserId();
	}

	public Date getLoginTime() {
		return user.getLoginTime();
	}

	public String getExternalUserId() {
		return user.getExternalUserId();
	}

}
