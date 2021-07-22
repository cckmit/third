package cn.redflagsoft.base.bean;

import java.util.Date;

/****
 * 	��������׷��	
 * 
 * @author lifeng
 *
 */
public class SignCardPenalty extends VersionableBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private Long id; 								//
	private Long signCardId; 							// ������
	private Long penaltyDutyerId;						// �����˱��
	private String penaltyDutyerName;					// ����������
	private String penaltySummary;						// �������ժҪ
	private String penaltyFileNo;						// ���������ĺ�
	private Long penaltyCreator;						// ������Ϣ�Ǽ���
	private Date penaltyActualTime;						// ����ʱ��
	private Date penaltyTime;							// ����ϵͳ����ʱ��
	private String penaltyRemark;						// ����ע

	// private int type; 								//����
	// private byte status; 							//״̬
	// private String remark; 							//��ע
	// private Long creator; 							//������
	// private Date creationTime; 						//����ʱ��
	// private Long modifier; 							//����޸���
	// private Date modificationTime; 					//����޸�ʱ��
	
	public Long getSignCardId() {
		return signCardId;
	}

	public void setSignCardId(Long signCardId) {
		this.signCardId = signCardId;
	}

	public Long getPenaltyDutyerId() {
		return penaltyDutyerId;
	}

	public void setPenaltyDutyerId(Long penaltyDutyerId) {
		this.penaltyDutyerId = penaltyDutyerId;
	}

	public String getPenaltyDutyerName() {
		return penaltyDutyerName;
	}

	public void setPenaltyDutyerName(String penaltyDutyerName) {
		this.penaltyDutyerName = penaltyDutyerName;
	}

	public String getPenaltySummary() {
		return penaltySummary;
	}

	public void setPenaltySummary(String penaltySummary) {
		this.penaltySummary = penaltySummary;
	}

	public String getPenaltyFileNo() {
		return penaltyFileNo;
	}

	public void setPenaltyFileNo(String penaltyFileNo) {
		this.penaltyFileNo = penaltyFileNo;
	}

	public Long getPenaltyCreator() {
		return penaltyCreator;
	}

	public void setPenaltyCreator(Long penaltyCreator) {
		this.penaltyCreator = penaltyCreator;
	}

	public Date getPenaltyActualTime() {
		return penaltyActualTime;
	}

	public void setPenaltyActualTime(Date penaltyActualTime) {
		this.penaltyActualTime = penaltyActualTime;
	}

	public Date getPenaltyTime() {
		return penaltyTime;
	}

	public void setPenaltyTime(Date penaltyTime) {
		this.penaltyTime = penaltyTime;
	}

	public String getPenaltyRemark() {
		return penaltyRemark;
	}

	public void setPenaltyRemark(String penaltyRemark) {
		this.penaltyRemark = penaltyRemark;
	}

}
