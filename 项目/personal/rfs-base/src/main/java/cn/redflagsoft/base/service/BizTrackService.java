package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.BizRoute;
import cn.redflagsoft.base.bean.BizTrack;
import cn.redflagsoft.base.vo.BizTrackVO;
import cn.redflagsoft.base.vo.BizVO;

public interface BizTrackService {
	/**
	 * 创建业务轨迹
	 * 
	 * @param category
	 * @param taskSn
	 * @param routeId
	 * @return Long
	 */
	Long createBizTrack(byte bizCategory, Long bizSN, Long routeId);
	
	/**
	 * 创建业务轨迹
	 * 
	 * @param bizCategory
	 * @param bizSN
	 * @param bizRoute
	 * @return Long
	 */
	Long createBizTrack(byte bizCategory, Long bizSN, BizRoute bizRoute);
	
	List<BizTrackVO> getBizTrackVO(Long nodeSN);
	
	List<BizTrackVO> getBizTrackVOByObjectId(Long objectId);
	
	List<BizTrackVO> getBizTrackVOByObjectId(Long objectId, Byte category, Integer bizType, Long bizId, Byte flag, Long managerId);
	
	List<BizTrackVO> getBizTrackVOByClueField(Long objectId);
	
	BizTrackVO getBizTrackVOByTrackSn(Long trackSN);
	
	int deleteBizTrack(BizTrack bizTrack);
	
	BizTrack updateBizTrack(BizTrack bizTrack);
	
	List<BizVO> findChildDetails(Long nodeSN);
}
