package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

public class BizTrackSect extends VersionableBean implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5737963043426300273L;
	public static final Long ROUTEID_PARENT = 10000L;
	public static final Long ROUTEID_CHILD = 10100L;	
	private Long sn;
	private Long trackSN;
	private byte sectNo;
	private String sectName;
	private byte status;
	
	/**
	 * Î¨Ò»±àºÅ
	 * 
	 * @return Long
	 */
	public Long getSn() {
		return sn;
	}
	/**
	 * @param sn
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}
	
	public void setId(Long id){
		setSn(id);
	}
	
	public Long getId(){
		return getSn();
	}
	
	/**
	 * ¹ì¼£
	 * 
	 * @return Long
	 */
	public Long getTrackSN() {
		return trackSN;
	}
	
	/**
	 * @param trackSN
	 */
	public void setTrackSN(Long trackSN) {
		this.trackSN = trackSN;
	}
	
	/**
	 * ¶ÎºÅ
	 * 
	 * ²»¿É¿Õ
	 * @return byte
	 */
	public byte getSectNo() {
		return sectNo;
	}
	
	/**
	 * @param sectNo
	 */
	public void setSectNo(byte sectNo) {
		this.sectNo = sectNo;
	}
	
	/**
	 * Ãû³Æ
	 * 
	 * 25¸öºº×Ö£¬²»ÄÜÎª¿Õ.
	 * @return String
	 */
	public String getSectName() {
		return sectName;
	}
	
	/**
	 * @param sectName
	 */
	public void setSectName(String sectName) {
		this.sectName = sectName;
	}
	
	/**
	 * ×´Ì¬
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
}
