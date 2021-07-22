package cn.redflagsoft.base.bean;

import java.util.Date;

import cn.redflagsoft.base.bean.VersionableBean;
import cn.redflagsoft.base.util.CodeMapUtils;

/**
 * 	过错受理
 * 	
 * @author lifeng
 *
 */
public class SignCardAccept extends VersionableBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final byte ACCEPTTYPE_受理 = 1;
	public static final byte ACCEPTTYPE_不受理 = 5;
	// private Long id; 							//

	private Long signCardId;						//过错编号
	private Long acceptDutyerId;					//受理人编号
	private String acceptDutyerName;				//受理人姓名
	private byte acceptType;						//受理类型：1-受理，5-不受理
	private String acceptFileNo;					//受理回执编号
	private Long acceptCreator;						//受理信息登记人
	private Date acceptActualTime;					//受理实际时间
	private Date acceptTime;						//受理系统操作时间
	private String acceptRemark;					//受理备注

	// private int type; 							//类型
	// private byte status; 						//状态
	// private String remark; 						//备注
	// private Long creator; 						//创建者
	// private Date creationTime; 					//创建时间
	// private Long modifier; 						//最后修改者
	// private Date modificationTime; 				//最后修改时间
	public Long getSignCardId() {
		return signCardId;
	}

	public void setSignCardId(Long signCardId) {
		this.signCardId = signCardId;
	}

	public Long getAcceptDutyerId() {
		return acceptDutyerId;
	}

	public void setAcceptDutyerId(Long acceptDutyerId) {
		this.acceptDutyerId = acceptDutyerId;
	}

	public String getAcceptDutyerName() {
		return acceptDutyerName;
	}

	public void setAcceptDutyerName(String acceptDutyerName) {
		this.acceptDutyerName = acceptDutyerName;
	}

	public byte getAcceptType() {
		return acceptType;
	}

	public void setAcceptType(byte acceptType) {
		this.acceptType = acceptType;
	}

	public String getAcceptFileNo() {
		return acceptFileNo;
	}

	public void setAcceptFileNo(String acceptFileNo) {
		this.acceptFileNo = acceptFileNo;
	}

	public Long getAcceptCreator() {
		return acceptCreator;
	}

	public void setAcceptCreator(Long acceptCreator) {
		this.acceptCreator = acceptCreator;
	}

	public Date getAcceptActualTime() {
		return acceptActualTime;
	}

	public void setAcceptActualTime(Date acceptActualTime) {
		this.acceptActualTime = acceptActualTime;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getAcceptRemark() {
		return acceptRemark;
	}

	public void setAcceptRemark(String acceptRemark) {
		this.acceptRemark = acceptRemark;
	}

	public String getAcceptTypeName() {
		return CodeMapUtils.getCodeName(SignCardAccept.class, "ACCEPTTYPE", getAcceptType());
	}
}
