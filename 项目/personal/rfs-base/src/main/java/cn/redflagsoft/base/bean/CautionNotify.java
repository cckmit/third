package cn.redflagsoft.base.bean;

import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

import cn.redflagsoft.base.ObjectTypes;


/**
 * 警示告知
 * @author Administrator
 *
 */
public class CautionNotify extends VersionableBean implements RFSItemable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int TYPE_SMS_NOTIFY = 1;
	public static final int TYPE_BOOK_NOYIFY = 2;
	
	private Long id;
	
	private Long cautionId;                    //警示id
	private String cautionName;                //警示名称
	private String cautionCode;                //警示编号
	
	private String notifyFileNo;              //告知书文号
	private Long notifyFileId;                //告知书文件id
	
	private String notifyMsgCode;             //告知短信编号
	private Long notifyTargetId;              //告知对象id
	private String notifyTargetName;          //告知对象名称
	private String notifyContent;             //告知对象内容
	
	private Long notifyClerkId;               //告知人id
	private String notifyClerkName;           //告知人名字
	private Date notifyTime;                  //告知时间
	
	
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
	public String getNotifyFileNo() {
		return notifyFileNo;
	}
	public void setNotifyFileNo(String notifyFileNo) {
		this.notifyFileNo = notifyFileNo;
	}
	public Long getNotifyFileId() {
		return notifyFileId;
	}
	public void setNotifyFileId(Long notifyFileId) {
		this.notifyFileId = notifyFileId;
	}
	public String getNotifyMsgCode() {
		return notifyMsgCode;
	}
	public void setNotifyMsgCode(String notifyMsgCode) {
		this.notifyMsgCode = notifyMsgCode;
	}
	public Long getNotifyTargetId() {
		return notifyTargetId;
	}
	public void setNotifyTargetId(Long notifyTargetId) {
		this.notifyTargetId = notifyTargetId;
	}
	public String getNotifyTargetName() {
		return notifyTargetName;
	}
	public void setNotifyTargetName(String notifyTargetName) {
		this.notifyTargetName = notifyTargetName;
	}
	public String getNotifyContent() {
		return notifyContent;
	}
	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}
	public Long getNotifyClerkId() {
		return notifyClerkId;
	}
	public void setNotifyClerkId(Long notifyClerkId) {
		this.notifyClerkId = notifyClerkId;
	}
	public String getNotifyClerkName() {
		return notifyClerkName;
	}
	public void setNotifyClerkName(String notifyClerkName) {
		this.notifyClerkName = notifyClerkName;
	}
	
	@JSON(format = "yyyy-MM-dd")
	public Date getNotifyTime() {
		return notifyTime;
	}
	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}
	
	public CautionNotify(Long cautionId, String cautionName) {
		this.cautionId = cautionId;
		this.cautionName = cautionName;
	}
	
	public CautionNotify() {
	}
	
	public int getObjectType() {
		return ObjectTypes.OBJECT_CAUTIONNOTIFY;
	}
}
