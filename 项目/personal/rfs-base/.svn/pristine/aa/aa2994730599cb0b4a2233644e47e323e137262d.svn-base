package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.ndao.Domain;

public class Log implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3876625093948223862L;
	public static final byte STATUS_UNUSE = 0;
	public static final byte STATUS_ONUSE = 1;
	public static final String MODULE_RISK = "risk";
	public static final String MODULE_SYSTEM = "system";
	public static final String TYPE_INFO = "info";
	public static final String TYPE_ERROR = "error";
	public static final String TYPE_WARNING = "warning";
	public static final String TYPE_FATAL = "fatal";
	private Long id;
	private String module;	//所属模块。如：risk、system 
	private String title;	//信息摘要 
	private String content;	//信息描述 
	private String type;	//信息类型：info/error/warning/fatal
	private Date creationTime;	
	private String remark;	
	private Byte status;//0 未用，1在用。缺省为1 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
}
