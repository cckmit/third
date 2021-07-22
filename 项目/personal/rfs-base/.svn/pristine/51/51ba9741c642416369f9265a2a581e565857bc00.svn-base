package cn.redflagsoft.base.dao;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Holiday;

public interface HolidayDao extends Dao<Holiday, Long> {
	
	/**
	 * 查询开始时间结束时间之间的节假日列表，包括开始时间的日期，但不包含结束时间的日期。
	 * 
	 * @param startDate
	 * @param finishDate
	 * @return
	 */
	List<Date> findHolidaysDateList(Date startDate, Date finishDate);
	
	/**
	 * 查询开始时间结束时间之间的节假日列表，包括开始时间的日期，但不包含结束时间的日期。
	 * 
	 * @param startDate
	 * @param finishDate
	 * @return
	 */
	List<Holiday> findHolidays(Date startDate, Date finishDate);
	
	/**
	 * 查询开始时间结束时间之间的节假日天数，包括开始时间的日期，但不包含结束时间的日期。
	 * @param startDate
	 * @param finishiDate
	 * @return
	 */
	int getHoliDays(Date startDate, Date finishiDate);
	
	/**
	 * 查询节假日中重复定义的日期集合。
	 * 
	 * @return
	 */
	List<Date> findRepeatedDays();
}
