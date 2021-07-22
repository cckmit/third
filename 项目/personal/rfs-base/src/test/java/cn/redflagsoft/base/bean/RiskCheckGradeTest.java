/*
 * $Id: RiskCheckGradeTest.java 5241 2011-12-21 09:28:27Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.test.BaseTests;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class RiskCheckGradeTest extends BaseTests {
	
	protected RiskService riskService;
	
	public void testCheckGrade(){
		System.out.println(riskService);

		Risk risk = new Risk();
		risk.setId(14681L);
		risk.setObjectID(26475L);
		risk.setType(1);
		risk.setObjectType(102);
		risk.setDutyerType((short) 3);
		risk.setDutyerID(1257694555862L);
		risk.setValueSign((byte) 1);
		risk.setValueType((byte) 3);
		risk.setValueUnit((byte) 4);
		risk.setValueFormat("##,###日");
		risk.setValueSource((byte) 1);
		risk.setValue(BigDecimal.ZERO);
		risk.setGrade((byte) 0);
		risk.setScaleValue1(BigDecimal.ZERO);
		risk.setScaleValue2(new BigDecimal(25));
		risk.setScaleValue3(new BigDecimal(30));
		risk.setScaleValue4(new BigDecimal(0));
		risk.setScaleValue5(new BigDecimal(35));
		risk.setScaleValue6(new BigDecimal(0));
		risk.setScaleMark((byte)22);
		risk.setScaleValue(new BigDecimal(30));
		risk.setRefType(101133);
		risk.setRefObjectId(1256606244623L);
		risk.setRefObjectType(1002);
		risk.setRefObjectName("西丽百旺第二工业区西侧边坡治理");
		risk.setPause(Risk.PAUSE_YES);
		risk.setName("资产移交时限监察");
		risk.setStatus((byte) 3);
		risk.setCreationTime(newDate(2011, 10, 29));//2011-11-29
		risk.setModificationTime(newDate(2011, 10, 29, 16, 20, 17));
		risk.setConclusion("正常");
		risk.setObjectAttr("timeUsed");
		risk.setRuleID(21L);
		risk.setInitValue(BigDecimal.ZERO);
		//risk.setLastRunTime(newDate(2011, 10, 29, 16, 20, 17));
		risk.setBeginTime(newDate(2011, 5, 9, 16, 20, 17));
		risk.setHangTime(newDate(2011, 6, 20, 16, 20, 17));
		
		
		byte oldGrade = risk.checkGrade(null, new Date());
		System.out.println(oldGrade);
		System.out.println(risk.getGrade());
		System.out.println(risk.getGradeExplain());
		System.out.println(risk.getGradeName());
		System.out.println(risk.getValue());
	}

	private static Date newDate(int year, int month, int date){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date);
		return calendar.getTime();
	}
	
	private static Date newDate(int year, int month, int date, int hourOfDay, int minute, int second){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date, hourOfDay, minute, second);
		return calendar.getTime();
	}
	
	public static void main(String[] args){
		System.out.println(newDate(2011, 9, 10));
	}
}
