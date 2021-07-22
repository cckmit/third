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
 * ����һ���յ�workProcess����Ҫ����ʾ����дһ��WorkProcess��
 * 
 * @author Alex Lin
 *
 */
//���ע���ǿ�ѡ�ģ�ʹ��AnnotationAwareWorkProcessManager�������á�
@ProcessType(EmptyWorkProcess.TYPE)
public class EmptyWorkProcess extends AbstractWorkProcess {
	
	public EmptyWorkProcess() {
		super();
		System.out.println("�½�����" + this);
	}

	/**
	 * ������һ������ΪTYPE�ľ�̬�ֶΣ��ھ�������п����õ�������ԡ�
	 */
	public static final int TYPE = -1001;
	
	private String name;
	private String abcd;
	
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.WorkProcess#execute(java.util.Map, boolean)
	 */
	@SuppressWarnings("unchecked")
	public Object execute(Map parameters, boolean needLog) {
		System.out.println("��һЩProcess�����顣������" );

		System.out.println("name = " + name);
		System.out.println("abcd = " + abcd);
		return "ִ���˵Ľ�����ַ���";
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
