package cn.redflagsoft.base.bean;

import java.util.Date;

import cn.redflagsoft.base.util.CodeMapUtils;

/****
 * 	��������֪	
 * 
 * @author lifeng
 *
 */
public class SignCardNotice extends VersionableBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public static final byte NOTICETYPE_�ֻ�����Ϣ = 1;
	public static final byte NOTICETYPE_�绰ֱ�Ӹ�֪ = 2;
	public static final byte NOTICETYPE_�����ʼ� = 3;
	public static final byte NOTICETYPE_�����֪ = 4;
	public static final byte NOTICETYPE_���� = 99;
	
	// private Long id; 							//
	private Long signCardId; 						// ������
	private Long noticeDutyerId;					//��֪�˱��
	private String noticeDutyerName;				//��֪������
	private byte noticeType;						//��֪��ʽ��1-�ֻ�����Ϣ��2-�绰ֱ�Ӹ�֪��3-�����ʼ���4-�����֪��99-����
	private String noticeWay;						//��֪;��/��ַ
	private String noticeContent;					//��֪����
	private Long noticeCreator;						//��֪��Ϣ�Ǽ���
	private Date noticeActualTime;					//��֪ʱ��
	private Date noticeTime;						//��֪ϵͳ����ʱ��
	private String noticeRemark;					//��֪��ע

	// private int type; 							//����
	// private byte status; 						//״̬
	// private String remark; 						//��ע
	// private Long creator; 						//������
	// private Date creationTime; 					//����ʱ��
	// private Long modifier; 						//����޸���
	// private Date modificationTime; 				//����޸�ʱ��
	
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
