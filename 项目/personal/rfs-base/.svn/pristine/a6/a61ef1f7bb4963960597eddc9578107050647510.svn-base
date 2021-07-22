package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.vo.UserClerkVO;


public interface UserService{
	
	/**
	 * 查询所有部门级管理员。
	 * 
	 * @param filter
	 * @return
	 */
	List<Clerk> findAllAdminUser(ResultFilter filter);
	
	/**
	 * 
	 * 查询角色为“信息管理员”的用户。
	 * @param roleIds
	 * @return
	 */
	List<UserClerkVO> findInformationMgrUserOfRoles(String roleIds);
}
