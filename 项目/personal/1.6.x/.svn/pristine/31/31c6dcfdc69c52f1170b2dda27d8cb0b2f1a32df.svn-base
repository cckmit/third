package org.opoo.apps.security.dao.hibernate3;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.opoo.apps.dao.hibernate3.AbstractAppsHibernateDao;
import org.opoo.apps.security.Group;
import org.opoo.apps.security.User;
import org.opoo.apps.security.bean.GroupAuthority;
import org.opoo.apps.security.bean.GroupMember;
import org.opoo.apps.security.dao.GroupDao;
import org.opoo.ndao.NonUniqueResultException;
import org.opoo.ndao.ObjectNotFoundException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;


public class GroupHibernateDao extends AbstractSecurityHibernateDao<Group, Long> implements GroupDao {
	private static final Log log = LogFactory.getLog(GroupHibernateDao.class);
	/**
	 * GroupAuthority的Dao.
	 */
	private AbstractAppsHibernateDao<GroupAuthority, Long> groupAuthorityDao = new AbstractAppsHibernateDao<GroupAuthority, Long>(){};
	/**
	 * GroupMember的Dao。
	 */
	private AbstractAppsHibernateDao<GroupMember, Long> groupMemberDao = new AbstractAppsHibernateDao<GroupMember, Long>(){};
	
	
	
	@SuppressWarnings("unchecked")
	public List<GroupAuthority> findGroupAuthoritiesByUsername(String username) {
		String qs = "select ga " +
	    "from Group g, GroupMember gm, GroupAuthority ga " +
	    "where gm.username = ? " +
	    "and g.id = ga.groupId " +
	    "and g.id = gm.groupId";
		if(log.isDebugEnabled()){
			log.debug("Load authorities for user: " + username + " >>" + qs);
		}
		return getHibernateTemplate().find(qs, username);
	}
	
    public void addGroupAuthority(final String groupName, final String authority) {
        final Long id = findGroupId(groupName);
        addGroupAuthority(id, authority);
    }
    
    
    @SuppressWarnings("unchecked")
	private Long findGroupId(String groupName) {
    	String qs = "select id from Group where name=?";
    	List<Long> list = getHibernateTemplate().find(qs, groupName);
    	if(list.size() == 0){
    		throw new ObjectNotFoundException(groupName, Group.class.getName());
    	}
    	return list.get(0);
    }
    
    

	/**
	 * 这样查询的用户没有权限信息。只有用户信息，适合用户管理。
	 * 
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUsersInGroup(Long groupId){
		String qs = "select u from LiveUser u, GroupMember gm where u.username=gm.username and gm.groupId=?";
		return getHibernateTemplate().find(qs, groupId);
	}
	
	/**
	 * 
	 * @param group
	 * @param authorities
	 */
	public void createGroup(Group group, String[] authorities){
		group = save(group);
		createGroupAuthorities(group.getId(), authorities);
	}
	
	/**
	 * 
	 */
	public void deleteGroup(Long groupId){
		deleteGroupAuthorities(groupId);
		deleteGroupMembers(groupId);
		remove(groupId);
	}
	
	protected void deleteGroupAuthorities(Long groupId){
		String qs = "delete GroupAuthority where groupId=?";
		getQuerySupport().executeUpdate(qs, groupId);
		
	}
	protected void deleteGroupMembers(Long groupId){
		String qs = "delete GroupMember where groupId=?";
		getQuerySupport().executeUpdate(qs, groupId);
	}

	protected void createGroupAuthorities(Long groupId, String[] authorities){
		for(String a: authorities){
			GroupAuthority ga = new GroupAuthority();
			ga.setAuthority(a);
			ga.setGroupId(groupId);
			groupAuthorityDao.save(ga);
		}
	}

	
	
	


	public void addGroupAuthority(Long groupId, String authority) {
        GroupAuthority ga = new GroupAuthority();
        ga.setAuthority(authority);
        ga.setGroupId(groupId);
        groupAuthorityDao.save(ga);   
	}

	public void addUserToGroup(String username, Long groupId) {
		GroupMember gm = new GroupMember();
		gm.setGroupId(groupId);
		gm.setUsername(username);
		groupMemberDao.save(gm);
	}

	@SuppressWarnings("unchecked")
	public List<String> findGroupAuthorities(Long groupId) {
		String qs = "select authority from GroupAuthority where groupId=?";
		return getHibernateTemplate().find(qs, groupId);
	}

	public List<Group> findGroups() {
		return find();
	}

	public Group getGroup(Long id) {
		return get(id);
	}

	public void removeGroupAuthority(Long groupId, String authority) {
		String qs = "delete from GroupAuthority where groupId=? and authority=?";
		getQuerySupport().executeUpdate(qs, new Object[]{groupId, authority});
	}

	public void removeUserFromGroup(String username, Long groupId) {
		String qs = "delete from GroupMember where groupId = ? and username = ?";
		getQuerySupport().executeUpdate(qs, new Object[]{groupId, username});
	}

	public void renameGroup(Long id, String newName) {
		String qs = "update Group set name=? where id=?";
		getQuerySupport().executeUpdate(qs, new Object[]{newName, id});
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.dao.support.DaoSupport#initDao()
	 */
	@Override
	protected void initDao() throws Exception {
		Assert.notNull(getSecurityIdGeneratorProvider());
		super.initDao();
		groupAuthorityDao.setSessionFactory(getSessionFactory());
		groupAuthorityDao.setIdGeneratorProvider(getSecurityIdGeneratorProvider());
		groupMemberDao.setSessionFactory(getSessionFactory());
		groupMemberDao.setIdGeneratorProvider(getSecurityIdGeneratorProvider());
	}


	@SuppressWarnings("unchecked")
	public List<String> findGroupNames() {
		String qs = "select name from Group";
		return getHibernateTemplate().find(qs);
	}

	@SuppressWarnings("unchecked")
	public List<String> findUsernamesInGroup(String groupName) {
		String qs = "select gm.username from GroupMember gm, Group g where gm.groupId=g.id and g.name=?";
		return getHibernateTemplate().find(qs, groupName);
	}

	public void deleteGroup(String groupName) {
		Long id = findGroupId(groupName);
		if(id != null){
			deleteGroup(id);
		}
	}

	public void renameGroup(String oldName, String newName) {
		String qs = "update Group set name=? where name=?";
		getQuerySupport().executeUpdate(qs, new String[]{newName, oldName});
	}

	public void addUserToGroup(String username, String groupName) {
		Long id = findGroupId(groupName);
		addUserToGroup(username, id);
	}

	public void removeUserFromGroup(String username, String groupName) {
		Long id = findGroupId(groupName);
		removeUserFromGroup(username, id);
	}

	@SuppressWarnings("unchecked")
	public List<String> findGroupAuthorities(String groupName) {
		String qs = "select ga.authority from GroupAuthority ga, Group g where ga.groupId=g.id where g.name=?";
		return getHibernateTemplate().find(qs, groupName);
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.dao.GroupDao#removeGroupAuthority(java.lang.String, java.lang.String)
	 */
	public void removeGroupAuthority(String groupName, String authority) {
        final Long id = findGroupId(groupName);
        removeGroupAuthority(id, authority);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findGroupPropertiesByUsername(final String username) {
		final String sql = "select a.PROPNAME, a.PROPVALUE from SEC_GROUPS_PROP a, SEC_GM b where a.GROUPID=b.GROUPID and b.USERNAME=?";
		List<Object[]> list = getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql)
				.addScalar("PROPNAME", Hibernate.STRING)
				.addScalar("PROPVALUE", Hibernate.STRING)
				.setString(0, username)
				//.setResultTransformer(Transformers.TO_LIST)
				.list();
			}
		});
		Map<String, String> map = new HashMap<String, String>();
		for(Object[] arr:list){
			map.put((String) arr[0], (String) arr[1]);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public List<Group> findGroupsByUsername(String username) {
		String qs = "select g from Group g, GroupMember gm where g.id=gm.groupId and gm.username=?";
		return getHibernateTemplate().find(qs, username);
	}


	@SuppressWarnings("unchecked")
	public List<String> findUsernamesInGroup(Long groupId) {
		String qs = "select gm.username from GroupMember gm where gm.groupId=?";
		return getHibernateTemplate().find(qs, groupId);
	}

	public Group getGroupByName(String groupName) {
		String qs = "from Group where name=?";
		
    	@SuppressWarnings("unchecked")
		List<Group> list = getHibernateTemplate().find(qs, groupName);
    	if(list.size() > 1){
    		throw new NonUniqueResultException(list.size());
    	}
    	if(list.size() == 1){
    		return list.iterator().next();
    	}
		return null;
	}

	
	@SuppressWarnings("unchecked")
	public List<Long> findGroupIdsByUsername(String username) {
		String qs = "select gm.groupId from GroupMember gm where gm.username=?";
		return getHibernateTemplate().find(qs, username);
	}
}
