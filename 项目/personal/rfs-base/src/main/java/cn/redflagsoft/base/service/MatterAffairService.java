package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.MatterAffair;

public interface MatterAffairService {
	/**
	 * 查询MatterAffair配置。
	 * 
	 * @param category
	 * @param bizId
	 * @param bizAction
	 * @param tag
	 * @return
	 */
	List<MatterAffair> findAffairs(byte category,Long  bizId,byte bizAction,short tag);
	/**
	 * 查询MatterAffair配置。
	 * 
	 * @param category
	 * @param bizId
	 * @param bizAction
	 * @return
	 */
	//List<MatterAffair> findAffairs(byte category,Long  bizId,byte bizAction);
	
	/**
	 * 查询MatterAffair配置。
	 * 
	 * @param bizCategory 业务种类
	 * @param bizId 业务ID
	 * @param bizType 业务类型
	 * @param bizAction 业务操作
	 * @param tag 标记
	 * @return MatterAffair配置信息集合
	 */
	List<MatterAffair> findAffairs(byte bizCategory, Long bizId, int bizType, Byte bizAction, short tag);
}
