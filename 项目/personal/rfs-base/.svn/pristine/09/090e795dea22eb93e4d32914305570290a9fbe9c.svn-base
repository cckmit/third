package cn.redflagsoft.base.service;

import java.util.List;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.test.BaseTests;
import cn.redflagsoft.base.vo.UserClerkVO;


/**
 * ClerkServiceµƒ≤‚ ‘¿‡°£
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ClerkServiceTest extends BaseTests {
	protected ClerkService clerkService;
	
	public void testService(){
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("rfsa", "123", 
						new GrantedAuthority[]{new GrantedAuthorityImpl("ROLE_ADMIN")}));
		System.out.println(clerkService);
		
		List<Clerk> list2 = clerkService.findClerks(null);
		System.out.println(list2);
		
		List<UserClerkVO> list = clerkService.findUserClerkVOs(null);
		System.out.println(list);
	}
}
