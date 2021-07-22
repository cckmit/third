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
	 * 仅保存，草稿。
	 * 此时消息仍可以编辑。
	 * 此状态由前台指定。
	 */
	public static final byte STATUS_SAVED = 0;
	/**
	 * 消息已经发送，此时不可再编辑。
	 * 此状态由系统自动更改。
	 */
	public static final byte STATUS_SENT = 1;
	/**
	 * 消息已经准备好了，可以发送。
	 * 此状态由前台指定，要立即发送消息时，将这个状态设置为ready，
	 * 保存时触发后台发送和分拆程序。
	 */
	public static final byte STATUS_READY = 2;
	
	/*
	 * 已经保存且待手工确认是否发送
	 */
	public static final byte STATUS_CONFIRM = 9;

	/*
	 * 消息作废
	 */
	public static final byte STATUS_CANCEL = 21;

	/*
	 * 其它
	 */
	public static final byte STATUS_OTHERS = 99;

	
//	//消息类型：0个人消息, 100公告，101通知
//	public static final int TYPE_PERSONAL = 0;
//	public static final int TYPE_NOTICE = 100;
//	public static final int TYPE_ANNOUNCEMENT = 101;
	
	public static final String RECEIVERS_NOTICE = Msg.TOTYPE_PUBLIC + ":" + Msg.TOID_NOTICE;
	public static final String RECEIVERS_ANNOUNCEMENT = Msg.TOTYPE_PUBLIC + ":" + Msg.TOID_ANNOUNCEMENT;
	
	
	private Long id;
	private boolean supportsEmail;//是否发邮件，默认false
	private boolean supportsSms;//是否发短信，默认false
	private String receivers;//多个人使用分号分隔, 0:clerkid0;0:clerkid1;1:orgid;…0表示个人，1表示单位，9表示全部，公告通知只有一节
	private String emailTo;//邮件地址，多个使用分号隔开，目前为空
	private String smsTo;//短信接收手机号码，多个使用分号隔开，目前为空
	private String title;//消息(公告、通知、邮件)标题，短信只使用标题
	private String content;//消息正文
	private Long fromId;//发布 者
//	private int type = 0;//这个字段暂时无用？
	private String fromName;//发布者姓名
	private boolean attached;
	private Date publishTime;//发布时间		
	private Date expirationTime;//过期时间
	
	
	
	
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
			return "可发送";
		}else if(status == SendingMsg.STATUS_SAVED){
			return "草稿";
		}else if(status == SendingMsg.STATUS_SENT){
			return "已发送";
		}else if(status == SendingMsg.STATUS_CONFIRM){
			return "待确认";
		}else if(status == SendingMsg.STATUS_CANCEL){
			return "已作废";
		}else{
			return "其它";
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
