/*
 * $Id: UserScheme.java 6333 2013-12-11 06:23:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Labelable;
import org.opoo.apps.Model;
import org.opoo.apps.security.Group;
import org.opoo.apps.security.GroupManager;
import org.opoo.apps.security.SecurityUser;
import org.opoo.apps.security.SecurityUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.opoo.apps.security.UserManager;
import org.opoo.apps.security.bean.LiveUser;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.CriterionBuffer;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.annotation.Secured;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.util.Assert;

import cn.redflagsoft.base.BusinessException;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.security.AuthUtils;
import cn.redflagsoft.base.security.Authority;
import cn.redflagsoft.base.security.BaseAuthority;
import cn.redflagsoft.base.security.Property;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.OrgService;
import cn.redflagsoft.base.vo.UserClerkVO;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.validator.ActionValidatorManagerFactory;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.Validator;

/**
 * 用户管理。
 * 
 * 注意：该类中涉及到的Group对象，实则是 Role（角色）的意思。
 * 
 * @author Alex Lin
 *
 */
public class UserScheme extends AbstractScheme implements Scheme {
	public static final Log log = LogFactory.getLog(UserScheme.class);
	public static final String USER_DEFAULT_AUTHORITY = "USER";
	private UserManager userManager;
	//private MenuDao menuDao;
	private ClerkService clerkService;
	private OrgService orgService;
	
	private String username;
	private Long id;
	private String newPassword;
	private String oldPassword;
	private UserClerkVO userClerkVO;
	private List<String> usernames;
	private List<Long> ids;
	private List<Property> properties;
	private List<String> authorities;
	//authorities[0] => [ USER ] authorities[1] => [ SUPERVISOR ]
	
	
	private boolean includeSuborgUsers;
	private Long orgId;
	private String name;
	private String onlineStatus;
	private Boolean enabled;
	private Date lastLoginTimeFrom;
	private Date lastLoginTimeTo;
	private boolean updateGroupInfo = false;
	
	private String homepageTitle;
	private String homepage;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
	
	protected String generateDefaultCode(UserClerkVO ucvo){
		return dateFormat.format(new Date());
	}
	
	private User getUser(String username){
		return (User) userManager.loadUserByUsername(username);
	}
	
	/**
	 * 创建用户。
	 * 
	 * 部门管理员创建的用户部门不能超出本单位，系统管理员不能创建系统管理部门的用户，
	 * 这些限制在前台已经限制，但仍有可能出现越权访问，严密的程序需要设置此项判断。
	 * 
	 * @return 操作结果信息
	 * @throws SchemeException
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_CREATEUSER", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public Model doCreateUser() throws SchemeException {
		Assert.notNull(userClerkVO);
		Model result = new Model();
		
		
		
		//暂不考虑这种情况
		//if(userClerkVO.getId() != null){//已经存在职员，只创建用户账号
			
		//}else{//职员信息不存在，既要创建职员也要创建账号
			//id自动产生
			//IdGenerator<Long> idGenerator = ((IdGeneratable<Long>) ((ClerkServiceImpl) clerkService).getClerkDao()).getIdGenerator();
			//Long clerkId = idGenerator.getNext();
			//userClerkVO.setId(clerkId);
			
			String username = userClerkVO.getUsername();
			
			if(StringUtils.isBlank(username)){
				result.addError("username", "用户名不能为空。");
				result.setMessage("userClerkVO");
				return result;
			}
			
			if(userManager.userExists(username)){
				result.addError("username", username + " 已经存在，请尝试其他用户名。");
				result.setMessage(username + " 已经存在，请尝试其他用户名。");
				return result;
			}
			
			
			if(StringUtils.isBlank(userClerkVO.getCode())){
				userClerkVO.setCode(generateDefaultCode(userClerkVO));
			}
			
			//创建职员
			Clerk clerk = new Clerk();
			copyFromUserClerkVO(clerk, userClerkVO);
			if(clerk.getPaperType() == null){
				clerk.setPaperType("0");//默认类型为身份证
			}
			//人员状态跟着用户的有效性变
			if(!userClerkVO.isEnabled()){
				clerk.setStatus((byte) 1);
			}
			
			clerk = clerkService.saveClerk(clerk);
			userClerkVO.setId(clerk.getId());
			
			//创建用户账号
			User user = buildUserFromUserClerkVO(userClerkVO);
			userManager.createUser(user);
			
			//如果同时更新角色信息，一般给部门管理员使用
			if(updateGroupInfo){
				Long groupID = userClerkVO.getGroupID();
				Assert.notNull(groupID, "必须选择用户所属角色");
				GroupManager gm = ((GroupManager) getUserManager());
				gm.addUserToGroup(user.getUsername(), groupID);
			}
			
			if(result.isSuccess()){
				result.setMessage("用户创建成功！");
			}
			
			/*
			//创建职员
			Clerk clerk = new Clerk();
			copyFromUserClerkVO(clerk, userClerkVO);
			if(clerk.getPaperType()==null){
				clerk.setPaperType("0");//默认类型为身份证
			}
			if(clerk.getPaperNo()==null||("".equals(clerk.getPaperNo()))){
				return "证件号不允许为空！";
			}
			if(clerk.getCode()==null){
				return "编号不允许为空！";
			}
			if(clerk.getName()==null){
				return "姓名不允许为空！";
			}
			clerk = clerkService.saveClerk(clerk);
			userClerkVO.setId(clerk.getId());
			*/
		//}
		
		//User user = createUserFromUserClerkVO(userClerkVO);
		//userManager.createUser(user);
		
		
//		if (userClerkVO == null) {
//			log.warn("doCreateUser 时 userClerkVO = null,创建用户失败,操作忽略 ... ");
//			return "创建用户信息失败!";
//		} else {
//			Long clerkId = userClerkVO.getId();
//			Clerk clerk;
//			if (clerkId != null) {
//				clerk = clerkService.getClerk(clerkId);
//				if (clerk != null) {
//					//创建用户
//					userManager.createUser(newUserInstance(clerkId));
//				} else {
//					//创建职员
//					clerk = newClerkInstance(clerkId);
//					clerk.setCreationTime(new Date());
//					clerk.setCreator(userClerkVO.getCreator());
//					clerkService.saveClerk(clerk);
//					//创建用户
//					userManager.createUser(newUserInstance(clerkId));				
//				}
//			} else {
//				//生成Clerk主键
//				IdGenerator<Long> idGenerator = ((IdGeneratable<Long>) ((ClerkServiceImpl) clerkService).getClerkDao()).getIdGenerator();
//				clerkId = idGenerator.getNext();
//				//创建职员
//				clerk = newClerkInstance(clerkId);
//				clerk.setCreationTime(new Date());
//				clerk.setCreator(userClerkVO.getCreator());
//				System.out.println("\n---------");
//				System.out.println(clerk.toJSONString());
//				System.out.println("\n---------");
//				clerkService.saveClerk(clerk);
//				//创建用户
//				userManager.createUser(newUserInstance(clerkId));
//			}
//		}
		//return "创建用户信息成功!";
		
		return result;
	}
	
	
	
	
//	private User newUserInstance(Long clerkId) {
//		return new User(
//				userClerkVO.getUsername(),
//				userClerkVO.getPassword(),
//				true,
//				null,
//				null,
//				true,
//				clerkId,
//				Arrays.asList(new GrantedAuthority[] { new GrantedAuthorityImpl(USER_DEFAULT_AUTHORITY) })
//				);
//	}
	
	/**
	 * 
	 * @param clerk
	 * @param userClerkVO
	 */
	protected void copyFromUserClerkVO(Clerk clerk, UserClerkVO userClerkVO){
		try {
			PropertyUtils.copyProperties(clerk, userClerkVO);
		} catch (Exception e) {
			throw new SchemeException(e);
		} 
	}
	/**
	 * 
	 * @param userClerkVO
	 * @return
	 */
	protected User buildUserFromUserClerkVO(UserClerkVO userClerkVO) {
		Assert.notNull(userClerkVO.getId());
		GrantedAuthority[] authorities = new GrantedAuthority[] { /*new GrantedAuthorityImpl(USER_DEFAULT_AUTHORITY)*/
				BaseAuthority.USER.getGrantedAuthority()
		};
		
		//FIXME
		if(userClerkVO.isManager()){
			authorities = new GrantedAuthority[] { 
				BaseAuthority.USER.getGrantedAuthority(), 
				BaseAuthority.MANAGER.getGrantedAuthority()
			};
		}
		
		if(log.isDebugEnabled()){
			String s = "";
			for(GrantedAuthority ga: authorities){
				s += ga + "\n";
			}
			log.debug("新用户权限为：" + s);
		}
		
		return new SecurityUser(
				userClerkVO.getUsername(),
				userClerkVO.getPassword(),
				true,
				null,
				null,
				true,
				userClerkVO.getId(),
				//new GrantedAuthority[] { new GrantedAuthorityImpl(USER_DEFAULT_AUTHORITY) },
				authorities,
				new HashMap<String,String>()
				);
	}
	

	/**
	 * 
	 * @param clerkId
	 * @return
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private Clerk newClerkInstance(Long clerkId) {
		Clerk clerk = new Clerk();
		clerk.setId(clerkId);
		clerk.setCode(userClerkVO.getCode());
		clerk.setClerkNo(userClerkVO.getClerkNo());
		clerk.setEntityID(userClerkVO.getEntityID());
		clerk.setEntityName(userClerkVO.getEntityName());
		clerk.setDepartmentID(userClerkVO.getDepartmentID());
		clerk.setDepartmentName(userClerkVO.getDepartmentName());
		clerk.setDepartmentNum(userClerkVO.getDepartmentNum());
		clerk.setPostNum(userClerkVO.getPostNum());
		clerk.setWorkNum(userClerkVO.getWorkNum());
		clerk.setTaskNum(userClerkVO.getTaskNum());
		clerk.setProcessNum(userClerkVO.getProcessNum());
		clerk.setName(userClerkVO.getName());
		clerk.setAlias(userClerkVO.getAlias());
		clerk.setBirthDay(userClerkVO.getBirthDay());
		clerk.setPaperType(userClerkVO.getPaperType());
		clerk.setPaperNo(userClerkVO.getPaperNo());
		clerk.setPaperElse(userClerkVO.getPaperElse());
		clerk.setNativeAddr(userClerkVO.getNativeAddr());
		clerk.setHomeTelNo(userClerkVO.getHomeTelNo());
		clerk.setHomeAddr(userClerkVO.getHomeAddr());
		clerk.setMobNo(userClerkVO.getMobNo());
		clerk.setFaxNo(userClerkVO.getFaxNo());
		clerk.setEmailAddr(userClerkVO.getEmailAddr());
		clerk.setWorkEntity(userClerkVO.getWorkEntity());
		clerk.setWorkAddr(userClerkVO.getWorkAddr());
		clerk.setTelNo(userClerkVO.getTelNo());
		clerk.setObjectID(userClerkVO.getObjectID());
		clerk.setType(userClerkVO.getType());
		clerk.setStatus(userClerkVO.getStatus());
		return clerk;
	}
	
//	private UserDetails loadUserByUsername(String username){
//		try{
//			return userManager.loadUserByUsername(username);
//		}catch(Exception e){
//		}
//		return null;
//	}
	
	/**
	 * 检查用户名是否存在。
	 * 
	 * 如果用户名可用，返回的model.success=true。
	 * @return
	 */
	public Object viewCheckUsername(){
		boolean userExists = userManager.userExists(username);
		Model model = new Model();
		model.setSuccess(!userExists);
		model.setMessage(userExists ? username + " 已经存在，请尝试其他用户名。" : "用户名有效");
		return model;
	}
	
	/**
	 * 修改指定用户的密码。
	 * 输入参数为用户名username和密码newPassword。
	 * @return 操作结果信息
	 * @throws SchemeException
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public String doChangePassword() throws SchemeException{
		User user = getUser(username);
		if(AuthUtils.isInvisible(user)){
			return "该用户不能修改密码！";
		}
		userManager.updatePassword(username, newPassword);
		return "密码修改成功！";
	}
	
	/**
	 * 修改当前登录用户的密码。
	 * 输入参数为当前用户的原密码oldPassword和新密码newPassword。
	 * 
	 * @return 操作结果信息
	 * @throws SchemeException
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_MANAGER", "ROLE_ADMINISTRATOR", "ROLE_USER"})
	public Object doChangeCurrentUserPassword() throws SchemeException{
		User user = UserHolder.getNullableUser();
		if(AuthUtils.isInvisible(user)){
			Model model = new Model();
			model.setMessage(false, "该用户不能修改密码！", null);
			return model;
		}
		
		try{
			getUserManager().changePassword(oldPassword, newPassword);
			return "密码修改成功！";
		}catch(AccessDeniedException e){
			Model model = new Model();
			model.setMessage(false, "原密码错误！", null);
			return model;
		}catch(AuthenticationException e){
			Model model = new Model();
			model.setMessage(false, "原密码无效！", null);
			return model;
		}catch(Exception e){
			Model model = new Model();
			model.setException(e);
			return model;
		}
	}
	
	/**
	 * 获取当前登录用户的用户信息，不需要输入参数。
	 * 
	 * @return UserClerkVO 对象
	 */
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR", "ROLE_USER"})
	public UserClerkVO viewGetCurrentUserInfo(){
		User user = UserHolder.getUser();
		Clerk clerk = UserClerkHolder.getNullableClerk();//Clerk();
		
		//内部用户
		if(clerk == null){
			clerk = new Clerk();
			clerk.setName(user.getUsername());
		}
		return new UserClerkVO(user, clerk);
	}
	
	/**
	 * 修改当前用户的信息。
	 * 输入参数 UserClerkVO 对象.
	 * 
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_ADMINISTRATOR", "ROLE_MANAGER", "ROLE_USER", "ROLE_SYS"})
	public Object doUpdateCurrentUserInfo(){
		Assert.notNull(userClerkVO, "提交数据不正确。");
		User user = UserHolder.getUser();
		Clerk clerk = UserClerkHolder.getNullableClerk();
		//内部用户
		if(user != null && clerk == null || AuthUtils.isInternalUser(user)){
			throw new BusinessException("该用户不能被修改。");
		}
		if(AuthUtils.isInvisible(clerk)){
			throw new BusinessException("该用户不能被修改。");
		}
		if(clerk != null) {
			if(userClerkVO.getTelNo() != null) {
				clerk.setTelNo(userClerkVO.getTelNo());
			}
			if(userClerkVO.getFaxNo() != null) {
				clerk.setFaxNo(userClerkVO.getFaxNo());
			}
			if(userClerkVO.getPaperType() != null) {
				clerk.setPaperType(userClerkVO.getPaperType());
			}
			if(userClerkVO.getPaperNo() != null) {
				clerk.setPaperNo(userClerkVO.getPaperNo());
			}
			if(userClerkVO.getHomeAddr() != null) {
				clerk.setHomeAddr(userClerkVO.getHomeAddr());
			}
			if(userClerkVO.getWorkAddr() != null) {
				clerk.setWorkAddr(userClerkVO.getWorkAddr());
			}
			if(userClerkVO.getMobNo() != null) {
				clerk.setMobNo(userClerkVO.getMobNo());
			}
			if(userClerkVO.getEmailAddr() != null) {
				clerk.setEmailAddr(userClerkVO.getEmailAddr());
			}
			if(userClerkVO.getHomeTelNo() != null) {
				clerk.setHomeTelNo(userClerkVO.getHomeTelNo());
			}
			if(Byte.MAX_VALUE >= userClerkVO.getPaperElse() && Byte.MIN_VALUE <= userClerkVO.getPaperElse()) {
				clerk.setPaperElse(userClerkVO.getPaperElse());
			}
			if(userClerkVO.getNativeAddr() != null) {
				clerk.setNativeAddr(userClerkVO.getNativeAddr());
			}
			//不是输入的，取当期登陆用户的userId
//			if(userClerkVO.getModifier() != null) {
//				clerk.setModifier(userClerkVO.getModifier());
//			}
			
			
			clerk.setModificationTime(new Date());
			clerkService.updateClerk(clerk);
			return "修改用户信息成功!";
		} else {
			log.warn("doUpdateCurrentUserInfo 时获取 ClerkId = " + userClerkVO.getId() + " 的 Clerk 对象不存在,修改当前用户信息失败 ... ");			
			return "修改用户信息失败!";
		}
	}
	
	/**
	 * 修改指定用户的信息。
	 * 
	 * 输入参数 username， UserClerkVO 对象.
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public Object doUpdateUserInfo(){
		LiveUser user = userManager.loadLiveUserByUsername(userClerkVO.getUsername());
		if(AuthUtils.isInvisible(user)){
			throw new SchemeException("该用户不能被修改。");
		}
		
		
		Long userId = user.getUserId();
		Clerk clerk = clerkService.getClerk(userId);
		UserClerkVO vo = userClerkVO;
		
		boolean isUserChanged = false;
		
		boolean isManager = userClerkVO.isManager();//FIXME userClerkVO.isManager(); false
		boolean isUserAuthoritiesChanged = false;
		List<String> roles = new ArrayList<String>();
		GrantedAuthority[] auths = user.getAuthorities();
		for(GrantedAuthority ga: auths){
			roles.add(ga.getAuthority());
		}

		log.debug("原有权限：" + roles);
		log.debug("当前操作是否将用户设置成部门管理员：" + isManager);
		
		if(roles.contains(BaseAuthority.MANAGER.name())){
			if(isManager){
				//same
			}else{
				isUserAuthoritiesChanged = true;
				roles.remove(BaseAuthority.MANAGER.name());
			}
		}else{
			if(isManager){
				isUserAuthoritiesChanged = true;
				roles.add(BaseAuthority.MANAGER.name());
			}else{
				//same
			}
		}
		
		//用户权限改变了
		if(isUserAuthoritiesChanged){
			auths = new GrantedAuthority[roles.size()];
			for(int i = 0 ; i < roles.size(); i++){
				auths[i] = new GrantedAuthorityImpl(roles.get(i));
			}
			
			user.setAuthorities(auths);
			log.debug("处理后权限：" + roles);
			isUserChanged = true;
		}
		
		//更新
		if(user.isEnabled() != userClerkVO.isEnabled()){
			user.setEnabled(userClerkVO.isEnabled());
			//userManager.updateLiveUser(user);
			isUserChanged = true;
		}
		
		//如果用户改变了，保存修改
		if(isUserChanged){
			userManager.updateUser(user);
		}

		clerk.setPaperNo(vo.getPaperNo());
		clerk.setName(vo.getName());
		clerk.setCode(vo.getCode());
		clerk.setNativeAddr(vo.getNativeAddr());
		clerk.setBirthDay(vo.getBirthDay());
		clerk.setEntityID(vo.getEntityID());
		clerk.setEntityName(vo.getEntityIDLabel());
		clerk.setClerkNo(vo.getClerkNo());
		clerk.setEmailAddr(vo.getEmailAddr());
		clerk.setTelNo(vo.getTelNo());
		clerk.setFaxNo(vo.getFaxNo());
		clerk.setHomeAddr(vo.getHomeAddr());
		clerk.setMobNo(vo.getMobNo());
		clerk.setHomeTelNo(vo.getHomeTelNo());
		clerk.setWorkAddr(vo.getWorkAddr());
		clerk.setPosition(vo.getPosition());
		clerk.setPrivateMobNo(vo.getPrivateMobNo());
		clerk.setDisplayOrder(vo.getDisplayOrder());
		//用户有效时，状态为0，否则为1
		clerk.setStatus((byte) (userClerkVO.isEnabled() ? 0 : 1));
		
		clerkService.updateClerk(clerk);
		
		//如果同时更新角色信息，一般给部门管理员使用
		if(updateGroupInfo){
			Long groupID = userClerkVO.getGroupID();
			Assert.notNull(groupID, "必须选择用户所属角色");
			boolean hasGroup = false;
			GroupManager gm = ((GroupManager) getUserManager());
			List<Group> list = gm.findGroupsOfUser(userClerkVO.getUsername());
			for(Group g: list){
				if(g.getId().longValue() != groupID.longValue()){
					log.debug("去除原角色：" + g.getName());
					gm.removeUserFromGroup(userClerkVO.getUsername(), g.getId());
				}else{
					hasGroup = true;
				}
			}
			//如果原角色中没有当前要添加的角色，则添加
			if(!hasGroup){
				log.debug("添加新角色：" + groupID);
				gm.addUserToGroup(userClerkVO.getUsername(), groupID);
			}
		}
		
		return "用户信息已更新！";
//		
//		
//		if(userClerkVO == null && userClerkVO.getId() == null) {
//			log.warn("doUpdateUserInfo 时 userClerkVO = null 或 ClerkId = null,修改指定用户信息失败,操作忽略 ... ");
//			return "修改用户信息失败!";
//		} else {
//			User user = (User) userManager.loadUserByUsername(username);
//			if(user != null && user.getUserId() != null) {
//				Clerk clerk = clerkService.getClerk(user.getUserId());
//				
//				
//				
////				clerk = newClerkInstance(clerk.getId());
////				clerk.setModificationTime(new Date());
////				clerk.setModifier(userClerkVO.getModifier());
//				clerkService.updateClerk(clerk);
//			} else {
//				log.warn("doUpdateUserInfo 时获取 User = null 或 User.UserId = null,修改指定用户信息失败,操作忽略 ... ");
//				return "修改用户信息失败!";
//			}
//		}
//		return null;
	}
	
	/**
	 * 根据组ID查询用户信息
	 * 
	 * @return List<UserClerkVO>
	 */
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public List<UserClerkVO> viewFindUsersByGroupId() {
		List<User> users = ((GroupManager)userManager).findUsersInGroup(id);
		List<UserClerkVO> result = new ArrayList<UserClerkVO>();
		if(users != null && !users.isEmpty()) {
			UserClerkVO ucv;
			for(User user : users) {
				//user.setPassword("[PROTECTED]");
				ucv = new UserClerkVO(user, clerkService.getClerk(user.getUserId()));
				result.add(ucv);
			}
		} else {
			log.warn("findUsersByGroupId 时 groupId = " + id + " 的 User 对象为空 ,return  List<UserClerkVO> = empty ...");
		}
		return result;
	}
	
	/**
	 * 根据用户名查询组信息。
	 * 
	 * 输入参数为用户名，username。
	 * 
	 * @return List<Group>
	 */
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public List<Group> viewFindGroupsByUsername() {
		List<Group> list = wrapGroups(((GroupManager)userManager).findGroupsOfUser(username));
		if(log.isDebugEnabled()){
			for(Group p: list){
				System.out.println("[DEBUG] group: " + p);
			}
		}
		return list;
	}
	
	private List<Group> wrapGroups(List<Group> gps){
		List<Group> list = new ArrayList<Group>();
		for(Group g : gps){
			Group group = new Group(g.getId(), g.getName());
			list.add(group);
		}
		return list;
	}
	
	/**
	 * 查询指定用户可加入的用户组。
	 * 
	 * 输入参数为用户名，username。
	 * 
	 * 部门管理员也可以使用这个功能，但查询出来的用户组（角色）只有本部门的。
	 * 
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public List<Group> viewFindCandidateGroupsByUsername(){
		GroupManager gm = ((GroupManager) getUserManager());
		List<Group> groups = findAllGroups(gm, username);//gm.findGroups();
		List<Group> candidate = new ArrayList<Group>();
		List<Group> userGroups = gm.findGroupsOfUser(username);
		boolean isAdmin = AuthUtils.isAdmin();
		
		for(Group g: groups){
			if(!containsGroup(userGroups, g)){
				//临时处理，将来会控制更严格
//				if(isAdministrator && g.getId().longValue() <= 0){
//					log.debug("非超级管理员不能查看0的角色");
//					continue;
//				}
				//除了RFSA其它人不能查看系统角色
				if(!isAdmin && AuthUtils.isSystemGroup(g)){
					log.debug("非RFSA不能查询该角色：" + g.getName());
					continue;
				}
				
				candidate.add(g);
			}else{
				if(log.isDebugEnabled()){
					log.debug("===>><><><><<>::已经存在：" + g);
				}
			}
		}
		if(log.isDebugEnabled()){
			for(Group p: candidate){
				System.out.println("[BEBUG] candidate group: " + p);
			}
			for(Group p: userGroups){
				System.out.println("[BEBUG] user group: " + p);
			}
		}
		return wrapGroups(candidate);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public List<Group> viewFindAllGroups(){
		GroupManager gm = ((GroupManager) getUserManager());
		List<Group> groups = gm.findGroups();
		boolean isAdmin = AuthUtils.isAdmin();
		List<Group> candidate = new ArrayList<Group>();
		for(Group g: groups){
			//除了RFSA其它人不能查看系统角色
			if(!isAdmin && AuthUtils.isSystemGroup(g)){
				log.debug("非超管不能查询该角色：" + g.getName());
				continue;
			}
			
			candidate.add(g);
		}
		return wrapGroups(candidate);
	}
	
	/**
	 * 查询指定用户涉及的所有角色。如果调用者是部门管理员，则只能
	 * 查询本单位的角色。
	 * 
	 * @param gm
	 * @param username
	 * @return
	 */
	private List<Group> findAllGroups(GroupManager gm, String username){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//判断当前登录用户是否部门管理员
		boolean isManager = SecurityUtils.isGranted(authentication, "ROLE_MANAGER");
		return isManager ? findOrgRolesOfUser(username) : gm.findGroups();
	}
	
	private List<Group> findOrgRolesOfUser(String username){
		User user = (User) userManager.loadUserByUsername(username);
		Clerk clerk = clerkService.getClerk(user.getUserId());
		Long orgId = clerk.getEntityID();
		return orgService.findRolesByOrgId(orgId);
	}
	
	
	private static boolean containsGroup(List<Group> userGroups, Group group){
		for(Group g: userGroups){
			if(g.getId().longValue() == group.getId().longValue()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 给指定用户增加用户组。
	 * 
	 * 输入参数是username,用户组id集合ids
	 * 
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public Object doAddGroupsToUser(){
		Assert.notNull(username, "用户名不能为空");
		Assert.notEmpty(ids, "必须提交要加入的用户组");
		
		User user = getUser(username);
		if(AuthUtils.isInvisible(user)){
			throw new SchemeException("该用户不能被修改。");
		}
		
		GroupManager gm = ((GroupManager) getUserManager());
		for(Long id: ids){
			gm.addUserToGroup(username, id);
		}
		
		return "成功添加了 "  + ids.size() + " 个用户组";
	}
	
	
	/**
	 * 删除指定用户的制定用户组。
	 * 
	 *  输入参数是username,用户组id集合ids
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public Object doDeleteGroupsOfUser(){
		Assert.notNull(username, "用户名不能为空");
		Assert.notEmpty(ids, "必须提交要加入的用户组");
		
		User user = getUser(username);
		if(AuthUtils.isInvisible(user)){
			throw new SchemeException("该用户不能被修改。");
		}
		
		GroupManager gm = ((GroupManager) getUserManager());
		for(Long id: ids){
			gm.removeUserFromGroup(username, id);
		}
		
		return "成功删除了 "  + ids.size() + " 个用户组";
	}
		
	
//	/**
//	 * 查询用户组所拥有权限。
//	 * 输入参数组id.
//	 * 
//	 * @return List<Menu>
//	 * @deprecated
//	 */
//	public List<Menu> viewFindGroupOwnAuthoritiesByGroupId() {
//		return menuDao.findGroupOwnAuthorityMenusByGroupId(id);
//		//return ((GroupManager)userManager).findGroupAuthorities(id);
//	}
//	
//	/**
//	 *  查询用户组没有拥有的权限
//	 * 
//	 * @return List<Menu>
//	 * @deprecated
//	 */
//	public List<Menu> viewFindGroupNotOwnAuthoritiesByGroupId() {
//		return menuDao.findGroupNotOwnAuthoritiesByGroupId(id);
//	}
	
	
	/**
	 * 根据用户名查询用户详细信息。
	 * 
	 * 输入参数为用户名username。
	 * @return UserClerkVO 对象
	 */
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR", "ROLE_USER"})
	public UserClerkVO viewGetUserInfo(){
		User user = (User) userManager.loadUserByUsername(username);
		Clerk clerk = clerkService.getClerk(user.getUserId());
		UserClerkVO vo = new UserClerkVO(user, clerk);
		//FIXME
		/*
		if(SecurityUtils.isGranted(user, AuthUtils.ROLE_MANAGER)
				|| SecurityUtils.isGranted(user, "MANAGER")){
			vo.setManager(true);
		}*/
		
		
		//2013-06-25 该部分功能已经转移到 UserClerkVO中，见 UserClerkVO#tryLoadGroupInfo()
//		//部门管理员查询用户时，同时查询用户的第一个角色信息
//		if(AuthUtils.isGranted("ROLE_MANAGER")){
//			log.debug("查询用户的第一个角色信息：" + username);
//			//设置用户的第一个角色信息
//			List<Group> groupsOfUser = ((GroupManager) userManager).findGroupsOfUser(username);
//			if(groupsOfUser != null && !groupsOfUser.isEmpty()){
//				Group group = groupsOfUser.iterator().next();
//				vo.setGroupID(group.getId());
//				vo.setGroupName(group.getName());
//			}
//		}
		
		
		return vo;
	}
	
	/**
	 * 删除用户。
	 * 
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_ADMINISTRATOR"})
	public Model doDeleteUsers(){
		Model m = new Model();
		
		if(usernames == null || usernames.isEmpty()){
			m.setMessage(false, "请选择要删除的用户", null);
			return m;
		}
		
		m.setItemCount(0);
		m.setLimit(0);
		List<Long> clerkIds = new ArrayList<Long>();
		
		//删除users
		for(String localusername: usernames){
			deleteUser(localusername, m, clerkIds);
		}
		//删除clerks
		clerkService.deleteClerks(clerkIds);
		
		
		
		//处理结果
		String msg = "";
		if(m.getItemCount() == 0){
			msg = "删除失败！";
			m.setSuccess(false);
		}else{
			msg = "成功删除 " + m.getItemCount() + " 个用户！";
		}
		
		if(m.getLimit() > 0){
			msg += "未删除 " + m.getLimit() + " 个系统用户。";
		}
		m.setMessage(msg);
		
		return m;
	}
	
	private void deleteUser(String localusername, Model m, List<Long> clerkIds){
		if(localusername != null){
			User user = getUser(localusername);//(User) userManager.loadUserByUsername(localusername);
			long userId = user.getUserId();
			if(AuthUtils.isSystemUser(user) || AuthUtils.isInvisible(user)){
				//m.setMessage("不能删除系统用户");
				m.setLimit(m.getLimit() + 1);
			}else{
//			if("rfsa".equalsIgnoreCase(localusername) || "sys".equalsIgnoreCase(localusername)){
//				//m.setMessage("不能删除系统用户");
//				m.setLimit(m.getLimit() + 1);
//			}else{
//				User user = (User) userManager.loadUserByUsername(localusername);
				clerkIds.add(userId/*user.getUserId()*/);
				userManager.deleteUser(localusername);
				m.setItemCount(m.getItemCount() + 1);
			}
		}
	}
	
	
	
	/////////////////////////////////////////////////////////////
	// 用户属性
	/////////////////////////////////////////////////////////////
	
	/**
	 * 返回用户的属性。包含未设置值的属性。
	 * 
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public List<Property> viewFindUserProperties(){
		LiveUser user = userManager.loadLiveUserByUsername(username);
		Map<String, String> props = user.getProperties();
		Set<String> names = Property.getNames();
		List<Property> list = new ArrayList<Property>();
		for(String name: names){
			String value = props.get(name);
			Property p = new Property(name, value);
			list.add(p);
		}
		return list;
	}
	
	/**
	 * 重设用户的属性。
	 * 
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_ADMINISTRATOR", "ROLE_MANAGER"})
	public Object doUpdateUserProperties(){
		LiveUser user = userManager.loadLiveUserByUsername(username);
		if(AuthUtils.isInvisible(user)){
			throw new SchemeException("该用户不能被修改。");
		}
		
		Map<String, String> props = new HashMap<String, String>();
		for(Property p: properties){
			if(StringUtils.isNotBlank(p.getValue())){
				props.put(p.getName(), p.getValue());
			}
		}
		
		user.setProperties(props);
		userManager.updateUser(user);
		return "保存用户属性成功！";
	}
	
	public Object doUpdateCurrentUserHomepage(){
		User user = UserHolder.getNullableUser();
		if(AuthUtils.isInvisible(user)){
			Model model = new Model();
			model.setMessage(false, "该用户不能更新主页！", null);
			return model;
		}
		Assert.notNull(homepage, "参数不完整");
		Assert.notNull(homepageTitle, "参数不完整");
		
		Map<String, String> map = user.getProperties();
		if(map == null){
			map = new HashMap<String, String>();
		}
		map.put("homepage", homepage);
		map.put("homepageTitle", homepageTitle);

		LiveUser liveUser = userManager.loadLiveUserByUsername(user.getUsername());
		liveUser.setProperties(map);
		userManager.updateUser(user);
		return "主页设置成功";
	}
	
	/////////////////////////////////////////////////////////////
	// 用户权限
	/////////////////////////////////////////////////////////////
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public List<Authority> viewFindUserAuthorities(){	
		return baseAuthoritiesToAuthorities(findUserAuthorities());
	}
	
	private List<BaseAuthority> findUserAuthorities(){
		LiveUser user = userManager.loadLiveUserByUsername(username);
		GrantedAuthority[] authorities = user.getAuthorities();
		List<BaseAuthority> uas = new ArrayList<BaseAuthority>();
		for(GrantedAuthority ga: authorities){
			String role = ga.getAuthority();
			//IlleagalArgumentException may be
			BaseAuthority a = BaseAuthority.valueOf(role);
			uas.add(a);
		}
		
		return uas;
	}
	
	static List<Authority> baseAuthoritiesToAuthorities(List<BaseAuthority> list){
		List<Authority> result = new ArrayList<Authority>();
		for(BaseAuthority ba: list){
			Authority a = new Authority(ba);
			result.add(a);
		}
		return result;
	}
	
	/**
	 * 查询用户可分配的权限。
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public List<Authority> viewFindCandidateUserAuthorities(){
		//用户已经分配的权限
		List<BaseAuthority> userAuthorities = findUserAuthorities();
		List<BaseAuthority> all = AuthUtils.getBaseAuthorities();//BaseAuthority.values();
		List<BaseAuthority> candidate = new ArrayList<BaseAuthority>();
		for(BaseAuthority a: all){
			if(!userAuthorities.contains(a)){
				candidate.add(a);
			}
		}
		return baseAuthoritiesToAuthorities(candidate);
	}
	
	/**
	 * 
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_ADMINISTRATOR", "ROLE_MANAGER"})
	public Object doUpdateUserAuthorities(){
		//Assert.notEmpty(authorities);
		LiveUser user = userManager.loadLiveUserByUsername(username);
		if(AuthUtils.isInvisible(user)){
			throw new SchemeException("该用户不能被修改。");
		}
		//System.out.println("================> " + authorities);
		if(log.isDebugEnabled()){
			log.debug("设置用户 " + username + "的权限 " + authorities);
		}
		
		if(authorities == null || authorities.isEmpty()){
			user.setAuthorities(new GrantedAuthority[]{
					new GrantedAuthorityImpl(BaseAuthority.USER.name())});
		}else{
			//make sure contains 'USER' authority
			String userAuth = BaseAuthority.USER.name();
			if(!authorities.contains(userAuth)){
				authorities.add(userAuth);
			}
			GrantedAuthority[] auths = new GrantedAuthority[authorities.size()];
			for(int i = 0  ; i < auths.length ; i++){
				String auth = authorities.get(i);
				auths[i] = new GrantedAuthorityImpl(auth);
			}
			user.setAuthorities(auths);
		}
		
		userManager.updateUser(user);
		
		return "成功更新了用户权限！";
	}
	
	
	

	
	
	
	
	
	
	
	
	
	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return userManager;
	}
	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	public UserClerkVO getUserClerkVO() {
		return userClerkVO;
	}

	public void setUserClerkVO(UserClerkVO userClerkVO) {
		this.userClerkVO = userClerkVO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public MenuDao getMenuDao() {
//		return menuDao;
//	}
//
//	public void setMenuDao(MenuDao menuDao) {
//		this.menuDao = menuDao;
//	}


	/**
	 * @return the usernames
	 */
	public List<String> getUsernames() {
		return usernames;
	}



	/**
	 * @param usernames the usernames to set
	 */
	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	/**
	 * @return the ids
	 */
	public List<Long> getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	/**
	 * @return the properties
	 */
	public List<Property> getProperties() {
		return properties;
	}


	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}


	/**
	 * @return the authorities
	 */
	public List<String> getAuthorities() {
		return authorities;
	}


	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}


	/**
	 * @return the orgService
	 */
	public OrgService getOrgService() {
		return orgService;
	}




	/**
	 * @param orgService the orgService to set
	 */
	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}
	

	public boolean isIncludeSuborgUsers() {
		return includeSuborgUsers;
	}

	public void setIncludeSuborgUsers(boolean includeSuborgUsers) {
		this.includeSuborgUsers = includeSuborgUsers;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLastLoginTimeFrom() {
		return lastLoginTimeFrom;
	}

	public void setLastLoginTimeFrom(Date lastLoginTimeFrom) {
		this.lastLoginTimeFrom = lastLoginTimeFrom;
	}

	public Date getLastLoginTimeTo() {
		return lastLoginTimeTo;
	}

	public void setLastLoginTimeTo(Date lastLoginTimeTo) {
		this.lastLoginTimeTo = lastLoginTimeTo;
	}
	
	/**
	 * @return the updateGroupInfo
	 */
	public boolean isUpdateGroupInfo() {
		return updateGroupInfo;
	}

	/**
	 * @param updateGroupInfo the updateGroupInfo to set
	 */
	public void setUpdateGroupInfo(boolean updateGroupInfo) {
		this.updateGroupInfo = updateGroupInfo;
	}

	/**
	 * 根据基本查询条件查询指定部门的用户。
	 * 
	 * @return
	 */
	//@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public Object viewFindUserClerkVOs(){
		CriterionBuffer buffer = new CriterionBuffer();
		if(StringUtils.isNotBlank(name)){
			log.debug("查询姓名：" + name);
			buffer.and(Restrictions.like("b.name", "%" + name + "%"));
		}
		if(StringUtils.isNotBlank(username)){
			log.debug("查询用户名：" + username);
			buffer.and(Restrictions.like("a.username", "%" + username + "%"));
		}
		if(StringUtils.isNotBlank(onlineStatus)){
			log.debug("查询在线状态：" + onlineStatus);
			buffer.and(Restrictions.eq("a.onlineStatus", onlineStatus));
		}
		if(enabled != null){
			log.debug("查询有效状态：" + enabled);
			buffer.and(Restrictions.eq("a.enabled", enabled));
		}
		if(lastLoginTimeFrom != null){
			log.debug("最后登录时间（开始）：" + lastLoginTimeFrom);
			buffer.and(Restrictions.ge("a.lastLoginTime", lastLoginTimeFrom));
		}
		if(lastLoginTimeTo != null){
			log.debug("最后登录时间（截止）：" + lastLoginTimeTo);
			buffer.and(Restrictions.le("a.lastLoginTime", lastLoginTimeTo));
		}
		
		return clerkService.findUserClerkVOs(buffer.toCriterion(), null, orgId, includeSuborgUsers);
	}

	/**
	 * 查询当前（登录者，通常是部门管理员）的单位或者指定单位的所有角色选择数据。
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public List<Labelable> viewFindOrgGroupsLabelable(){
		Long theOrgId = orgId;
		if(theOrgId == null){
			Clerk clerk = UserClerkHolder.getClerk();
			theOrgId = clerk.getEntityID();
		}
		List<Group> list = orgService.findRolesByOrgId(theOrgId);
		
//		Clerk clerk = UserClerkHolder.getClerk();
//		Long orgId = clerk.getEntityID();
//		List<Group> list = orgService.findRolesByOrgId(orgId);
		List<Labelable> beans = Lists.newArrayList();
		for(Group g: list){
			beans.add(g);
		}
		Collections.sort(beans, new Comparator<Labelable>(){
			public int compare(Labelable o1, Labelable o2) {
				return o1.getLabel().compareTo(o2.getLabel());
			}
		});
		return beans;
	}
	
	/**
	 * @return the homepageTitle
	 */
	public String getHomepageTitle() {
		return homepageTitle;
	}

	/**
	 * @param homepageTitle the homepageTitle to set
	 */
	public void setHomepageTitle(String homepageTitle) {
		this.homepageTitle = homepageTitle;
	}

	/**
	 * @return the homepage
	 */
	public String getHomepage() {
		return homepage;
	}

	/**
	 * @param homepage the homepage to set
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public static void main(String[] args) throws ValidationException{
		ObjectFactory.setObjectFactory(new ObjectFactory());
		List<Validator> list = ActionValidatorManagerFactory.getInstance().getValidators(UserScheme.class, "doCreateUser");
		System.out.println(list);
		UserScheme us = new UserScheme();
		us.setUsername("aaaaaaa");
		ActionValidatorManagerFactory.getInstance().validate(us, "doCreateUser");
		System.out.println(us.hasActionErrors());
		System.out.println(us.hasErrors());
		System.out.println(us.hasActionMessages());
		System.out.println(us.hasFieldErrors());
		Map<?, ?> map = us.getFieldErrors();
		System.out.println(map);
	}

}
