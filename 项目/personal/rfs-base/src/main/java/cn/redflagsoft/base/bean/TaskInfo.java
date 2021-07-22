/*
 * $Id: TaskInfo.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;



/**
 * ���񸽼���Ϣ�ӿڡ�
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface TaskInfo extends Taskable {

	/**
	 * ��ȡ��ǰ�������������SN.
	 * @return
	 */
	Long getTaskSN();
	
	/**
	 * ���õ�ǰ�������������SN.
	 * @param taskSN
	 */
	void setTaskSN(Long taskSN);
}
