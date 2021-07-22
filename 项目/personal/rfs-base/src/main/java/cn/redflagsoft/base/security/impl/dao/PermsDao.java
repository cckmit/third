/*
 * $Id: PermsDao.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security.impl.dao;

import java.util.List;

/**
 * Ȩ��DAO��
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface PermsDao {

	/**
	 * 
	 * @param groupId
	 * @return
	 */
	List<Perms> findGroupPerms(long groupId);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<Perms> findUserPerms(long userId);

	/**
	 * 
	 * @param aceId
	 * @return
	 */
	List<Perms> findUserPerms(String aceId);

	/**
	 * 
	 * @param aceId
	 * @return
	 */
	List<Perms> findGroupPerms(String aceId);
	
	List<Perms> findGroupPermsByUserId(long userId);
	
	List<Perms> findGroupPermsByUsername(String username);


	Perms addUserPermission(long userId, String aceId, int permissionType, long permission);
	
	void removeUserPermission(Long userId, String aceId, int permissionType, long permission);

	void removeAllUserPermissions(String aceId, int permissionType);

	Perms addGroupPermission(Long groupId, String aceId, int permissionType, long permission);

	void removeGroupPermission(Long groupId, String aceId, int permissionType, long permission);

	void removeAllGroupPermissions(String aceId, int permissionType);

	void removeAllUserPermissions(long userId, int permissionType);

	void removeAllGroupPermissions(long groupId, int permissionType);
}
