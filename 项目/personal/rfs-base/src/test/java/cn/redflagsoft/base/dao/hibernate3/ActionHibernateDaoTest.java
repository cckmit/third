package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;
import java.util.UUID;

import org.junit.Ignore;

import cn.redflagsoft.base.bean.Action;
import cn.redflagsoft.base.dao.ActionDao;

public class ActionHibernateDaoTest extends AbstractDaoSpringTests {

	protected ActionDao actionDao;
	

	public void testSetUp(){
		assertNotNull(actionDao);
	}
	
	@Ignore
	public void ptestFind(){
		Action action = new Action();
		action.setId(System.currentTimeMillis());
		action.setName("ҵ�񱨱�");
		action.setUid(UUID.randomUUID().toString());
		action.setLocation("ywbb.jsp");
		actionDao.save(action);
		
		List<Action> list = actionDao.find();
		System.out.println("��ѯ Action �����" + list);
	}
}
