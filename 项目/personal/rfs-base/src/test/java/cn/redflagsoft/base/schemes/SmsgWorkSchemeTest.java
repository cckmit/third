package cn.redflagsoft.base.schemes;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.scheme.schemes.smsg.SmsgWorkScheme;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.test.BaseTests;

public class SmsgWorkSchemeTest extends BaseTests{
	protected SchemeManager schemeManager;
	
	public void testCreateSmsg(){
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("rfsa", "123"));
		
		SmsgWorkScheme scheme = (SmsgWorkScheme)schemeManager.getScheme("smsgWorkScheme");
		
		
		Clerk clerk = UserClerkHolder.getClerk();
		
		Smsg msg = new Smsg();
		msg.setKind(Smsg.KIND_手机短信);
		msg.setTitle("测试信息" + new Date());
		msg.setContent("测试信息正式内容..." + new Date());
		msg.setKeyword("测试");
		msg.setAttached(2);
		msg.setSendNum(5);
		msg.setFrOrgId(clerk.getEntityID());
		msg.setFrOrgName(clerk.getEntityName());
		msg.setFrId(clerk.getId());
		msg.setFrName(clerk.getName());
		//msg.setFrAddr(clerk.getEmailAddr());
		msg.setFrAddr("test@redflagsoft.cn");
		msg.setWriteTime(new Date());
		msg.setCheckTime(new Date());
		
		
		
		List<SmsgReceiver> list = new ArrayList<SmsgReceiver>();
		
		for(int i =0;i<5;i++){
			SmsgReceiver sr = new  SmsgReceiver();
			sr.setToId(clerk.getId());
			//sr.setToAddr(clerk.getEmailAddr());
			sr.setToAddr("demo" + i + "@163.com");
			sr.setToName(clerk.getName());
			list.add(sr);
		}
//		scheme.setSmsg(msg);
//		scheme.setSmsgReceiverList(list);
//		
//		
//		scheme.createObject();
//		setComplete();
	}
	
}
