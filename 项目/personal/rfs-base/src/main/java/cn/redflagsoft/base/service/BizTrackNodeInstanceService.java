package cn.redflagsoft.base.service;

public interface BizTrackNodeInstanceService {
	/**
	 * 创建轨迹节点实例
	 * 
	 * @param category
	 * @param bizType
	 * @param trackSN
	 * @param taskSN
	 * @param nodeSN
	 * @return boolean
	 */
	boolean createBizTrackNodeInstance(byte bizCategory, int bizType, Long bizSN, Long trackSN, Long nodeSN);
	
	/**
	 * 创建轨迹节点实例
	 * 
	 * @param category
	 * @param bizType
	 * @param trackSN
	 * @param taskSN
	 * @return boolean
	 */
	boolean createBizTrackNodeInstance(byte bizCategory, int bizType, Long bizSN, Long trackSN);
	
	boolean updateBizTrackNodeStatus(byte bizCategory, Long bizSN, byte status);
	
	boolean moveBizTrackNodeInstance(byte bizCategory, int bizType, Long bizSN, Long trackSN);
}
