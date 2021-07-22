package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.ndao.Domain;

public class Holiday implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4722510297506522151L;
	private Long id;
	private int type;
	private Date holDate;
	private byte status;
	private String note;
	private String remark;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getKey(){
		return getId();
	}
	
	public void setKey(Long key){
		setId(key);
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public Date getHolDate() {
		return holDate;
	}
	
	public void setHolDate(Date holDate) {
		this.holDate = holDate;
	}
	
	public byte getStatus() {
		return status;
	}
	
	public void setStatus(byte status) {
		this.status = status;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getStatusName(){
		if(getStatus() == (byte)1) {
			return "ÊÇ";
		}else{
			return "·ñ";
		}
	}
}
