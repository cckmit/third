/*
 * $Id: YearProgress.java 6102 2012-11-08 01:38:48Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class YearProgress implements Serializable {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = -4622006437341508428L;

	private int year;
	private List<Progress> monthProgressList;
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	public Progress getProgress(int month){
		if(month < 1 || month > 12){
			throw new IllegalArgumentException("�·ݲ���ȷ��" + month);
		}
		
		return monthProgressList.get(month-1);
	}
	public List<Progress> getMonthProgressList() {
		return monthProgressList;
	}
	public void setMonthProgressList(List<Progress> monthProgressList) {
		List<Progress> result = new ArrayList<Progress>();
		for (int i = 0; i < 12; i++) {
			result.add(null);
		}
		if(!monthProgressList.isEmpty()&& monthProgressList.size() > 0){
			for (Progress progress : monthProgressList) {
				Date reportTime = progress.getReportTime();
				Calendar cal=Calendar.getInstance();
				cal.setTime(reportTime);
				int i = cal.get(Calendar.MONTH);
				result.set(i, progress);
			}
		}
		this.monthProgressList = result;
	}

}
