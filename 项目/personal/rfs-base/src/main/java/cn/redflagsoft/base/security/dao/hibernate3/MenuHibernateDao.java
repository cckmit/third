/*
 * $Id: MenuHibernateDao.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security.dao.hibernate3;

import java.util.ArrayList;
import java.util.List;

import org.opoo.apps.dao.hibernate3.AbstractAppsHibernateDao;

import cn.redflagsoft.base.security.Menu;
import cn.redflagsoft.base.security.dao.MenuDao;

/**
 * @author Alex Lin
 * @deprecated
 */
public class MenuHibernateDao extends AbstractAppsHibernateDao<Menu, String> implements MenuDao{



	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.auth.dao.MenuDao#findMenusByUsernameAndGroupId(java.lang.String, java.lang.Long)
	 */
	public List<Menu> findMenusByUsernameAndGroupId(String username, Long groupId) {
		String qs1 = "select m from Menu m, UserAuthority ua where m.authority=ua.authority" +
				" and ua.username=? and m.groupId=?";
		String qs2 = "select m from Menu m, GroupAuthority ga, GroupMember gm where m.authority=ga.authority" +
				" and ga.groupId=gm.groupId and gm.username=? and m.groupId=?";
		
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.auth.dao.MenuDao#findMenusByAuthorities(java.lang.String[])
	 */
	public List<Menu> findMenusByAuthorities(String[] authorities) {
		if(authorities.length == 0){
			return new ArrayList<Menu>();
		}
		String qs = "from Menu where authority in (:authorities) order by displayOrder";
		return getQuerySupport().find(qs, "authorities", authorities);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.security.dao.MenuDao#findMenusByUsername(java.lang.String)
	 */
	public List<Menu> findMenusByUsername(String username) {
		String qs = "select m from GroupMember gm, GroupAuthority ga, Menu m where gm.groupId=ga.groupId and ga.authority=m.authority and gm.username=? order by m.groupId, m.displayOrder";
		return getHibernateTemplate().find(qs, username);
	}
	
	public List<Menu> findMenusByUserGroupId(long groupId){
		String qs = "select m from GroupAuthority ga, Menu m where ga.authority=m.authority and ga.groupId=? order by m.groupId, m.displayOrder";
		return getHibernateTemplate().find(qs, groupId);
	}
	
	public List<Menu> findNotOwnAuthorityMenusByUsername(String username) {
		String hql = "select a from Menu a where a.groupId<>(select gm.groupId from GroupMember gm where gm.username=?)";
		return getHibernateTemplate().find(hql, username);
	}

	public List<Menu> findGroupOwnAuthorityMenusByGroupId(Long groupId) {
		String hql = "select m from GroupAuthority ga, Menu m where ga.authority=m.authority and m.groupId=?";
		return getHibernateTemplate().find(hql, groupId);
	}
	
	public List<Menu> findGroupNotOwnAuthoritiesByGroupId(Long groupId) {
		//String hql = "select ga from GroupAuthority ga, Menu m where ga.authority=m.authority and m.groupId<>?";
		String hql = "select m from Menu m where m.authority not in(select ga.authority from GroupAuthority ga where ga.groupId=?)";
		
		return getHibernateTemplate().find(hql, groupId);
	}

	public List<Menu> findMenuManageInfo() {
		String hql = "select new cn.redflagsoft.base.security.Menu(m.authority,m.application,m.displayOrder,m.label2,m.groupId,mg.groupName,m.icon,m.image,m.logo,m.name)" +
				" from Menu m,MenuGroup mg where m.groupId=mg.id order by mg.displayOrder asc, m.displayOrder asc";
		return getHibernateTemplate().find(hql);
	}
}
