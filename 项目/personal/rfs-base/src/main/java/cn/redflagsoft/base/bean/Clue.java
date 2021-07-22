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
	 * Ψһ
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
	 * �����Ķ���
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
	 * ҵ������
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
	 * Ĭ��Ϊ0����ʾ����
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
	 * ��������������ҵ������Ϊ��һ������ֻ��һ��JOB��
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
	 * һ������ֻ��һ���켣����һ���켣���ܶ�Ӧ���������
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
