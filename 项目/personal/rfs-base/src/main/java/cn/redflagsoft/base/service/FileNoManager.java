/*
 * $Id: FileNoManager.java 5508 2012-04-11 08:02:27Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.FileNo;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface FileNoManager {

	//FileNo saveFileNoDef(FileNo def);
	
	/**
	 * ��ȡָ��ID���ĺŶ��塣
	 * @param uid
	 * @return
	 */
	FileNo getFileNo(String uid);
	
	/**
	 * ����ָ��ID���ĺţ������޸��ĺŶ���ĵ�ǰֵ��
	 * ͨ���ڼ��ر�����ʱ�����������������һ���ĺŸ�ǰ̨��ʾ��
	 * @param uid
	 * @return
	 */
	String generateFileNo(String uid);

	/**
	 * ���浱ǰID���һ�����ɵ��ĺš�
	 * ͨ�����ύʱ�������������
	 * ����ĺ����Զ������ģ�����������Խ���������+1��
	 * @param uid
	 * @param lastFileNo
	 * @return
	 */
	FileNo updateFileNo(String uid, String lastFileNo);

	/**
	 * ���������ĺŵ���������Ϊָ��ֵ��
	 * ���磺����ĺŰ���������������ÿ�����������ô��������ֵ����Ϊ0.
	 * @param uid
	 * @param value
	 * @return
	 */
	FileNo resetFileNoAutoIncrementValue(String uid, int value);
}
