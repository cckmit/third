package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.MenuItem;

public interface MenuItemDao extends Dao<MenuItem, Long> {
	
//	/**
//	 * 
//	 * @param actionId
//	 * @return
//	 */
//	int removeByAction(Long actionId);
	

	List<Long> findIds();
	
	List<Long> findIds(ResultFilter filter);
	
	List<Long> findSuperIds();
	
	/**
	 * 
	 */
	void removeAll();
}
