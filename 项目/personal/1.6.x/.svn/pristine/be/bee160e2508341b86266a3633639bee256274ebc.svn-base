package org.opoo.apps.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

public abstract class AuthorityUtils {

	/**
	 * 
	 * @param authorities
	 * @param rolePrefix
	 * @return
	 */
	public static List<GrantedAuthority> dropRolePrefix(List<GrantedAuthority> authorities, String rolePrefix){
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		for(GrantedAuthority a: authorities){
			GrantedAuthority b = AuthorityUtils.dropRolePrefix(a, rolePrefix);
			list.add(b);
		}
		return list;
	}
	
	/**
	 * 
	 * @param authorities
	 * @param rolePrefix
	 * @return
	 */
	public static GrantedAuthority[] dropRolePrefix(GrantedAuthority[] authorities, String rolePrefix){
		GrantedAuthority[] result = new GrantedAuthority[authorities.length];
		for(int i = 0 ; i < authorities.length ; i++){
			GrantedAuthority a = authorities[i];
			GrantedAuthority b = AuthorityUtils.dropRolePrefix(a, rolePrefix);
			result[i] = b;
		}
		return result;
	}
	
	/**
	 * 
	 * @param authority
	 * @param rolePrefix
	 * @return
	 */
	public static GrantedAuthority dropRolePrefix(GrantedAuthority authority, String rolePrefix){
		String role = authority.getAuthority();
		if(role.startsWith(rolePrefix)){
			int prefixLength = rolePrefix.length();
			role = role.substring(prefixLength);
			return new GrantedAuthorityImpl(role);
		}
		return authority;
	}
	
	
	/**
	 * 
	 * @param authorities
	 * @param rolePrefix
	 * @return
	 */
	public static List<GrantedAuthority> addRolePrefix(List<GrantedAuthority> authorities, String rolePrefix){
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		for(GrantedAuthority a : authorities){
			GrantedAuthority b = new GrantedAuthorityImpl(rolePrefix + a.getAuthority());
			list.add(b);
		}
		return list;
	}
	
	/**
	 * 
	 * @param authorities
	 * @param rolePrefix
	 * @return
	 */
	public static GrantedAuthority[] addRolePrefix(GrantedAuthority[] authorities, String rolePrefix){
		GrantedAuthority[] result = new GrantedAuthority[authorities.length];
		for(int i = 0 ; i < authorities.length ; i++){
			GrantedAuthority a = authorities[i];
			GrantedAuthority b = new GrantedAuthorityImpl(rolePrefix + a.getAuthority());
			result[i] = b;
		}
		return result;
	}
	
	/**
	 * 
	 * @param authorities
	 * @return
	 */
	public static GrantedAuthority[] authoritiesToArray(Collection<GrantedAuthority> authorities){
		int size = authorities.size();
		return authorities.toArray(new GrantedAuthority[size]);
	}
}
