/*
 * $Id: Glossary.java 6172 2013-01-10 03:04:16Z lcj $
 * Glossary.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package springold.bean;

import springold.base.version.VersionableBean;

/**
 * @author mwx
 *
 */
public class Glossary extends VersionableBean {
	/**
	 * 
	 */
	
	public static final short CATEGORY_AFFAIR = 101;         //ҵ���±�
	public static final short CATEGORY_MATTER = 102;         //ҵ������	
	public static final short CATEGORY_BIZTYPE=110;          //ҵ�����
	public static final short CATEGORY_JOBTYPE=111;          //��ҵ����
	public static final short CATEGORY_TASKTYPE=112;         //��������
	public static final short CATEGORY_WORKTYPE=113;         //��������
	public static final short CATEGORY_PROCESSTYPE=114;      //��������

	private static final long serialVersionUID = -9021132824200466813L;
	private Long rn;
	private short category;
	private short displayOrder;
	private int code;
	private String term;
	/**
	 * Ψһ��ʾ
	 * 
	 * @return Long
	 */
	public Long getId() {
		return getRn();
	}
	
	/**
	 * @param id
	 */
	public void setId(Long id) {
		setRn(id);
	}
	/**
	 * @return the rn
	 */
	public Long getRn() {
		return rn;
	}
	/**��¼���
	 * @param rn the rn to set
	 */
	public void setRn(Long rn) {
		this.rn = rn;
	}
	/**
	 * @return the category
	 */
	public short getCategory() {
		return category;
	}
	/**
	 * ����
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @param category the category to set
	 */
	public void setCategory(short category) {
		this.category = category;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * ����
	 * 
	 * ����
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the displayOrder
	 */
	public short getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * ˳��
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(short displayOrder) {
		this.displayOrder = displayOrder;
	}
	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}
	/**
	 * ����
	 * ��ʮ����������
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	
}
