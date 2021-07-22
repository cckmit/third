package cn.redflagsoft.base.menu;

import java.util.List;

/**
 * �˵�����
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface MenuService {

	/**
	 * ��ȡָ���û��Ķ����˵����ϡ�
	 * 
	 * �����ɫ�Ĳ˵�����ϲ���
	 * @param username
	 * @return
	 */
	List<RoleMenu> findMenusByUsername(String username);
	
	/**
	 * ��ȡָ����ɫ�Ķ����˵����ϡ�
	 * 
	 * @param roleId
	 * @return
	 */
	List<RoleMenu> findMenusByRoleId(Long roleId);
	
	/**
	 * ��ȡָ�� id �Ĳ˵���
	 * 
	 * @param id
	 * @return �Ҳ���ָ���˵�ʱ����null
	 */
	Menu getMenu(Long id);
}
