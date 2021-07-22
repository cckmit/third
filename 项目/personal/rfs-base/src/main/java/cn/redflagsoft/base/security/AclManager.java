/*
 * $Id: AclManager.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security;

import java.util.List;
import java.util.Map;

import org.opoo.apps.security.Group;
import org.opoo.apps.security.User;

/**
 * ACL����
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface AclManager {

	/**
	 * ����ָ���û��ķ����б����б������пɷ����Ȩ����Ϊģ�塣
	 * 
	 * 
	 * @param user �û�
	 * @param permissionType ��Ȩ����
	 * @return
	 */
	List<Ace> getUserAcl(User user, PermissionType permissionType);
	
	/**
	 * ����ָ����ķ����б����б������пɷ����Ȩ����Ϊģ�塣
	 * 
	 * @param group �û���
	 * @param permissionType ��Ȩ����
	 * @return
	 */
	List<Ace> getGroupAcl(Group group, PermissionType permissionType);
	
	/**
	 * ��ѯ�û����յĿ��Է��ʵ���Ȩ��ID��Ȩ��ֵ��
	 * ֻ��ѯ�ѷ����Ȩ�ޣ�δ����Ĳ��������б��У����ò�ѯ���Կɷ���Ȩ��Ϊģ�塣
	 * 
	 * @param user
	 * @return map�ṹ������keyΪ��Ȩ��ID��ValueΪȨ��ֵ
	 */
	Map<String, Long> getUserPermissions(User user);
}
