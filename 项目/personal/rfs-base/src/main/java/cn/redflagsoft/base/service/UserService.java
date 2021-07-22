package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.vo.UserClerkVO;


public interface UserService{
	
	/**
	 * ��ѯ���в��ż�����Ա��
	 * 
	 * @param filter
	 * @return
	 */
	List<Clerk> findAllAdminUser(ResultFilter filter);
	
	/**
	 * 
	 * ��ѯ��ɫΪ����Ϣ����Ա�����û���
	 * @param roleIds
	 * @return
	 */
	List<UserClerkVO> findInformationMgrUserOfRoles(String roleIds);
}
