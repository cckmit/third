package cn.redflagsoft.base.service;

public interface BizTrackNodeService {
	/**
	 * �������̽ڵ�
	 * 
	 * @param routeId
	 * @return boolean
	 */
	boolean createBizTrackNode(Long bizTrackSN, Long routeId);
	
	boolean deriveBizTrackNode(byte bizCategory, int bizType, Long bizSN, Long trackSN);
}
