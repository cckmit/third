package cn.redflagsoft.base.vo;

import java.io.Serializable;
import java.util.Date;

import cn.redflagsoft.base.bean.Task;

public class BizVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2194548465415570797L;
	public static final String SUPERVISESTATUS_DEFAULT = "����";
	public static final byte GRADE_DEFAULT = 0;
	public static final short GLOSSARY_JOB_CODE=111;
	public static final short GLOSSARY_TASK_CODE=112;
	public static final short GLOSSARY_WORK_CODE=113;
	
	private Long order;
	private String code;
	private int type;
	private byte status;	
	private String statusName;
	private Date beginTime;
	private Date endTime;
	private byte result;
	private short timeLimit;
	private short timeUsed;
	private String superviseStatus;
	private String typeName; 
	private String remarks;
	private Long sn;	
	private byte grade;
	private Long clerkID;
	private Long dutyer;
	private String clerkName;
	private String dutyName;
	private String objectSn;//�����ţ���Ŀ��ţ�
	private String objectName;//���������ƣ���Ŀ���ƣ�
	
	
	public String getObjectSn() {
		return objectSn;
	}

	public void setObjectSn(String objectSn) {
		this.objectSn = objectSn;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Long getClerkID() {
		return clerkID;
	}

	public void setClerkID(Long clerkID) {
		this.clerkID = clerkID;
	}

	public Long getDutyer() {
		return dutyer;
	}

	public void setDutyer(Long dutyer) {
		this.dutyer = dutyer;
	}

	public String getClerkName() {
		return clerkName;
	}

	public void setClerkName(String clerkName) {
		this.clerkName = clerkName;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public byte getGrade() {
		return grade;
	}

	public void setGrade(byte grade) {
		this.grade = grade;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * ���
	 * 
	 * @return Long
	 */
	public Long getOrder() {
		return order;
	}
	
	/**
	 * @param order
	 */
	public void setOrder(Long order) {
		this.order = order;
	}
	
	/**
	 * ҵ����
	 * 
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ҵ������
	 * 
	 * @return short
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * ״̬
	 * 
	 * @return byte
	 */
	public byte getStatus() {
		return status;
	}
	
	/**
	 * @param status
	 */
	public void setStatus(byte status) {
		this.status = status;
	}
	
	/**
	 * ��ʼʱ��
	 * 
	 * @return Date
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	
	/**
	 * @param beginTime
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	/**
	 * ����ʱ��
	 * 
	 * @return Date
	 */
	public Date getEndTime() {
		return endTime;
	}
	
	/**
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * ���
	 * 
	 * @return byte
	 */
	public byte getResult() {
		return result;
	}
	
	/**
	 * @param result
	 */
	public void setResult(byte result) {
		this.result = result;
	}
	
	/**
	 * ����ʱ��
	 * 
	 * @return short
	 */
	public short getTimeLimit() {
		return timeLimit;
	}
	
	/**
	 * @param timeLimit
	 */
	public void setTimeLimit(short timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	/**
	 * ʵ����ʱ
	 * 
	 * @return short
	 */
	public short getTimeUsed() {
		return timeUsed;
	}
	
	/**
	 * @param timeUsed
	 */
	public void setTimeUsed(short timeUsed) {
		this.timeUsed = timeUsed;
	}
	
	/**
	 * �����Ϣ
	 * 
	 * @return String
	 */
	public String getSuperviseStatus() {
		return superviseStatus;
	}
	
	/**
	 * @param superviseStatus
	 */
	public void setSuperviseStatus(String superviseStatus) {
		this.superviseStatus = superviseStatus;
	}
	/**
	 * ��������
	 * 
	 * @return String
	 */
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Long getSn() {
		return sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	/**
	 * ״̬����
	 * 
	 * @return String
	 */
	public String getStatusName() {
		byte status=getStatus();
		if(status==Task.TASK_STATUS_WORK){
			statusName="�ڰ�";	
		}else if(status==Task.TASK_STATUS_HANG){
			statusName="��ͣ";
		}else if(status==Task.TASK_STATUS_TERMINATE){			
			statusName="����";
		}else if(status==Task.TASK_STATUS_CANCEL){
			statusName="���";
		}else if(status==Task.TASK_STATUS_STOP){
			statusName="��ֹ";
		}else
			statusName=Byte.toString(status) ;
		
		return statusName;
	}
}
