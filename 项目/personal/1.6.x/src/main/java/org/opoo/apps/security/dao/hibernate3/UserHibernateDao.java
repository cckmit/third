package org.opoo.apps.security.dao.hibernate3;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.annotation.NotQueryable;
import org.opoo.apps.dao.hibernate3.AbstractAppsHibernateDao;
import org.opoo.apps.security.User.OnlineStatus;
import org.opoo.apps.security.bean.LiveUser;
import org.opoo.apps.security.bean.UserAuthority;
import org.opoo.apps.security.dao.UserDao;
import org.opoo.ndao.DataAccessException;
import org.opoo.ndao.NonUniqueResultException;
import org.springframework.security.GrantedAuthority;
import org.springframework.util.Assert;

@NotQueryable
public class UserHibernateDao extends AbstractSecurityHibernateDao<LiveUser, String> implements UserDao {

	private AbstractAppsHibernateDao<UserAuthority, Long> userAuthorityDao = new AbstractAppsHibernateDao<UserAuthority, Long>(){};
	
	@Override
	public LiveUser get(String id) throws DataAccessException {
		if(StringUtils.isBlank(id)){
			return null;
		}
		return super.get(id);
	}

	@SuppressWarnings("unchecked")
	public List<UserAuthority> findUserAuthoritiesByUsername(String username) {
		String qs = "from UserAuthority where username=?";
		return getHibernateTemplate().find(qs, username);
	}

	public boolean isUserExists(String username) {
		String qs = "select username from LiveUser where username=?";
		return getHibernateTemplate().find(qs, username).size() > 0;
	}
	
	public void createUser(LiveUser user){
		save(user);
		createUserAuthorities(user);
	}

	public void updateUser(LiveUser user){
		update(user);
        deleteUserAuthorities(user.getUsername());
        createUserAuthorities(user);
	}
	
	protected void createUserAuthorities(LiveUser user) {
		for(GrantedAuthority ga : user.getAuthorities()){
			UserAuthority ua = new UserAuthority(user.getUsername(), ga.getAuthority());
			userAuthorityDao.save(ua);
		}
	}
	
	
	
	
	
	public void deleteUser(String username) {
		
		//数据库使用了级联删除，可以不调用以下2句。
		deleteUserAuthorities(username);
		deleteGroupMember(username);
		
		remove(username);
	}
	
	

	
	
	protected void deleteUserAuthorities(String username){
		String qs = "delete from UserAuthority where username=?";
		userAuthorityDao.getQuerySupport().executeUpdate(qs, username);
	}
	
	protected void deleteGroupMember(String username){
		//String qs0 = "delete from GroupAuthority where groupId in (select id from Group where username=?)";
		//getQuerySupport().executeUpdate(qs0, username);
		String qs = "delete from GroupMember where username=?";
		getQuerySupport().executeUpdate(qs, username);
	}
	
	public void updatePassword(String username, String password, String modifier){
		String qs = "update LiveUser set password=?, modifier=?, modificationTime=? where username=?";
		getQuerySupport().executeUpdate(qs, new Object[]{password, modifier, new Date(), username});
	}
	
	
//	/**
//	 * 更新用户信息，不改变密码、属性及权限集合。
//	 * @param user
//	 */
//	public void updateBaseInfo(LiveUser user){
//		String qs = "update LiveUser set enabled=?, accountExpireTime=?, accountNonLocked=?, credentialsExpireTime=?," +
//				"lastLoginTime=?, loginCount=?, tryLoginCount=?, loginAddress=?, onlineStatus=?," +
//				"modificationTime=?, modifier=? where username=?";
//		Object[] values = new Object[]{user.isEnabled(), user.getAccountExpireTime(), user.isAccountNonLocked(),
//				user.getCredentialsExpireTime(), user.getLastLoginTime(), user.getLoginCount(), user.getTryLoginCount(),
//				user.getLoginAddress(), user.getOnlineStatus(), new Date(), user.getModifier(), user.getUsername()};
//		getQuerySupport().executeUpdate(qs, values);
//	}
	
	
	
	

	/* (non-Javadoc)
	 * @see org.springframework.dao.support.DaoSupport#initDao()
	 */
	@Override
	protected void initDao() throws Exception {
		Assert.notNull(getSecurityIdGeneratorProvider());
		super.initDao();
		userAuthorityDao.setSessionFactory(getSessionFactory());
		userAuthorityDao.setIdGeneratorProvider(getSecurityIdGeneratorProvider());
	}

	public void loginFailure(String username) {
		String qs = "update LiveUser set tryLoginCount=tryLoginCount+1, onlineStatus=?, " +
				"modificationTime=?, modifier=? where username=?";
		getQuerySupport().executeUpdate(qs, new Object[]{OnlineStatus.OFFLINE, new Date(), username, username});
	}

	public void loginSuccess(LiveUser user) {
		String qs = "update LiveUser set loginCount=loginCount+1, tryLoginCount=0, lastLoginTime=loginTime, loginTime=?, " +
				"onlineStatus=?, " +
				"modificationTime=?, modifier=? where username=?";
		Date date = new Date();
		Object[] values = new Object[]{date, OnlineStatus.ONLINE, date, user.getUsername(), user.getUsername()};
//		Object[] values = new Object[]{date, OnlineStatus.ONLINE, date, user.getUsername(), user.getUsername()};
		getQuerySupport().executeUpdate(qs, values);
	}

	public void logout(LiveUser user) {
		String qs = "update LiveUser set onlineStatus=?, "+
				"modificationTime=?, modifier=? where username=?";;
		Date date = new Date();
		Object[] values = new Object[]{OnlineStatus.OFFLINE, date, user.getUsername(), user.getUsername()};
		getQuerySupport().executeUpdate(qs, values);
	}

	/* (non-Javadoc)
	 * @see org.opoo.ndao.hibernate3.CachedHibernateDao#update(org.opoo.ndao.Domain)
	 */
	@Override
	public LiveUser update(LiveUser entity) throws DataAccessException {
		LiveUser user = get(entity.getUsername());
		String originPassword = user.getPassword();
		user.copyFrom(entity);
		user.setPassword(originPassword);
		
		return super.update(user);
	}

	@SuppressWarnings("unchecked")
	public String getUsernameByUserId(Long userId) {
		String qs = "select username from LiveUser where userId=?";
		List<String> list = getHibernateTemplate().find(qs, userId);
		if(list != null && !list.isEmpty()){
			if(list.size() > 1){
				throw new NonUniqueResultException(list.size());
			}else{
				return list.iterator().next();
			}
		}
		return null;
	}
	
}
