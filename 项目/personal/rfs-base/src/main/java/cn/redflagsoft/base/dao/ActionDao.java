package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Action;

public interface ActionDao extends Dao<Action, Long> {
	/**
	 * 
	 * @return
	 */
	List<Long> findIds();
	
	
	/**
	 * 
	 * @param uid
	 * @return
	 */
	Action getByUid(String uid);
}
