package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.MatterAffair;

public interface MatterAffairService {
	/**
	 * ��ѯMatterAffair���á�
	 * 
	 * @param category
	 * @param bizId
	 * @param bizAction
	 * @param tag
	 * @return
	 */
	List<MatterAffair> findAffairs(byte category,Long  bizId,byte bizAction,short tag);
	/**
	 * ��ѯMatterAffair���á�
	 * 
	 * @param category
	 * @param bizId
	 * @param bizAction
	 * @return
	 */
	//List<MatterAffair> findAffairs(byte category,Long  bizId,byte bizAction);
	
	/**
	 * ��ѯMatterAffair���á�
	 * 
	 * @param bizCategory ҵ������
	 * @param bizId ҵ��ID
	 * @param bizType ҵ������
	 * @param bizAction ҵ�����
	 * @param tag ���
	 * @return MatterAffair������Ϣ����
	 */
	List<MatterAffair> findAffairs(byte bizCategory, Long bizId, int bizType, Byte bizAction, short tag);
}
