/*
 * $Id: UserScheme.java 6333 2013-12-11 06:23:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * �û�����
 * 
 * ע�⣺�������漰����Group����ʵ���� Role����ɫ������˼��
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
	 * �����û���
	 * 
	 * ���Ź���Ա�������û����Ų��ܳ�������λ��ϵͳ����Ա���ܴ���ϵͳ�����ŵ��û���
	 * ��Щ������ǰ̨�Ѿ����ƣ������п��ܳ���ԽȨ���ʣ����ܵĳ�����Ҫ���ô����жϡ�
	 * 
	 * @return ���������Ϣ
	 * @throws SchemeException
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_CREATEUSER", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public Model doCreateUser() throws SchemeException {
		Assert.notNull(userClerkVO);
		Model result = new Model();
		
		
		
		//�ݲ������������
		//if(userClerkVO.getId() != null){//�Ѿ�����ְԱ��ֻ�����û��˺�
			
		//}else{//ְԱ��Ϣ�����ڣ���Ҫ����ְԱҲҪ�����˺�
			//id�Զ�����
			//IdGenerator<Long> idGenerator = ((IdGeneratable<Long>) ((ClerkServiceImpl) clerkService).getClerkDao()).getIdGenerator();
			//Long clerkId = idGenerator.getNext();
			//userClerkVO.setId(clerkId);
			
			String username = userClerkVO.getUsername();
			
			if(StringUtils.isBlank(username)){
				result.addError("username", "�û�������Ϊ�ա�");
				result.setMessage("userClerkVO");
				return result;
			}
			
			if(userManager.userExists(username)){
				result.addError("username", username + " �Ѿ����ڣ��볢�������û�����");
				result.setMessage(username + " �Ѿ����ڣ��볢�������û�����");
				return result;
			}
			
			
			if(StringUtils.isBlank(userClerkVO.getCode())){
				userClerkVO.setCode(generateDefaultCode(userClerkVO));
			}
			
			//����ְԱ
			Clerk clerk = new Clerk();
			copyFromUserClerkVO(clerk, userClerkVO);
			if(clerk.getPaperType() == null){
				clerk.setPaperType("0");//Ĭ������Ϊ���֤
			}
			//��Ա״̬�����û�����Ч�Ա�
			if(!userClerkVO.isEnabled()){
				clerk.setStatus((byte) 1);
			}
			
			clerk = clerkService.saveClerk(clerk);
			userClerkVO.setId(clerk.getId());
			
			//�����û��˺�
			User user = buildUserFromUserClerkVO(userClerkVO);
			userManager.createUser(user);
			
			//���ͬʱ���½�ɫ��Ϣ��һ������Ź���Աʹ��
			if(updateGroupInfo){
				Long groupID = userClerkVO.getGroupID();
				Assert.notNull(groupID, "����ѡ���û�������ɫ");
				GroupManager gm = ((GroupManager) getUserManager());
				gm.addUserToGroup(user.getUsername(), groupID);
			}
			
			if(result.isSuccess()){
				result.setMessage("�û������ɹ���");
			}
			
			/*
			//����ְԱ
			Clerk clerk = new Clerk();
			copyFromUserClerkVO(clerk, userClerkVO);
			if(clerk.getPaperType()==null){
				clerk.setPaperType("0");//Ĭ������Ϊ���֤
			}
			if(clerk.getPaperNo()==null||("".equals(clerk.getPaperNo()))){
				return "֤���Ų�����Ϊ�գ�";
			}
			if(clerk.getCode()==null){
				return "��Ų�����Ϊ�գ�";
			}
			if(clerk.getName()==null){
				return "����������Ϊ�գ�";
			}
			clerk = clerkService.saveClerk(clerk);
			userClerkVO.setId(clerk.getId());
			*/
		//}
		
		//User user = createUserFromUserClerkVO(userClerkVO);
		//userManager.createUser(user);
		
		
//		if (userClerkVO == null) {
//			log.warn("doCreateUser ʱ userClerkVO = null,�����û�ʧ��,�������� ... ");
//			return "�����û���Ϣʧ��!";
//		} else {
//			Long clerkId = userClerkVO.getId();
//			Clerk clerk;
//			if (clerkId != null) {
//				clerk = clerkService.getClerk(clerkId);
//				if (clerk != null) {
//					//�����û�
//					userManager.createUser(newUserInstance(clerkId));
//				} else {
//					//����ְԱ
//					clerk = newClerkInstance(clerkId);
//					clerk.setCreationTime(new Date());
//					clerk.setCreator(userClerkVO.getCreator());
//					clerkService.saveClerk(clerk);
//					//�����û�
//					userManager.createUser(newUserInstance(clerkId));				
//				}
//			} else {
//				//����Clerk����
//				IdGenerator<Long> idGenerator = ((IdGeneratable<Long>) ((ClerkServiceImpl) clerkService).getClerkDao()).getIdGenerator();
//				clerkId = idGenerator.getNext();
//				//����ְԱ
//				clerk = newClerkInstance(clerkId);
//				clerk.setCreationTime(new Date());
//				clerk.setCreator(userClerkVO.getCreator());
//				System.out.println("\n---------");
//				System.out.println(clerk.toJSONString());
//				System.out.println("\n---------");
//				clerkService.saveClerk(clerk);
//				//�����û�
//				userManager.createUser(newUserInstance(clerkId));
//			}
//		}
		//return "�����û���Ϣ�ɹ�!";
		
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
			log.debug("���û�Ȩ��Ϊ��" + s);
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
	 * ����û����Ƿ���ڡ�
	 * 
	 * ����û������ã����ص�model.success=true��
	 * @return
	 */
	public Object viewCheckUsername(){
		boolean userExists = userManager.userExists(username);
		Model model = new Model();
		model.setSuccess(!userExists);
		model.setMessage(userExists ? username + " �Ѿ����ڣ��볢�������û�����" : "�û�����Ч");
		return model;
	}
	
	/**
	 * �޸�ָ���û������롣
	 * �������Ϊ�û���username������newPassword��
	 * @return ���������Ϣ
	 * @throws SchemeException
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public String doChangePassword() throws SchemeException{
		User user = getUser(username);
		if(AuthUtils.isInvisible(user)){
			return "���û������޸����룡";
		}
		userManager.updatePassword(username, newPassword);
		return "�����޸ĳɹ���";
	}
	
	/**
	 * �޸ĵ�ǰ��¼�û������롣
	 * �������Ϊ��ǰ�û���ԭ����oldPassword��������newPassword��
	 * 
	 * @return ���������Ϣ
	 * @throws SchemeException
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_MANAGER", "ROLE_ADMINISTRATOR", "ROLE_USER"})
	public Object doChangeCurrentUserPassword() throws SchemeException{
		User user = UserHolder.getNullableUser();
		if(AuthUtils.isInvisible(user)){
			Model model = new Model();
			model.setMessage(false, "���û������޸����룡", null);
			return model;
		}
		
		try{
			getUserManager().changePassword(oldPassword, newPassword);
			return "�����޸ĳɹ���";
		}catch(AccessDeniedException e){
			Model model = new Model();
			model.setMessage(false, "ԭ�������", null);
			return model;
		}catch(AuthenticationException e){
			Model model = new Model();
			model.setMessage(false, "ԭ������Ч��", null);
			return model;
		}catch(Exception e){
			Model model = new Model();
			model.setException(e);
			return model;
		}
	}
	
	/**
	 * ��ȡ��ǰ��¼�û����û���Ϣ������Ҫ���������
	 * 
	 * @return UserClerkVO ����
	 */
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR", "ROLE_USER"})
	public UserClerkVO viewGetCurrentUserInfo(){
		User user = UserHolder.getUser();
		Clerk clerk = UserClerkHolder.getNullableClerk();//Clerk();
		
		//�ڲ��û�
		if(clerk == null){
			clerk = new Clerk();
			clerk.setName(user.getUsername());
		}
		return new UserClerkVO(user, clerk);
	}
	
	/**
	 * �޸ĵ�ǰ�û�����Ϣ��
	 * ������� UserClerkVO ����.
	 * 
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_ADMINISTRATOR", "ROLE_MANAGER", "ROLE_USER", "ROLE_SYS"})
	public Object doUpdateCurrentUserInfo(){
		Assert.notNull(userClerkVO, "�ύ���ݲ���ȷ��");
		User user = UserHolder.getUser();
		Clerk clerk = UserClerkHolder.getNullableClerk();
		//�ڲ��û�
		if(user != null && clerk == null || AuthUtils.isInternalUser(user)){
			throw new BusinessException("���û����ܱ��޸ġ�");
		}
		if(AuthUtils.isInvisible(clerk)){
			throw new BusinessException("���û����ܱ��޸ġ�");
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
			//��������ģ�ȡ���ڵ�½�û���userId
//			if(userClerkVO.getModifier() != null) {
//				clerk.setModifier(userClerkVO.getModifier());
//			}
			
			
			clerk.setModificationTime(new Date());
			clerkService.updateClerk(clerk);
			return "�޸��û���Ϣ�ɹ�!";
		} else {
			log.warn("doUpdateCurrentUserInfo ʱ��ȡ ClerkId = " + userClerkVO.getId() + " �� Clerk ���󲻴���,�޸ĵ�ǰ�û���Ϣʧ�� ... ");			
			return "�޸��û���Ϣʧ��!";
		}
	}
	
	/**
	 * �޸�ָ���û�����Ϣ��
	 * 
	 * ������� username�� UserClerkVO ����.
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public Object doUpdateUserInfo(){
		LiveUser user = userManager.loadLiveUserByUsername(userClerkVO.getUsername());
		if(AuthUtils.isInvisible(user)){
			throw new SchemeException("���û����ܱ��޸ġ�");
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

		log.debug("ԭ��Ȩ�ޣ�" + roles);
		log.debug("��ǰ�����Ƿ��û����óɲ��Ź���Ա��" + isManager);
		
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
		
		//�û�Ȩ�޸ı���
		if(isUserAuthoritiesChanged){
			auths = new GrantedAuthority[roles.size()];
			for(int i = 0 ; i < roles.size(); i++){
				auths[i] = new GrantedAuthorityImpl(roles.get(i));
			}
			
			user.setAuthorities(auths);
			log.debug("�����Ȩ�ޣ�" + roles);
			isUserChanged = true;
		}
		
		//����
		if(user.isEnabled() != userClerkVO.isEnabled()){
			user.setEnabled(userClerkVO.isEnabled());
			//userManager.updateLiveUser(user);
			isUserChanged = true;
		}
		
		//����û��ı��ˣ������޸�
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
		//�û���Чʱ��״̬Ϊ0������Ϊ1
		clerk.setStatus((byte) (userClerkVO.isEnabled() ? 0 : 1));
		
		clerkService.updateClerk(clerk);
		
		//���ͬʱ���½�ɫ��Ϣ��һ������Ź���Աʹ��
		if(updateGroupInfo){
			Long groupID = userClerkVO.getGroupID();
			Assert.notNull(groupID, "����ѡ���û�������ɫ");
			boolean hasGroup = false;
			GroupManager gm = ((GroupManager) getUserManager());
			List<Group> list = gm.findGroupsOfUser(userClerkVO.getUsername());
			for(Group g: list){
				if(g.getId().longValue() != groupID.longValue()){
					log.debug("ȥ��ԭ��ɫ��" + g.getName());
					gm.removeUserFromGroup(userClerkVO.getUsername(), g.getId());
				}else{
					hasGroup = true;
				}
			}
			//���ԭ��ɫ��û�е�ǰҪ��ӵĽ�ɫ�������
			if(!hasGroup){
				log.debug("����½�ɫ��" + groupID);
				gm.addUserToGroup(userClerkVO.getUsername(), groupID);
			}
		}
		
		return "�û���Ϣ�Ѹ��£�";
//		
//		
//		if(userClerkVO == null && userClerkVO.getId() == null) {
//			log.warn("doUpdateUserInfo ʱ userClerkVO = null �� ClerkId = null,�޸�ָ���û���Ϣʧ��,�������� ... ");
//			return "�޸��û���Ϣʧ��!";
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
//				log.warn("doUpdateUserInfo ʱ��ȡ User = null �� User.UserId = null,�޸�ָ���û���Ϣʧ��,�������� ... ");
//				return "�޸��û���Ϣʧ��!";
//			}
//		}
//		return null;
	}
	
	/**
	 * ������ID��ѯ�û���Ϣ
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
			log.warn("findUsersByGroupId ʱ groupId = " + id + " �� User ����Ϊ�� ,return  List<UserClerkVO> = empty ...");
		}
		return result;
	}
	
	/**
	 * �����û�����ѯ����Ϣ��
	 * 
	 * �������Ϊ�û�����username��
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
	 * ��ѯָ���û��ɼ�����û��顣
	 * 
	 * �������Ϊ�û�����username��
	 * 
	 * ���Ź���ԱҲ����ʹ��������ܣ�����ѯ�������û��飨��ɫ��ֻ�б����ŵġ�
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
				//��ʱ������������Ƹ��ϸ�
//				if(isAdministrator && g.getId().longValue() <= 0){
//					log.debug("�ǳ�������Ա���ܲ鿴0�Ľ�ɫ");
//					continue;
//				}
				//����RFSA�����˲��ܲ鿴ϵͳ��ɫ
				if(!isAdmin && AuthUtils.isSystemGroup(g)){
					log.debug("��RFSA���ܲ�ѯ�ý�ɫ��" + g.getName());
					continue;
				}
				
				candidate.add(g);
			}else{
				if(log.isDebugEnabled()){
					log.debug("===>><><><><<>::�Ѿ����ڣ�" + g);
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
			//����RFSA�����˲��ܲ鿴ϵͳ��ɫ
			if(!isAdmin && AuthUtils.isSystemGroup(g)){
				log.debug("�ǳ��ܲ��ܲ�ѯ�ý�ɫ��" + g.getName());
				continue;
			}
			
			candidate.add(g);
		}
		return wrapGroups(candidate);
	}
	
	/**
	 * ��ѯָ���û��漰�����н�ɫ������������ǲ��Ź���Ա����ֻ��
	 * ��ѯ����λ�Ľ�ɫ��
	 * 
	 * @param gm
	 * @param username
	 * @return
	 */
	private List<Group> findAllGroups(GroupManager gm, String username){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//�жϵ�ǰ��¼�û��Ƿ��Ź���Ա
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
	 * ��ָ���û������û��顣
	 * 
	 * ���������username,�û���id����ids
	 * 
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public Object doAddGroupsToUser(){
		Assert.notNull(username, "�û�������Ϊ��");
		Assert.notEmpty(ids, "�����ύҪ������û���");
		
		User user = getUser(username);
		if(AuthUtils.isInvisible(user)){
			throw new SchemeException("���û����ܱ��޸ġ�");
		}
		
		GroupManager gm = ((GroupManager) getUserManager());
		for(Long id: ids){
			gm.addUserToGroup(username, id);
		}
		
		return "�ɹ������ "  + ids.size() + " ���û���";
	}
	
	
	/**
	 * ɾ��ָ���û����ƶ��û��顣
	 * 
	 *  ���������username,�û���id����ids
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public Object doDeleteGroupsOfUser(){
		Assert.notNull(username, "�û�������Ϊ��");
		Assert.notEmpty(ids, "�����ύҪ������û���");
		
		User user = getUser(username);
		if(AuthUtils.isInvisible(user)){
			throw new SchemeException("���û����ܱ��޸ġ�");
		}
		
		GroupManager gm = ((GroupManager) getUserManager());
		for(Long id: ids){
			gm.removeUserFromGroup(username, id);
		}
		
		return "�ɹ�ɾ���� "  + ids.size() + " ���û���";
	}
		
	
//	/**
//	 * ��ѯ�û�����ӵ��Ȩ�ޡ�
//	 * ���������id.
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
//	 *  ��ѯ�û���û��ӵ�е�Ȩ��
//	 * 
//	 * @return List<Menu>
//	 * @deprecated
//	 */
//	public List<Menu> viewFindGroupNotOwnAuthoritiesByGroupId() {
//		return menuDao.findGroupNotOwnAuthoritiesByGroupId(id);
//	}
	
	
	/**
	 * �����û�����ѯ�û���ϸ��Ϣ��
	 * 
	 * �������Ϊ�û���username��
	 * @return UserClerkVO ����
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
		
		
		//2013-06-25 �ò��ֹ����Ѿ�ת�Ƶ� UserClerkVO�У��� UserClerkVO#tryLoadGroupInfo()
//		//���Ź���Ա��ѯ�û�ʱ��ͬʱ��ѯ�û��ĵ�һ����ɫ��Ϣ
//		if(AuthUtils.isGranted("ROLE_MANAGER")){
//			log.debug("��ѯ�û��ĵ�һ����ɫ��Ϣ��" + username);
//			//�����û��ĵ�һ����ɫ��Ϣ
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
	 * ɾ���û���
	 * 
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_ADMINISTRATOR"})
	public Model doDeleteUsers(){
		Model m = new Model();
		
		if(usernames == null || usernames.isEmpty()){
			m.setMessage(false, "��ѡ��Ҫɾ�����û�", null);
			return m;
		}
		
		m.setItemCount(0);
		m.setLimit(0);
		List<Long> clerkIds = new ArrayList<Long>();
		
		//ɾ��users
		for(String localusername: usernames){
			deleteUser(localusername, m, clerkIds);
		}
		//ɾ��clerks
		clerkService.deleteClerks(clerkIds);
		
		
		
		//������
		String msg = "";
		if(m.getItemCount() == 0){
			msg = "ɾ��ʧ�ܣ�";
			m.setSuccess(false);
		}else{
			msg = "�ɹ�ɾ�� " + m.getItemCount() + " ���û���";
		}
		
		if(m.getLimit() > 0){
			msg += "δɾ�� " + m.getLimit() + " ��ϵͳ�û���";
		}
		m.setMessage(msg);
		
		return m;
	}
	
	private void deleteUser(String localusername, Model m, List<Long> clerkIds){
		if(localusername != null){
			User user = getUser(localusername);//(User) userManager.loadUserByUsername(localusername);
			long userId = user.getUserId();
			if(AuthUtils.isSystemUser(user) || AuthUtils.isInvisible(user)){
				//m.setMessage("����ɾ��ϵͳ�û�");
				m.setLimit(m.getLimit() + 1);
			}else{
//			if("rfsa".equalsIgnoreCase(localusername) || "sys".equalsIgnoreCase(localusername)){
//				//m.setMessage("����ɾ��ϵͳ�û�");
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
	// �û�����
	/////////////////////////////////////////////////////////////
	
	/**
	 * �����û������ԡ�����δ����ֵ�����ԡ�
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
	 * �����û������ԡ�
	 * 
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_ADMINISTRATOR", "ROLE_MANAGER"})
	public Object doUpdateUserProperties(){
		LiveUser user = userManager.loadLiveUserByUsername(username);
		if(AuthUtils.isInvisible(user)){
			throw new SchemeException("���û����ܱ��޸ġ�");
		}
		
		Map<String, String> props = new HashMap<String, String>();
		for(Property p: properties){
			if(StringUtils.isNotBlank(p.getValue())){
				props.put(p.getName(), p.getValue());
			}
		}
		
		user.setProperties(props);
		userManager.updateUser(user);
		return "�����û����Գɹ���";
	}
	
	public Object doUpdateCurrentUserHomepage(){
		User user = UserHolder.getNullableUser();
		if(AuthUtils.isInvisible(user)){
			Model model = new Model();
			model.setMessage(false, "���û����ܸ�����ҳ��", null);
			return model;
		}
		Assert.notNull(homepage, "����������");
		Assert.notNull(homepageTitle, "����������");
		
		Map<String, String> map = user.getProperties();
		if(map == null){
			map = new HashMap<String, String>();
		}
		map.put("homepage", homepage);
		map.put("homepageTitle", homepageTitle);

		LiveUser liveUser = userManager.loadLiveUserByUsername(user.getUsername());
		liveUser.setProperties(map);
		userManager.updateUser(user);
		return "��ҳ���óɹ�";
	}
	
	/////////////////////////////////////////////////////////////
	// �û�Ȩ��
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
	 * ��ѯ�û��ɷ����Ȩ�ޡ�
	 * @return
	 */
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public List<Authority> viewFindCandidateUserAuthorities(){
		//�û��Ѿ������Ȩ��
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
			throw new SchemeException("���û����ܱ��޸ġ�");
		}
		//System.out.println("================> " + authorities);
		if(log.isDebugEnabled()){
			log.debug("�����û� " + username + "��Ȩ�� " + authorities);
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
		
		return "�ɹ��������û�Ȩ�ޣ�";
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
	 * ���ݻ�����ѯ������ѯָ�����ŵ��û���
	 * 
	 * @return
	 */
	//@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public Object viewFindUserClerkVOs(){
		CriterionBuffer buffer = new CriterionBuffer();
		if(StringUtils.isNotBlank(name)){
			log.debug("��ѯ������" + name);
			buffer.and(Restrictions.like("b.name", "%" + name + "%"));
		}
		if(StringUtils.isNotBlank(username)){
			log.debug("��ѯ�û�����" + username);
			buffer.and(Restrictions.like("a.username", "%" + username + "%"));
		}
		if(StringUtils.isNotBlank(onlineStatus)){
			log.debug("��ѯ����״̬��" + onlineStatus);
			buffer.and(Restrictions.eq("a.onlineStatus", onlineStatus));
		}
		if(enabled != null){
			log.debug("��ѯ��Ч״̬��" + enabled);
			buffer.and(Restrictions.eq("a.enabled", enabled));
		}
		if(lastLoginTimeFrom != null){
			log.debug("����¼ʱ�䣨��ʼ����" + lastLoginTimeFrom);
			buffer.and(Restrictions.ge("a.lastLoginTime", lastLoginTimeFrom));
		}
		if(lastLoginTimeTo != null){
			log.debug("����¼ʱ�䣨��ֹ����" + lastLoginTimeTo);
			buffer.and(Restrictions.le("a.lastLoginTime", lastLoginTimeTo));
		}
		
		return clerkService.findUserClerkVOs(buffer.toCriterion(), null, orgId, includeSuborgUsers);
	}

	/**
	 * ��ѯ��ǰ����¼�ߣ�ͨ���ǲ��Ź���Ա���ĵ�λ����ָ����λ�����н�ɫѡ�����ݡ�
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
