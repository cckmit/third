package cn.redflagsoft.base.menu.migration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.Group;
import org.opoo.apps.security.GroupManager;
import org.opoo.apps.security.UserManager;
import org.opoo.ndao.hibernate3.HibernateDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.redflagsoft.base.bean.Action;
import cn.redflagsoft.base.dao.MenuItemDao;
import cn.redflagsoft.base.dao.MenuItemRelationDao;
import cn.redflagsoft.base.menu.ActionManager;
import cn.redflagsoft.base.security.Menu;
import cn.redflagsoft.base.security.MenuGroup;
import cn.redflagsoft.base.security.MenuManagerImpl;
import cn.redflagsoft.base.security.dao.MenuDao;


/**
 * �������������������С�
 * 
 * ��Ǩ�Ƴ���ɽ�ԭ�˵��ṹ���ѷ�����û���Ĳ˵�����ȫ��Ǩ�ơ�
 * Ǩ�ƺ�ϵͳ�������������µĲ˵�ϵͳ�У��²˵�ϵͳ�е��м�ṹ
 * ������ݽ���ǳ���ԭ��ÿ���û���Ĳ˵������γ��µ��м���
 * ��һ�����ڵ㡣
 * 
 * 
 * ˳�������public���������ߵ���run��
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class MenuMigration implements Runnable {
	
	private MenuItemDao menuItemDao = Application.getContext().get("menuItemDao", MenuItemDao.class);
	private MenuItemRelationDao memuItemRelationDao = Application.getContext().get("memuItemRelationDao", MenuItemRelationDao.class);
	private UserManager userManager = Application.getContext().getUserManager();
	private MenuManagerImpl menuManager = Application.getContext().get("menuManager", MenuManagerImpl.class);
	private MenuDao menuDao = Application.getContext().get("menuDao", MenuDao.class);
	private cn.redflagsoft.base.menu.impl.MenuManagerImpl newMenuManager =
		Application.getContext().get("menuManagerV2", cn.redflagsoft.base.menu.impl.MenuManagerImpl.class);
	private ActionManager actionManager = Application.getContext().get("actionManager", ActionManager.class);
	
	
	
	
	private void execHQL(String hql){
		((HibernateDao)menuItemDao).getQuerySupport().executeUpdate(hql);
	}
	/**
	 * ���
	 */
	public void clear(){
		String hql = "delete from RoleMenuItem";
		execHQL(hql);
		
		hql = "delete from RoleMenuRemark";
		execHQL(hql);
		
		hql = "delete from MenuItemRelation";
		execHQL(hql);
		
		hql = "update MenuItem set parentId=null";
		execHQL(hql);
		
		hql = "delete from MenuItem";
		execHQL(hql);
		
		hql = "delete from Action";
		execHQL(hql);
	}
	
	public void processAllMenus(){
		List<Menu> list = menuDao.find();
		processAllMenusToAction(list);
	}
	private void processAllMenusToAction(List<Menu> list){
		for(Menu menu: list){
			if(StringUtils.isNotBlank(menu.getApplication())){
				saveActionAndNewMenu(menu);
			}
		}
	}
	
	/**
	 * 
	 * @param m
	 * @param action
	 */
	private void saveActionAndNewMenu(Menu menu){
		Action action = new Action();
		action.setId(actionIdIndex++);
		//Ϊ�˺�ԭ���Ĳ˵���ͼ����ݣ�ԭ���˵���icon�Ǹ��ݲ˵���id�����ġ�
		action.setIcon(menu.getId());//menu.getIcon());
		action.setLocation(menu.getApplication());
		action.setLogo(menu.getLogo());
		action.setName(menu.getName());
		action.setRemark(menu.getLabel());
		action.setStatus((byte) 1);
		action.setType(1);
		action.setUid("UID-" + (action.getId()));
		
		
		actionManager.saveAction(action);
		actions.put(menu.getAuthority(), action);
		
		
		cn.redflagsoft.base.menu.Menu mm = newMenuManager.createMenu(newMenuIdIndex++, menu.getName(), menu.getLabel2(),
				action.getIcon(), menu.getLogo(), null, action, 1, (byte)1, menu.getLabel());
		menus.put(menu.getAuthority(), mm);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * ��ԭ��ÿ���û��飨��ɫ���Ĳ˵���Ϊ�µĲ˵��ṹ�е�һ�����ڵ㣬
	 * �ԡ���ɫ xxx �Ĳ˵���������������xxx���û�������ơ�
	 * ���������������󣬲�������ת���µĲ˵��Ľṹ�������԰�ԭ�в˵�����
	 * ������Զ�����RoleMenuItem��Ϣ��
	 * ����������ɺ󣬼���ʹ���²˵����û����ǿ��Բ�ѯ����ԭ����һ���Ĳ˵�
	 * ��Ͳ˵��ṹ��
	 * 
	 */
	public void processAllGroupMenus(){
		GroupManager gm = (GroupManager) userManager;
		List<Group> groups = gm.findGroups();
		for(Group g: groups){
			List<MenuGroup> list = menuManager.findMenusByUserGroupId(g.getId());
			System.out.println("��ɫ��" + g.getName()  + "�Ĳ˵���" + list);
			processGroupMenu(g, list);
		}
	}
	
	private void processGroupMenu(Group group, List<MenuGroup> list){
		if(list.isEmpty()){
			return;
		}
		
		
		String name = "ԭ" + group.getName() + "�Ĳ˵�";
		cn.redflagsoft.base.menu.Menu superior = newMenuManager.createMenu(newMenuIdIndex++,
				name, name, null, null, null, null, 1, (byte)1, "��ɫ�����˵�");
		
		
		for (int i = 0; i < list.size(); i++) {
			processSingleGroupMenu(group, list.get(i), i, superior);
		}
	}
	
	
	private void processSingleGroupMenu(Group group, MenuGroup mg, int index, cn.redflagsoft.base.menu.Menu superior){
		//����ԭ�˵����Ӧ�Ĳ˵���;
		cn.redflagsoft.base.menu.Menu menu = newMenuManager.createMenu(newMenuIdIndex++, mg.getLabel(),
				mg.getGroupName(), mg.getIcon(), mg.getLogo(), null, null, 1, (byte)1, "MGID-" + mg.getId());
		menus.put("MG" + mg.getId(), menu);
		
		
		
		
		//���ϼ��˵����Ӹ���ǰ��ɫ
		newMenuManager.addMenuToRole(group.getId(), menu, (index + 1) * 100);
		
		
		
		//����������ϵ
		//newMenuManager.addMenuToSuperior(superior, menu, (index + 1) * 100);
		superior.addSubmenu(menu, (index + 1) * 100);
		
		
		//�����¼��˵��Ĺ�����ϵ
		List<Menu> menus2 = mg.getMenus();
		for (int i = 0; i < menus2.size(); i++) {
			Menu oldMenu = menus2.get(i);
			cn.redflagsoft.base.menu.Menu newmenu = menus.get(oldMenu.getAuthority());
			System.out.println("�ҵ���" + newmenu);
			menu.addSubmenu(newmenu, (i + 1) * 100);
		}
	}
	
	
	
	
	private long newMenuIdIndex = 1L;
	private long actionIdIndex = 1L;
	private Map<String,Action> actions = new LinkedHashMap<String, Action>();
	private Map<String, cn.redflagsoft.base.menu.Menu> menus = new LinkedHashMap<String, cn.redflagsoft.base.menu.Menu>();


	@Transactional(propagation=Propagation.REQUIRED)
	public void run() {
		clear();
		processAllMenus();
		processAllGroupMenus();
	}
}
