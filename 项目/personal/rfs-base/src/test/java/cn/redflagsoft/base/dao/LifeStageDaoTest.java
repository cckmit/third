package cn.redflagsoft.base.dao;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.opoo.ndao.hibernate3.HibernateDao;

import cn.redflagsoft.base.bean.LifeStage;
import cn.redflagsoft.base.test.BaseTests;

public class LifeStageDaoTest extends BaseTests {
	
	protected LifeStageDao lifeStageDao;
	
	
	public void testSave(){
		
		super.setComplete();
		LifeStage temp =new LifeStage();
		temp.setObjectType((short)1004);
		temp.setObjectId(new Long(167));
		temp.setName("测试数据KK");
//		temp.setStatus0((byte)0);
//		temp.setStatus1((byte)1);
//		temp.setStatus2((byte)2);
//		temp.setStatus9((byte)9);

//		temp.setObjectType((short)1002);
//		temp.setObjectId(new Long(72));
//		temp.setName("测试数据2");

		temp  = lifeStageDao.save(temp);
		LifeStage findLife =lifeStageDao.get(temp.getObjectId());
		assertEquals(temp, findLife);
		
	}
	
	public void testUpdate() throws Exception{
		LifeStage  lifestate =lifeStageDao.get(new Long(1001));
		LifeStage  tempLife =new LifeStage();
		PropertyUtils.copyProperties(tempLife,lifestate);
		lifestate.setObjectType((byte)(1002));
		assertNotSame(lifestate.getObjectType(),tempLife.getObjectType());
		super.setComplete();
	}
	
	public void testDelete(){
		LifeStage lifestage =getOneLifeStage();
		int result = lifeStageDao.delete(lifestage);
		System.out.println(result);
		assertSame(result, 1);
	}
	
	public void testfind(){
		List<LifeStage> lifestagelist = lifeStageDao.find();
		assertNotNull(lifestagelist);
	}
	
	
	public LifeStage getOneLifeStage()
	{
		List<LifeStage> lifestagelist = lifeStageDao.find();
		LifeStage   result = null;
		if(lifestagelist.size()!= 0 ||lifestagelist!= null){
			result =lifestagelist.get(0);
		}
		return result;
	}
	
	
	public void testFind2(){
		String hql = "select a from LifeStage a, Objects b where a.id=b.sndObject " +
				" and a.type=1003";
		
		List list = ((HibernateDao) lifeStageDao).getQuerySupport().find(hql);
		System.out.println(list);
	}
	
	public void testSynchronizeLifeStageStatusForObject(){
		lifeStageDao.synchronizeLifeStageStatusForObject(3887L);
	}
}
