package cn.redflagsoft.base.bean;

import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.util.CodeMapUtils;


/**
 * 警示处理决定
 * @author Administrator
 *
 */
public class CautionDecide extends VersionableBean implements RFSItemable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long Id;
	
	private Long cautionId;                  //警示id
	private String cautionName;             //警示名称
	private String cautionCode;            //警示编号

	private String decideFileNo;           //处理决定文件文号
	private Long decideFileId;            //处理决定文件id
	private int decideHandleMode;         //处理决定方式
	
	private Long decideOrgId;             //处理决定单位id
	private String decideOrgName;         //处理决定单位名称
	private Date decideTime;             //处理决定时间

	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public String getDecideFileNo() {
		return decideFileNo;
	}

	public void setDecideFileNo(String decideFileNo) {
		this.decideFileNo = decideFileNo;
	}

	public Long getDecideFileId() {
		return decideFileId;
	}

	public void setDecideFileId(Long decideFileId) {
		this.decideFileId = decideFileId;
	}

	public int getDecideHandleMode() {
		return decideHandleMode;
	}

	public void setDecideHandleMode(int decideHandleMode) {
		this.decideHandleMode = decideHandleMode;
	}
	
	public String getdecideHandleModeName(){
		return CodeMapUtils.getCodeName((short)1099, getDecideHandleMode());
	}

	public Long getDecideOrgId() {
		return decideOrgId;
	}

	public void setDecideOrgId(Long decideOrgId) {
		this.decideOrgId = decideOrgId;
	}

	public String getDecideOrgName() {
		return decideOrgName;
	}

	public void setDecideOrgName(String decideOrgName) {
		this.decideOrgName = decideOrgName;
	}
	@JSON(format = "yyyy-MM-dd")
	public Date getDecideTime() {
		return decideTime;
	}

	public void setDecideTime(Date decideTime) {
		this.decideTime = decideTime;
	}

	public CautionDecide(Long cautionId, String cautionName) {
		this.cautionId = cautionId;
		this.cautionName = cautionName;
	}
	
	public CautionDecide() {
	}
	
	public int getObjectType() {
		return ObjectTypes.OBJECT_CAUTIONDECIDE;
	}

}
