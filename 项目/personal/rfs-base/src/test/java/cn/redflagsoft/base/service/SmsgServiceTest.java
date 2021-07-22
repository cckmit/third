package cn.redflagsoft.base.service;

import java.util.List;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.service.impl.SmsgServiceImpl;
import cn.redflagsoft.base.test.BaseTests;

public class SmsgServiceTest extends  BaseTests{
	
	
	public SmsgServiceTest(){
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("rfsa", "123", 
						new GrantedAuthority[]{new GrantedAuthorityImpl("ROLE_ADMIN")}));
	}
	protected SmsgServiceImpl smsgService;
	/*
	public void testCreateSmsg(){
		
		Clerk clerk = UserClerkHolder.getClerk();
		
		Smsg msg = new Smsg();
		msg.setKind(Smsg.KIND_短消息);
		msg.setTitle("测试信息");
		msg.setContent("测试信息正式内容...");
		msg.setKeyword("测试");
		msg.setAttached(2);
		msg.setSendNum(5);
		msg.setFrOrgId(clerk.getEntityID());
		msg.setFrOrgName(clerk.getEntityName());
		msg.setFrId(clerk.getId());
		msg.setFrName(clerk.getName());
		msg.setFrAddr(clerk.getEmailAddr());
		msg.setWriteTime(new Date());
		msg.setCheckTime(new Date());
		
		
		
		List<SmsgReceiver> list = new ArrayList<SmsgReceiver>();
		
		for(int i =0;i<5;i++){
			SmsgReceiver sr = new  SmsgReceiver();
			sr.setToId(clerk.getId());
			sr.setToAddr(clerk.getEmailAddr());
			sr.setToName(clerk.getName());
			list.add(sr);
		}
		
		smsgService.createSmsg(msg, list);
		
		super.setComplete();
	}
	
	*/
	public void etestPublishSmsg(){
		//smsgService.publishSmsg(106L);
		//super.setComplete();
	}
	
	public void etestCancelSmsg(){
		//smsgService.cancelSmsg(106L);
		//super.setComplete();
	}
	
	public void etestDeleteSmsg(){
		smsgService.deleteSmsg(106L);
		super.setComplete();
	}
	
	public void etestSenders(){
		List<SmsgSender> list = smsgService.getSenders();
		System.out.println(list);
		for (SmsgSender smsgSender : list) {
			System.out.println(smsgSender);
		}
		int msgCount = smsgService.getSendableSmsgCount();
		System.out.println("可发送的消息数量：" + msgCount);
	}
	
	/**
	 * 发送所有可以发送的消息。
	 */
	public void testSendAllAvailableMsgs(){
		smsgService.sendAvailableMsgs();
	}
}
