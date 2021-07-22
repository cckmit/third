package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

public class Clue extends VersionableBean implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7942007744467052174L;
	private Long sn;
	private Long objectId;
	private byte category;
	private int bizType;
	private Long bizId;
	private Long bizSN;
	private Long bizTrack;
	
	public Long getId() {
		return getSn();
	}

	public void setId(Long id) {
		setSn(id);
	}

	/**
	 * 唯一
	 * @return the sn
	 */
	public Long getSn() {
		return sn;
	}
	
	/**
	 * @param sn the sn to set
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}
	
	/**
	 * 线索的对象
	 * @return the objectId
	 */
	public Long getObjectId() {
		return objectId;
	}
	
	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
	/**
	 * @return the category
	 */
	public byte getCategory() {
		return category;
	}
	
	/**
	 * @param category the category to set
	 */
	public void setCategory(byte category) {
		this.category = category;
	}
	
	/**
	 * 业务种类
	 * @return the bizType
	 */
	public int getBizType() {
		return bizType;
	}
	
	/**
	 * @param bizType the bizType to set
	 */
	public void setBizType(int bizType) {
		this.bizType = bizType;
	}
	
	/**
	 * 默认为0，表示忽略
	 * @return the bizId
	 */
	public Long getBizId() {
		return bizId;
	}
	
	/**
	 * @param bizId the bizId to set
	 */
	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}
	
	/**
	 * 本线索创建的作业。暂认为，一个线索只有一个JOB。
	 * @return the bizSN
	 */
	public Long getBizSN() {
		return bizSN;
	}
	
	/**
	 * @param bizSN the bizSN to set
	 */
	public void setBizSN(Long bizSN) {
		this.bizSN = bizSN;
	}
	
	/**
	 * 一个线索只有一个轨迹，但一个轨迹可能对应多个线索。
	 * @return the bizTrack
	 */
	public Long getBizTrack() {
		return bizTrack;
	}
	
	/**
	 * @param bizTrack the bizTrack to set
	 */
	public void setBizTrack(Long bizTrack) {
		this.bizTrack = bizTrack;
	}
}
