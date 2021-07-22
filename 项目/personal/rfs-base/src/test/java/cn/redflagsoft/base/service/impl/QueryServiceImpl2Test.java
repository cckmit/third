package cn.redflagsoft.base.service.impl;


import java.util.List;

import org.opoo.apps.json.JSONUtils;
import org.opoo.apps.util.SerializableUtils;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.test.BaseTests;


public class QueryServiceImpl2Test extends BaseTests {
	protected QueryServiceImpl2 queryService;
	
	public void testPresent(){
		assertNotNull(queryService);
	}
	
	public void testSQLQuery() throws Exception{
		List<Object[]> list = (List<Object[]>) queryService.find("action.list|SQL", ResultFilter.createEmptyResultFilter());
		System.out.println(list);
		for(Object[] a:list){
			for(Object o: a){
				System.out.print(o + "\t");
			}
			System.out.println();
		}
		
		JSONUtils.toJSONList(list);
		System.out.println(list);
		String s = SerializableUtils.toJSON(list);
		System.out.println(s);
	}
}
