package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.Clue;

public interface ClueService {
	
	Clue getActiveClue(Long objectId, byte category, int bizType, Long bizId);

	Clue getClue(Long objectId, byte category);

	Clue getClue(Long objectId, byte category, int bizType, Long bizId);

	Clue createClue(Long objectId, byte category, int bizType, Long bizId,
			Long bizSN, Long bizTrack);

	boolean updateClueStatus(byte category, Long bizSN, byte status);

	void deleteClueByBizSN(final Long bizSN);
}
