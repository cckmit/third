/*
 * $Id: DatumCategoryService.java 4677 2011-09-14 01:29:38Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.DatumCategory;
import cn.redflagsoft.base.vo.DatumCategoryVO;


/**
 * ���ϴ���
 *
 */
public interface DatumCategoryService {

	/**
	 * ��ѯ�����б�
	 * 
	 * @param taskType
	 * @param workType
	 * @param processType
	 * @param matterID
	 * @param objectID
	 * @return
	 */
	List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType,  Long matterID,
			Long objectID, int objectType);

	/**
	 * ��ѯ�����б�
	 * 
	 * @param taskType
	 * @param workType
	 * @param processType
	 * @param matterID
	 * @param objectID
	 * @param matterDatumType �����Ƿ�����
	 * @return
	 */
	List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType, Long matterID, int matterDatumType,
			Long objectID, int objectType);

	/**
	 * ��ѯָ��id���������á�
	 * 
	 * @param id
	 * @return
	 */
	DatumCategory getDatumCategory(Long id);

	/**
	 * ɾ���������á�
	 * @param datumCategory
	 * @return
	 */
	int deleteDatumCategory(DatumCategory datumCategory);

	/**
	 * �����������á�
	 * @param datumCategory
	 * @return
	 */
	DatumCategory updateDatumCategory(DatumCategory datumCategory);
	
	
	/**
	 * ��ѯ�����б�
	 * 
	 * @param taskType ҵ������
	 * @param workType ��������
	 * @param processType 
	 * @param matterID
	 * @param matterDatumType �����Ƿ����� 
	 * @param objectID ����ID�����ܲ�����
	 * @param objectType ��������
	 * @param bizAction ҵ���������ͣ������ȡ�
	 * @return
	 */
	List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType, Long matterID,
			int matterDatumType, Long objectID, int objectType, byte bizAction);
	
	/**
	 * ��ѯ�����б�
	 * 
	 * @param taskType
	 * @param workType
	 * @param processType
	 * @param matterID
	 * @param objectID
	 * @param objectType
	 * @param bizAction
	 * @return
	 */
	List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType, Long matterID,
			Long objectID, int objectType, byte bizAction);
}
