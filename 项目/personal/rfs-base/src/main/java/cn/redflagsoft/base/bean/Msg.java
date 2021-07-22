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
	 * 接收者ID为公共的，一般是指公告，通知
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

	
	//消息类型：0内部消息，1短信息，2电子邮件
	public static final int TYPE_INTERNAL = 0;
	public static final int TYPE_SMS = 1;
	public static final int TYPE_EMAIL = 2;
	
	//， 100公告，101通知 属于内部消息，去掉
	//public static final int TYPE_NOTICE = 100;
	//public static final int TYPE_ANNOUNCEMENT = 101;

	

	private Long id;				//消息id
	private Long sendingMsgId;		//消息发送id
	private String title;			//消息(公告、通知、邮件)标题，短信只使用标题
	private String content;			//消息正文
	private Long toId;				//接收者，0，1表示全部，即公告、通知等，其他表示单位id或者clerkID
	private byte toType;			//接收者类型，0表示个人，1表示单位，9便是所有人（如公告通知）
	private String toAddr;			//接收者地址，邮件地址，手机号码，内部消息无
	private String toName;			//接收者姓名，接收单位名称
	private Long fromId;			//发布者id
	private byte sendStatus;		//发送状态，0未发送，1已发送，2正发送，3失败包括各种 ，-1失效（重试超过最大次数)
	private Date sendTime;			//发送时间
	private byte readStatus;		//阅读状态，0未读，1已读，内部消息有效
	private Long reader;			//当消息的接收人是部门时，这个字段记录该部门第一个阅读的人id,内部消息有效
	private Date readTime;			//阅读时间，内部消息有效
	private boolean attached;		//是否包含附件
	private int type = 0;			//消息类型：0内部消息，1短信息，2电子邮件
	private String fromName;		//发送者姓名
	private Date publishTime;		//发布时间		
	private Date expirationTime;	//过期时间
	private int trySendCount;		//尝试发送次数
	
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
			return "公告";
		}else if(toId == Msg.TOID_NOTICE){
			return "通知";
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
			return "已读";
		}else if(readStatus == Msg.READ_STATUS_UNREAD){
			return "未读";
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
		return attached ? "有附件" : "无附件";
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
		case TYPE_INTERNAL:return "内部消息";
		case TYPE_SMS:return "短信息";
		case TYPE_EMAIL:return "电子邮件";
		}
		return "";
	}
	
	public String getSendStatusName(){
		switch(sendStatus){
		case SEND_STATUS_UNSENT:return "未发送";
		case SEND_STATUS_SEND:return "已发送";
		case SEND_STATUS_SENDING:return "正发送";
		case SEND_STATUS_FAIL:return "失败";
		case SEND_STATUS_DISABLE:return "作废";
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
