package org.opoo.apps.security;

import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;

public abstract class SecurityUtils {
	
	/**
	 * 
	 * @param user
	 * @param authority
	 * @return
	 */
	public static boolean isGranted(User user, String authority){
		return isGranted(user.getAuthorities(), authority);
	}
	
	/**
	 * 
	 * @param auth
	 * @param authority
	 * @return
	 */
	public static boolean isGranted(Authentication auth, String authority){
		return isGranted(auth.getAuthorities(), authority);
	}
	
	private static boolean isGranted(GrantedAuthority[] authorities, String authority){
		for(GrantedAuthority ga:authorities){
			if(ga.getAuthority().equals(authority)){
				return true;
			}
		}
		return false;
	}
}
