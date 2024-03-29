/*
 * $Id: SampleBean1.java 5981 2012-08-10 02:48:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.spring;

/**
 *
 */
public class SampleBean1 {
	private String s1;
	private String s3;
	private Season season;
	/**
	 * @return the s1
	 */
	public String getS1() {
		return s1;
	}
	/**
	 * @param s1 the s1 to set
	 */
	public void setS1(String s1) {
		this.s1 = s1;
	}
	/**
	 * @return the s3
	 */
	public String getS3() {
		return s3;
	}
	/**
	 * @param s3 the s3 to set
	 */
	public void setS3(String s3) {
		this.s3 = s3;
	}
	
	/**
	 * @return the season
	 */
	public Season getSeason() {
		return season;
	}
	/**
	 * @param season the season to set
	 */
	public void setSeason(Season season) {
		this.season = season;
	}

	public static enum Season{
		SPRING,SUMMER,AUTUM,WINTER;
	}
}
