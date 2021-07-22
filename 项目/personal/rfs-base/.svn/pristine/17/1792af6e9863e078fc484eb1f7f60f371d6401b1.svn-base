package cn.redflagsoft.base.bean;

import java.util.Date;

import cn.redflagsoft.base.util.CodeMapUtils;

/****
 * 	过错纠错告知	
 * 
 * @author lifeng
 *
 */
public class SignCardNotice extends VersionableBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public static final byte NOTICETYPE_手机短信息 = 1;
	public static final byte NOTICETYPE_电话直接告知 = 2;
	public static final byte NOTICETYPE_电子邮件 = 3;
	public static final byte NOTICETYPE_书面告知 = 4;
	public static final byte NOTICETYPE_其他 = 99;
	
	// private Long id; 							//
	private Long signCardId; 						// 过错编号
	private Long noticeDutyerId;					//告知人编号
	private String noticeDutyerName;				//告知人姓名
	private byte noticeType;						//告知方式：1-手机短信息、2-电话直接告知、3-电子邮件、4-书面告知、99-其他
	private String noticeWay;						//告知途径/地址
	private String noticeContent;					//告知内容
	private Long noticeCreator;						//告知信息登记人
	private Date noticeActualTime;					//告知时间
	private Date noticeTime;						//告知系统操作时间
	private String noticeRemark;					//告知备注

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

	public Long getNoticeDutyerId() {
		return noticeDutyerId;
	}

	public void setNoticeDutyerId(Long noticeDutyerId) {
		this.noticeDutyerId = noticeDutyerId;
	}

	public String getNoticeDutyerName() {
		return noticeDutyerName;
	}

	public void setNoticeDutyerName(String noticeDutyerName) {
		this.noticeDutyerName = noticeDutyerName;
	}

	public byte getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(byte noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeWay() {
		return noticeWay;
	}

	public void setNoticeWay(String noticeWay) {
		this.noticeWay = noticeWay;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Long getNoticeCreator() {
		return noticeCreator;
	}

	public void setNoticeCreator(Long noticeCreator) {
		this.noticeCreator = noticeCreator;
	}

	public Date getNoticeActualTime() {
		return noticeActualTime;
	}

	public void setNoticeActualTime(Date noticeActualTime) {
		this.noticeActualTime = noticeActualTime;
	}

	public Date getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}

	public String getNoticeRemark() {
		return noticeRemark;
	}

	public void setNoticeRemark(String noticeRemark) {
		this.noticeRemark = noticeRemark;
	}
	
	public String getNoticeTypeName(){
		return CodeMapUtils.getCodeName(SignCardNotice.class, "NOTICETYPE", getNoticeType());
	}
}
