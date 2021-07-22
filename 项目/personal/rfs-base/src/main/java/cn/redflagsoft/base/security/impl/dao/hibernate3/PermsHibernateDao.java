/*
 * $Id: PermsHibernateDao.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security.impl.dao.hibernate3;

import java.util.List;

import org.opoo.ndao.hibernate3.HibernateDaoSupport;

import cn.redflagsoft.base.security.impl.dao.Perms;
import cn.redflagsoft.base.security.impl.dao.PermsDao;

public class PermsHibernateDao extends HibernateDaoSupport implements PermsDao {

	@SuppressWarnings("unchecked")
	public List<Perms> findGroupPerms(long groupId) {
		String qs = "from GroupPerms where id.sid=?";
		return getHibernateTemplate().find(qs, groupId);
	}

	@SuppressWarnings("unchecked")
	public List<Perms> findUserPerms(long userId) {
		String qs = "from UserPerms where id.sid=?";
		return getHibernateTemplate().find(qs, userId);
	}

	@SuppressWarnings("unchecked")
	public List<Perms> findUserPerms(String aceId) {
		String qs = "from UserPerms where id.aceId=?";
		return getHibernateTemplate().find(qs, aceId);
	}

	@SuppressWarnings("unchecked")
	public List<Perms> findGroupPerms(String aceId) {
		String qs = "from GroupPerms where id.aceId=?";
		return getHibernateTemplate().find(qs, aceId);
	}

	public Perms addUserPermission(long userId, String aceId, int permissionType, long permission) {
		PermsKey id = new PermsKey(aceId, userId, permissionType); 
		UserPerms userPerms = (UserPerms) getHibernateTemplate().get(UserPerms.class, id);
		if(userPerms == null){
			userPerms = new UserPerms(id);
			userPerms.setPermissions(permission);
			getHibernateTemplate().save(userPerms);
		}else{
			long permissions = userPerms.getPermissions();
			permissions |= permission;
			userPerms.setPermissions(permissions);
			getHibernateTemplate().update(userPerms);
		}
		return userPerms;
	}

	public void removeUserPermission(Long userId, String aceId, int permissionType, long permission) {
		PermsKey id = new PermsKey(aceId, userId, permissionType); 
		UserPerms userPerms = (UserPerms) getHibernateTemplate().get(UserPerms.class, id);
		if(userPerms != null){
			permission = ~permission;
            long permissions = userPerms.getPermissions() & permission;
            if(permissions > 1){
            	userPerms.setPermissions(permissions);
            	getHibernateTemplate().update(userPerms);
            }else{
            	getHibernateTemplate().delete(userPerms);
            }
		}
	}

	public Perms addGroupPermission(Long groupId, String aceId, int permissionType, long permission) {
		PermsKey id = new PermsKey(aceId, groupId, permissionType); 
		GroupPerms groupPerms = (GroupPerms) getHibernateTemplate().get(GroupPerms.class, id);
		if(groupPerms == null){
			groupPerms = new GroupPerms(id);
			groupPerms.setPermissions(permission);
			getHibernateTemplate().save(groupPerms);
		}else{
			long permissions = groupPerms.getPermissions();
			permissions |= permission;
			groupPerms.setPermissions(permissions);
			getHibernateTemplate().update(groupPerms);
		}
		return groupPerms;
	}

	public void removeGroupPermission(Long groupId, String aceId, int permissionType, long permission) {
		PermsKey id = new PermsKey(aceId, groupId, permissionType); 
		GroupPerms groupPerms = (GroupPerms) getHibernateTemplate().get(GroupPerms.class, id);
		if(groupPerms != null){
			permission = ~permission;
            long permissions = groupPerms.getPermissions() & permission;
            if(permissions > 1){
            	groupPerms.setPermissions(permissions);
            	getHibernateTemplate().update(groupPerms);
            }else{
            	getHibernateTemplate().delete(groupPerms);
            }
		}
	}

	public void removeAllUserPermissions(String aceId, int permissionType) {
		String qs = "delete from UserPerms where id.aceId=? and id.type=?";
		getQuerySupport().executeUpdate(qs, new Object[]{aceId, permissionType});
	}
	
	public void removeAllGroupPermissions(String aceId, int permissionType) {
		String qs = "delete from GroupPerms where id.aceId=? and id.type=?";
		getQuerySupport().executeUpdate(qs, new Object[]{aceId, permissionType});
	}

	public void removeAllUserPermissions(long userId, int permissionType) {
		String qs = "delete from UserPerms where id.sid=? and id.type=?";
		getQuerySupport().executeUpdate(qs, new Object[]{userId, permissionType});
	}

	public void removeAllGroupPermissions(long groupId, int permissionType) {
		String qs = "delete from GroupPerms where id.sid=? and id.type=?";
		getQuerySupport().executeUpdate(qs, new Object[]{groupId, permissionType});
	}

	@SuppressWarnings("unchecked")
	public List<Perms> findGroupPermsByUserId(long userId) {
//		String qs = "from GroupPerms where id.sid in " +
//				"(select distinct gm.groupId from GroupMember gm, LiveUser u where gm.username=u.username and u.userId=?)";
		String qs = "select distinct(a) from GroupPerms a, GroupMember gm, LiveUser u " +
				"where a.id.sid=gm.groupId and gm.username=u.username and u.userId=?";
		return getHibernateTemplate().find(qs, userId);
	}

	@SuppressWarnings("unchecked")
	public List<Perms> findGroupPermsByUsername(String username) {
//		String qs = "from GroupPerms where id.sid in (select distinct gm.groupId from GroupMember gm where gm.username=?)";
		String qs = "select distinct(a) from GroupPerms a, GroupMember b where a.id.sid=b.groupId and b.username=?";
		return getHibernateTemplate().find(qs, username);
	}
}
