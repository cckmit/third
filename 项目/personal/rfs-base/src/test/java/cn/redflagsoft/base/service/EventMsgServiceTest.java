package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.test.BaseTests;


public class EventMsgServiceTest extends  BaseTests {
	protected EventMsgService eventMsgService;
	
	public void testDealEventMsg() throws Exception{	
		List<Smsg> list = eventMsgService.dealEventMsg((short)1001, 1238329078511L, (short)2000, 1243856003854L, (short)0, (short)8);
		for(Smsg m:list){
			System.out.println(m.toJSONString());	
		}
	}
}
