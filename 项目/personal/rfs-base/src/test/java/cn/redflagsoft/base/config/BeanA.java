/*
 * $Id$
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.config;

import java.util.List;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class BeanA extends AbstractBean {
	private String desc;
	private List<Long> matterIds;
	
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the matterIds
	 */
	public List<Long> getMatterIds() {
		return matterIds;
	}

	/**
	 * @param matterIds the matterIds to set
	 */
	public void setMatterIds(List<Long> matterIds) {
		this.matterIds = matterIds;
	}
}
