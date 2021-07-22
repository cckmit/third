package cn.redflagsoft.base.bean;

import java.io.Serializable;
import java.util.Date;

public class SignCardVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7791188681498908041L;
	private Long id;							//���
	private String label;						//����
	private String code;						//�ⲿ���
	private Long ruleID;						//������
	private String ruleName;					//�������
	private Byte grade;							//Υ��ȼ�
	private String gradeName;					//Υ��ȼ�(����)
	private int type;							//Υ������
	private String typeName;					//Υ������(����)
	private Date rvOccurTime;					//Υ��ʱ��
	private Short createType;					//��������
	private String createTypeName;				//��������(����)
	private String rvEntityName;				//Υ�����ε�λ
	private String rvDutyerName;				//Υ��������
	private String telNo;						//�����˵绰
	private String mobNo;						//�������ֻ�
	private String rvDesc;						//Υ����ʵ
	private String juralLimit;					//��������
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getRuleID() {
		return ruleID;
	}
	public void setRuleID(Long ruleID) {
		this.ruleID = ruleID;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Byte getGrade() {
		return grade;
	}
	public void setGrade(Byte grade) {
		this.grade = grade;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Date getRvOccurTime() {
		return rvOccurTime;
	}
	public void setRvOccurTime(Date rvOccurTime) {
		this.rvOccurTime = rvOccurTime;
	}
	public Short getCreateType() {
		return createType;
	}
	public void setCreateType(Short createType) {
		this.createType = createType;
	}
	public String getCreateTypeName() {
		return createTypeName;
	}
	public void setCreateTypeName(String createTypeName) {
		this.createTypeName = createTypeName;
	}
	public String getRvEntityName() {
		return rvEntityName;
	}
	public void setRvEntityName(String rvEntityName) {
		this.rvEntityName = rvEntityName;
	}
	public String getRvDutyerName() {
		return rvDutyerName;
	}
	public void setRvDutyerName(String rvDutyerName) {
		this.rvDutyerName = rvDutyerName;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getMobNo() {
		return mobNo;
	}
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
	public String getRvDesc() {
		return rvDesc;
	}
	public void setRvDesc(String rvDesc) {
		this.rvDesc = rvDesc;
	}
	public String getJuralLimit() {
		return juralLimit;
	}
	public void setJuralLimit(String juralLimit) {
		this.juralLimit = juralLimit;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
