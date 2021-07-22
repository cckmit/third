package cn.redflagsoft.base.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.opoo.ndao.Domain;

public class ManagementAccount implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5697091594080902835L;
	private Long id; 
	private Long projectId;// 项目编号
	private String abbr; // 建设项目名称及建设起止年月
	private String kind; // 性质
	private String investScale; // 总投资及建设规模
	private BigDecimal planInvest; // xxxx年止累计安排资金
	private BigDecimal yearPlanInvest; // xxxx年计划投资
	private String yearContent; // xxxx年主要建设内容计划及时间进度计划
	private String progressSituation; // 上月项目进展情况(含上月投资完成情况、本年度投资完成情况)
	private String visualPrograss; // 截至目前累计完成形象进度
	private BigDecimal actualInvest; // 截至目前累计完成投资
	private String investLimit; // 累计完成投资占总投资比例
	private String questions; // 需要协调解决的问题
	private String director; // 项目主任
	private String remark; // 备注
	private Long creator;
	private Date creationTime;
	private int type;
	private short status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getInvestScale() {
		return investScale;
	}

	public void setInvestScale(String investScale) {
		this.investScale = investScale;
	}

	public BigDecimal getPlanInvest() {
		return planInvest;
	}

	public void setPlanInvest(BigDecimal planInvest) {
		this.planInvest = planInvest;
	}

	public BigDecimal getYearPlanInvest() {
		return yearPlanInvest;
	}

	public void setYearPlanInvest(BigDecimal yearPlanInvest) {
		this.yearPlanInvest = yearPlanInvest;
	}

	public String getYearContent() {
		return yearContent;
	}

	public void setYearContent(String yearContent) {
		this.yearContent = yearContent;
	}

	public String getProgressSituation() {
		return progressSituation;
	}

	public void setProgressSituation(String progressSituation) {
		this.progressSituation = progressSituation;
	}

	public String getVisualPrograss() {
		return visualPrograss;
	}

	public void setVisualPrograss(String visualPrograss) {
		this.visualPrograss = visualPrograss;
	}

	public BigDecimal getActualInvest() {
		return actualInvest;
	}

	public void setActualInvest(BigDecimal actualInvest) {
		this.actualInvest = actualInvest;
	}

	public String getInvestLimit() {
		return investLimit;
	}

	public void setInvestLimit(String investLimit) {
		this.investLimit = investLimit;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
}
