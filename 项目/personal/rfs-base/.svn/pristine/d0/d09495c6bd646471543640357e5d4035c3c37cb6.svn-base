package cn.redflagsoft.base.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.security.Group;
import org.opoo.apps.security.SecurityUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

import com.google.common.collect.Maps;

import cn.redflagsoft.base.bean.Clerk;

/**
 * 授权检查的相关工具类。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AuthUtils {
	private static final Log log = LogFactory.getLog(AuthUtils.class);
	
	/**
	 * 普通可登录用户
	 */
	public static final String ROLE_USER = "ROLE_" + BaseAuthority.USER.name();
	/**
	 * 管理员
	 */
	public static final String ROLE_ADMINISTRATOR = "ROLE_" + BaseAuthority.ADMINISTRATOR.name();
	/**
	 * 超级管理（RFSA）
	 */
	public static final String ROLE_ADMIN = "ROLE_" + BaseAuthority.ADMIN.name();
//	public static final String ROLE_SUPERVISOR = "ROLE_" + BaseAuthority.SUPERVISOR.name();
//	public static final String ROLE_SYS = "ROLE_" + BaseAuthority.SYS.name();
	/**
	 * 部门（单位）管理员
	 */
	public static final String ROLE_MANAGER = "ROLE_" + BaseAuthority.MANAGER.name();
	
	private static Map<String, InternalUser> internalUsers = Maps.newHashMap();
	private static Map<Long, String> internalUsernames = Maps.newHashMap();
	
	static{
		internalUsers.put(SchedulerUser.USERNAME, SchedulerUser.getInstance());
		internalUsers.put(WorkflowUser.USERNAME, WorkflowUser.getInstance());
		internalUsers.put(SupervisionUser.USERNAME, SupervisionUser.getInstance());
		internalUsers.put(SupervisorUser.USERNAME, SupervisorUser.getInstance());
		internalUsers.put(RFSA.USERNAME, new RFSA());
		
		internalUsernames.put(SchedulerUser.USER_ID, SchedulerUser.USERNAME);
		internalUsernames.put(WorkflowUser.USER_ID, WorkflowUser.USERNAME);
		internalUsernames.put(SupervisionUser.USER_ID, SupervisionUser.USERNAME);
		internalUsernames.put(SupervisorUser.USER_ID, SupervisorUser.USERNAME);
		internalUsernames.put(RFSA.USER_ID, RFSA.USERNAME);
	}
	
	public static boolean isInternalUser(String username){
		return internalUsers.containsKey(username);
	}
	
	public static boolean isInternalUser(Long userId){
		return internalUsernames.containsKey(userId);
	}
	
	public static boolean isInternalUser(UserDetails user){
		if(user instanceof InternalUser){
			log.debug("user is internal: " + user);
			return true;
		}
		
		String username = user.getUsername();
		if(internalUsers.containsKey(username)){
			log.debug("username is internal: " + username);
			return true;
		}
		
		if(user instanceof User){
			User u = (User) user;
			if(internalUsernames.containsKey(u.getUserId())){
				log.debug("userId is internal: " + u.getUserId());
				return true;
			}
		}
		return false;
	}
	
	public static InternalUser getInternalUser(String username){
		return internalUsers.get(username);
	}
	
	public static InternalUser getInternalUser(Long userId){
		String username = internalUsernames.get(userId);
		if(username != null){
			return getInternalUser(username);
		}
		return null;
	}
	
	public static InternalUser getInternalUser(UserDetails user){
		if(user instanceof InternalUser){
			return (InternalUser) user;
		}
		
		String username = user.getUsername();
		InternalUser internalUser = getInternalUser(username);
		if(internalUser != null){
			return internalUser;
		}
		
		if(user instanceof User){
			User u = (User) user;
			return getInternalUser(u.getUserId());
		}
		
		return null;
	}
	
	public static boolean isGranted(String authority){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return SecurityUtils.isGranted(authentication, authority);
	}

	//public static boolean isSupervisor(){
	//	return isGranted(ROLE_ADMIN) || isGranted(ROLE_SUPERVISOR) || isGranted(ROLE_SYS);
	//}
	public static boolean isManager(){
		return isGranted(ROLE_MANAGER);
	}
	
	public static boolean isAdministrator(){
		return isGranted(ROLE_ADMINISTRATOR);
	}
	
	public static boolean isAdmin(){
		return isGranted(ROLE_ADMIN);
	}
	
	public static boolean isRFSA(){
		User user = UserHolder.getNullableUser();
		if(user != null && RFSA.USERNAME.equals(user.getUsername())){
			return true;
		}
		return false;
	}
	
	public static boolean isInvisible(User user){
		return isInvisible(user.getUserId());
	}
	
	public static boolean isInvisible(Clerk clerk){
		return isInvisible(clerk.getId());
	}
	
	static boolean isInvisible(long userId){
		return userId > 10L  && userId < 100L;
	}
	
	public static boolean isSystemUser(User user){
		return isSystemUser(user.getUserId());
	}
	public static boolean isSystemUser(Clerk clerk){
		return isSystemUser(clerk.getId());
	}
	
	public static boolean isSystemUser(long userId){
		return userId < 100L;
	}
	
	public static boolean isSystemGroup(Group g){
		return isSystemGroup(g.getId());
	}
	
	public static boolean isSystemGroup(long groupId){
		return groupId < 100L;
	}
	
	/**
	 * 根据当前管理员的权限来查询基本权限。
	 * 不同级别的管理员来查询的权限集合不同。
	 * @return
	 */
	public static List<BaseAuthority> getBaseAuthorities(){
		BaseAuthority[] all = BaseAuthority.values();
		List<BaseAuthority> list = new ArrayList<BaseAuthority>();
		boolean admin = isAdmin();
		boolean administrator = isAdministrator();
		boolean manager = isManager();
		for(BaseAuthority a: all){
			if(admin && a.getLevel() >= 0){
				list.add(a);
			}else if(administrator && a.getLevel() >= 2){
				list.add(a);
			}else if(manager && a.getLevel() >= 3){
				list.add(a);
			}
		}
		return list;
	}
}
