package org.opoo.apps.security;


public enum AppsGrantedAuthority{// implements GrantedAuthority, Comparable<AppsGrantedAuthority> {
	USER(1L), 
	ADMIN(0x8000000000000000L), 
	SYS(0x400000000000000L);

	private long permission;
	AppsGrantedAuthority(long permission){
		this.permission = permission;
	}
	
	public String getAuthority() {
		return name();
	}

	public long getPermission() {
		return permission;
	}
}
