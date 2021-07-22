/*
 * $Id: RiskValue.java 5189 2011-12-12 04:52:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Risk��ֵ��
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface RiskValue {

	/**
	 * risk��value
	 * @return
	 */
	BigDecimal getValue();
	
	/**
	 * risk��valueֵ������ʱ��
	 * @return
	 */
	Date getValueGeneratedTime();
	
	/**
	 * risk��valueֵ����Դ
	 * 
	 * @return
	 */
	Object getSource();
}
