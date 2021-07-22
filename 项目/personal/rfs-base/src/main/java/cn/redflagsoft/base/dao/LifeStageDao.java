package cn.redflagsoft.base.dao;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.LifeStage;

public interface LifeStageDao extends Dao<LifeStage, Long>{
	
	/**
	 * ͬ��ָ������� Task ״̬���ö����LifeStage����
	 * @param id
	 */
	void synchronizeLifeStageStatusForObject(Long id);
}
