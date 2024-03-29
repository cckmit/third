/*
 * $Id: StageTaskDetails.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

import cn.redflagsoft.base.util.Ignore;

import com.googlecode.jsonplugin.annotations.JSON;


/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class StageTaskDetails extends VersionableBean {
	public static final byte STATUS_未办 = 0;
	public static final byte STATUS_完成 = 9;
	public static final byte STATUS_免办 = 80;
	@Ignore
	public static final byte STATUS_无效数据 = 100;
	
	public static final int TYPE_必办 = 1;
	public static final int TYPE_免办 = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2118024299487513466L;
	
	private long objectId;				//相关对象ID
	private String objectName;			//相关对象名称
	private int objectType;			//相关对象类型
	private int taskType;				//相关阶段的task_type
	private String taskSNs;				//该阶段办理时的taskSN集合
	private String workSNs;				//该阶段办理时的workSN集合
	private String workItemName;		//审批事项名称
	private Long spbmId;				//审批部门ID
	private String spbmName;			//审批部门名称
	private Long bzdwId;				//编制单位ID
	private String bzdwName;			//编制单位名称
	private Date sbTime;				//申报时间
	private String sbFileNo;			//申报文号
	private Long sbFileId;				//申报文件附件ID
	private String pfFileNo;			//批复文号
	private Long pfFileId;				//批复文件附件ID
	private Long pfdwId;				//批复单位ID
	private String pfdwName;			//批复单位名称
	private int type = 1;				//默认为必须办理
	
	
	private Date expectedStartTime;		//预计开始时间
//	private Date expectedBzStartTime;			//预计编制开始时间  -- 同预计开始时间
	private Date expectedBzFinishTime;		//预计编制结束时间
	private Date expectedSbTime;				//预计申报时间
//	private Date expectedPfTime;				//预计批复时间	-- 同实际完成时间
	private Date expectedFinishTime;	//预计完成时间
	
	private Date actualStartTime;		//实际开始时间
//	private Date bzStartTime;			//编制开始时间  -- 同实际开始时间
	private Date bzFinishTime;				//编制结束时间
//	private Date pfTime;				//批复时间  -- 同实际完成时间
	private Date actualFinishTime;		//实际完成时间
	
	
	@Override
	public int getType() {
		return this.type;
	}
	@Override
	public void setType(int type) {
		this.type = type;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public long getObjectId() {
		return objectId;
	}
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	public String getTaskSNs() {
		return taskSNs;
	}
	public void setTaskSNs(String taskSNs) {
		this.taskSNs = taskSNs;
	}
	public String getWorkSNs() {
		return workSNs;
	}
	public void setWorkSNs(String workSNs) {
		this.workSNs = workSNs;
	}
	public String getWorkItemName() {
		return workItemName;
	}
	public void setWorkItemName(String workItemName) {
		this.workItemName = workItemName;
	}
	public Long getSpbmId() {
		return spbmId;
	}
	public void setSpbmId(Long spbmId) {
		this.spbmId = spbmId;
	}
	public String getSpbmName() {
		return spbmName;
	}
	public void setSpbmName(String spbmName) {
		this.spbmName = spbmName;
	}
	public Long getBzdwId() {
		return bzdwId;
	}
	public void setBzdwId(Long bzdwId) {
		this.bzdwId = bzdwId;
	}
	public String getBzdwName() {
		return bzdwName;
	}
	public void setBzdwName(String bzdwName) {
		this.bzdwName = bzdwName;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getSbTime() {
		return sbTime;
	}
	public void setSbTime(Date sbTime) {
		this.sbTime = sbTime;
	}
	public String getSbFileNo() {
		return sbFileNo;
	}
	public void setSbFileNo(String sbFileNo) {
		this.sbFileNo = sbFileNo;
	}
	public Long getSbFileId() {
		return sbFileId;
	}
	public void setSbFileId(Long sbFileId) {
		this.sbFileId = sbFileId;
	}
	
	@JSON(format="yyyy-MM-dd")
	public Date getPfTime() {
		return getActualFinishTime();
	}
	public void setPfTime(Date pfTime) {
		this.setActualFinishTime(pfTime);
	}
	public String getPfFileNo() {
		return pfFileNo;
	}
	public void setPfFileNo(String pfFileNo) {
		this.pfFileNo = pfFileNo;
	}
	public Long getPfFileId() {
		return pfFileId;
	}
	public void setPfFileId(Long pfFileId) {
		this.pfFileId = pfFileId;
	}
	public Long getPfdwId() {
		return pfdwId;
	}
	public void setPfdwId(Long pfdwId) {
		this.pfdwId = pfdwId;
	}
	public String getPfdwName() {
		return pfdwName;
	}
	public void setPfdwName(String pfdwName) {
		this.pfdwName = pfdwName;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getExpectedFinishTime() {
		return expectedFinishTime;
	}
	public void setExpectedFinishTime(Date expectedFinishTime) {
		this.expectedFinishTime = expectedFinishTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getActualFinishTime() {
		return actualFinishTime;
	}
	public void setActualFinishTime(Date actualFinishTime) {
		this.actualFinishTime = actualFinishTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getBzStartTime() {
		return getActualStartTime();
	}
	public void setBzStartTime(Date bzStartTime) {
		setActualStartTime(bzStartTime);
	}
	@JSON(format="yyyy-MM-dd")
	public Date getBzFinishTime() {
		return bzFinishTime;
	}
	public void setBzFinishTime(Date bzFinishTime) {
		this.bzFinishTime = bzFinishTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getExpectedStartTime() {
		return expectedStartTime;
	}
	public void setExpectedStartTime(Date expectedStartTime) {
		this.expectedStartTime = expectedStartTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getActualStartTime() {
		return actualStartTime;
	}
	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getExpectedBzStartTime() {
		return getExpectedStartTime();
	}
	public void setExpectedBzStartTime(Date expectedBzStartTime) {
		setExpectedStartTime(expectedBzStartTime);
	}
	@JSON(format="yyyy-MM-dd")
	public Date getExpectedBzFinishTime() {
		return expectedBzFinishTime;
	}
	public void setExpectedBzFinishTime(Date expectedBzFinishTime) {
		this.expectedBzFinishTime = expectedBzFinishTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getExpectedSbTime() {
		return expectedSbTime;
	}
	public void setExpectedSbTime(Date expectedSbTime) {
		this.expectedSbTime = expectedSbTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getExpectedPfTime() {
		return getExpectedFinishTime();
	}
	public void setExpectedPfTime(Date expectedPfTime) {
		setExpectedFinishTime(expectedPfTime);
	}
	
	/**
	 * 复制所有的实际时间到预计时间。
	 * 
	 * @param copyNullValue 是否复制空值，参数为true时，不管实际时间是否为空都复制到预计时间，为false时
	 * 	只复制不为空的实际时间到对应的预计时间。
	 */
	public void copyAllActualTimesToExpextedTimes(boolean copyNullValue){
		if(copyNullValue){
			setExpectedStartTime(getActualStartTime());
			setExpectedBzFinishTime(getBzFinishTime());
			setExpectedSbTime(getSbTime());
			setExpectedFinishTime(getActualFinishTime());
		}else{
			if(getActualStartTime() != null) setExpectedStartTime(getActualStartTime());
			if(getBzFinishTime() != null) setExpectedBzFinishTime(getBzFinishTime());
			if(getSbTime() != null) setExpectedSbTime(getSbTime());
			if(getActualFinishTime() != null) setExpectedFinishTime(getActualFinishTime());
		}
	}
	
	/**
	 * 复制所有的实际时间到预计时间，只复制不为空的实际时间到对应的预计时间。
	 */
	public void copyAllActualTimesToExpextedTimes(){
		copyAllActualTimesToExpextedTimes(false);
	}
}