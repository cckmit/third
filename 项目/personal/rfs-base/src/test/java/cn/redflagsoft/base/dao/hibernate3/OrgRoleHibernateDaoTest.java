package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.dao.OrgRoleDao;
import cn.redflagsoft.base.test.BaseTests;


public class OrgRoleHibernateDaoTest extends  BaseTests {
	protected OrgRoleDao orgRoleDao;
	
	public void testFindOrgsByRoleId() throws Exception{	
		List<Org> lo=orgRoleDao.findOrgsByRoleId(0L);
	}
	
	
}
