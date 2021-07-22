/*
 * $Id: LifeStage.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * Î´¾­ÉîÛÚÊĞºìÆìĞÅÏ¢¼¼ÊõÓĞÏŞ¹«Ë¾Ğí¿É£¬ÈÎºÎÈË²»µÃÉÃ×Ô£¨°üÀ¨µ«²»ÏŞÓÚ£º
 * ÒÔ·Ç·¨µÄ·½Ê½¸´ÖÆ¡¢´«²¥¡¢Õ¹Ê¾¡¢¾µÏñ¡¢ÉÏÔØ¡¢ÏÂÔØ¡¢ÒıÓÃ£©Ê¹ÓÃ¡£
 */
package cn.redflagsoft.base.bean;


public class LifeStage extends VersionableBean {

	/**
	 * ¶ÔÏó½×¶Î
	 */
	private static final long serialVersionUID = 2908307817930597812L;
	
//	private Long objectId;
	private int objectType;
	private String sn;
	private String code;
//	private int type;						//	ÀàĞÍ
	private String typeName;				//	ÀàĞÍÃû³Æ
	private String name;					//	Ãû³Æ
	private Long managerId;					//	ÔğÈÎÈËID 
	private String managerName;				//	ÔğÈÎÈËĞÕÃû
	private byte status0;					//  ½×¶Î×´Ì¬0
	private byte status1;					//  ½×¶Î×´Ì¬1
	private byte status2;					//  ½×¶Î×´Ì¬2
	private byte status3;					//  ½×¶Î×´Ì¬3
	private byte status4;					//  ½×¶Î×´Ì¬4
	private byte status5;					//  ½×¶Î×´Ì¬5
	private byte status6;					//  ½×¶Î×´Ì¬6
	private byte status7;					//  ½×¶Î×´Ì¬7
	private byte status8;					//  ½×¶Î×´Ì¬8
	private byte status9;					//  ½×¶Î×´Ì¬9
	private byte status10;					//  ½×¶Î×´Ì¬10
	private byte status11;					//  ½×¶Î×´Ì¬11
	private byte status12;					//  ½×¶Î×´Ì¬12
	private byte status13;					//  ½×¶Î×´Ì¬13
	private byte status14;					//  ½×¶Î×´Ì¬14
	private byte status15;					//  ½×¶Î×´Ì¬15
	private byte status16;					//  ½×¶Î×´Ì¬16
	private byte status17;					//  ½×¶Î×´Ì¬17
	private byte status18;					//  ½×¶Î×´Ì¬18
	private byte status19;					//  ½×¶Î×´Ì¬19
	private byte status20;					//  ½×¶Î×´Ì¬20
	private byte status21;					//  ½×¶Î×´Ì¬21
	private byte status22;					//  ½×¶Î×´Ì¬22
	private byte status23;					//  ½×¶Î×´Ì¬23
	private byte status24;					//  ½×¶Î×´Ì¬24
	private byte status25;					//  ½×¶Î×´Ì¬25
	private byte status26;					//  ½×¶Î×´Ì¬26
	private byte status27;					//  ½×¶Î×´Ì¬27
	private byte status28;					//  ½×¶Î×´Ì¬28
	private byte status29;					//  ½×¶Î×´Ì¬29
	private byte status30;					//  ½×¶Î×´Ì¬30
	private byte status31;					//  ½×¶Î×´Ì¬31
	private byte status32;					//  ½×¶Î×´Ì¬32
	private byte status33;					//  ½×¶Î×´Ì¬33
	private byte status34;					//  ½×¶Î×´Ì¬34
	private byte status35;					//  ½×¶Î×´Ì¬35
	private byte status36;					//  ½×¶Î×´Ì¬36
	private byte status37;					//  ½×¶Î×´Ì¬37
	private byte status38;					//  ½×¶Î×´Ì¬38
	private byte status39;					//  ½×¶Î×´Ì¬39
	private byte status40;					//  ½×¶Î×´Ì¬40
	private byte status41;					//  ½×¶Î×´Ì¬41
	private byte status42;					//  ½×¶Î×´Ì¬42
	private byte status43;					//  ½×¶Î×´Ì¬43
	private byte status44;					//  ½×¶Î×´Ì¬44
	private byte status45;					//  ½×¶Î×´Ì¬45
	private byte status46;					//  ½×¶Î×´Ì¬46
	private byte status47;					//  ½×¶Î×´Ì¬47
	private byte status48;					//  ½×¶Î×´Ì¬48
	private byte status49;					//  ½×¶Î×´Ì¬49
	private byte status50;					//  ½×¶Î×´Ì¬50
	private byte status51;					//  ½×¶Î×´Ì¬51
	private byte status52;					//  ½×¶Î×´Ì¬52
	private byte status53;					//  ½×¶Î×´Ì¬53
	private byte status54;					//  ½×¶Î×´Ì¬54
	private byte status55;					//  ½×¶Î×´Ì¬55
	private byte status56;					//  ½×¶Î×´Ì¬56
	private byte status57;					//  ½×¶Î×´Ì¬57
	private byte status58;					//  ½×¶Î×´Ì¬58
	private byte status59;					//  ½×¶Î×´Ì¬59
	private byte status60;					//  ½×¶Î×´Ì¬60
	private byte status61;					//  ½×¶Î×´Ì¬61
	private byte status62;					//  ½×¶Î×´Ì¬62
	private byte status63;					//  ½×¶Î×´Ì¬63
	private byte status64;					//  ½×¶Î×´Ì¬64
	private byte status65;					//  ½×¶Î×´Ì¬65
	private byte status66;					//  ½×¶Î×´Ì¬66
	private byte status67;					//  ½×¶Î×´Ì¬67
	private byte status68;					//  ½×¶Î×´Ì¬68
	private byte status69;					//  ½×¶Î×´Ì¬69
	private byte status70;					//  ½×¶Î×´Ì¬70
	private byte status71;					//  ½×¶Î×´Ì¬71
	private byte status72;					//  ½×¶Î×´Ì¬72
	private byte status73;					//  ½×¶Î×´Ì¬73
	private byte status74;					//  ½×¶Î×´Ì¬74
	private byte status75;					//  ½×¶Î×´Ì¬75
	private byte status76;					//  ½×¶Î×´Ì¬76
	private byte status77;					//  ½×¶Î×´Ì¬77
	private byte status78;					//  ½×¶Î×´Ì¬78
	private byte status79;					//  ½×¶Î×´Ì¬79
	private byte status80;					//  ½×¶Î×´Ì¬80
	private byte status81;					//  ½×¶Î×´Ì¬81
	private byte status82;					//  ½×¶Î×´Ì¬82
	private byte status83;					//  ½×¶Î×´Ì¬83
	private byte status84;					//  ½×¶Î×´Ì¬84
	private byte status85;					//  ½×¶Î×´Ì¬85
	private byte status86;					//  ½×¶Î×´Ì¬86
	private byte status87;					//  ½×¶Î×´Ì¬87
	private byte status88;					//  ½×¶Î×´Ì¬88
	private byte status89;					//  ½×¶Î×´Ì¬89
	private byte status90;					//  ½×¶Î×´Ì¬90
	private byte status91;					//  ½×¶Î×´Ì¬91
	private byte status92;					//  ½×¶Î×´Ì¬92
	private byte status93;					//  ½×¶Î×´Ì¬93
	private byte status94;					//  ½×¶Î×´Ì¬94
	private byte status95;					//  ½×¶Î×´Ì¬95
	private byte status96;					//  ½×¶Î×´Ì¬96
	private byte status97;					//  ½×¶Î×´Ì¬97
	private byte status98;					//  ½×¶Î×´Ì¬98
	private byte status99;					//  ½×¶Î×´Ì¬99
	
//	private byte status;					//  ×´Ì¬
//	private String remark;					//±¸×¢ĞÅÏ¢
//	private Long creator;					//´´½¨Õß
//	private Date creationTime;				//´´½¨Ê±¼ä
//	private Long modifier;					//×îºóĞŞ¸ÄÕß
//	private Date modificationTime;			//×îºóĞŞ¸ÄÊ±¼ä
	private Long refObjectId; // Ïà¹Ø¶ÔÏóID
	private String refObjectName; // Ïà¹Ø¶ÔÏóÃû³Æ
	private Integer refObjectType; // Ïà¹Ø¶ÔÏóÀàĞÍ
	
	private String string0;
	private String string1;
	private String string2;
	private String string3;
	private String string4;
	private String string5;
	private String string6;
	private String string7;
	private String string8;
	private String string9;
	
	/**
	 * 
	 */
	public LifeStage() {
		super();
	}
	/**
	 * 
	 * @param id
	 */
	public LifeStage(Long id) {
		super();
		setId(id);
	}
	
	
	/**
	 * ¸ù¾İ RFSObject ´´½¨ÉúÃü½×¶Î±í¡£
	 * Ö÷ÒªÉèÖÃµÄÊôĞÔÎª£ºid, name, managerId, managerName, objectType, 
	 * remark, status, type.
	 * @param object
	 */
	public LifeStage(RFSObject object){
		super();
		copyFrom(object);
	}

	public void copyFrom(RFSObject object){
		setId(object.getId());
		setName(object.getName());
		setManagerId(object.getDutyClerkID());
		setManagerName(object.getDutyClerkName());
		setObjectType(object.getObjectType());
		setRemark(object.getRemark());
		setStatus(object.getStatus());
		setType(object.getType());
	}
	
	public byte getStatus32() {
		return status32;
	}
	public void setStatus32(byte status32) {
		this.status32 = status32;
	}
	public byte getStatus33() {
		return status33;
	}
	public void setStatus33(byte status33) {
		this.status33 = status33;
	}
	public byte getStatus34() {
		return status34;
	}
	public void setStatus34(byte status34) {
		this.status34 = status34;
	}
	public byte getStatus35() {
		return status35;
	}
	public void setStatus35(byte status35) {
		this.status35 = status35;
	}
	public byte getStatus36() {
		return status36;
	}
	public void setStatus36(byte status36) {
		this.status36 = status36;
	}
	public byte getStatus37() {
		return status37;
	}
	public void setStatus37(byte status37) {
		this.status37 = status37;
	}
	public byte getStatus38() {
		return status38;
	}
	public void setStatus38(byte status38) {
		this.status38 = status38;
	}
	public byte getStatus39() {
		return status39;
	}
	public void setStatus39(byte status39) {
		this.status39 = status39;
	}
	public byte getStatus40() {
		return status40;
	}
	public void setStatus40(byte status40) {
		this.status40 = status40;
	}
	public byte getStatus41() {
		return status41;
	}
	public void setStatus41(byte status41) {
		this.status41 = status41;
	}
	public byte getStatus42() {
		return status42;
	}
	public void setStatus42(byte status42) {
		this.status42 = status42;
	}
	public byte getStatus43() {
		return status43;
	}
	public void setStatus43(byte status43) {
		this.status43 = status43;
	}
	public byte getStatus44() {
		return status44;
	}
	public void setStatus44(byte status44) {
		this.status44 = status44;
	}
	public byte getStatus45() {
		return status45;
	}
	public void setStatus45(byte status45) {
		this.status45 = status45;
	}
	public byte getStatus46() {
		return status46;
	}
	public void setStatus46(byte status46) {
		this.status46 = status46;
	}
	public byte getStatus47() {
		return status47;
	}
	public void setStatus47(byte status47) {
		this.status47 = status47;
	}
	public byte getStatus48() {
		return status48;
	}
	public void setStatus48(byte status48) {
		this.status48 = status48;
	}
	public byte getStatus49() {
		return status49;
	}
	public void setStatus49(byte status49) {
		this.status49 = status49;
	}
	public byte getStatus50() {
		return status50;
	}
	public void setStatus50(byte status50) {
		this.status50 = status50;
	}
	public byte getStatus51() {
		return status51;
	}
	public void setStatus51(byte status51) {
		this.status51 = status51;
	}
	public byte getStatus52() {
		return status52;
	}
	public void setStatus52(byte status52) {
		this.status52 = status52;
	}
	public byte getStatus53() {
		return status53;
	}
	public void setStatus53(byte status53) {
		this.status53 = status53;
	}
	public byte getStatus54() {
		return status54;
	}
	public void setStatus54(byte status54) {
		this.status54 = status54;
	}
	public byte getStatus55() {
		return status55;
	}
	public void setStatus55(byte status55) {
		this.status55 = status55;
	}
	public byte getStatus56() {
		return status56;
	}
	public void setStatus56(byte status56) {
		this.status56 = status56;
	}
	public byte getStatus57() {
		return status57;
	}
	public void setStatus57(byte status57) {
		this.status57 = status57;
	}
	public byte getStatus58() {
		return status58;
	}
	public void setStatus58(byte status58) {
		this.status58 = status58;
	}
	public byte getStatus59() {
		return status59;
	}
	public void setStatus59(byte status59) {
		this.status59 = status59;
	}
	public byte getStatus60() {
		return status60;
	}
	public void setStatus60(byte status60) {
		this.status60 = status60;
	}
	public byte getStatus61() {
		return status61;
	}
	public void setStatus61(byte status61) {
		this.status61 = status61;
	}
	public byte getStatus62() {
		return status62;
	}
	public void setStatus62(byte status62) {
		this.status62 = status62;
	}
	public byte getStatus63() {
		return status63;
	}
	public void setStatus63(byte status63) {
		this.status63 = status63;
	}
	public byte getStatus64() {
		return status64;
	}
	public void setStatus64(byte status64) {
		this.status64 = status64;
	}
	public byte getStatus65() {
		return status65;
	}
	public void setStatus65(byte status65) {
		this.status65 = status65;
	}
	public byte getStatus66() {
		return status66;
	}
	public void setStatus66(byte status66) {
		this.status66 = status66;
	}
	public byte getStatus67() {
		return status67;
	}
	public void setStatus67(byte status67) {
		this.status67 = status67;
	}
	public byte getStatus68() {
		return status68;
	}
	public void setStatus68(byte status68) {
		this.status68 = status68;
	}
	public byte getStatus69() {
		return status69;
	}
	public void setStatus69(byte status69) {
		this.status69 = status69;
	}
	public byte getStatus70() {
		return status70;
	}
	public void setStatus70(byte status70) {
		this.status70 = status70;
	}
	public byte getStatus71() {
		return status71;
	}
	public void setStatus71(byte status71) {
		this.status71 = status71;
	}
	public byte getStatus72() {
		return status72;
	}
	public void setStatus72(byte status72) {
		this.status72 = status72;
	}
	public byte getStatus73() {
		return status73;
	}
	public void setStatus73(byte status73) {
		this.status73 = status73;
	}
	public byte getStatus74() {
		return status74;
	}
	public void setStatus74(byte status74) {
		this.status74 = status74;
	}
	public byte getStatus75() {
		return status75;
	}
	public void setStatus75(byte status75) {
		this.status75 = status75;
	}
	public byte getStatus76() {
		return status76;
	}
	public void setStatus76(byte status76) {
		this.status76 = status76;
	}
	public byte getStatus77() {
		return status77;
	}
	public void setStatus77(byte status77) {
		this.status77 = status77;
	}
	public byte getStatus78() {
		return status78;
	}
	public void setStatus78(byte status78) {
		this.status78 = status78;
	}
	public byte getStatus79() {
		return status79;
	}
	public void setStatus79(byte status79) {
		this.status79 = status79;
	}
	public byte getStatus80() {
		return status80;
	}
	public void setStatus80(byte status80) {
		this.status80 = status80;
	}
	public byte getStatus81() {
		return status81;
	}
	public void setStatus81(byte status81) {
		this.status81 = status81;
	}
	public byte getStatus82() {
		return status82;
	}
	public void setStatus82(byte status82) {
		this.status82 = status82;
	}
	public byte getStatus83() {
		return status83;
	}
	public void setStatus83(byte status83) {
		this.status83 = status83;
	}
	public byte getStatus84() {
		return status84;
	}
	public void setStatus84(byte status84) {
		this.status84 = status84;
	}
	public byte getStatus85() {
		return status85;
	}
	public void setStatus85(byte status85) {
		this.status85 = status85;
	}
	public byte getStatus86() {
		return status86;
	}
	public void setStatus86(byte status86) {
		this.status86 = status86;
	}
	public byte getStatus87() {
		return status87;
	}
	public void setStatus87(byte status87) {
		this.status87 = status87;
	}
	public byte getStatus88() {
		return status88;
	}
	public void setStatus88(byte status88) {
		this.status88 = status88;
	}
	public byte getStatus89() {
		return status89;
	}
	public void setStatus89(byte status89) {
		this.status89 = status89;
	}
	public byte getStatus90() {
		return status90;
	}
	public void setStatus90(byte status90) {
		this.status90 = status90;
	}
	public byte getStatus91() {
		return status91;
	}
	public void setStatus91(byte status91) {
		this.status91 = status91;
	}
	public byte getStatus92() {
		return status92;
	}
	public void setStatus92(byte status92) {
		this.status92 = status92;
	}
	public byte getStatus93() {
		return status93;
	}
	public void setStatus93(byte status93) {
		this.status93 = status93;
	}
	public byte getStatus94() {
		return status94;
	}
	public void setStatus94(byte status94) {
		this.status94 = status94;
	}
	public byte getStatus95() {
		return status95;
	}
	public void setStatus95(byte status95) {
		this.status95 = status95;
	}
	public byte getStatus96() {
		return status96;
	}
	public void setStatus96(byte status96) {
		this.status96 = status96;
	}
	public byte getStatus97() {
		return status97;
	}
	public void setStatus97(byte status97) {
		this.status97 = status97;
	}
	public byte getStatus98() {
		return status98;
	}
	public void setStatus98(byte status98) {
		this.status98 = status98;
	}
	public byte getStatus99() {
		return status99;
	}
	public void setStatus99(byte status99) {
		this.status99 = status99;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public Long getObjectId() {
		return getId();
	}
	public void setObjectId(Long objectId) {
		setId(objectId);
	}
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public byte getStatus0() {
		return status0;
	}
	public void setStatus0(byte status0) {
		this.status0 = status0;
	}
	public byte getStatus1() {
		return status1;
	}
	public void setStatus1(byte status1) {
		this.status1 = status1;
	}
	public byte getStatus2() {
		return status2;
	}
	public void setStatus2(byte status2) {
		this.status2 = status2;
	}
	public byte getStatus3() {
		return status3;
	}
	public void setStatus3(byte status3) {
		this.status3 = status3;
	}
	public byte getStatus4() {
		return status4;
	}
	public void setStatus4(byte status4) {
		this.status4 = status4;
	}
	public byte getStatus5() {
		return status5;
	}
	public void setStatus5(byte status5) {
		this.status5 = status5;
	}
	public byte getStatus6() {
		return status6;
	}
	public void setStatus6(byte status6) {
		this.status6 = status6;
	}
	public byte getStatus7() {
		return status7;
	}
	public void setStatus7(byte status7) {
		this.status7 = status7;
	}
	public byte getStatus8() {
		return status8;
	}
	public void setStatus8(byte status8) {
		this.status8 = status8;
	}
	public byte getStatus9() {
		return status9;
	}
	public void setStatus9(byte status9) {
		this.status9 = status9;
	}
	public byte getStatus10() {
		return status10;
	}
	public void setStatus10(byte status10) {
		this.status10 = status10;
	}
	public byte getStatus11() {
		return status11;
	}
	public void setStatus11(byte status11) {
		this.status11 = status11;
	}
	public byte getStatus12() {
		return status12;
	}
	public void setStatus12(byte status12) {
		this.status12 = status12;
	}
	public byte getStatus13() {
		return status13;
	}
	public void setStatus13(byte status13) {
		this.status13 = status13;
	}
	public byte getStatus14() {
		return status14;
	}
	public void setStatus14(byte status14) {
		this.status14 = status14;
	}
	public byte getStatus15() {
		return status15;
	}
	public void setStatus15(byte status15) {
		this.status15 = status15;
	}
	public byte getStatus16() {
		return status16;
	}
	public void setStatus16(byte status16) {
		this.status16 = status16;
	}
	public byte getStatus17() {
		return status17;
	}
	public void setStatus17(byte status17) {
		this.status17 = status17;
	}
	public byte getStatus18() {
		return status18;
	}
	public void setStatus18(byte status18) {
		this.status18 = status18;
	}
	public byte getStatus19() {
		return status19;
	}
	public void setStatus19(byte status19) {
		this.status19 = status19;
	}
	public byte getStatus20() {
		return status20;
	}
	public void setStatus20(byte status20) {
		this.status20 = status20;
	}
	public byte getStatus21() {
		return status21;
	}
	public void setStatus21(byte status21) {
		this.status21 = status21;
	}
	public byte getStatus22() {
		return status22;
	}
	public void setStatus22(byte status22) {
		this.status22 = status22;
	}
	public byte getStatus23() {
		return status23;
	}
	public void setStatus23(byte status23) {
		this.status23 = status23;
	}
	public byte getStatus24() {
		return status24;
	}
	public void setStatus24(byte status24) {
		this.status24 = status24;
	}
	public byte getStatus25() {
		return status25;
	}
	public void setStatus25(byte status25) {
		this.status25 = status25;
	}
	public byte getStatus26() {
		return status26;
	}
	public void setStatus26(byte status26) {
		this.status26 = status26;
	}
	public byte getStatus27() {
		return status27;
	}
	public void setStatus27(byte status27) {
		this.status27 = status27;
	}
	public byte getStatus28() {
		return status28;
	}
	public void setStatus28(byte status28) {
		this.status28 = status28;
	}
	public byte getStatus29() {
		return status29;
	}
	public void setStatus29(byte status29) {
		this.status29 = status29;
	}
	public byte getStatus30() {
		return status30;
	}
	public void setStatus30(byte status30) {
		this.status30 = status30;
	}
	public byte getStatus31() {
		return status31;
	}
	public void setStatus31(byte status31) {
		this.status31 = status31;
	}
//	public short getType() {
//		return type;
//	}
//	public void setType(int type) {
//		this.type = type;
//	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * @return the refObjectId
	 */
	public Long getRefObjectId() {
		return refObjectId;
	}
	/**
	 * @param refObjectId the refObjectId to set
	 */
	public void setRefObjectId(Long refObjectId) {
		this.refObjectId = refObjectId;
	}
	/**
	 * @return the refObjectName
	 */
	public String getRefObjectName() {
		return refObjectName;
	}
	/**
	 * @param refObjectName the refObjectName to set
	 */
	public void setRefObjectName(String refObjectName) {
		this.refObjectName = refObjectName;
	}
	/**
	 * @return the refObjectType
	 */
	public Integer getRefObjectType() {
		return refObjectType;
	}
	/**
	 * @param refObjectType the refObjectType to set
	 */
	public void setRefObjectType(Integer refObjectType) {
		this.refObjectType = refObjectType;
	}
	/**
	 * @return the string0
	 */
	public String getString0() {
		return string0;
	}
	/**
	 * @param string0 the string0 to set
	 */
	public void setString0(String string0) {
		this.string0 = string0;
	}
	/**
	 * @return the string1
	 */
	public String getString1() {
		return string1;
	}
	/**
	 * @param string1 the string1 to set
	 */
	public void setString1(String string1) {
		this.string1 = string1;
	}
	/**
	 * @return the string2
	 */
	public String getString2() {
		return string2;
	}
	/**
	 * @param string2 the string2 to set
	 */
	public void setString2(String string2) {
		this.string2 = string2;
	}
	/**
	 * @return the string3
	 */
	public String getString3() {
		return string3;
	}
	/**
	 * @param string3 the string3 to set
	 */
	public void setString3(String string3) {
		this.string3 = string3;
	}
	/**
	 * @return the string4
	 */
	public String getString4() {
		return string4;
	}
	/**
	 * @param string4 the string4 to set
	 */
	public void setString4(String string4) {
		this.string4 = string4;
	}
	/**
	 * @return the string5
	 */
	public String getString5() {
		return string5;
	}
	/**
	 * @param string5 the string5 to set
	 */
	public void setString5(String string5) {
		this.string5 = string5;
	}
	/**
	 * @return the string6
	 */
	public String getString6() {
		return string6;
	}
	/**
	 * @param string6 the string6 to set
	 */
	public void setString6(String string6) {
		this.string6 = string6;
	}
	/**
	 * @return the string7
	 */
	public String getString7() {
		return string7;
	}
	/**
	 * @param string7 the string7 to set
	 */
	public void setString7(String string7) {
		this.string7 = string7;
	}
	/**
	 * @return the string8
	 */
	public String getString8() {
		return string8;
	}
	/**
	 * @param string8 the string8 to set
	 */
	public void setString8(String string8) {
		this.string8 = string8;
	}
	/**
	 * @return the string9
	 */
	public String getString9() {
		return string9;
	}
	/**
	 * @param string9 the string9 to set
	 */
	public void setString9(String string9) {
		this.string9 = string9;
	}
	
}
