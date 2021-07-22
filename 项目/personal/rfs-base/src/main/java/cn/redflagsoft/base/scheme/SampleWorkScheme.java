/*
 * $Id: SampleWorkScheme.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import cn.redflagsoft.base.bean.RFSObject;

/**
 * @author Alex Lin
 *
 */
public class SampleWorkScheme extends AbstractWorkScheme {
	private String name;
	
	public Object doScheme(){
		System.out.println("指定doScheme：" + this);
		System.out.println("name==" + name);
		return "OK";
	}
	
	public Object doDefault(){
		System.out.println("指定doScheme：" + this);
		System.out.println("name==" + name);
		return "OK";
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.WorkScheme#getObject()
	 */
	public RFSObject getObject() {
		RFSObject obj = new RFSObject();
		obj.setId(1L);
		return obj;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.Scheme#getDisplayName()
	 */
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}
}
