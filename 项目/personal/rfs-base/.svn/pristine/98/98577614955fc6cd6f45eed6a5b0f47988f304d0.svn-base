package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.MatterAffair;

public interface MatterAffairDao extends Dao<MatterAffair, Long> {
		
//	public Byte getAffairAction(Long affairID,Long matterID);
//	public List<Long> findMatters(Long affairID);
//	public List<Long> findAffairs(Long... matterIds);
	public List<MatterAffair> findAffairs(byte category,Long  bizId,byte bizAction,short tag);
	
	public List<MatterAffair> findAffairs(byte category,Long  bizId,byte bizAction);

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
	public List<MatterAffair> findAffairs(byte bizCategory, Long bizId,	int bizType, Byte bizAction, short tag);
	
}
