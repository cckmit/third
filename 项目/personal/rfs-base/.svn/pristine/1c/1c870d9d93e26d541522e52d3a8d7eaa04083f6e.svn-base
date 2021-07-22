package cn.redflagsoft.base.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 业务统计表。
 *
 */
public class BizStatistics extends VersionableBean {

	/**
	 * FINANCEPAY  financePay
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 	DataID:AnPaiTouZi              DataName:安排投资统计
	 *	DataID:WanChengTouZi		   DataName:完成投资统计
	 *	DataID:BoFuZiJin			   DataName:拨付资金统计
	 */
	
	
	private int refObjectType = 0;
	private long refObjectId = 0;
	private String refObjectName;
	private BigDecimal dataSum = BigDecimal.ZERO;
	private short theYear = 0;
	private byte theMonth = 0;
	private byte theWeek = 0;
	private short theDay = 0;
	private long lastBizSn =0;
	private Date lastBizTime;
	private Date lastBizGetTime;
	
	//many to one
	private BizStatisticsDefinition definition;
	////
	private String dataId;
	private String dataName;
	private Short dataUnit;
	private String dataUnitName;
	private String dataFormat;
	
	
	/**
	 * 
	 */
	public BizStatistics() {
		super();
	}
	
	public void setDefinition(BizStatisticsDefinition def){
		this.definition = def;
		this.dataId = def.getDataId();
		this.dataName = def.getDataName();
		this.dataUnit = def.getDataUnit();
		this.dataUnitName = def.getDataUnitName();
		this.dataFormat = def.getDataFormat();
	}
	
	@JSON(serialize=false)
	public BizStatisticsDefinition getDefinition(){
		return this.definition;
	}
	
	/**
	 * @return the refObjectId
	 */
	public long getRefObjectId() {
		return refObjectId;
	}
	/**
	 * @param refObjectId the refObjectId to set
	 */
	public void setRefObjectId(long refObjectId) {
		this.refObjectId = refObjectId;
	}
	/**
	 * @return the dataSum
	 */
	public BigDecimal getDataSum() {
		return dataSum;
	}
	/**
	 * @param dataSum the dataSum to set
	 */
	public void setDataSum(BigDecimal dataSum) {
		this.dataSum = dataSum;
	}
	/**
	 * @return the lastBizSn
	 */
	public long getLastBizSn() {
		return lastBizSn;
	}
	/**
	 * @param lastBizSn the lastBizSn to set
	 */
	public void setLastBizSn(long lastBizSn) {
		this.lastBizSn = lastBizSn;
	}
	public int getRefObjectType() {
		return refObjectType;
	}
	public void setRefObjectType(int refObjectType) {
		this.refObjectType = refObjectType;
	}
	public String getRefObjectName() {
		return refObjectName;
	}
	public void setRefObjectName(String refObjectName) {
		this.refObjectName = refObjectName;
	}
	public String getDataId() {
		return dataId;
	}
//	public void setDataId(String dataId) {
//		this.dataId = dataId;
//	}
	public String getDataName() {
		return dataName;
	}
	public Short getDataUnit() {
		return dataUnit;
	}
	public String getDataUnitName() {
		return dataUnitName;
	}
	public String getDataFormat() {
		return dataFormat;
	}
	public short getTheYear() {
		return theYear;
	}
	public void setTheYear(short theYear) {
		this.theYear = theYear;
	}
	public byte getTheMonth() {
		return theMonth;
	}
	public void setTheMonth(byte theMonth) {
		this.theMonth = theMonth;
	}
	public byte getTheWeek() {
		return theWeek;
	}
	public void setTheWeek(byte theWeek) {
		this.theWeek = theWeek;
	}
	public short getTheDay() {
		return theDay;
	}
	public void setTheDay(short theDay) {
		this.theDay = theDay;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getLastBizTime() {
		return lastBizTime;
	}
	public void setLastBizTime(Date lastBizTime) {
		this.lastBizTime = lastBizTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getLastBizGetTime() {
		return lastBizGetTime;
	}
	public void setLastBizGetTime(Date lastBizGetTime) {
		this.lastBizGetTime = lastBizGetTime;
	}
	
	public String toString(){
		return new ToStringBuilder(this)
		.append("definition", definition)
		.append("refObjectId", refObjectId)
		.append("refObjectType", refObjectType)
		.append("theYear", theYear)
		.append("theMonth", theMonth)
		.append("theWeek", theWeek)
		.append("theDay", theDay)
		.append("dataSum", dataSum)
		.toString();
	}
}
