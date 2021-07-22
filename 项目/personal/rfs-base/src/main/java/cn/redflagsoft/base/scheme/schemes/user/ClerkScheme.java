package cn.redflagsoft.base.scheme.schemes.user;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSGroup;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.service.ClerkGroupService;
import cn.redflagsoft.base.service.ClerkService;

public class ClerkScheme extends AbstractScheme{
	public static final Log log = LogFactory.getLog(ClerkScheme.class);
	
	private ClerkService clerkService;
	private ClerkGroupService clerkGroupService;
	private Clerk clerk;
	private List<Long> ids;
	private Long groupId;
	private Long id;
	
	//单位中人员查询条件
	private Long orgId;
	private String code;
	private String name;
	private boolean includeSuborgClerks;
	
	

	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}
	
	
	public ClerkGroupService getClerkGroupService() {
		return clerkGroupService;
	}

	public void setClerkGroupService(ClerkGroupService clerkGroupService) {
		this.clerkGroupService = clerkGroupService;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Clerk getClerk() {
		return clerk;
	}

	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}
	
	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isIncludeSuborgClerks() {
		return includeSuborgClerks;
	}

	public void setIncludeSuborgClerks(boolean includeSuborgClerks) {
		this.includeSuborgClerks = includeSuborgClerks;
	}

	public String doUpdateClerk() {
		Assert.notNull(clerk, "被修改的人员对象不能为空");
		Assert.notNull(clerk.getId(), "被修改的人员的主键不能为空");
		
		Clerk tmp = clerkService.getClerk(clerk.getId());
		if(tmp == null){
			throw new SchemeException("被修改的人员不存在");
		}
		
		if(clerk.getTelNo() != null) {
			tmp.setTelNo(clerk.getTelNo());
		}
		if(clerk.getFaxNo() != null) {
			tmp.setFaxNo(clerk.getFaxNo());
		}
		if(clerk.getPaperType() != null) {
			tmp.setPaperType(clerk.getPaperType());
		}
		if(clerk.getPaperNo() != null) {
			tmp.setPaperNo(clerk.getPaperNo());
		}
		if(clerk.getHomeAddr() != null) {
			tmp.setHomeAddr(clerk.getHomeAddr());
		}
		if(clerk.getWorkAddr() != null) {
			tmp.setWorkAddr(clerk.getWorkAddr());
		}
		if(clerk.getMobNo() != null) {
			tmp.setMobNo(clerk.getMobNo());
		}
		if(clerk.getEmailAddr() != null) {
			tmp.setEmailAddr(clerk.getEmailAddr());
		}
		if(clerk.getHomeTelNo() != null) {
			tmp.setHomeTelNo(clerk.getHomeTelNo());
		}
		if(Byte.MAX_VALUE <= clerk.getPaperElse() && Byte.MIN_VALUE >= clerk.getPaperElse()) {
			tmp.setPaperElse(clerk.getPaperElse());
		}
		if(clerk.getNativeAddr() != null) {
			tmp.setNativeAddr(clerk.getNativeAddr());
		}
		if(clerk.getModifier() != null) {
			tmp.setModifier(clerk.getModifier());
		}
		tmp.setModificationTime(new Date());
		tmp.setDisplayOrder(clerk.getDisplayOrder());
		tmp.setName(clerk.getName());
		
		clerkService.updateClerk(tmp);
		
		return "修改人员信息成功!";
//		String result;
//		if(clerk.getKey() != null) {
//			Clerk tmp = clerkService.getClerk(clerk.getId());
//			if(tmp != null) {
//				if(clerk.getTelNo() != null) {
//					tmp.setTelNo(clerk.getTelNo());
//				}
//				if(clerk.getFaxNo() != null) {
//					tmp.setFaxNo(clerk.getFaxNo());
//				}
//				if(clerk.getPaperType() != null) {
//					tmp.setPaperType(clerk.getPaperType());
//				}
//				if(clerk.getPaperNo() != null) {
//					tmp.setPaperNo(clerk.getPaperNo());
//				}
//				if(clerk.getHomeAddr() != null) {
//					tmp.setHomeAddr(clerk.getHomeAddr());
//				}
//				if(clerk.getWorkAddr() != null) {
//					tmp.setWorkAddr(clerk.getWorkAddr());
//				}
//				if(clerk.getMobNo() != null) {
//					tmp.setMobNo(clerk.getMobNo());
//				}
//				if(clerk.getEmailAddr() != null) {
//					tmp.setEmailAddr(clerk.getEmailAddr());
//				}
//				if(clerk.getHomeTelNo() != null) {
//					tmp.setHomeTelNo(clerk.getHomeTelNo());
//				}
//				if(Byte.MAX_VALUE <= clerk.getPaperElse() && Byte.MIN_VALUE >= clerk.getPaperElse()) {
//					tmp.setPaperElse(clerk.getPaperElse());
//				}
//				if(clerk.getNativeAddr() != null) {
//					tmp.setNativeAddr(clerk.getNativeAddr());
//				}
//				if(clerk.getModifier() != null) {
//					tmp.setModifier(clerk.getModifier());
//				}
//				tmp.setModificationTime(new Date());
//				clerkService.updateClerk(tmp);
//				result = "修改职员信息成功!";
//			} else {
//				log.warn("doSaveOrUpdateClerk 时获取 ClerkId = " + clerk.getId() + " 的 Clerk 对象不存在,修改职员信息失败 ... ");				
//				result = "修改职员信息失败!";				
//			}
//		} else {
//			clerkService.saveClerk(clerk);
//			result = "添加职员信息成功";
//		}
//		return result;
	}
	
	public String doDelete() {
		Assert.notNull(clerk, "被删除的人员对象不能为空。");
		Assert.notNull(clerk.getId(), "被删除的人员的ID不能为空。");
		clerkService.deleteClerk(clerk);
		return "删除人员信息成功";
	}
	
	public String doDeleteList() {
		Assert.notNull(ids, "被删除的人员ID集合不能为空。");
		clerkService.deleteClerks(ids);
		return "删除人员信息成功";
	}
	
	public Object doScheme() throws SchemeException {
		throw new UnsupportedOperationException("scheme");
	}
	
	public Object doCreateClerk() throws SchemeException{
		Assert.notNull(clerk, "被创建的人员对象不能为空。");
		if(clerk.getCode() == null){
			clerk.setCode("N/A");
		}
		if(clerk.getPaperType() == null){
			clerk.setPaperType("0");//默认类型为身份证
		}
		Clerk clerk2 = clerkService.saveClerk(clerk);
		
		//并添加到对应的组中
		if(groupId != null){
			clerkGroupService.addClerkToGroup(clerk2.getId(), groupId);
		}
		return "人员创建成功。";
	}
	
	
	
	
	
	public Object doAddGroup(){
		Assert.notNull(id, "人员的 id 不能为空。");
		Assert.notNull(groupId, "要添加的分组的 id 不能为空。");
		
		clerkGroupService.addClerkToGroup(id, groupId);
		return "人员所属分组添加成功。";
	}
	
	
	public Object doAddGroups(){
		Assert.notNull(id, "人员的 id 不能为空。");
		Assert.notEmpty(ids, "要添加的分组集合不能为空。");
		
		clerkGroupService.addClerkToGroups(id, ids);
		return "人员所属分组添加成功，共添加了 " + ids.size() + " 个分组。";
	}

	public Object doRemoveGroup(){
		Assert.notNull(id, "人员的 id 不能为空。");
		Assert.notNull(groupId, "要添加的分组的 id 不能为空。");
		
		clerkGroupService.removeClerkFromGroup(id, groupId);
		return "人员所属分组删除成功。";
	}
	
	public Object doRemoveGroups(){
		Assert.notNull(id, "人员的 id 不能为空。");
		Assert.notEmpty(ids, "要删除的分组集合不能为空。");
		
		int n = clerkGroupService.removeClerkFromGroups(id, ids);
		return "人员所属分组删除成功，共删除了 " + n + " 个分组。";
	}
	
	
	
	public Object viewFindGroups(){
		Assert.notNull(id, "人员 ID 不能为空。");

		List<RFSGroup> list = clerkGroupService.findGroupsOfClerk(id);
		//Collections.sort(list, ORG_GROUP_COMPARATOR);
		return list;
	}
	
	public Object viewFindCandidateGroups(){
		Assert.notNull(id, "人员 ID 不能为空。");
		List<RFSGroup> list = clerkGroupService.findGroupsNotOfClerk(id);
//		Collections.sort(list, ORG_GROUP_COMPARATOR);
		return list;
	}
	
	
	/**
	 * 根据指定的关键字搜索指定单位的人员。
	 * 
	 * <P>调用：json/scheme/clerkScheme/findClerks.jspa?name=&orgId=1002
	 * 
	 * @see ClerkService#findClerks(Long, String, boolean)
	 * @return
	 */
	public Object viewFindClerks(){
		Criterion c = null;
		if(StringUtils.isNotBlank(code)){
			c = Restrictions.like("code", "%" + code.trim() + "%");
		}
		if(StringUtils.isNotBlank(name)){
			Criterion tmp = Restrictions.like("name", "%" + name.trim() + "%");
			if(c == null){
				c = tmp;
			}else{
				c = Restrictions.logic(c).and(tmp);
			}
		}
		return clerkService.findClerks(c, null, orgId, includeSuborgClerks);
	}
	
	/**
	 * 根据查询条件查询指定人员分组指定单位的人员。
	 * 
	 * <P>调用：json/scheme/clerkScheme/findClerksInGroup.jspa?groupId=1&name=&orgId=1002
	 * @see ClerkService#findClerksInGroup(long, Criterion, org.opoo.ndao.criterion.Order, Long, boolean)
	 * @return
	 */
	public Object viewFindClerksInGroup(){
		Assert.notNull(groupId, "组 ID 不能为空。");
		
		Criterion c = null;
		if(StringUtils.isNotBlank(code)){
			c = Restrictions.like("code", "%" + code.trim() + "%");
		}
		if(StringUtils.isNotBlank(name)){
			Criterion tmp = Restrictions.like("name", "%" + name.trim() + "%");
			if(c == null){
				c = tmp;
			}else{
				c = Restrictions.logic(c).and(tmp);
			}
		}
		return clerkService.findClerksInGroup(groupId.longValue(), c, null, orgId, includeSuborgClerks);
	}
}
