package springold.bean;

import springold.base.version.VersionableBean;
import springold.utils.CodeMapUtils;

/**
 * ��ʱ�����װ��
 * @author   xiaohe
 */
public class ScheduleJob extends VersionableBean {

    public static final byte STATUS_��ִ�� = 0 ;
    public static final byte STATUS_ִ���� = 1 ;
    public static final byte STATUS_��ͣ�� = 2 ;
    public static final byte STATUS_��ע�� = 3 ;

    /** ����id */
    private int jobId;

    /** �������� */
    private String jobName;

    /** ������� */
    private String jobGroup;

    /** ����״̬ ��status��Ӧ */
    private String jobStatus;

    private String jobStatusName;

    /** ��������ʱ����ʽ  */
    private String cronExpression;

    /** ����ִ���� */
    private String beanClass;

    /** ����ִ�з��� */
    private String executeMethod;

    /** ���񴴽�ʱ�� */
    private String createTime;

    /** �������ʱ�� */
    private String updateTime;

    /** �������� */
    private String jobDesc;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
        this.jobStatusName = CodeMapUtils.getCodeName(ScheduleJob.class,"STATUS",getJobStatus());

    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(String executeMethod) {
        this.executeMethod = executeMethod;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJobStatusName() {
        return jobStatusName;
    }

    public void setJobStatusName(String jobStatusName) {
    }

    @Override
    public String toString() {
        return "ScheduleJob{" +
                "jobId=" + jobId +
                ", jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", beanClass='" + beanClass + '\'' +
                ", executeMethod='" + executeMethod + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", jobDesc='" + jobDesc + '\'' +
                '}';
    }
}