/*
 * $Id: EmptyWorkProcess.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process;

import cn.redflagsoft.base.process.annotation.ProcessType;

import java.util.Map;

/**
 * 这是一个空的workProcess，主要是演示怎样写一个WorkProcess。
 * 
 * @author Alex Lin
 *
 */
//这个注释是可选的，使用AnnotationAwareWorkProcessManager必须设置。
@ProcessType(EmptyWorkProcess.TYPE)
public class EmptyWorkProcess extends AbstractWorkProcess {
	
	public EmptyWorkProcess() {
		super();
		System.out.println("新建对象：" + this);
	}

	/**
	 * 必须有一个名称为TYPE的静态字段，在具体操作中可能用到这个属性。
	 */
	public static final int TYPE = -1001;
	
	private String name;
	private String abcd;
	
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.WorkProcess#execute(java.util.Map, boolean)
	 */
	@SuppressWarnings("unchecked")
	public Object execute(Map parameters, boolean needLog) {
		System.out.println("做一些Process的事情。。。。" );

		System.out.println("name = " + name);
		System.out.println("abcd = " + abcd);
		return "执行了的结果是字符串";
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

	/**
	 * @return the abcd
	 */
	public String getAbcd() {
		return abcd;
	}

	/**
	 * @param abcd the abcd to set
	 */
	public void setAbcd(String abcd) {
		this.abcd = abcd;
	}
}
