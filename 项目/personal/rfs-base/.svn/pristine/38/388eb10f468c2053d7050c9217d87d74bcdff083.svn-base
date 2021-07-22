package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.MenuItemRelation;

public interface MenuItemRelationDao extends Dao<MenuItemRelation, Long> {

	List<MenuItemRelation> findBySupId(long supId);
	
	void remove(Long supId, Long subId);
	
	MenuItemRelation get(Long supId, Long subId);
}
