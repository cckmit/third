/*
 * $Id: MatterDatumDao.java 4677 2011-09-14 01:29:38Z lcj $
 * BizDatumDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.DatumCategory;
import cn.redflagsoft.base.bean.DatumCategoryWithMatterDatum;
import cn.redflagsoft.base.bean.MatterDatum;

/**
 * @author Administrator
 *
 */
public interface MatterDatumDao extends Dao<MatterDatum, Long> {
	
	
	/**
	 * ����������ѯDatumCategory��MatterDatum��϶���ļ��ϡ�
	 * 
	 * ���ж���DatumCategory����Ϊ a��MatterDatum�ı���Ϊ b��
	 * @param filter
	 * @return
	 */
	List<DatumCategoryWithMatterDatum> findDatumCategoryWithMatterDatum(ResultFilter filter);

	/**
	 * ����task���ͣ�work���ͣ�process���ͺ�matterId��ѯ���õ����ϡ�
	 * 
	 * @param taskType task����
	 * @param workType work����
	 * @param processType process����
	 * @param matterID
	 * @return
	 */
	List<DatumCategory> findDatumCategory(int taskType, int workType, int processType, Long matterID);

	/**
	 * ����task���ͣ�work���ͣ�process���͡�matterId��matterdatum�����Ͳ�ѯ���õ����ϡ�
	 * @param taskType task����
	 * @param workType work����
	 * @param processType process����
	 * @param matterID
	 * @param matterDatumType matterdatum����
	 * @return
	 */
	List<DatumCategory> findDatumCategory(int taskType, int workType, int processType, Long matterID,
			int matterDatumType);
	
	/**
	 * ��ѯMatterDatum��
	 * 
	 * @param category
	 * @param taksType
	 * @param workType
	 * @param processType
	 * @param matterID
	 * @return
	 */
	List<MatterDatum> findMatterDatum(Byte category, int taksType, int workType, int processType, Long matterID);

	/**
	 * ��ѯMatterDatum��
	 * 
	 * @param category
	 * @param taksType
	 * @param workType
	 * @param processType
	 * @param matterID
	 * @param datumType
	 * @return
	 */
	List<MatterDatum> findMatterDatum(Byte category, int taksType, int workType, int processType, Long matterID,
			Long datumType);
}
