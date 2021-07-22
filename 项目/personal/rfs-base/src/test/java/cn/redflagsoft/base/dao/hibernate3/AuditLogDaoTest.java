package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.aop.annotation.Operation;
import cn.redflagsoft.base.aop.aspect.AspectJ;
import cn.redflagsoft.base.audit.AuditMessage;
import cn.redflagsoft.base.bean.AuditLog;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.test.BaseTests;

public class AuditLogDaoTest extends BaseTests {

	protected AuditLogHibernateDao auditLogDao;
	protected cn.redflagsoft.base.audit.AuditManager auditManager;
	
	
	public void testDaoPresent(){
		assertNotNull(auditLogDao);
	}
	
	public void testDaoFind() throws InterruptedException{
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("wuj", "123"));
		
		
		List<AuditLog> list = auditLogDao.find();
		System.out.println(list);
		
		System.out.println(auditManager.getClass());
		
		List<AuditMessage> list2 = auditManager.findAuditMessagesByUser(1137);
		System.out.println(list2);
		
//		RFSObject domain = new RFSObject();
//		domain.setId(1101L);
//		list2 = auditManager.findAuditMessagesByDomain(domain);
//		System.out.println(list2);
		
		super.setComplete();
		AspectJ.testMethodAudit();
		
		AspectJ.testMethodOperation();
		
		RFSObject obj = new RFSObject();
		obj.setId(1000L);
		obj.setName("测试对象");
		AspectJ.testMethodOperstion(obj);
		
		AspectJ.testMethodOperstion(9000L);
		
		AspectJ.testMethodOperstion2(90009L);
		Thread.sleep(10000);
	}
	
	@Operation(targetName="项目", operation="新建", domainArgIndex=0)
	public void createProject(RFSObject obj){
		
	}
	
	@Operation(targetName="项目", operation="删除", domainType="cn...Project", domainIdArgIndex=0)
	public void removeProject(Long id){
		
	}
	@Operation(targetName="项目", operation="修改", domainArgIndex=1)
	public void update(RFSObject old, RFSObject newo){
		
	}
	

}
