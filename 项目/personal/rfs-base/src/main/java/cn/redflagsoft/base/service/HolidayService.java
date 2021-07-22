package cn.redflagsoft.base.service;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Holiday;

public interface HolidayService extends HolidayChecker{
	
	/**
	 * 判断指定的日期是否时节假日。
	 * @param date
	 * @return
	 */
	boolean isHoliday(Date date);
	/**
	 * 获取指定日期间假日天数
	 * 
	 * @param startDate
	 * @param finishiDate
	 * @return int
	 */
	int getHolidays(Date startDate, Date finishDate);
	
	/**
	 * 保存holiday
	 * 
	 * @param holiday
	 * @return Holiday
	 */
	Holiday saveHoliday(Holiday holiday);
	
	/**
	 * 修改指定holiday
	 * 
	 * @param holiday
	 * @return Holiday
	 */
	Holiday updateHoliday(Holiday holiday);

	/**
	 * 更新日期定义
	 * 
	 * @param day
	 * @param status
	 * @param note
	 * @param remark
	 * @return Holiday
	 */
	Holiday updateDayDefine(Date day, byte status, String note, String remark);
	
	/**
	 * 删除指定编号holiday
	 * 
	 * @param id
	 * @return int
	 */
	int deleteHoliday(Long id);
	
	/**
	 * 删除指定日期holiday
	 * 
	 * @param time
	 * @return int
	 */
	int deleteHoliday(Date time);
	
	/**
     * 获取指定日期day（含工作日、节假日）
     * 
     * @param time
     * @return Holiday
     */
    Holiday getDay(Date time);
    
	/**
	 * 获取指定日期holiday
	 * 
	 * @param time
	 * @return Holiday
	 */
	Holiday getHoliday(Date time);
	
	/**
	 * 获取重复的日期定义 
	 * 
	 * @return List
	 */
	List<Date> findRepeatedDays();
	
	/**
	 * 获取指定日期范围内,Holiday中定义的所有数据 
	 * 
	 * @param lowerBound
	 * @param upperBound
	 * @return List<Holiday>
	 */
	List<Holiday> findDaysDefineInBound(Date lowerBound, Date upperBound);
	
	/**
	 * 获取指定日期范围内,未在Holiday表中定义的所有日期
	 * 
	 * @param lowerBound
	 * @param upperBound
	 * @return List<Date>
	 */
	List<Date> findUndefDaysInBound(Date lowerBound, Date upperBound);
	
	/**
	 * 
	 * 前台带查询条件查询日期
	 * @param yaer
	 * @param month
	 * @param status
	 * @return
	 */
	List<Holiday> findDays(ResultFilter filter);
	
	Holiday getDay(long id);
}
