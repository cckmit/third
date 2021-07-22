package cn.redflagsoft.base.bean;

import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

import cn.redflagsoft.base.ObjectTypes;


/**
 * ��ʾ��֪
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
	
	private Long cautionId;                    //��ʾid
	private String cautionName;                //��ʾ����
	private String cautionCode;                //��ʾ���
	
	private String notifyFileNo;              //��֪���ĺ�
	private Long notifyFileId;                //��֪���ļ�id
	
	private String notifyMsgCode;             //��֪���ű��
	private Long notifyTargetId;              //��֪����id
	private String notifyTargetName;          //��֪��������
	private String notifyContent;             //��֪��������
	
	private Long notifyClerkId;               //��֪��id
	private String notifyClerkName;           //��֪������
	private Date notifyTime;                  //��֪ʱ��
	
	
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
