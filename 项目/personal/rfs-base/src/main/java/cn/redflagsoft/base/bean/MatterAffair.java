package cn.redflagsoft.base.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.opoo.ndao.Domain;

public class MatterAffair extends VersionableBean implements Domain<Long> {
	private static final long serialVersionUID = 1477995422670846231L;
	/**
	 * 作业 1
	 */
	public static final byte CATEGORY_JOB = 1;
	/**
	 * 任务 2
	 */
	public static final byte CATEGORY_TASK = 2;
	/**
	 * 工作 3
	 */
	public static final byte CATEGORY_WORK = 3;
	/**
	 * 事务 4
	 */
	public static final byte CATEGORY_PROCESS = 4;
	/**
	 * 对象 8
	 */
	public static final byte CATEGORY_OBJECT = 8;
	
	//ACTION_XXX 用于bizAction和affairAction
	/**
	 * 受理 1
	 */
	public static final byte ACTION_ACCEPT_MATTER = 1;
	/**
	 * 撤消 2
	 */
	public static final byte ACTION_UNDO_MATTER = 2;
	/**
	 * 审结 3
	 */
	public static final byte ACTION_CONFIRM_MATTER = 3;
	/**
	 * 办结 4
	 */
	public static final byte ACTION_DO_END_MATTER = 4;
	/**
	 * 结束 5
	 */
	public static final byte ACTION_END_MATTER = 5;
	
	/**
	 * 暂停 6
	 */
	public static final byte ACTION_HANG = 6;
	
	/**
	 * 唤醒，续办 7
	 */
	public static final byte ACTION_WAKE = 7;
	
	/**
	 * 免办 9
	 */
	public static final byte ACTION_AVOID = 9;
	/**
	 * 取消 10
	 */
	public static final byte ACTION_CANCEL = 10;
	/**
	 * 中止 8
	 */
	public static final byte ACTION_STOP = 8;
	
	/**
	 * 撤回 11
	 */
	public static final byte ACTION_WITHDRAW = 11;
	
	/**
	 * 驳回 12
	 */
	public static final byte ACTION_REJECT = 12;
	
	//@since 2011-08-11
	/**
	 * 转出 15
	 */
	public static final byte ACTION_TRANSFER = 15;
	
	//注意下面5个定义，不用于MatterAffair驱动，仅定义。
	/**
	 * 业务转发 21
	 */
	public static final byte ACTION_TRANSMIT = 21;
	
	/**
	 * 业务分发 22
	 */
	public static final byte ACTION_DISTRIBUTE = 22;
	
	/**
	 * 业务延期 23
	 */
	public static final byte ACTION_DELAY = 23;
	
	/**
	 * 业务删除 24
	 */
	public static final byte ACTION_DELETE = 24;
			
	/**
	 * 业务还原 25
	 */
	public static final byte ACTION_RESTORE = 25;
	
	/**
	 * 未明操作，仅定义，通常也表示与操作无关 0
	 */
	public static final byte ACTION_UNKOWN = 0;
	
	

	//THE_ACTION_XXX 用于action
	/**
	 * 所找到的业务 0
	 */
	public static final byte THE_ACTION_DEFAULT = 0;
	/**
	 * 若找不到，则新建 1
	 */
	public static final byte THE_ACTION_CREATE_IF_NOT_EXISTS = 1;
	/**
	 * 若找到，先中止然后重建 2
	 */
	public static final byte THE_ACTION_STOP_AND_CREATE_IF_EXISTS = 2;
	/**
	 * 若找到，先中止然后重建；若找不到，则新建 3
	 */
	public static final byte THE_ACTION_STOP_AND_CREATE_EXISTS_OR_CREATE_NOT_EXISTS = 3;
	/**
	 * 直属上级业务 4
	 */
	public static final byte THE_ACTION_DIRECT_PARENT_BIZ = 4;
	
	/**
	 * 无论是否找到，都新建 5
	 */
	public static final byte THE_ACTION_CREATE_NEW = 5;
	
	
	private Long sn;
	private Long affairID;
	private byte action;
	private byte taskAction;
	private byte objectAction;
	private int bizType;
	private Long bizID;
	private byte category;
	private int refType;
	private byte bizAction = 1;
	private int objectLifeStage;
	private short objectTag;
	private byte affairCategory;
	private byte affairAction;
	private Long dutyerID;
	private short dutyerType;
	private short affairObjectRelation;
	private short tag;						//自动创建标志 0 ;默认为0;其余为创建条件
	private byte objectStatus;
	private byte objectKeyMatter;
	private short clerkType;
	private Long clerkID;
	
	//11年5月16日，SMC添加
	private int affairType;
	private short displayOrder;
	private int bizObjectType;
	private int affairObjectType;
	
	//对象维护时的有效tag
	private short validTag = Short.MAX_VALUE;
	//要调用的Scheme信息  schemeName!method 或者 schemeName
	private String schemeInfo;
	
	
	public short getClerkType() {
		return clerkType;
	}

	public void setClerkType(short clerkType) {
		this.clerkType = clerkType;
	}

	public Long getClerkID() {
		return clerkID;
	}

	public void setClerkID(Long clerkID) {
		this.clerkID = clerkID;
	}

	public byte getBizAction() {
		return bizAction;
	}

	public void setBizAction(byte bizAction) {
		this.bizAction = bizAction;
	}


	public byte getTaskAction() {
		return taskAction;
	}

	public void setTaskAction(byte taskAction) {
		this.taskAction = taskAction;
	}

	public byte getObjectAction() {
		return objectAction;
	}

	public void setObjectAction(byte objectAction) {
		this.objectAction = objectAction;
	}

	/**
	 * 将当前流程持到指定流程的节点索引[对应BizType]
	 * 
	 * @return the refType
	 */
	public int getRefType() {
		return refType;
	}

	/**
	 * @param refType
	 *            the refType to set
	 */
	public void setRefType(int refType) {
		this.refType = refType;
	}

	/**
	 * @return the category
	 */
	public byte getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(byte category) {
		this.category = category;
	}

	public MatterAffair() {

	}

	public Long getId() {
		return getSn();
	}

	/**
	 * @return the bizType
	 */
	public int getBizType() {
		return bizType;
	}

	/**
	 * @param bizType
	 *            the bizType to set
	 */
	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	/**
	 * @return the bizID
	 */
	public Long getBizID() {
		return bizID;
	}

	/**
	 * @param bizID
	 *            the bizID to set
	 */
	public void setBizID(Long bizID) {
		this.bizID = bizID;
	}

	public void setId(Long id) {
		setSn(id);
	}

	public Long getSn() {
		return sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public Long getAffairID() {
		return affairID;
	}

	public void setAffairID(Long affairID) {
		this.affairID = affairID;
	}

	public byte getAction() {
		return action;
	}

	public void setAction(byte action) {
		this.action = action;
	}

	/**
	 * @return the objectLifeStage
	 */
	public int getObjectLifeStage() {
		return objectLifeStage;
	}

	/**
	 * @param objectLifeStage the objectLifeStage to set
	 */
	public void setObjectLifeStage(int objectLifeStage) {
		this.objectLifeStage = objectLifeStage;
	}

	public short getObjectTag() {
		return objectTag;
	}

	public void setObjectTag(short objectTag) {
		this.objectTag = objectTag;
	}

	public byte getAffairCategory() {
		return affairCategory;
	}

	public void setAffairCategory(byte affairCategory) {
		this.affairCategory = affairCategory;
	}

	public byte getAffairAction() {
		return affairAction;
	}

	public void setAffairAction(byte affairAction) {
		this.affairAction = affairAction;
	}

	/**
	 * 默认为0，表示忽略。
	 * @return
	 */
	public Long getDutyerID() {
		return dutyerID;
	}

	public void setDutyerID(Long dutyerID) {
		this.dutyerID = dutyerID;
	}

	/**
	 * 1：单位；2：部门；3：个人；默认为0，表示忽略。
	 * @return
	 */
	public short getDutyerType() {
		return dutyerType;
	}

	public void setDutyerType(short dutyerType) {
		this.dutyerType = dutyerType;
	}

	/**
	 * affair对象与matter对象的关系
	 * @return short
	 */
	public short getAffairObjectRelation() {
		return affairObjectRelation;
	}

	public void setAffairObjectRelation(short affairObjectRelation) {
		this.affairObjectRelation = affairObjectRelation;
	}

	public short getTag() {
		return tag;
	}

	public void setTag(short tag) {
		this.tag = tag;
	}

	public byte getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(byte objectStatus) {
		this.objectStatus = objectStatus;
	}

	public byte getObjectKeyMatter() {
		return objectKeyMatter;
	}

	public void setObjectKeyMatter(byte objectKeyMatter) {
		this.objectKeyMatter = objectKeyMatter;
	}

	/**
	 * @return the affairType
	 */
	public int getAffairType() {
		return affairType;
	}

	/**
	 * @param affairType the affairType to set
	 */
	public void setAffairType(int affairType) {
		this.affairType = affairType;
	}

	/**
	 * @return the displayOrder
	 */
	public short getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(short displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the bizObjectType
	 */
	public int getBizObjectType() {
		return bizObjectType;
	}

	/**
	 * @param bizObjectType the bizObjectType to set
	 */
	public void setBizObjectType(int bizObjectType) {
		this.bizObjectType = bizObjectType;
	}

	/**
	 * @return the affairObjectType
	 */
	public int getAffairObjectType() {
		return affairObjectType;
	}

	/**
	 * @param affairObjectType the affairObjectType to set
	 */
	public void setAffairObjectType(int affairObjectType) {
		this.affairObjectType = affairObjectType;
	}
	
	/**
	 * @return the validTag
	 */
	public short getValidTag() {
		return validTag;
	}

	/**
	 * @param validTag the validTag to set
	 */
	public void setValidTag(short validTag) {
		this.validTag = validTag;
	}

	/**
	 * @return the schemeInfo
	 */
	public String getSchemeInfo() {
		return schemeInfo;
	}

	/**
	 * @param schemeInfo the schemeInfo to set
	 */
	public void setSchemeInfo(String schemeInfo) {
		this.schemeInfo = schemeInfo;
	}

	public String toString(){
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			.append("sn", sn)
			.append("affairCategory", affairCategory)
			.append("affairID", affairID)
			.append("affairType", affairType)
			.append("affairAction", affairAction)
			.append("bizType", bizType)
			.append("action", action)
			.append("objectLifeStage", objectLifeStage)
			.append("objectTag", objectTag)
			.append("dutyerID", dutyerID)
			.append("dutyerType", dutyerType)
			.append("objectStatus", objectStatus)
			.append("objectKeyMatter", objectKeyMatter)
			.append("clerkType", clerkType)
			.append("clerkID", clerkID)
			
			.append("affairObjectRelation", affairObjectRelation)
			.append("taskAction", taskAction)
			.append("objectAction", objectAction)
			.append("bizID", bizID)
			.append("category", category)
			.append("refType", refType)
			.append("category", category)
			.append("bizAction", bizAction)
			.append("tag", tag)
			
			.append("displayOrder", displayOrder)
			.append("bizObjectType", bizObjectType)
			.append("affairObjectType", affairObjectType)
			.append("validTag", validTag)
			.append("schemeInfo", schemeInfo)
			.toString();
	}
}
