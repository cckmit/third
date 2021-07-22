/*
 * $Id: IssueWorkSchemeTest.java 4352 2011-04-26 05:56:19Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.schemes;

import java.util.Random;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.bean.Issue;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.scheme.schemes.IssueWorkScheme;
import cn.redflagsoft.base.test.BaseTests;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class IssueWorkSchemeTest extends BaseTests {
	
	protected SchemeManager schemeManager;
	
	public void testPrototype(){
		Scheme scheme1 = schemeManager.getScheme("projectIssueWorkScheme");
		Scheme scheme2 = schemeManager.getScheme("projectIssueWorkScheme");
		System.out.println(scheme1);
		System.out.println(scheme2);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void testInvokeWorkScheme() throws Exception{
		super.setComplete();
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("rfsa", "123"));
		
		Scheme scheme = schemeManager.getScheme("issueWorkScheme");
		
		assertNotNull(scheme);
		
		assertTrue(scheme instanceof IssueWorkScheme);
		
		
		Issue is = new Issue();
		is.setDescription("这是一个测试问题:"+ new Random().nextInt(200));
		RFSObject obj = new RFSObject();
		obj.setName("测试项目");
		obj.setId(1L);
		
		IssueWorkScheme iws = (IssueWorkScheme)scheme;
		iws.setIssue(is);
		iws.setRfsObject(obj);
		iws.doCreate();
		
		
		
	
/*
		if(scheme instanceof ParametersAware){
			Map<String,String> map = new HashMap<String,String>();
//			map.put("issue.", value)
//			map.put("rfsObject.key", value)
//			map.put("rfsObject.name", value)
			
			((ParametersAware)scheme).setParameters(map);
		}
		SchemeInvoker.invoke(scheme, "submit");
		
*/
	}
	
	
	
}
