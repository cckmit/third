package cn.redflagsoft.base.scheme.schemes.menu.v2;

import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.test.BaseTests;


public class RoleMenuSchemeTest extends BaseTests {
	protected SchemeManager schemeManager;
	protected RoleMenuScheme roleMenuScheme;
	
	
	
	public void testSave() throws Exception{
		System.out.println(roleMenuScheme.getClass());
		Scheme scheme = schemeManager.getScheme("roleMenuScheme");
		System.out.println(scheme.getClass());
		
		roleMenuScheme.setRoleId(0L);
		roleMenuScheme.setFile("d:\\nodes2.xml");
		roleMenuScheme.doSave();
		//super.setComplete();
	}
}
