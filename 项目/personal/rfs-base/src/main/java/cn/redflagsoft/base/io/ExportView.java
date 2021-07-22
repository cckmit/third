/*
 * $Id: ExportView.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.io;

import java.io.InputStream;

import org.opoo.apps.Model;

/**
 * @author Alex Lin
 *
 */
public interface ExportView {
	/**
	 * 
	 * @return
	 */
	String getMimeType();
	
	/**
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	InputStream doExport(Model model) throws Exception;
}
