/*
 * $Id: AclServiceTest.java 5462 2012-03-21 07:56:51Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.security;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.User;
import org.opoo.apps.util.SerializableUtils;

import cn.redflagsoft.base.security.Ace;
import cn.redflagsoft.base.security.AclManager;
import cn.redflagsoft.base.security.PermissionType;
import cn.redflagsoft.base.test.BaseTests;


public class AclServiceTest extends BaseTests {
	protected AclManager aclService;
	protected AceManager aceManager;
	protected PermissionsManager permissionsManager;
	
	public void testGetAcl() throws Exception{
		User user = (User) Application.getContext().getUserManager().loadUserByUsername("wuj");
		List<Ace> list = aclService.getUserAcl(user, PermissionType.ADDITIVE);
		System.out.println(SerializableUtils.toJSON(list));
	}
	
	public void testGetUserPermissions(){
		User user = (User) Application.getContext().getUserManager().loadUserByUsername("wuj");
		
		//第一种
		Map<String,Long> perms = new LinkedHashMap<String,Long>();
		long userId = user.getUserId();
		Set<Ace> set = aceManager.getAcl();
		for(Ace ace: set){
			String aceId = ace.getId();
			Permissions permissions = permissionsManager.getPermissions(userId, aceId);
			long permissionsValue = permissions.value();
			if(permissionsValue > 0){
				perms.put(aceId, permissionsValue);
			}
		}
		System.out.println(perms);
		
		System.out.println("===================================================================");
		//第二种
		Map<String, Long> map = aclService.getUserPermissions(user);
		System.out.println(map);
	}
}
