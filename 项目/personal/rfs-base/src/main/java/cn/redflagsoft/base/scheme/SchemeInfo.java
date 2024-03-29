/*
 * $Id: SchemeInfo.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SchemeInfo {
	private String name;
	private String method;

	public SchemeInfo(String name, String method) {
		this.name = name;
		this.method = method;
	}

	public String getName() {
		return name;
	}

	public String getMethod() {
		return method;
	}

	public String toString() {
		if(method != null){
			return name + "!" + method;
		}
		return name;
	}
}
