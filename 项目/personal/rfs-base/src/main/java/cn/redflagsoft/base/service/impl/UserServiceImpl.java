package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.security.GroupManager;
import org.opoo.apps.security.SecurityUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserManager;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.dao.AdminUserDao;
import cn.redflagsoft.base.security.AuthUtils;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.UserService;
import cn.redflagsoft.base.vo.UserClerkVO;

/**
 * 
 *
 */
public class UserServiceImpl implements UserService{
	
	private static final Log log = LogFactory.getLog(UserServiceImpl.class);
//	private Long[] roleID = new Long[]{1086L,1087L,1088L};
	private ClerkService clerkService;
	private UserManager userManager;
	private AdminUserDao adminUserDao;
	private GroupManager groupManager;
	
	public GroupManager getGroupManager() {
		return groupManager;
	}

	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}

	public AdminUserDao getAdminUserDao() {
		return adminUserDao;
	}

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Queryable
	public List<Clerk> findAllAdminUser(ResultFilter filter){
		List<UserClerkVO> findUserClerkVOs = clerkService.findUserClerkVOs(filter);
		List<Clerk> resultList = new ArrayList<Clerk>();
		for (UserClerkVO userClerkVO : findUserClerkVOs) {
			User user = (User) userManager.loadUserByUsername(userClerkVO.getUsername());
			if(SecurityUtils.isGranted(user, AuthUtils.ROLE_MANAGER) && user.isEnabled()){
				resultList.add(userClerkVO);
			}
		}
//		List<Clerk> resultList = new ArrayList<Clerk>();
//		for (Object obj : findAllAdminUsers) {
//			Clerk c = (Clerk)obj;
//			UserDetails user = userManager.loadUserByUserId(c.getId());
//			if(user == null){
//				continue;
//			}
//			// 判断用户是否有管理员权限
//			if(SecurityUtils.isGranted((User)user, AuthUtils.ROLE_MANAGER)){
//				// 判断用户是否 被禁用
//				if(user.isEnabled()){
//					resultList.add(c);
//				}
//			}
//		}
		return resultList;      
	}

	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	/**
	 * 
	 */
	@Queryable(argNames={"roleIds"})
	public List<UserClerkVO> findInformationMgrUserOfRoles(String roleIds) {
		//处理参数，组装roleIds数组
		if(StringUtils.isBlank(roleIds)){
			throw new IllegalArgumentException("必须指定参数roleIds，调用形式 ?...&s[0].n=roleIds&s[0].v=id1,id2,id3...");
		}
		List<Long> longRoleIds = new ArrayList<Long>();
		StringTokenizer st = new StringTokenizer(roleIds, " ,");
		while(st.hasMoreTokens()){
			longRoleIds.add(Long.parseLong(st.nextToken()));
		}
		
		//查询数据
		if(longRoleIds.isEmpty()){
			return Collections.emptyList();
		}
		
		List<User> users = new ArrayList<User>();
		for(long roleId: longRoleIds) {
			List<User> usersInGroup = groupManager.findUsersInGroup(roleId);
			if(!usersInGroup.isEmpty()){
				users.addAll(usersInGroup);
			}
		}
		
		if(users.isEmpty()){
			return Collections.emptyList();
		}
		
		List<UserClerkVO> userClerks = new ArrayList<UserClerkVO>();
		for(User user: users) {
			Clerk clerk = clerkService.getClerk(user.getUserId());
			if(clerk == null) {
				log.warn("找不到用户对应的clerk：" + user.getUsername());
			} else {
				UserClerkVO vo = new UserClerkVO(user,clerk);
				userClerks.add(vo);
			}
		}
		return userClerks;
	}
}
