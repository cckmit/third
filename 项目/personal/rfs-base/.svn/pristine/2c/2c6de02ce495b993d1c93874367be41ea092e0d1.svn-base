package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.OrgCategory;
import cn.redflagsoft.base.bean.OrgCategoryToOrgGroup;
import cn.redflagsoft.base.bean.OrgCategoryToRole;
import cn.redflagsoft.base.dao.OrgCategoryDao;
import cn.redflagsoft.base.service.OrgCategoryService;
import cn.redflagsoft.base.service.OrgCategoryToOrgGroupService;
import cn.redflagsoft.base.service.OrgCategoryToRoleService;

public class OrgCategoryServiceImpl implements OrgCategoryService{

	public static final Log log = LogFactory.getLog(OrgCategoryServiceImpl.class);
	
	private OrgCategoryDao orgCategoryDao;
	private OrgCategoryToOrgGroupService orgCategoryToOrgGroupService;
	private OrgCategoryToRoleService orgCategoryToRoleService;
	
	public OrgCategoryToOrgGroupService getOrgCategoryToOrgGroupService() {
		return orgCategoryToOrgGroupService;
	}


	public void setOrgCategoryToOrgGroupService(
			OrgCategoryToOrgGroupService orgCategoryToOrgGroupService) {
		this.orgCategoryToOrgGroupService = orgCategoryToOrgGroupService;
	}


	public OrgCategoryToRoleService getOrgCategoryToRoleService() {
		return orgCategoryToRoleService;
	}


	public void setOrgCategoryToRoleService(
			OrgCategoryToRoleService orgCategoryToRoleService) {
		this.orgCategoryToRoleService = orgCategoryToRoleService;
	}


	public OrgCategoryDao getOrgCategoryDao() {
		return orgCategoryDao;
	}


	public void setOrgCategoryDao(OrgCategoryDao orgCategoryDao) {
		this.orgCategoryDao = orgCategoryDao;
	}


	public OrgCategory save(OrgCategory orgCategory) {
		return orgCategoryDao.save(orgCategory);
	}


	public OrgCategory getOrgCategoryById(Long orgCategoryId) {
		return orgCategoryDao.get(orgCategoryId);
	}


	public OrgCategory update(OrgCategory orgCategory) {
		 OrgCategory update = orgCategoryDao.update(orgCategory);
		 return update;
	}


	public int deleteOrgCategory(List<Long> ids) {
		int result = 0;
		for (Long id : ids) {
			deleteOrgCategory(id);
			result++;
		}
		return result;
	}


	public void deleteOrgCategory(Long id) {
		try {
			// 删除相关的单位分组
			List<OrgCategoryToOrgGroup> list = orgCategoryToOrgGroupService.findOrgCategoryToOrgGroupByOrgCategoryId(id);
			for (OrgCategoryToOrgGroup og : list) {
				orgCategoryToOrgGroupService.delete(og.getId());
			}
			
			// 删除相关的角色
			List<OrgCategoryToRole> list2 = orgCategoryToRoleService.findOrgCategoryToRoleByOrgCategoryId(id);
			for (OrgCategoryToRole rl : list2) {
				orgCategoryToRoleService.delete(rl.getId());
			}
			
			orgCategoryDao.remove(id);
			
		} catch (Exception e) {
			log.debug("删除机构管理出错 ： " + id);
		}
	}

}
