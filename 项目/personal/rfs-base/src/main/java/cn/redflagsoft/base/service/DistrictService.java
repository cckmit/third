/*
 * $Id: DistrictService.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.District;

/**
 * ���������Ĺ���
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface DistrictService {

	/**
	 * ��ȡ������
	 * @return
	 */
	District getRootDistrict();
	
	/**
	 * ��ȡ������������
	 * 
	 * @param code
	 * @return
	 */
	District getDistrict(String code);
	
	
	/**
	 * ɾ�����������������¼�����������
	 * 
	 * @param code
	 */
	void removeDistrict(String code);
	
	
	/**
	 * ��������������ע����ѭ��Ƕ�ס�
	 * 
	 * @param code
	 * @param name
	 * @param parent
	 * @param remark
	 * @param displayOrder
	 * @param status
	 * @param type
	 * @return
	 */
	District updateDistrict(String code, String name, District parent, String remark, 
			int displayOrder, byte status, int type);
	
	/**
	 * ��������������
	 * 
	 * @param code
	 * @param name
	 * @param parent
	 * @param remark
	 * @param displayOrder
	 * @param status
	 * @param type
	 * @return
	 */
	District saveDistrict(String code, String name, District parent, String remark, 
			int displayOrder, byte status, int type);
}
