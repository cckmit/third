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
 * 过错
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
	private String code; 						// 外部编号
	private Byte grade; 						// 等级
//	private String gradeName;                  // 等级名称
	private String label; 						// 名称
	private Long rvDutyerOrgId; 				// 违规责任单位ID
	private String rvDutyerOrgName; 			// 违规责任单位名称
	private Long rvDutyerID; 					// 违规责任人ID
	private String rvDutyerName; 				// 违规责任人姓名
	private Date rvOccurTime; 					// 违规发生时间
	private String rvDesc; 					// 违规事实
	private String juralLimit; 				// 法律依据
	private String finalDealResult; 			// 处理结果
	private Date finalDealTime; 				// 处结时间
	private Long dealPersonID; 				// 在办人
	private Short createType; 					// 产生类型
	private Integer refObjectType; 				// 相关对象类型
	private Long refObjectId; 					// 相关对象编号
	private Long refRiskLogID; 				// 相关risklog编号

	// private int type; 						//类型
	// private byte status; 					//状态
	// private String remark; 					//备注
	// private Long creator; 					//创建者
	// private Date creationTime; 				//创建时间
	// private Long modifier; 					//最后修改者
	// private Date modificationTime; 			//最后修改时间
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
			result = "自动产生";
			break;
		case CREATE_TYPE_HAND:
			result = "手动产生";
			break;
		case CREATE_TYPE_OTHER:
			result = "其他";
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
