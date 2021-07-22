package cn.redflagsoft.base.dao;


import java.util.List;

import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.test.BaseTests;

public class MatterAffairTest extends BaseTests {
	protected MatterAffairDao  matterAffairDao;
	
	public void testFindAll(){
		
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(Order.asc("sn"));
		List<MatterAffair> list = matterAffairDao.find(filter);
		for(MatterAffair mf : list){
			System.out.println(mf);
		}
	}
}
