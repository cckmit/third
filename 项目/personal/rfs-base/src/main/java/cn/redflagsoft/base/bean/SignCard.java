package cn.redflagsoft.base.bean;

import static org.opoo.util.BeanUtils.getProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.opoo.apps.Labelable;
import org.opoo.apps.Mappable;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.ObjectType;

import com.googlecode.jsonplugin.annotations.JSON;

/***
 * ����
 * 
 * @author lifeng
 * 
 */
@ObjectType(ObjectTypes.SIGNCARD)
public class SignCard extends LifeStageableObject implements Mappable,
		Labelable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9136915693607208321L;
	public static final int OBJECT_TYPE = ObjectTypes.SIGNCARD;

	public static final short CREATE_TYPE_AUTO = 0;
	public static final short CREATE_TYPE_HAND = 10;
	public static final short CREATE_TYPE_OTHER = 90;

	public static final short DUYTER_TYPE_ENTITY = 1;
	public static final short DUYTER_TYPE_DEPT = 2;
	public static final short DUYTER_TYPE_PEOPLE = 3;
	public static final short GLOSSARY_CATEGORY = 194;

	// private Long id;
	private String code; 						// �ⲿ���
	private Byte grade; 						// �ȼ�
//	private String gradeName;                  // �ȼ�����
	private String label; 						// ����
	private Long rvDutyerOrgId; 				// Υ�����ε�λID
	private String rvDutyerOrgName; 			// Υ�����ε�λ����
	private Long rvDutyerID; 					// Υ��������ID
	private String rvDutyerName; 				// Υ������������
	private Date rvOccurTime; 					// Υ�淢��ʱ��
	private String rvDesc; 					// Υ����ʵ
	private String juralLimit; 				// ��������
	private String finalDealResult; 			// ������
	private Date finalDealTime; 				// ����ʱ��
	private Long dealPersonID; 				// �ڰ���
	private Short createType; 					// ��������
	private Integer refObjectType; 				// ��ض�������
	private Long refObjectId; 					// ��ض�����
	private Long refRiskLogID; 				// ���risklog���

	// private int type; 						//����
	// private byte status; 					//״̬
	// private String remark; 					//��ע
	// private Long creator; 					//������
	// private Date creationTime; 				//����ʱ��
	// private Long modifier; 					//����޸���
	// private Date modificationTime; 			//����޸�ʱ��
	public Byte getGrade() {
		return grade;
	}

	public void setGrade(Byte grade) {
		this.grade = grade;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRvDutyerName() {
		return rvDutyerName;
	}

	public void setRvDutyerName(String rvDutyerName) {
		this.rvDutyerName = rvDutyerName;
	}

	public Long getRvDutyerID() {
		return rvDutyerID;
	}

	public void setRvDutyerID(Long rvDutyerID) {
		this.rvDutyerID = rvDutyerID;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getRvOccurTime() {
		return rvOccurTime;
	}

	public void setRvOccurTime(Date rvOccurTime) {
		this.rvOccurTime = rvOccurTime;
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

	public String getFinalDealResult() {
		return finalDealResult;
	}

	public void setFinalDealResult(String finalDealResult) {
		this.finalDealResult = finalDealResult;
	}

	public Date getFinalDealTime() {
		return finalDealTime;
	}

	public void setFinalDealTime(Date finalDealTime) {
		this.finalDealTime = finalDealTime;
	}

	public Long getDealPersonID() {
		return dealPersonID;
	}

	public void setDealPersonID(Long dealPersonID) {
		this.dealPersonID = dealPersonID;
	}

	public Short getCreateType() {
		return createType;
	}

	public void setCreateType(Short createType) {
		this.createType = createType;
	}

	public Integer getRefObjectType() {
		return refObjectType;
	}

	public void setRefObjectType(Integer refObjectType) {
		this.refObjectType = refObjectType;
	}

	public Long getRefObjectId() {
		return refObjectId;
	}

	public void setRefObjectId(Long refObjectId) {
		this.refObjectId = refObjectId;
	}

	public Long getRefRiskLogID() {
		return refRiskLogID;
	}

	public void setRefRiskLogID(Long refRiskLogID) {
		this.refRiskLogID = refRiskLogID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getRvDutyerOrgId() {
		return rvDutyerOrgId;
	}

	public void setRvDutyerOrgId(Long rvDutyerOrgId) {
		this.rvDutyerOrgId = rvDutyerOrgId;
	}

	public String getRvDutyerOrgName() {
		return rvDutyerOrgName;
	}

	public void setRvDutyerOrgName(String rvDutyerOrgName) {
		this.rvDutyerOrgName = rvDutyerOrgName;
	}

	public String getCreateTypeName() {
		String result = "";
		short createType = 0;
		createType = getCreateType();
		switch (createType) {
		case CREATE_TYPE_AUTO:
			result = "�Զ�����";
			break;
		case CREATE_TYPE_HAND:
			result = "�ֶ�����";
			break;
		case CREATE_TYPE_OTHER:
			result = "����";
			break;
		default:
			break;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Map toMap() {
		return getProperties(this);
	}

	public Serializable getData() {
		return getId();
	}

	public String getTypeLabel() {
		return getLabel();
	}

	public void setTypeLabel(String typeLabel) {
		this.setLabel(typeLabel);
	}

	public String getGradeName() {
	    String result =Risk.getGradeExplain(getGrade());
		return result;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RFSObject#getObjectType()
	 */
	@Override
	public int getObjectType() {
		return OBJECT_TYPE;
	}

//	@Override
//	public LifeStage toLifeStage() {
//		LifeStage lifeStage = super.toLifeStage();
//		lifeStage.setSn(getCode());
//		lifeStage.setName(getLabel());
//		lifeStage.setTypeName(getGradeName());
//		lifeStage.setRefObjectType(getObjectType());
//		lifeStage.setRefObjectId(getId());
//		lifeStage.setRefObjectName(getLabel());
//		lifeStage.setManagerId(getRvDutyerID());
//		lifeStage.setManagerName(getRvDutyerName());
//		
//		return lifeStage;
//	}
	
}
