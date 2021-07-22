package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Clue;

public interface ClueDao extends Dao<Clue,Long> {
	
	List<Clue> getActiveClue(Long objectId, byte category, int bizType, Long bizId);
	
	void deleteClueByBizSN(final Long bizSN);
}
