/*
 * $Id: Address.java 4341 2011-04-22 02:12:39Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * 
 * @author Alex Lin
 *
 */
public class Address extends VersionableBean implements Observable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -466426354977963910L;
	private Long objectID;
	private Long id;
	private String code;
	private String name;
	private String abbr;
	private String postCode;
	private byte nation;
	private String nationName;
	private byte proVince;
	private String proVinceName;
	private short city;
	private String cityName;
	private short county;
	private String countyName;
	private short town;
	private String townName;
	private short  village;
	private String villageName;
	private short manor;
	private String manorName;
	private short rode;
	private String rodeName;
	private String note;

	
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Ψһ,4������Ϊ����
	 * @param id :
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code 
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * ȫ��,����ȫ��
	 * @param name :
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the abbr
	 */
	public String getAbbr() {
		return abbr;
	}

	/**
	 * ���,ע���Ʋ����ظ�
	 * @param abbr :
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * ��������
	 * @param postCode :
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return the nation
	 */
	public byte getNation() {
		return nation;
	}

	/**
	 * ���ң�Ĭ��Ϊ0,��ʾ�й�
	 * @param nation :
	 */
	public void setNation(byte nation) {
		this.nation = nation;
	}

	/**
	 * @return the nationName
	 */
	public String getNationName() {
		return nationName;
	}

	/**
	 * Ĭ��Ϊ�й�
	 * @param nationName :
	 */
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	/**
	 * @return the proVince
	 */
	public byte getProVince() {
		return proVince;
	}

	/**
	 * ʡ�У�Ĭ��Ϊ0,��ʾ����
	 * @param proVince :
	 */
	public void setProVince(byte proVince) {
		this.proVince = proVince;
	}

	/**
	 * @return the proVinceName
	 */
	public String getProVinceName() {
		return proVinceName;
	}

	/**
	 * @param proVinceName 
	 */
	public void setProVinceName(String proVinceName) {
		this.proVinceName = proVinceName;
	}

	/**
	 * @return the city
	 */
	public short getCity() {
		return city;
	}

	/**
	 * ���У�Ĭ��Ϊ0,��ʾ����
	 * @param city :
	 */
	public void setCity(short city) {
		this.city = city;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName 
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the county
	 */
	public short getCounty() {
		return county;
	}

	/**
	 * ������Ĭ��Ϊ0,��ʾ����
	 * @param county :
	 */
	public void setCounty(short county) {
		this.county = county;
	}

	/**
	 * @return the countyName
	 */
	public String getCountyName() {
		return countyName;
	}

	/**
	 * @param countyName 
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
	 * @return the town
	 */
	public short getTown() {
		return town;
	}

	/**
	 * @param town :����Ĭ��Ϊ0,��ʾ����
	 */
	public void setTown(short town) {
		this.town = town;
	}

	/**
	 * @return the townName
	 */
	public String getTownName() {
		return townName;
	}

	/**
	 * @param townName 
	 */
	public void setTownName(String townName) {
		this.townName = townName;
	}

	/**
	 * @return the village
	 */
	public short getVillage() {
		return village;
	}

	/**
	 * ��ӣ�Ĭ��Ϊ0,��ʾ����
	 * @param village :
	 */
	public void setVillage(short village) {
		this.village = village;
	}

	/**
	 * @return the villageName
	 */
	public String getVillageName() {
		return villageName;
	}

	/**
	 * @param villageName 
	 */
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	/**
	 * @return the manor
	 */
	public short getManor() {
		return manor;
	}

	/**
	 * ������Ĭ��Ϊ0,��ʾ����
	 * @param manor :
	 */
	public void setManor(short manor) {
		this.manor = manor;
	}

	/**
	 * @return the manorName
	 */
	public String getManorName() {
		return manorName;
	}

	/**
	 * @param manorName 
	 */
	public void setManorName(String manorName) {
		this.manorName = manorName;
	}

	/**
	 * @return the rode
	 */
	public short getRode() {
		return rode;
	}

	/**
	 * ��·��Ĭ��Ϊ0,��ʾ����
	 * @param rode :
	 */
	public void setRode(short rode) {
		this.rode = rode;
	}

	/**
	 * @return the rodeName
	 */
	public String getRodeName() {
		return rodeName;
	}

	/**
	 * @param rodeName 
	 */
	public void setRodeName(String rodeName) {
		this.rodeName = rodeName;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * ע�ͣ�˵������
	 * @param note :
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * object���ֵ�ID.Ĭ��Ϊ0�������
	 */
	public Long getObjectID() {
		
		return objectID;
	}

	public void setObjectID(Long objectID) {
		this.objectID = objectID;
	}

}
