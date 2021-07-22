package cn.redflagsoft.base.dao;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.LifeStage;

public interface LifeStageDao extends Dao<LifeStage, Long>{
	
	/**
	 * 同步指定对象的 Task 状态到该对象的LifeStage对象。
	 * @param id
	 */
	void synchronizeLifeStageStatusForObject(Long id);
}
