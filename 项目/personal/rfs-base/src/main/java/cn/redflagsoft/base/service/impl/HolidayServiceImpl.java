package cn.redflagsoft.base.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.util.DateUtils;
import org.opoo.cache.Cache;
import org.opoo.cache.CacheSizes;
import org.opoo.cache.Cacheable;
import org.opoo.cache.NullCache;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Holiday;
import cn.redflagsoft.base.dao.HolidayDao;
import cn.redflagsoft.base.service.HolidayService;
import cn.redflagsoft.base.util.DateUtil;
import cn.redflagsoft.base.util.MapUtils;

public class HolidayServiceImpl implements HolidayService {
	private static final Log log = LogFactory.getLog(HolidayServiceImpl.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	private HolidayDao holidayDao;
	private Cache<Integer, YearHolidays> holidaysCache = new NullCache<Integer, YearHolidays>();
	
	public HolidayDao getHolidayDao() {
		return holidayDao;
	}

	public void setHolidayDao(HolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}

	/**
	 * @return the holidaysCache
	 */
	public Cache<Integer, YearHolidays> getHolidaysCache() {
		return holidaysCache;
	}

	/**
	 * @param holidaysCache the holidaysCache to set
	 */
	public void setHolidaysCache(Cache<Integer, YearHolidays> holidaysCache) {
		this.holidaysCache = holidaysCache;
	}

	public int getHolidays(Date startDate, Date finishDate) {
		return holidayDao.getHoliDays(startDate, finishDate);
	}
	
	public Holiday saveHoliday(Holiday holiday) {
		holidaysCache.clear();
		return holidayDao.save(holiday);
	}
	
	/**
	 * 判断指定的日期是否时节假日。
	 * 
	 * @param date
	 * @return
	 */
	public boolean isHoliday(Date date){
		YearHolidays holidays = getYearHolidays(date);
		return holidays.contains(date);
	}
	
	public YearHolidays getYearHolidays(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		YearHolidays yearHolidays = holidaysCache.get(year);
		if(yearHolidays == null){
			yearHolidays = getYearHolidays(year);
			holidaysCache.put(year, yearHolidays);
		}
		return yearHolidays;
	}
	
	private YearHolidays getYearHolidays(int year){
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		begin.set(year, Calendar.JANUARY, 1);
		end.set(year + 1, Calendar.JANUARY, 1);

		List<Date> list = holidayDao.findHolidaysDateList(begin.getTime(), end.getTime());
		YearHolidays days = new YearHolidays(year);
		for (Date date : list) {
			//log.debug("添加节假日：" + date);
			days.addHoliday(date);
		}
		if(IS_DEBUG_ENABLED){
			log.debug("对应的 " + year + " 年的节假日天数：" + days.holidays.length);
//			for(long day: days.holidays){
//				log.debug(new Date(day));
//			}
		}
		return days;
	}
	

	/**
     * 获取日期设置对象（含工作日、节假日）
     */
    public Holiday getDay(Date time) {
        return holidayDao.get(Restrictions.logic(
                Restrictions.ge("holDate", DateUtils.toStartOfDay(time))).and(
                Restrictions.le("holDate", DateUtils.toEndOfDay(time))));
    }
  
	/**
	 * 仅获取是节假日的对象
	 */
	public Holiday getHoliday(Date time) {
		return holidayDao.get(Restrictions.logic(
				Restrictions.ge("holDate", DateUtils.toStartOfDay(time))).and(
				Restrictions.le("holDate", DateUtils.toEndOfDay(time))).and(
				Restrictions.eq("status", (byte) 1)));
	}
	
	public List<Date> findUndefDaysInBound(Date lowerBound, Date upperBound) {
		if (lowerBound.compareTo(upperBound) == 1) {
			throw new RuntimeException("lowerBound greater than upperBound, it is not allow ... ");
		}
		List<Date> dates = new ArrayList<Date>();
		//查询指定日期Holiday
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(Restrictions.logic(
				Restrictions.ge("holDate", DateUtils.toStartOfDay(lowerBound)))
				.and(Restrictions.le("holDate", DateUtils.toEndOfDay(upperBound))));
		rf.setOrder(Order.asc("holDate"));
		List<Holiday> holidays = holidayDao.find(rf);
		//初始化变量
		Date lower = DateUtils.toStartOfDay(lowerBound);
		Date upper = DateUtils.toStartOfDay(upperBound);
		Date current = null;
		Holiday holiday;
		int index = 0;
		int len = holidays.size();
		//搜索未在Holiday表中定义的所有日期
		while (lower.compareTo(upper) <= 0) {
			if (len == 0) {//处理查询列表为空
				dates.add(lower);
			} else if (index < len){//处理查询结果集列表
				holiday = holidays.get(index);
				current = DateUtils.toStartOfDay(holiday.getHolDate());
				if (lower.compareTo(current) == -1) {
					dates.add(lower);
				} else if (lower.compareTo(current) == 0) {
					index++;
				}
			} else if (index == len) {//查询结果集列表处理完成后,处理上限(如:结果集最大至 2009-01-31为止,上线为2009-06-30)
				dates.add(lower);
			}
			lower = DateUtil.daysLater(lower, 1);//DateUtil.getDays(lower, 1, Calendar.DATE);//日期累加
		}
		return dates;
	}

	public List<Date> findRepeatedDays() {
		return holidayDao.findRepeatedDays();
	}

	public List<Holiday> findDaysDefineInBound(Date lowerBound, Date upperBound) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(Restrictions.logic(
				Restrictions.ge("holDate", DateUtils.toStartOfDay(lowerBound)))
				.and(Restrictions.le("holDate", DateUtils.toEndOfDay(upperBound))));
		rf.setOrder(Order.asc("holDate"));
		return holidayDao.find(rf);
	}
	
	public int deleteHoliday(Date time) {
		int remove = holidayDao.remove(Restrictions.eq("holDate", time));
		holidaysCache.clear();
		return remove;
	}

	public int deleteHoliday(Long id) {
		int remove = holidayDao.remove(id);	
		holidaysCache.clear();
		return remove;
	}

	public Holiday updateHoliday(Holiday holiday) {
		Holiday update = holidayDao.update(holiday);
		holidaysCache.clear();
		return update;
	}

	public Holiday updateDayDefine(Date day, byte status, String note,
			String remark) {
		Holiday holiday = holidayDao.get(Restrictions.logic(
				Restrictions.eq("holDate", day)).and(
				Restrictions.eq("status", status)));
		if (holiday != null) {
			holiday.setNote(note);
			holiday.setRemark(remark);
			holiday = holidayDao.update(holiday);
			holidaysCache.clear();
		}
		return holiday;
	}
	
	
	public static class YearHolidays implements Serializable, Cacheable{
		private static final long serialVersionUID = 4025664487460842368L;
		private int year;
		private long[] holidays = new long[0];
		
		/* (non-Javadoc)
		 * @see org.opoo.cache.Cacheable#getCachedSize()
		 */
		public int getCachedSize() {
			return CacheSizes.sizeOfObject() 
				+ CacheSizes.sizeOfInt()
				+ CacheSizes.sizeOfLongList(holidays.length);
		}
		
		/**
		 * @param year
		 * @param holidays
		 */
		public YearHolidays(int year) {
			super();
			this.year = year;
		}
		
		public void addHoliday(Date date){
			date = DateUtils.toStartOfDay(date);
			long time = date.getTime();
			if(!contains(time)){
				holidays = ArrayUtils.add(holidays, time);
				//log.debug("Add date to cache: " + date);
			}
		}
		
		public boolean contains(Date date){
			date = DateUtils.toStartOfDay(date);
			long time = date.getTime();
			return contains(time);
		}
		
		boolean contains(long time){
			return ArrayUtils.contains(holidays, time);
		}
		
		/**
		 * 
		 */
		public YearHolidays() {
			super();
		}

		/**
		 * @return the year
		 */
		public int getYear() {
			return year;
		}
		/**
		 * @param year the year to set
		 */
		public void setYear(int year) {
			this.year = year;
		}
		/**
		 * @return the holidays
		 */
		public long[] getHolidays() {
			return holidays;
		}
		/**
		 * @param holidays the holidays to set
		 */
		public void setHolidays(long[] holidays) {
			this.holidays = holidays;
		}
	}
	
	/**
	 * 先查询出所有的日期，再依据条件进行筛选
	 */
	@Queryable
	public List<Holiday> findDays(ResultFilter filter) {
		ResultFilter newFilter = ResultFilter.createEmptyResultFilter();
		if(filter != null){
			newFilter.setOrder(filter.getOrder());
		}
		
		List<Holiday> listAll = holidayDao.find(newFilter);
		
		List<Holiday> list = new ArrayList<Holiday>();
		
		Map<String, ?> parameters = filter.getRawParameters();
		String year = null;
		String month = null;
		String status = null;
		
		for(int i=0;i<parameters.size();i++){
			String temp = MapUtils.getString(parameters, "q["+ i+"].n");
			if(temp != null){
				if(temp.equals("year")) {
					year = MapUtils.getString(parameters, "q["+ i+"].v");
				}
				
				if(temp.equals("month")) {
					month = MapUtils.getString(parameters, "q["+ i+"].v");
				}
				
				if(temp.equals("status")) {
					status = MapUtils.getString(parameters, "q["+ i+"].v");
				}
			}  
		}
		
		for(Holiday holiday:listAll) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(holiday.getHolDate());
			short y = (short) calendar.get(Calendar.YEAR);
			byte m = (byte) (calendar.get(Calendar.MONTH) + 1);
			
			if(year != null) {
				if(month != null) {
					if(status != null){
						short ye = Short.parseShort(year);
						byte mo = Byte.parseByte(month);
						byte st = Byte.parseByte(status);
						
						
						if(y==ye && m==mo && st == holiday.getStatus()){
							list.add(holiday);
						}
					}else {
						short ye = Short.parseShort(year);
						byte mo = Byte.parseByte(month);
						
						if(y==ye && m==mo){
							list.add(holiday);
						}
						
					}
				}else{
					if(status != null) {
						byte st = Byte.parseByte(status);
						short ye = Short.parseShort(year);
						
						if(st== holiday.getStatus() && y==ye){
							list.add(holiday);
						}
					}else{
						short ye = Short.parseShort(year);
						if(y==ye){
							list.add(holiday);
						}
					}
				}
			}else {
				if(month != null) {
					if(status != null){
						byte mo = Byte.parseByte(month);
						byte st = Byte.parseByte(status);
						if(m==mo && st == holiday.getStatus()){
							list.add(holiday);
						}
					}else{
						byte mo = Byte.parseByte(month);
						if(m==mo){
							list.add(holiday);
						}
					}
				}else{
					if(status != null) {
						byte st = Byte.parseByte(status);
						if(st== holiday.getStatus()){
							list.add(holiday);
						}
					}else {
						list.add(holiday);
					}
				}
			}
			
		}
		
		return list;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.HolidayService#getDay(long)
	 */
	public Holiday getDay(long id) {
		return holidayDao.get(id);
	}
}
