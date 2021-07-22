package cn.redflagsoft.base.security;

import org.springframework.security.annotation.Secured;

public class SampleSecuredService {

	@Secured("ROLE_USER")
	public void doUserMethod(){
		System.out.println(this + " --------- doUserMethod: ROLE_USER");
	}
	
	@Secured("ROLE_ADMIN")
	public void doAdminMethod(){
		System.out.println(this + " --------- doAdminMethod: ROLE_ADMIN");
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	public void doMethod(){
		System.out.println(this + " --------- doMethod: ROLE_ADMIN, ROLE_USER");
	}
	
	public void doNonSecuredMethod(){
		System.out.println(this + " --------- doNonSecuredMethod");
	}
}
