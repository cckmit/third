package cn.redflagsoft.base.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.opoo.ndao.Domain;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 
 * @author PY
 * @deprecated
 */
public class Msg extends VersionableBean implements Domain<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7922135171456376335L;
	
	public static final byte TOTYPE_CLERK = 0;
	public static final byte TOTYPE_ORG = 1;
	public static final byte TOTYPE_PUBLIC = 9;
	
	/**
	 * ������IDΪ�����ģ�һ����ָ���棬֪ͨ
	 */
	//public static final long TOID_PUBLIC = 0;
	public static final long TOID_NOTICE = 1;
	public static final long TOID_ANNOUNCEMENT = 0;
	
	public static final byte SEND_STATUS_UNSENT = 0;
	public static final byte SEND_STATUS_SEND = 1;
	public static final byte SEND_STATUS_SENDING = 2;
	public static final byte SEND_STATUS_FAIL = 3;
	public static final byte SEND_STATUS_DISABLE = -1;
	
	public static final byte READ_STATUS_UNREAD = 0;
	public static final byte READ_STATUS_READ = 1;
	
	public static final short NOTICE = 0;
	
	public static final int TRY_SEND_COUNT_MAX=2;

	
	//��Ϣ���ͣ�0�ڲ���Ϣ��1����Ϣ��2�����ʼ�
	public static final int TYPE_INTERNAL = 0;
	public static final int TYPE_SMS = 1;
	public static final int TYPE_EMAIL = 2;
	
	//�� 100���棬101֪ͨ �����ڲ���Ϣ��ȥ��
	//public static final int TYPE_NOTICE = 100;
	//public static final int TYPE_ANNOUNCEMENT = 101;

	

	private Long id;				//��Ϣid
	private Long sendingMsgId;		//��Ϣ����id
	private String title;			//��Ϣ(���桢֪ͨ���ʼ�)���⣬����ֻʹ�ñ���
	private String content;			//��Ϣ����
	private Long toId;				//�����ߣ�0��1��ʾȫ���������桢֪ͨ�ȣ�������ʾ��λid����clerkID
	private byte toType;			//���������ͣ�0��ʾ���ˣ�1��ʾ��λ��9���������ˣ��繫��֪ͨ��
	private String toAddr;			//�����ߵ�ַ���ʼ���ַ���ֻ����룬�ڲ���Ϣ��
	private String toName;			//���������������յ�λ����
	private Long fromId;			//������id
	private byte sendStatus;		//����״̬��0δ���ͣ�1�ѷ��ͣ�2�����ͣ�3ʧ�ܰ������� ��-1ʧЧ�����Գ���������)
	private Date sendTime;			//����ʱ��
	private byte readStatus;		//�Ķ�״̬��0δ����1�Ѷ����ڲ���Ϣ��Ч
	private Long reader;			//����Ϣ�Ľ������ǲ���ʱ������ֶμ�¼�ò��ŵ�һ���Ķ�����id,�ڲ���Ϣ��Ч
	private Date readTime;			//�Ķ�ʱ�䣬�ڲ���Ϣ��Ч
	private boolean attached;		//�Ƿ��������
	private int type = 0;			//��Ϣ���ͣ�0�ڲ���Ϣ��1����Ϣ��2�����ʼ�
	private String fromName;		//����������
	private Date publishTime;		//����ʱ��		
	private Date expirationTime;	//����ʱ��
	private int trySendCount;		//���Է��ʹ���
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
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
	
	public void setToIdLabel(String toIdLabel){
		setToName(toIdLabel);
	}
	
	public int getTrySendCount() {
		return trySendCount;
	}

	public void setTrySendCount(int trySendCount) {
		this.trySendCount = trySendCount;
	}

	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getCreationTime2(){
		return this.getCreationTime();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}
	
	public String getToIdName(){
		if(toId == Msg.TOID_ANNOUNCEMENT){
			return "����";
		}else if(toId == Msg.TOID_NOTICE){
			return "֪ͨ";
		}else{
			return null;
		}
	}
	
	public void setToIdName(String value){
		
	}
	

	public byte getToType() {
		return toType;
	}

	public void setToType(byte toType) {
		this.toType = toType;
	}

	public String getToAddr() {
		return toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public Long getSendingMsgId() {
		return sendingMsgId;
	}

	public void setSendingMsgId(Long sendingMsgId) {
		this.sendingMsgId = sendingMsgId;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public byte getSendStatus() {
		return sendStatus;
	}
	
	public String getSendTimeStr() {
		 if(getSendTime()!=null){
			return sdf.format(getSendTime());
		}
		return "";
	}
	public void setSendTimeStr(String sendTimeStr) {
		if(sendTimeStr!=null){
			try{
				if(sendTimeStr.length()<=10){
					sendTimeStr+=" 00:00";
				}
				Date sendTime=sdf.parse(sendTimeStr);
				if(sendTime!=null){
					setSendTime(sendTime);
				}
			}catch(Exception e){
				
			}
			
		}
	}
	
	public void setSendStatus(byte sendStatus) {
		this.sendStatus = sendStatus;
	}
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public byte getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(byte readStatus) {
		this.readStatus = readStatus;
	}
	
	public String getReadStatusName(){
		if(readStatus == Msg.READ_STATUS_READ){
			return "�Ѷ�";
		}else if(readStatus == Msg.READ_STATUS_UNREAD){
			return "δ��";
		}else{
			return null;
		}
	}
	
	public void setReadStatusName(String value){
		
	}

	public Long getReader() {
		return reader;
	}

	public void setReader(Long reader) {
		this.reader = reader;
	}
	
	
	public String getReadTimeStr() {
		 if(getReadTime()!=null){
			return sdf.format(getReadTime());
		}
		return "";
	}
	public void setReadTimeStr(String readTimeStr) {
		if(readTimeStr!=null){
			try{
				if(readTimeStr.length()<=10){
					readTimeStr+=" 00:00";
				}
				Date readTime=sdf.parse(readTimeStr);
				if(readTime!=null){
					setReadTime(readTime);
				}
			}catch(Exception e){
				
			}
			
		}
	}
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getReadTime() {
		return readTime;
	}
	
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public boolean isAttached() {
		return attached;
	}
	
	public String getAttachedName(){
		return attached ? "�и���" : "�޸���";
	}
	


	public void setAttached(boolean attached) {
		this.attached = attached;
	}
	
	public void setAttachedName(String value){
		
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	public String getTypeName(){
		switch(type){
		case TYPE_INTERNAL:return "�ڲ���Ϣ";
		case TYPE_SMS:return "����Ϣ";
		case TYPE_EMAIL:return "�����ʼ�";
		}
		return "";
	}
	
	public String getSendStatusName(){
		switch(sendStatus){
		case SEND_STATUS_UNSENT:return "δ����";
		case SEND_STATUS_SEND:return "�ѷ���";
		case SEND_STATUS_SENDING:return "������";
		case SEND_STATUS_FAIL:return "ʧ��";
		case SEND_STATUS_DISABLE:return "����";
		}
		return "";
	}
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
	
	public String toString(){
		return (new ToStringBuilder(id)).append("toAddr", toAddr)
		.append("title", title).append("content", content).toString();
	}
}
