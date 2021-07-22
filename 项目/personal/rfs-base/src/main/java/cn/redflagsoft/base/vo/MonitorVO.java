package cn.redflagsoft.base.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 监察VO
 * 
 * @author ck 修改
 * 
 */
public class MonitorVO implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private Date beginTime; // 业务开始时间task
    private Date endTime; // 业务结束时间task
    private BigDecimal value; // 实际办理天数risk
    private byte status; // 业务办理状态task
    private String statusName; // taskname
    private Date modificationTime; // 监察时间//risk
    private Date limitTime; // 法定截止时间
    private BigDecimal scaleValue; // 法定时限天数
    private String taskCode;// 业务编号
    private byte grade; // 监察状态
    private String gradeName;
    private String juralLimit; // 法律依据
    private String conclusion; // 结论
    private List<MonitorVO> monitorvos;
    private BigDecimal scaleValue1;
    private BigDecimal scaleValue2;
    private BigDecimal scaleValue3;
    private BigDecimal scaleValue4;
    private BigDecimal scaleValue5;
    private BigDecimal scaleValue6;
    private byte scaleMark;
    private String remark;
    private BigDecimal remainTime;
    private Short timeLimit;
    private Long dutyEntity;// 办理单位
    private String dutyEntityAbbr;// 办理单位(描述)
    private String taskName;// 业务名称
    private short category;
    private String thingCode;
    private String thingName;

    public Long getDutyEntity() {
        return dutyEntity;
    }

    public void setDutyEntity(Long dutyEntity) {
        this.dutyEntity = dutyEntity;
    }

    public String getDutyEntityAbbr() {
        return dutyEntityAbbr;
    }

    public void setDutyEntityAbbr(String dutyEntityAbbr) {
        this.dutyEntityAbbr = dutyEntityAbbr;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Short getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Short timeLimit) {
        this.timeLimit = timeLimit;
    }

    public BigDecimal getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(BigDecimal remainTime) {
        this.remainTime = remainTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getScaleValue1() {
        return scaleValue1;
    }

    public void setScaleValue1(BigDecimal scaleValue1) {
        this.scaleValue1 = scaleValue1;
    }

    public BigDecimal getScaleValue2() {
        return scaleValue2;
    }

    public void setScaleValue2(BigDecimal scaleValue2) {
        this.scaleValue2 = scaleValue2;
    }

    public BigDecimal getScaleValue3() {
        return scaleValue3;
    }

    public void setScaleValue3(BigDecimal scaleValue3) {
        this.scaleValue3 = scaleValue3;
    }

    public BigDecimal getScaleValue4() {
        return scaleValue4;
    }

    public void setScaleValue4(BigDecimal scaleValue4) {
        this.scaleValue4 = scaleValue4;
    }

    public BigDecimal getScaleValue5() {
        return scaleValue5;
    }

    public void setScaleValue5(BigDecimal scaleValue5) {
        this.scaleValue5 = scaleValue5;
    }

    public BigDecimal getScaleValue6() {
        return scaleValue6;
    }

    public void setScaleValue6(BigDecimal scaleValue6) {
        this.scaleValue6 = scaleValue6;
    }

    public byte getScaleMark() {
        return scaleMark;
    }

    public void setScaleMark(byte scaleMark) {
        this.scaleMark = scaleMark;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Date limitTime) {
        this.limitTime = limitTime;
    }

    public BigDecimal getScaleValue() {
        return scaleValue;
    }

    public void setScaleValue(BigDecimal scaleValue) {
        this.scaleValue = scaleValue;
    }

    public byte getGrade() {
        return grade;
    }

    public void setGrade(byte grade) {
        this.grade = grade;
    }

    public String getJuralLimit() {
        return juralLimit;
    }

    public void setJuralLimit(String juralLimit) {
        this.juralLimit = juralLimit;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public List<MonitorVO> getMonitorvos() {
        return monitorvos;
    }

    public void setMonitorvos(List<MonitorVO> monitorvos) {
        this.monitorvos = monitorvos;
    }

    public short getCategory() {
        return category;
    }

    public void setCategory(short category) {
        this.category = category;
    }

    public String getThingCode() {
        return thingCode;
    }

    public void setThingCode(String thingCode) {
        this.thingCode = thingCode;
    }

    public String getThingName() {
        return thingName;
    }

    public void setThingName(String thingName) {
        this.thingName = thingName;
    }
}
