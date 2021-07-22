package org.opoo.apps.test;

import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.UserManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;

public class UserManager2Test extends SpringTests {

	public void test00(){
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("rfsa", "123"));
		
		UserManager manager = Application.getContext().getUserManager();
		System.out.println(manager);
		
		UserDetails userDetails = manager.loadUserByUsername("rfsa");
		assertNotNull(userDetails);
	}
}
