package cn.redflagsoft.base.menu.impl;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.cache.Cache;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.redflagsoft.base.bean.Action;
import cn.redflagsoft.base.dao.ActionDao;
import cn.redflagsoft.base.menu.ActionManager;
import cn.redflagsoft.base.menu.event.ActionEvent;


/**
 * 服务管理实现类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ActionManagerImpl implements ActionManager, EventDispatcherAware {
	private Cache<Long, Action> actionCache;
	private Cache<Long,MenuImpl> menuCache;
	private ActionDao actionDao;
	private EventDispatcher eventDispatcher;
	
	public List<Action> findActions() {
		final List<Long> ids = actionDao.findIds();
		return Collections.unmodifiableList(new AbstractList<Action>(){
			@Override
			public Action get(int index) {
				Long id = ids.get(index);
				return getAction(id);
			}

			@Override
			public int size() {
				return ids.size();
			}
		});
	}

	public Action getAction(Long id) {
		Action action = actionCache.get(id);
		if(action == null){
			action = actionDao.get(id);
			if(action != null){
				actionCache.put(id, action);
			}
		}
		return action;
	}


	/**
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeAction(Long id) {
		actionDao.remove(id);
		actionCache.remove(id);
		menuCache.clear();
		Action action = new Action(id);
		this.eventDispatcher.dispatchEvent(new ActionEvent(ActionEvent.Type.REMOVED, action));
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public Action saveAction(Action action) {
		action = actionDao.save(action);
		actionCache.put(action.getId(), action);
		this.eventDispatcher.dispatchEvent(new ActionEvent(ActionEvent.Type.CREATED, action));
		return action;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public Action updateAction(Action action) {
		action = actionDao.update(action);
		actionCache.put(action.getId(), action);
		this.eventDispatcher.dispatchEvent(new ActionEvent(ActionEvent.Type.UPDATED, action));
		return action;
	}

	/**
	 * @return the actionCache
	 */
	public Cache<Long, Action> getActionCache() {
		return actionCache;
	}

	/**
	 * @param actionCache the actionCache to set
	 */
	public void setActionCache(Cache<Long, Action> actionCache) {
		this.actionCache = actionCache;
	}

	/**
	 * @return the actionDao
	 */
	public ActionDao getActionDao() {
		return actionDao;
	}

	/**
	 * @param actionDao the actionDao to set
	 */
	public void setActionDao(ActionDao actionDao) {
		this.actionDao = actionDao;
	}

	public void setEventDispatcher(EventDispatcher arg0) {
		this.eventDispatcher = arg0;
	}

	/**
	 * @return the menuCache
	 */
	public Cache<Long, MenuImpl> getMenuCache() {
		return menuCache;
	}

	/**
	 * @param menuCache the menuCache to set
	 */
	public void setMenuCache(Cache<Long, MenuImpl> menuCache) {
		this.menuCache = menuCache;
	}
	public int removeActions(List<Long> ids) {
		int n = 0;
		for(long id: ids) {
			n+=removeAction(id);
		}
		return n;
	}
	
	private int removeAction(long id) {
		return actionDao.remove(id);

	}
}
