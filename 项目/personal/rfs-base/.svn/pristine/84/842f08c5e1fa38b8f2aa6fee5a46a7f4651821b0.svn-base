package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.apps.bean.StringKeyBean;
import org.opoo.ndao.domain.Versionable;

import com.googlecode.jsonplugin.annotations.JSON;


/**
 * 业务统计定义。
 *
 */
public class BizStatisticsDefinition extends StringKeyBean implements VersionLogable, Versionable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1609654109150396923L;

	//ID
	private String dataId;

	private String dataName;
	private Short dataUnit;
	private String dataUnitName;
	private String dataFormat;
	private boolean yearEnabled = true;
	private boolean monthEnabled = true;
	private boolean weekEnabled = false;
	private boolean dayEnabled = false;
	private boolean allObjectEnabled = true;
	private boolean singleObjectEnabled = true;
	private boolean allTimeEnabled = true;
	  
	private int type;
	 private byte status; 				//状态
	 private String remark; 				//备注信息
	 private Long creator; 				//创建者
	 private Date creationTime; 			//创建时间
	 private Long modifier; 				//最后修改者
	 private Date modificationTime; 		//最后修改时间
	


	/**
	 * @param dataId
	 * @param dataName
	 * @param dataUnit
	 * @param dataUnitName
	 * @param dataFormat
	 * @param yearEnabled
	 * @param monthEnabled
	 * @param weekEnabled
	 * @param dayEnabled
	 * @param allObjectEnabled
	 * @param singleObjectEnabled
	 */
	public BizStatisticsDefinition(String dataId, String dataName,
			Short dataUnit, String dataUnitName, String dataFormat,
			boolean yearEnabled, boolean monthEnabled, boolean weekEnabled,
			boolean dayEnabled, boolean allObjectEnabled,
			boolean singleObjectEnabled) {
		super();
		this.dataId = dataId;
		this.dataName = dataName;
		this.dataUnit = dataUnit;
		this.dataUnitName = dataUnitName;
		this.dataFormat = dataFormat;
		this.yearEnabled = yearEnabled;
		this.monthEnabled = monthEnabled;
		this.weekEnabled = weekEnabled;
		this.dayEnabled = dayEnabled;
		this.allObjectEnabled = allObjectEnabled;
		this.singleObjectEnabled = singleObjectEnabled;
	}

	/**
	 * 
	 */
	public BizStatisticsDefinition() {
		super();
	}

	public String getId(){
		return getDataId();
	}
	
	public void setId(String id){
		this.setDataId(id);
	}
	
	/**
	 * @return the dataId
	 */
	public String getDataId() {
		return dataId;
	}
	/**
	 * @param dataId the dataId to set
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	/**
	 * @return the dataName
	 */
	public String getDataName() {
		return dataName;
	}
	/**
	 * @param dataName the dataName to set
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	/**
	 * @return the dataUnit
	 */
	public Short getDataUnit() {
		return dataUnit;
	}
	/**
	 * @param dataUnit the dataUnit to set
	 */
	public void setDataUnit(Short dataUnit) {
		this.dataUnit = dataUnit;
	}
	/**
	 * @return the dataUnitName
	 */
	public String getDataUnitName() {
		return dataUnitName;
	}
	/**
	 * @param dataUnitName the dataUnitName to set
	 */
	public void setDataUnitName(String dataUnitName) {
		this.dataUnitName = dataUnitName;
	}
	/**
	 * @return the dataFormat
	 */
	public String getDataFormat() {
		return dataFormat;
	}
	/**
	 * @param dataFormat the dataFormat to set
	 */
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
	/**
	 * @return the yearEnabled
	 */
	public boolean isYearEnabled() {
		return yearEnabled;
	}
	/**
	 * @param yearEnabled the yearEnabled to set
	 */
	public void setYearEnabled(boolean yearEnabled) {
		this.yearEnabled = yearEnabled;
	}
	/**
	 * @return the monthEnabled
	 */
	public boolean isMonthEnabled() {
		return monthEnabled;
	}
	/**
	 * @param monthEnabled the monthEnabled to set
	 */
	public void setMonthEnabled(boolean monthEnabled) {
		this.monthEnabled = monthEnabled;
	}
	/**
	 * @return the weekEnabled
	 */
	public boolean isWeekEnabled() {
		return weekEnabled;
	}
	/**
	 * @param weekEnabled the weekEnabled to set
	 */
	public void setWeekEnabled(boolean weekEnabled) {
		this.weekEnabled = weekEnabled;
	}
	/**
	 * @return the dayEnabled
	 */
	public boolean isDayEnabled() {
		return dayEnabled;
	}
	/**
	 * @param dayEnabled the dayEnabled to set
	 */
	public void setDayEnabled(boolean dayEnabled) {
		this.dayEnabled = dayEnabled;
	}
	/**
	 * @return the allObjectEnabled
	 */
	public boolean isAllObjectEnabled() {
		return allObjectEnabled;
	}
	/**
	 * @param allObjectEnabled the allObjectEnabled to set
	 */
	public void setAllObjectEnabled(boolean allObjectEnabled) {
		this.allObjectEnabled = allObjectEnabled;
	}
	/**
	 * @return the singleObjectEnabled
	 */
	public boolean isSingleObjectEnabled() {
		return singleObjectEnabled;
	}
	/**
	 * @param singleObjectEnabled the singleObjectEnabled to set
	 */
	public void setSingleObjectEnabled(boolean singleObjectEnabled) {
		this.singleObjectEnabled = singleObjectEnabled;
	}
	/**
	 * @return the creationTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getCreationTime() {
		return creationTime;
	}
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	/**
	 * @return the modificationTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getModificationTime() {
		return modificationTime;
	}
	/**
	 * @param modificationTime the modificationTime to set
	 */
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
	/**
	 * @return the creator
	 */
	public Long getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	/**
	 * @return the modifier
	 */
	public Long getModifier() {
		return modifier;
	}
	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the allTimeEnabled
	 */
	public boolean isAllTimeEnabled() {
		return allTimeEnabled;
	}

	/**
	 * @param allTimeEnabled the allTimeEnabled to set
	 */
	public void setAllTimeEnabled(boolean allTimeEnabled) {
		this.allTimeEnabled = allTimeEnabled;
	}

	public String toString(){
		return "BizStatisticsDefinition{dataId=" + dataId 
			+ ", dataName=" + dataName 
			+ ", dataUnit=" + dataUnit
			+ ", dataUnitName=" + dataUnitName
			+ ", dataFormat=" + dataFormat
			+ ", yearEnabled=" + yearEnabled
			+ ", monthEnbled=" + monthEnabled
			+ ", weekEnabled=" + weekEnabled
			+ ", dayEnabled=" + dayEnabled
			+ ", allObjectEnabled=" + allObjectEnabled
			+ ", singleObjectEnabled=" + singleObjectEnabled
			+ "}";
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BizStatisticsDefinition)) {
			return false;
		}
		final BizStatisticsDefinition def = (BizStatisticsDefinition) o;
		if(dataId == null || def.dataId == null){
			return false;
		}
		return def.dataId.endsWith(dataId);
	}
	
	public int hashCode(){
		return dataId != null ? dataId.hashCode() : super.hashCode();
	}
}
