/*
 * $Id: Glossary.java 6172 2013-01-10 03:04:16Z lcj $
 * Glossary.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author mwx
 *
 */
public class Glossary extends VersionableBean{
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
	public static final short CATEGORY_MATTERAFFAIR_ACTION=131; //�����±����
	public static final short CATEGORY_MATTERAFFAIR_TASKACTION=132;//����ҵ�����
	public static final short CATEGORY_MATTERAFFAIR_OBJECTACTION=133;//����������
	public static final short CATEGORY_RECEIVER_CATEGORY=141;        //ҵ��ת������
	public static final short CATEGORY_MATTERAFFAIR_BIZACTION=151;   //�����
	public static final short CATEGORY_TASK_STATUS=152; //����״̬
	public static final short CATEGORY_PHRASE_RESULTTYPE=161;     //����������
	public static final short CATEGORY_PROJECT_STATUS=162;        //��Ŀ״̬
	public static final short CATEGORY_DUTYERTYPE=163;            //����������
	public static final short CATEGORY_PROJECT_TYPE=164;          //��Ŀ����
	public static final short CATEGORY_PROJECTPLAN_KIND=165;      //��Ŀ�ƻ�����
	public static final short CATEGORY_RISK_TYPE=171; //��������
	public static final short CATEGORY_RISK_OBJECTTYPE=172;//���ն�������
	public static final short CATEGORY_RISK_DUTYERTYPE=173;//��������������
	public static final short CATEGORY_RISK_VALUETYPE=174; //����ֵ����
	public static final short CATEGORY_RISK_VALUE_SOURCE=175;//����ֵ��Դ
	public static final short CATEGORY_RISK_REFTYPE=176;  //���չ�������
	public static final short CATEGORY_INVESTORGIN=181;   //Ͷ����Դ
	public static final short CATEGORY_OBJECTLIFE_TYPE=1000;   //������������
	public static final short CATEGORY_PROJECT_LIFESTAGE = 1001;//��Ŀ��������
	public static final short CATEGORY_SUBPROJECT_LIFESTAGE = 1002; //����Ŀ��������
	public static final short CATEGORY_BID_LIFESTAGE = 1003;        //�ɹ���������
	public static final short CATEGORY_CONTRACT_LIFESTAGE = 1004;   //��ͬ��������
	public static final short CATEGORY_PROJECTMODIFY_LIFESTAGE = 1005;//��Ŀ�����������
	public static final short CATEGORY_BIZ_DEF_DUTYER_TYPE = 1107;//ҵ����������������
	public static final short CATEGORY_CAUTION_SMSG_YWZR = 1102;
	public static final short CATEGORY_CAUTION_SMSG_TITLE = 1109;
	
	/**
	 * 
	 */
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
	 * 
	 * ��ʮ����������
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	
}
