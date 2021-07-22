package cn.redflagsoft.base.menu;

import java.util.List;

import cn.redflagsoft.base.bean.Action;

/**
 * Action的管理。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface ActionManager {

	/**
	 * 获取Action
	 * @param id
	 * @return
	 */
	Action getAction(Long id);
	

	/**
	 * 删除指定Action，不级联删除Menu，指将menu的Action置为null。
	 * @param id
	 */
	int removeActions(List<Long> ids);

	/**
	 * 新建Action。
	 * @param action
	 * @return
	 */
	Action saveAction(Action action);
	
	/**
	 * 修改Action。
	 * 
	 * @param action
	 * @return
	 */
	Action updateAction(Action action);

	/**
	 * 
	 * @return
	 */
	List<Action> findActions();
}
