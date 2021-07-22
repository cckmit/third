package cn.redflagsoft.base.bean;

import java.util.Date;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.util.CodeMapUtils;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 事实复核
 * @author Administrator
 *
 */
public class CautionCheck extends VersionableBean implements RFSItemable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final short OBJECT_TYPE = ObjectTypes.OBJECT_CAUTIONCHECK;
	
	public static final String CHECK_ERROR = "情况有误";
	public static final String CHECK_OK = "情况基本属实";
	
	public static final String CHECK_RESULT_TRUE = "1";
	public static final String CHECK_RESULT_FALSE = "2";
	
	private Long id;
	
	private Long cautionId;                    //警示id
	private String cautionName;                //警示名称
	private String cautionCode;                //警示编号
	
	private Long checkFileId;                 //复核文件id
	private String checkFileNo;             //复核文编号
	
	private String checkResult;               //复核结果
	private Long checkerId;                   //复核人id
	private String checkerName;               //复核人
	private Date checkTime;                   //复核时间
	
	private Long checkOrgId;                  //复核单位id
	private String checkOrgName;              //复核单位
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCautionId() {
		return cautionId;
	}
	public void setCautionId(Long cautionId) {
		this.cautionId = cautionId;
	}
	public String getCautionName() {
		return cautionName;
	}
	public void setCautionName(String cautionName) {
		this.cautionName = cautionName;
	}
	public String getCautionCode() {
		return cautionCode;
	}
	public void setCautionCode(String cautionCode) {
		this.cautionCode = cautionCode;
	}
	public Long getCheckFileId() {
		return checkFileId;
	}
	public void setCheckFileId(Long checkFileId) {
		this.checkFileId = checkFileId;
	}
	public String getCheckFileNo() {
		return checkFileNo;
	}
	public void setCheckFileNo(String checkFileNo) {
		this.checkFileNo = checkFileNo;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	
	public String getCheckResultName() {
		return CodeMapUtils.getCodeName((short)1098, getCheckResult());
	}
	
	public Long getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(Long checkerId) {
		this.checkerId = checkerId;
	}
	
	public String getCheckerName() {
		return checkerName;
	}
	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}
	@JSON(format = "yyyy-MM-dd")
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public Long getCheckOrgId() {
		return checkOrgId;
	}
	public void setCheckOrgId(Long checkOrgId) {
		this.checkOrgId = checkOrgId;
	}
	public String getCheckOrgName() {
		return checkOrgName;
	}
	public void setCheckOrgName(String checkOrgName) {
		this.checkOrgName = checkOrgName;
	}
	
	
	public CautionCheck(Long cautionId, String cautionName) {
		this.cautionId = cautionId;
		this.cautionName = cautionName;
	}
	
	public CautionCheck() {
	}
	
	public int getObjectType() {
		return OBJECT_TYPE;
	}
	
}
