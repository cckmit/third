package cn.redflagsoft.base.menu;

import java.util.Date;
import java.util.List;

import org.springframework.core.Ordered;

import cn.redflagsoft.base.bean.Action;


/**
 * 菜单。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface Menu extends Ordered {

	/**
	 * id。
	 * 
	 * @return
	 */
	Long getId();
	/**
	 * 名称。
	 * @return
	 */
	String getName();
	/**
	 * 标签。
	 * @return
	 */
	String getLabel();
	
	/**
	 * 顺序
	 */
	int getOrder();
	
	/**
	 * 获取菜单的操作。
	 * @return
	 */
	Action getAction();
	
	/**
	 * 继承自菜单。
	 * 
	 * 如果菜单创建时是从其它菜单继承而来的，该项指定继承自哪个菜单。
	 * 
	 * @return
	 */
	Menu getParent();//inheritedFrom
	
	
	/**
	 * 
	 * @return
	 */
	List<Submenu> getSubmenus();
	
	
	/**
	 * 
	 * @return
	 */
	String getIcon();
	
	/**
	 * 
	 * @return
	 */
	String getLogo();
	
	
	/**
	 * 备注
	 * @return
	 */
	String getRemark();
	
	/**
	 * 获取创建者id。
	 * @return
	 */
	Long getCreator();
	
	/**
	 * 获取更新者id。
	 * @return
	 */
	Long getModifier();
	
	/**
	 * 获取创建时间。
	 * @return
	 */
	Date getCreationTime();
	
	/**
	 * 获取修改时间。
	 * @return
	 */
	Date getModificationTime();
	
	
	//菜单关联关系方法
	////////////////
	
	/**
	 * 添加子菜单。
	 * 被添加的菜单必须存在，该方法只增加关联关系。
	 * 
	 * @param menu 被加为子菜单的菜单
	 * @param order 在子菜单中的书序号
	 * @return 被添加后的子菜单
	 */
	Submenu addSubmenu(Menu menu, int order);
	
	/**
	 * 移除子菜单。
	 * 被移除的菜单必须存在，该方法只移除关联关系。
	 * 
	 * @param menu
	 * @return 被删除的子菜单
	 */
	Submenu removeSubmenu(Menu menu);
}
