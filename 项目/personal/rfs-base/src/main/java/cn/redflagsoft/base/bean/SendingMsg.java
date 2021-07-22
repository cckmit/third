package cn.redflagsoft.base.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.opoo.ndao.Domain;

import com.googlecode.jsonplugin.annotations.JSON;


/**
 * 
 * @deprecated
 */
public class SendingMsg extends VersionableBean implements Domain<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8374850691440005760L;
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	/**
	 * �����棬�ݸ塣
	 * ��ʱ��Ϣ�Կ��Ա༭��
	 * ��״̬��ǰָ̨����
	 */
	public static final byte STATUS_SAVED = 0;
	/**
	 * ��Ϣ�Ѿ����ͣ���ʱ�����ٱ༭��
	 * ��״̬��ϵͳ�Զ����ġ�
	 */
	public static final byte STATUS_SENT = 1;
	/**
	 * ��Ϣ�Ѿ�׼�����ˣ����Է��͡�
	 * ��״̬��ǰָ̨����Ҫ����������Ϣʱ�������״̬����Ϊready��
	 * ����ʱ������̨���ͺͷֲ����
	 */
	public static final byte STATUS_READY = 2;
	
	/*
	 * �Ѿ������Ҵ��ֹ�ȷ���Ƿ���
	 */
	public static final byte STATUS_CONFIRM = 9;

	/*
	 * ��Ϣ����
	 */
	public static final byte STATUS_CANCEL = 21;

	/*
	 * ����
	 */
	public static final byte STATUS_OTHERS = 99;

	
//	//��Ϣ���ͣ�0������Ϣ, 100���棬101֪ͨ
//	public static final int TYPE_PERSONAL = 0;
//	public static final int TYPE_NOTICE = 100;
//	public static final int TYPE_ANNOUNCEMENT = 101;
	
	public static final String RECEIVERS_NOTICE = Msg.TOTYPE_PUBLIC + ":" + Msg.TOID_NOTICE;
	public static final String RECEIVERS_ANNOUNCEMENT = Msg.TOTYPE_PUBLIC + ":" + Msg.TOID_ANNOUNCEMENT;
	
	
	private Long id;
	private boolean supportsEmail;//�Ƿ��ʼ���Ĭ��false
	private boolean supportsSms;//�Ƿ񷢶��ţ�Ĭ��false
	private String receivers;//�����ʹ�÷ֺŷָ�, 0:clerkid0;0:clerkid1;1:orgid;��0��ʾ���ˣ�1��ʾ��λ��9��ʾȫ��������ֻ֪ͨ��һ��
	private String emailTo;//�ʼ���ַ�����ʹ�÷ֺŸ�����ĿǰΪ��
	private String smsTo;//���Ž����ֻ����룬���ʹ�÷ֺŸ�����ĿǰΪ��
	private String title;//��Ϣ(���桢֪ͨ���ʼ�)���⣬����ֻʹ�ñ���
	private String content;//��Ϣ����
	private Long fromId;//���� ��
//	private int type = 0;//����ֶ���ʱ���ã�
	private String fromName;//����������
	private boolean attached;
	private Date publishTime;//����ʱ��		
	private Date expirationTime;//����ʱ��
	
	
	
	
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getExpirationTime() {
		return expirationTime;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getPublishTime() {
		return publishTime;
	}


	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}


	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isSupportsEmail() {
		return supportsEmail;
	}
	
	public void setSupportsEmail(boolean supportsEmail) {
		this.supportsEmail = supportsEmail;
	}
	
	public boolean isSupportsSms() {
		return supportsSms;
	}
	
	public void setSupportsSms(boolean supportsSms) {
		this.supportsSms = supportsSms;
	}
	
	public String getReceivers() {
		return receivers;
	}
	
	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}
	
	public String getEmailTo() {
		return emailTo;
	}
	
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	
	public String getSmsTo() {
		return smsTo;
	}
	
	public void setSmsTo(String smsTo) {
		this.smsTo = smsTo;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

//	/**
//	 * @return the type
//	 */
//	public int getType() {
//		return type;
//	}
//
//	/**
//	 * @param type the type to set
//	 */
//	public void setType(int type) {
//		this.type = type;
//	}

	/**
	 * @return the fromName
	 */
	public String getFromName() {
		return fromName;
	}

	/**
	 * @param fromName the fromName to set
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	/**
	 * @return the attached
	 */
	public boolean isAttached() {
		return attached;
	}

	/**
	 * @param attached the attached to set
	 */
	public void setAttached(boolean attached) {
		this.attached = attached;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getCreationTime(){
		return super.getCreationTime();
	}
	
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getModificationTime(){
		return super.getModificationTime();
	}
	
	
	public String getStatusName(){
		byte status = getStatus();
		if(status == SendingMsg.STATUS_READY){
			return "�ɷ���";
		}else if(status == SendingMsg.STATUS_SAVED){
			return "�ݸ�";
		}else if(status == SendingMsg.STATUS_SENT){
			return "�ѷ���";
		}else if(status == SendingMsg.STATUS_CONFIRM){
			return "��ȷ��";
		}else if(status == SendingMsg.STATUS_CANCEL){
			return "������";
		}else{
			return "����";
		}
	}
	public void setPublishTimeStr(String publishTimeStr){
		if(publishTimeStr!=null){
			try{
				if(publishTimeStr.length()<=10){
					publishTimeStr+=" 00:00";
				}
				Date publishTime=sdf.parse(publishTimeStr);
				
				if(publishTime!=null){
					setPublishTime(publishTime);
				}
			}catch(Exception e){
				
			}
			
		}
		
	}
	public void setExpirationTimeStr(String expirationTimeStr){
		if(expirationTimeStr!=null){
			try{
				if(expirationTimeStr.length()<=10){
					expirationTimeStr+=" 00:00";
				}
				Date expirationTime=sdf.parse(expirationTimeStr);
				if(expirationTime!=null){
					setExpirationTime(expirationTime);
				}
			}catch(Exception e){
				
			}
			
		}
		
	}
	public String getPublishTimeStr(){
		if(getPublishTime()!=null){
			return sdf.format(getPublishTime());
		}
		return "";
	}
	
	public String getExpirationTimeStr(){
		if(getExpirationTime()!=null){
			return sdf.format(getExpirationTime());
		}
		return "";
	}
}
