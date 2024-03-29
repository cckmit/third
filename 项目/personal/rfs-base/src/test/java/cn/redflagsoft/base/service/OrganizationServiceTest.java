/*
 * $Id: OrganizationServiceTest.java 4352 2011-04-26 05:56:19Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.Organization;
import cn.redflagsoft.base.test.BaseTests;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrganizationServiceTest extends BaseTests{
	protected OrganizationService organizationService;
	
	public void testGetRootOrg(){
		Organization organization = organizationService.getRootOrganization();
//		System.out.println(organization.getAbbr());
		print(organization, 0);
	}
	
	private void print(Organization o, int index){
		String b = blanks(index);
		System.out.println(b + o.getId() + ":" + o.getAbbr());
		List<Organization> list = o.getSuborgs();
		if(!list.isEmpty()){
			for(Organization d: list){
				print(d, index + 1);
			}
		}
	}
	
	private String blanks(int level){
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < level ; i++){
			sb.append("    ");
		}
		return sb.toString();
	}
}
