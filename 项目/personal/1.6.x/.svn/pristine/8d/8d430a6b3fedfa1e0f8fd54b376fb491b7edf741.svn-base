package org.opoo.apps.security;

import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.test.SpringTests;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

public class UserTest extends SpringTests {

	public void testLoadUser(){
		UserManager um = Application.getContext().getUserManager();
		System.out.println(um);
		
		UserDetails user = um.loadUserByUsername("rfsa");
		System.out.println(user);
		
		User u = null;
		GrantedAuthority[] authorities = u.getAuthorities();
		//"ROLE_MANAGER";
	}
}
