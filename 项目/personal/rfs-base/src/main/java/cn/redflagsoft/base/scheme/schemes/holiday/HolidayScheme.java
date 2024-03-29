/*
 * $Id: HolidayScheme.java 6241 2013-06-25 04:53:10Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.holiday;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.opoo.apps.AppsGlobals;
import org.opoo.apps.Model;

import cn.redflagsoft.base.bean.Holiday;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.service.HolidayService;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class HolidayScheme extends AbstractScheme {
	private HolidayService holidayService;
	
	private List<Long> ids;
	private Date day;
	private Integer year;
	private Integer month;
	private Holiday holiday;

	/**
	 * @return the holidayService
	 */
	public HolidayService getHolidayService() {
		return holidayService;
	}

	/**
	 * @param holidayService the holidayService to set
	 */
	public void setHolidayService(HolidayService holidayService) {
		this.holidayService = holidayService;
	}

	/**
	 * @return the ids
	 */
	public List<Long> getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	/**
	 * @return the day
	 */
	public Date getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(Date day) {
		this.day = day;
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	/**
	 * @return the holiday
	 */
	public Holiday getHoliday() {
		return holiday;
	}

	/**
	 * @param holiday the holiday to set
	 */
	public void setHoliday(Holiday holiday) {
		this.holiday = holiday;
	}
	
	
//	holiday.status:0
//	holiday.holDate:2009-04-28T00:00:00
//	holiday.statusLabel:否
//	holiday.id:39931
//	holiday.status:1
//	holiday.holDate:2009-04-28T00:00:00
//	holiday.statusLabel:是
//	holiday.id:39931

	public Object doDeleteHolidays(){
		if(ids == null || ids.isEmpty()){
			return new Model(false, "请选择要删除的项", null);
		}
		for(Long id : ids){
			holidayService.deleteHoliday(id);
		}
		
		return "所选日期已删除。";
	}
	
	
	public Object doUpdateHoliday(){
		if(holiday == null || holiday.getId() == null){
			throw new SchemeException("所提交数据无效。");
		}
		
		Holiday holiday2 = holidayService.getDay(holiday.getId());
		if(holiday2 == null){
			throw new SchemeException("查不到指定的日期：" + holiday.getId());
		}
		
		//if(holiday.getHolDate() != null){
		//	holiday2.setHolDate(holiday.getHolDate());
		//}
		holiday2.setStatus(holiday.getStatus());
		holiday2.setRemark(holiday.getRemark());
		holidayService.updateHoliday(holiday2);
	
		return "日期已更新。";
	}
	
	public Object doAddHolidays(){
		if(day != null){
			return createOneDay(day);
		}else if(year != null && month != null){
			return createHolidays(year, month);
		}else{
			return new Model(false, "所提交的参数不完整，请检查。", null);
		}
	}
	
	/**
	 * @param year
	 * @param month
	 */
	private Object createHolidays(int year, int month) {
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, year);
		instance.set(Calendar.MONTH, month);
		int dateStart = 1;
		int dateEnd = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		int n = 0;
//		System.out.println(dateStart+ " " + dateEnd);
		
		for(int x = dateStart ; x <= dateEnd ; x++){
			instance.set(Calendar.DAY_OF_MONTH, x);
//			System.out.println(instance.getTime());
			Date date = instance.getTime();
			Holiday holiday2 = createHoliday(date);
			if(holiday2 != null){
				n++;
			}
		}
	
		String s = String.format("批量处理了 %s 个日期，其中新建 %s 个，有 %s 已经存在。", dateEnd, n, dateEnd - n);
		return s;
	}
	
	private Object createOneDay(Date date){
		Holiday holiday2 = createHoliday(date);
		if(holiday2 == null){
			return new Model(false, "当前日期已经存在：" + AppsGlobals.formatDate(date), null);
		}
		return "日期添加成功";
	}

	private Holiday createHoliday(Date date){
		Holiday day2 = holidayService.getDay(date);
		if(day2 != null){
//			throw new SchemeException("当前日期已经存在：" + AppsGlobals.formatDate(date));
			return null;
		}
		day2 = new Holiday();
		day2.setHolDate(date);
		
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		int dayOfWeek = instance.get(Calendar.DAY_OF_WEEK);
		byte status = 0;
		if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){
			status = 1;
		}

		day2.setStatus(status);
		if(status == 1){
			day2.setRemark("节假日");
		}else{
			day2.setRemark("工作日");
		}
		
		return holidayService.saveHoliday(day2);
	}
	
	public static void main(String[] args){
		Calendar calendar = Calendar.getInstance();
		int i = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println(i);
		System.out.println(Calendar.MONDAY);
		System.out.println(Calendar.TUESDAY);
		System.out.println(Calendar.WEDNESDAY);
		System.out.println(Calendar.THURSDAY);
		System.out.println(Calendar.FRIDAY);
		System.out.println(Calendar.SATURDAY);
		System.out.println(Calendar.SUNDAY);
		
		
		
		System.out.println(calendar.getActualMaximum(Calendar.YEAR));
		System.out.println(calendar.getActualMaximum(Calendar.MONTH));
		System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		System.out.println(calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		
		calendar.set(Calendar.MONTH, 1);
		System.out.println(calendar.getActualMaximum(Calendar.YEAR));
		System.out.println(calendar.getActualMaximum(Calendar.MONTH));
		System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		System.out.println(calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		
		int year = 2013;
		int month = 1;
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, year);
		instance.set(Calendar.MONTH, month);
		int dateStart = 1;
		int dateEnd = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		System.out.println(dateStart+ " " + dateEnd);
		
		for(int x = dateStart ; x <= dateEnd ; x++){
			instance.set(Calendar.DAY_OF_MONTH, x);
			System.out.println(instance.getTime());
		}
	}
}
