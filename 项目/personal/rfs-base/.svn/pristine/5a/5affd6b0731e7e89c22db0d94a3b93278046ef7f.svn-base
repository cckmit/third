package cn.redflagsoft.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.util.DateUtils;

import cn.redflagsoft.base.service.HolidayChecker;


/**
 * 日期处理工具类。
 * 
 * 该类中部分方法需要在应用启动后运行，不是一个严格意义上的工具类。
 */
public abstract class DateUtil {
    private static final Log log = LogFactory.getLog(DateUtil.class);
    
    private static final int WORKDAYS_MAX_QUERY_COUNT = 20000;
    
    public static final long MILLIS_OF_ONE_DAY = (24 * 60 * 60 * 1000L);// 24 * 60 * 60 * 1000L;

    public static final int YYMMDDhhmmssxxx = 15;

    public static final int YYYYMMDDhhmmss = 14;

    public static final int YYMMDDhhmmss = 12;

    public static final int YYMMDDhhmm = 10;

    public static final int YYMMDDhh = 8;

    public static final String TIME_FORMAT_yyyy = "yyyy";

    public static final String TIME_FORMAT_MM = "MM";

    public static final String TIME_FORMAT_dd = "dd";
    
    public static final String SHORT_DATE_TIME_STYLE = "yyyy-MM-dd HH:mm";
    
    public static SimpleDateFormat shortDateTimeFormat = new SimpleDateFormat(SHORT_DATE_TIME_STYLE);

    /**
     * 返回时间间隔。 注意，这个方法调用了动态类，必须初始化Globals的BeansHolder，在使用Spring的容器时，必须先启动
     * Spring容器。 时间单位 时间单位。定义：0 无，1 年，2 月，3 周，4 日，5 时，6 分，7 秒，8 毫秒。默认为0，表示无定义或忽略
     * 
     * @param beginTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param timeUnitFlag
     *            时间周期单位：1-年、2-月、3-周、4-日(工作日)、5-天（自然天）、6-时、7-分、8-秒、9-毫秒
     * @return
     * @see #getTimeUsed(Date, Date, int, boolean)
     */
    public static int getTimeUsed(Date beginTime, Date endTime, int timeUnitFlag) {
    	return getTimeUsed(beginTime, endTime, timeUnitFlag, true);
    }
    /**
     * 
     * @param beginTime
     * @param endTime
     * @param timeUnitFlag
     * @param includeFirstDay
     * @return
     */
    public static int getTimeUsed(Date beginTime, Date endTime, int timeUnitFlag, boolean includeFirstDay) {
    	try {
			TimeUnit.valueOf(timeUnitFlag);
		} catch (Exception e) {
			log.warn("值单位无效，直接返回0值： " + e.getMessage());
			return 0;
		}
    	
    	Date now = new Date();
        if (beginTime == null) {
            beginTime = now;
        }
        if (endTime == null) {
            endTime = now;
        }
        
        //int flag = timeUnit.getFlag();
        
        int result = 0;
        switch (timeUnitFlag) {
        case 1:// 年
            result = yearsBetween(beginTime, endTime);
            break;
        case 2:// 月
            result = monthsBetween(beginTime, endTime);
            break;
        case 3:// 周
            result = weeksBetween(beginTime, endTime);
            break;
        case 4:// 日
            result = workdaysBetween(beginTime, endTime, includeFirstDay);
            break;
        case 5:// 天
            result = daysBetween(beginTime, endTime, includeFirstDay);
            break;
        case 6:// 时
            result = hoursBetween(beginTime, endTime);
            break;
        case 7:// 分
            result = minutesBetween(beginTime, endTime);
            break;
        case 8:// 秒
            result = secondsBetween(beginTime, endTime);
            break;
        case 9:// 毫秒
            result = (int) millisecondsBetween(beginTime, endTime);
            break;
        default:
            log.warn("getTimeUsed 时 flag = " + timeUnitFlag + ",ignore handler 返回 result = 0");
        }
        return result;
    }
    
    /**
     * 计算时间增加之后的新的时间。
     * 
     * @param from
     * @param delta 时间增量，可以为负数
     * @param timeUnitFlag 时间周期单位：1-年、2-月、3-周、4-日(工作日)、5-天（自然天）、6-时、7-分、8-秒、9-毫秒
     * @param ignoreWorkday
     * @return
     */
    public static Date getDate(Date from, int delta, int timeUnitFlag, boolean ignoreWorkday) {
    	try {
			TimeUnit.valueOf(timeUnitFlag);
		} catch (Exception e) {
			log.warn("值单位无效，直接返回0值： " + e.getMessage());
			return from;
		}
    	 switch (timeUnitFlag) {
         case 1:// 年
        	 return yearsLater(from, delta);
         case 2:// 月
        	 return monthsAfter(from, delta);
         case 3:// 周
        	 throw new IllegalArgumentException("不支持周的加减");
         case 4:// 日
        	 return ignoreWorkday ? daysLater(from, delta) : workdaysLater(from, delta);
         case 5:// 天
        	 return daysLater(from, delta);
         case 6:// 时
             return hoursLater(from, delta);
         case 7:// 分
             return minutesLater(from, delta);
         case 8:// 秒
             return new Date(from.getTime() + 1000L * delta);
         case 9:// 毫秒
             return new Date(from.getTime() + delta);
         default:
             //log.warn("getTimeUsed 时 flag = " + timeUnitFlag + ",ignore handler 返回 result = 0");
        	 throw new IllegalArgumentException("不支持的加减");
         }
    }
   

    /**
     * @deprecated 作废！flag字典和公司规定不一致。
     * @param beginTime
     * @param timeLimit
     * @param flag
     * @return
     */
    public static Date __getEndTime(Date beginTime, short timeLimit, byte flag) {
        Date result = null;
        switch (flag) {
        case 1:// 年
            break;
        case 2:// 月
            break;
        case 3:// 周
            break;
        case 4:// 日
            break;
        case 5:// 时
            break;
        case 6:// 分
            break;
        case 7:// 秒
            break;
        case 8:// 毫秒
            break;
        case 9:// 天
            //result = getDays(beginTime, timeLimit, Calendar.DATE);
            break;
        default:
            log
                    .warn("getEndTime 时 flag = 0,ignore handler 返回 result = null ...");
        }
        return result;
    }


    /**
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int hoursBetween(Date beginTime, Date endTime) {
   	 	Calendar start = Calendar.getInstance();
        start.setTime(beginTime);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        
        return (int)((end.getTimeInMillis() - start.getTimeInMillis()) / (60 * 60 * 1000L));
    }

    /**
     * 获得两个时间的间隔分钟数（前面的Date减后面的Date） 如果后面的Date晚于前面的Date，则返回负值
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int minutesBetween(Date beginTime, Date endTime) {
//        return Integer.parseInt(Long.toString((endTime.getTime() - beginTime
//                .getTime())
//                / (1000 * 60)));
        
        Calendar start = Calendar.getInstance();
        start.setTime(beginTime);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        
        return (int)((end.getTimeInMillis() - start.getTimeInMillis()) / (60 * 1000L));
    }

    /**
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int secondsBetween(Date beginTime, Date endTime) {
    	Calendar start = Calendar.getInstance();
        start.setTime(beginTime);
        start.set(Calendar.MILLISECOND, 0);
        
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        end.set(Calendar.MILLISECOND, 0);
        
        return (int)((end.getTimeInMillis() - start.getTimeInMillis()) / (1000L));
    }

    public static long millisecondsBetween(Date beginTime, Date endTime) {
        return endTime.getTime() - beginTime.getTime();
    }

    /**
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int weeksBetween(Date beginTime, Date endTime) {
        //FIXME:单纯按天数除以7的算法不对
//        return daysBetween(beginTime, endTime) / 7;
        return daysBetween(toStartOfWeek(beginTime), toStartOfWeek(endTime)) / 7;
    }

    /**
     * 获得两个时间（time1，time2）的净间隔工作日数，注意，不包含time1和time2所在的天。
     * 
     * <p><b>注意:这个方法调用了业务服务类，此类方法最好不要再静态工具类中调用。</b></p>
     * @see #workdaysBetween(Date, Date, boolean)
     * 
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int workdaysBetween(Date beginTime, Date endTime){
    	return workdaysBetween(beginTime, endTime, false);
    }
    /**
     * 获得两个时间（time1，time2）的净间隔工作日数，注意，不包含time2所在的天，是否包含time1由参数
     * <code>skipFirstDay</code> 指定。
     * 
     * <p><b>注意:这个方法调用了业务服务类，此类方法最好不要再静态工具类中调用。</b></p>
     * 
     * @param beginTime
     * @param endTime
     * @param includeFirstDay 是否包含第一天，如果忽略第一天则从第二天开始计时
     * @return
     */
    public static int workdaysBetween(Date beginTime, Date endTime, boolean includeFirstDay) {
    	HolidayChecker service = getHolidayService();//Application.getContext().get("holidayService", HolidayService.class);
//        if(endTime.after(beginTime)){
//        	return daysBetween(beginTime, endTime) - service.getHolidays(beginTime, endTime);
//        }else{
//        	return -1 * (daysBetween(endTime, beginTime) - service.getHolidays(endTime, beginTime));
//        }
        if(isSameDay(beginTime, endTime)){
    		return 0;
    	}
        
		if(beginTime.after(endTime)){
			return 0;
		}
		
        Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		begin.setTime(beginTime);
		end.setTime(endTime);
		
		DateUtils.toStartOfDay(begin);
		DateUtils.toStartOfDay(end);
		
		Calendar index = begin;
		
		if(!includeFirstDay){
			//从第二天开始
			index.add(Calendar.DAY_OF_YEAR, 1);
		}
		int days = 0;
		while(index.before(end)){
			//System.out.println(current.getTime());
			if(!service.isHoliday(index.getTime())){
				days++;
			}
			index.add(Calendar.DAY_OF_YEAR, 1);
		}

		//如果当前是节假日，则-1，即一直不结束之前的工作日 2013-12-10
		if(service.isHoliday(end.getTime())){
			days = days - 1;
			if(days < 0){
				days = 0;
			}
		}

		//System.out.println(pDate1 + " -> " + pDate2 + " : " + days);
		log.debug(beginTime + " -> " + endTime + " : workdays " + days);
		return days;
    }
    
    

    public static int monthsBetween(Date beginTime, Date endTime) {
        Calendar start = Calendar.getInstance();
        start.setTime(beginTime);
        
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        
        return (end.getTimeInMillis() > start.getTimeInMillis()) ? 
        		monthsBetween(start, end) : -1 * monthsBetween(end, start);
    }
    
    private static int monthsBetween(Calendar start, Calendar end){
    	int startYear = start.get(Calendar.YEAR);
        int endYear = end.get(Calendar.YEAR);
        
        int startMonth = start.get(Calendar.MONTH);
        int endMonth = end.get(Calendar.MONTH);
        
        return 12 * (endYear - startYear) + endMonth - startMonth;
    }
    

    public static int yearsBetween(Date beginTime, Date endTime) {
        Calendar start = Calendar.getInstance();
        start.setTime(beginTime);
        
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        
        return end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
    }

    private static HolidayChecker holidayService = null;
    private static HolidayChecker getHolidayService() {
        if (holidayService == null) {
            holidayService = Application.getContext().get("holidayService", HolidayChecker.class);
        }
        return holidayService;
    }
    
    /**
     * 获取指定日期前，第 x 个工作日的日期
     * @param from
     * @param days
     * @return
     */
    public static Date workdaysBefore(Date from, int days){
    	return workdaysLater(from, -1 * days);
    }

    /**
     * 获取指定日期后，第 x 个工作日的日期
     * 
     * @param from
     * @param timeList
     * @return Date
     */
    public static Date workdaysLater(Date from, int days) {
    	if(days == 0){
    		return from;
    	}
    	
        int count = 0;
        int index = 0;
        int absdays = Math.abs(days);
        //是之前还是之后
        boolean isLater = days > 0;
        Date tmp = from;
        
        while (true) {
            index++;
            // 找到指定工作日后退出，或达到系统最大限度退出
//            if (count == timeLimit) {        
//                time = tmp;
//                log.info("找到指定的"+timeLimit+"个工作日, 正常退出");
//                break;
//            }
            if (index >= WORKDAYS_MAX_QUERY_COUNT/*20000*/) {
                log.warn("无法获取指定日期"+days+"个工作日后日期,达到系统容纳最大限度退出,返回原始日期");
                //break;
                throw new IllegalArgumentException("无法获取指定日期"+days+"个工作日后日期,达到系统容纳最大限度退出");
            }
            
            //不应该从今天开始判断，从昨天或者明天开始循环判断
            tmp = isLater ? daysLater(tmp, 1) : daysBefore(tmp, 1);
            if(!getHolidayService().isHoliday(tmp)){
            	count++;
            	if(count >= absdays){
            		log.info("找到指定的"+days+"个工作日, 正常退出");
                	break;
            	}
            }
        }
        return tmp;
    }
    
    public static Date daysLater(Date from, int days){
    	return DateUtils.daysLater(days, from.getTime());
    }
    
    public static Date daysBefore(Date from, int days){
    	return DateUtils.daysBefore(days, from.getTime());
    }
    
    public static Date minutesLater(Date from, int minutes){
    	return DateUtils.minutesLater(minutes, from);
    }
    
    public static Date minutesBefore(Date from, int mibutes){
    	return DateUtils.minutesBefore(mibutes, from);
    }
    
    public static Date hoursLater(Date from, int hours){
    	return DateUtils.hoursLater(hours, from);
    }
    
    public static Date hoursBefore(Date from, int hours){
    	return DateUtils.hoursBefore(hours, from);
    }
    
    public static Date monthsAfter(Date from, int months){
    	return DateUtils.monthsAfter(months, from.getTime());
    }
    public static Date monthsBefore(Date from, int months){
    	return DateUtils.monthsBefore(months, from.getTime());
    }
    
    public static Date yearsLater(Date from, int years){
    	Calendar c = Calendar.getInstance();
    	c.setTime(from);
    	c.add(Calendar.YEAR, years);
    	return c.getTime();
    }
    
    public static Date yearsBefore(Date from, int years){
    	return yearsLater(from, -1 * years);
    }
    

//    /**
//     * 获取指定日期，x 自然日后日期
//     * 
//     * @param time
//     * @param timeLimit
//     * @param filed
//     *            须添加字段，Calendar静态字段
//     * @return
//     */
//    public static Date getDays(Date time, int timeLimit, int filed) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(time);
//        calendar.add(filed, timeLimit);
//        return calendar.getTime();
//    }

    /**
     * 转换成该日期最小值 如: 2009-01-01 00:00:00
     * 
     * @param date
     * @return Date
     * @deprecated using {@link DateUtils#toStartOfDay(Date)}
     */
    public static Date getMinDate(Date date) {
        /*
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
        */
        return DateUtils.toStartOfDay(date);
    }

    /**
     * 转换成该日期最大值 如: 2009-01-01 23:59:59
     * 
     * @param date
     * @return Date
     * @deprecated using {@link DateUtils#toEndOfDay(Date)}
     */
    public static Date getMaxDate(Date date) {
        /*
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
        */
    	return DateUtils.toEndOfDay(date);
    }


//    /**
//     * 得到加、减后的日期
//     * 
//     * @param pDate
//     *            Date 时间
//     * @param pField
//     *            int 被操作的域（Calendar.YEAR / Calendar.MONTH / Calendar.DATE 等）
//     * @param pCount
//     *            int 加、减的数量
//     * @return Date
//     */
//    public static java.util.Date getDate(java.util.Date pDate, int pField,
//        int pCount) {
//      java.util.Calendar gc = new GregorianCalendar();
//      gc.setTime(pDate);
//      gc.add(pField, pCount);
//      gc
//          .set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
//              .get(Calendar.DATE));
//      return gc.getTime();
//    }

//    /**
//     * 得到加、减后的日期
//     * 
//     * @param pDate
//     *            Date 时间
//     * @param pField
//     *            int 被操作的域（Calendar.YEAR / Calendar.MONTH / Calendar.DATE 等）
//     * @param pCount
//     *            int 加、减的数量
//     * @return Date
//     * @throws Exception 
//     */
//    public static java.util.Date getDate(java.sql.Timestamp pTimestamp, int pField,
//        int pCount) throws Exception {
//      java.util.Date pDate=convertTimestamp2Date(pTimestamp);
//      return getDate(pDate,pField,pCount);
//    }

//    /**
//     * 获取当前时间的 String 类型
//     * 
//     * @return 当前时间的 String 类型
//     */
//    public static String getTimeStr(String sFormat) throws Exception {
//      SimpleDateFormat format = new SimpleDateFormat(sFormat);
//      try {
//        return format.format(new java.util.Date());
//      } catch (Exception e) {
//        throw e;
//      }
//    }

//    /**
//     * 产生任意位的字符串
//     * 
//     * @param time
//     *            要转换格式的时间
//     * @param format
//     *            转换的格式
//     * @return String 转换的时间
//     */
//    private static String getFormatedTimeStr(int time, int format) {
//      StringBuffer numm = new StringBuffer();
//      int length = String.valueOf(time).length();
//
//      if (format < length)
//        return null;
//
//      for (int i = 0; i < format - length; i++) {
//        numm.append("0");
//      }
//      numm.append(time);
//      return numm.toString().trim();
//    }

//    /**
//     * 取得本地系统的时间，时间格式由参数决定
//     * 
//     * @param format
//     *            时间格式由常量决定
//     * @return String 具有format格式的字符串
//     */
//    public static String getTimeStr(int format) {
//      StringBuffer cTime = new StringBuffer(10);
//      Calendar time = Calendar.getInstance();
//      int miltime = time.get(Calendar.MILLISECOND);
//      int second = time.get(Calendar.SECOND);
//      int minute = time.get(Calendar.MINUTE);
//      int hour = time.get(Calendar.HOUR_OF_DAY);
//      int day = time.get(Calendar.DAY_OF_MONTH);
//      int month = time.get(Calendar.MONTH) + 1;
//      int year = time.get(Calendar.YEAR);
//      if (format != 14) {
//        if (year >= 2000)
//          year = year - 2000;
//        else
//          year = year - 1900;
//      }
//      if (format >= 2) {
//        if (format == 14)
//          cTime.append(year);
//        else
//          cTime.append(getFormatedTimeStr(year, 2));
//      }
//      if (format >= 4)
//        cTime.append(getFormatedTimeStr(month, 2));
//      if (format >= 6)
//        cTime.append(getFormatedTimeStr(day, 2));
//      if (format >= 8)
//        cTime.append(getFormatedTimeStr(hour, 2));
//      if (format >= 10)
//        cTime.append(getFormatedTimeStr(minute, 2));
//      if (format >= 12)
//        cTime.append(getFormatedTimeStr(second, 2));
//      if (format >= 15)
//        cTime.append(getFormatedTimeStr(miltime, 3));
//      return cTime.toString();
//    }

//    /**
//     * 获取当前时间的 String 类型
//     * 
//     * @return 当前时间的 String 类型
//     */
//    public static String getCurrentTimeStr() throws Exception {
//      try {
//        return getTimeStr("yyyy-MM-dd HH:mm:ss");
//      } catch (Exception e) {
//        throw e;
//      }
//    }

//    /**
//     * 获取当前时间的 Timestamp 类型
//     * 
//     * @return 当前时间的 Timestamp 类型
//     */
//    public static Timestamp getCurrentTimestamp() {
//      return new Timestamp(new java.util.Date().getTime());
//    }

//    /**
//     * 获取当前时间的 Date 类型
//     * 
//     * @return 当前时间的 Timestamp 类型
//     */
//    public static Date getCurrentDate() {
//      return new java.util.Date();
//    }
    
//    /**
//     * 将 timestamp 类型转换为日期字符串
//     * 
//     * @param strDate
//     *            String 输入 timestamp
//     * @param sFormat
//     *            String 输入 格式
//     * @return Timestamp
//     * @throws Exception
//     */
//    public static Timestamp convertStrToTimestamp(String strDate,
//        String sFormat) throws Exception {
//
//      if (strDate == null || strDate.equals("")) {
//        return null;
//      }
//      Timestamp t = null;
//      SimpleDateFormat format = new SimpleDateFormat(sFormat);
//
//      try {
//        t = new java.sql.Timestamp(format.parse(strDate).getTime());
//      } catch (Exception e) {
//        throw e;
//      }
//      return t;
//    }

//    /**
//     * 将 timestamp 类型转换为日期字符串
//     * 
//     * @param pTimestamp
//     *            Timestamp
//     * @param pFormat
//     *            String
//     * @return String
//     * @throws Exception
//     */
//    public static String convertTimestampToStr(Timestamp pTimestamp,
//        String pFormat) throws Exception {
//      if (pTimestamp == null) {
//        return "";
//      }
//
//      SimpleDateFormat format = new SimpleDateFormat(pFormat);
//      try {
//        return format.format(pTimestamp);
//      } catch (Exception e) {
//        throw e;
//      }
//    }

//    /**
//     * 将 timestamp 类型转换为日期字符串
//     * 
//     * @param pTimestamp
//     *            输入 timestamp
//     * @return 日期字符串
//     * @throws java.lang.Exception
//     */
//    public static String convertTimestampToStr(Timestamp pTimestamp)
//        throws Exception {
//      if (pTimestamp == null) {
//        return "";
//      }
//
//      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//      try {
//        return format.format(pTimestamp);
//      } catch (Exception e) {
//        throw e;
//      }
//    }

//    /**
//     * 转换日期字符串至 Timestamp 类型
//     * 
//     * @param strDate
//     *            日期字符串
//     * @return Timestamp
//     * @throws java.lang.Exception
//     */
//    public static Timestamp convertStrToTimestamp(String strDate)
//        throws Exception {
//
//      if (strDate == null || strDate.equals("")) {
//        return null;
//      }
//      Timestamp t = null;
//      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//      try {
//        t = new java.sql.Timestamp(format.parse(strDate).getTime());
//      } catch (Exception e) {
//        throw e;
//      }
//      return t;
//    }

//    /**
//     * 时间格式的转换
//     * 
//     * @param pDate
//     *            日期
//     * @param psFormat
//     *            格式
//     * @return 转换后的时间字符串
//     */
//    public static String convertDateToStr(java.util.Date pDate, String psFormat) {
//        if (pDate == null)
//            return "";
//          SimpleDateFormat oFormat = new java.text.SimpleDateFormat(psFormat);
//          return oFormat.format(pDate);
//    }

//    /**
//     * 字符串转换为时间
//     * 
//     * @param psDateStr
//     *            日期
//     * @param psFormat
//     *            格式
//     * @return 转换后的时间字符串
//     */
//    public static java.util.Date convertStrToDateTime(String psDateStr, String psFormat) {
//      SimpleDateFormat oFormat = new SimpleDateFormat(psFormat);
//      ParsePosition pos = new ParsePosition(0);
//      java.util.Date d = oFormat.parse(psDateStr, pos);
//      return d;
//    }
//
//    /**
//     * 字符串转换为SQL时间
//     * 
//     * @param psDateStr
//     *            日期
//     * @param psFormat
//     *            格式
//     * @return 转换后的时间字符串
//     */
//    public static java.sql.Date convertStrToSQLDateTime(String psDateStr, String psFormat) {
//      return new java.sql.Date(convertStrToDateTime(psDateStr, psFormat).getTime());
//    }

//    /**
//     * 将timestamp转换为date（'yyyyMMdd'格式）
//     * 
//     * @param timestamp
//     * @return
//     * @throws Exception
//     */
//    public static java.util.Date convertTimestamp2Date(java.sql.Timestamp timestamp)
//        throws Exception {
//      String tmp = convertTimestampToStr(timestamp, "yyyyMMdd");
//      java.util.Date ret = convertStrToSQLDateTime(tmp, "yyyyMMdd");
//      return ret;
//    }

//    /**
//     * 将date转换为timestamp（'yyyyMMdd'格式）
//     * 
//     * @param timestamp
//     * @return
//     * @throws Exception
//     */
//    public static java.sql.Timestamp convertDate2Timestamp(java.util.Date date)
//        throws Exception {
//      if (date==null)
//        return null;
//      
//      String tmp = convertDateToStr(date, "yyyyMMdd");
//      java.sql.Timestamp ret = convertStrToTimestamp(tmp, "yyyyMMdd");
//      return ret;
//    }

//    /**
//     * 将timestamp中的时、分、秒部分剔除，并返回该timestamp值
//     * 
//     * @param timestamp
//     * @return
//     * @throws Exception
//     */
//    public static java.sql.Timestamp truncTimestampToDay(
//        java.sql.Timestamp timestamp) throws Exception {
//      String tmp = convertTimestampToStr(timestamp, "yyyyMMdd");
//      java.sql.Timestamp ret = convertStrToTimestamp(tmp, "yyyyMMdd");
//      return ret;
//    }

    /**
     * 取当前年份
     * 
     * @return
     */
    public static String getCurrentYearStr() {
      Calendar calendar = Calendar.getInstance();
      java.util.Date date = new java.util.Date();
      calendar.setTime(date);
      return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * 取当前月份
     * 
     * @return
     */
    public static String getCurrentMonthStr() {
      Calendar calendar = Calendar.getInstance();
      java.util.Date date = new java.util.Date();
      calendar.setTime(date);
      int i = calendar.get(Calendar.MONTH) + 1;
      String result = "";
      if (i < 10) {
        result = "0" + String.valueOf(i);
      } else {
        result = String.valueOf(i);
      }
      return result;
    }

    /**
     * 取当前天数
     * 
     * @return
     */
    public static String getCurrentDayStr() {
      Calendar calendar = Calendar.getInstance();
      java.util.Date date = new java.util.Date();
      calendar.setTime(date);
      int i = calendar.get(Calendar.DAY_OF_MONTH);
      String result = "";
      if (i < 10) {
        result = "0" + String.valueOf(i);
      } else {
        result = String.valueOf(i);
      }
      return result;
    }

//    /**
//     * 判断timestamp是否是今天的
//     * 
//     * @param timestamp
//     * @return
//     * @throws Exception
//     */
//    public static boolean isToday(Timestamp timestamp) throws Exception {
//      String day1 = convertTimestampToStr(timestamp, "yyyyMMdd");
//      Timestamp currTimestamp = new Timestamp(System.currentTimeMillis());
//      String day2 = convertTimestampToStr(currTimestamp, "yyyyMMdd");
//      return day2.equalsIgnoreCase(day1);
//    }

    /**
     * 判断是否润年
     * 
     * @param piYear
     * @return
     */
    public static boolean isLeapYear(int piYear) {
      boolean b = ((piYear % 4) == 0) && ((piYear % 100) != 0) || ((piYear % 400) == 0);
      return (b);
    }
    
    /**
     * 获得两个时间（time1，time2）的净间隔天数，注意，不包含time1和time2所在的天。
     * 第二个版本。
     * 
     * @param pDate1
     * @param pDate2
     * @return
     */
    public static int daysBetween2(java.util.Date pDate1, java.util.Date pDate2){
		if(pDate1.after(pDate2)){
			return 0;
		}
		
    	Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		begin.setTime(pDate1);
		end.setTime(pDate2);
		DateUtils.toStartOfDay(begin);
		DateUtils.toStartOfDay(end);
		
		Calendar index = begin;// Calendar.getInstance();

		//从第二天开始
		index.add(Calendar.DAY_OF_YEAR, 1);
		int days = 0;
		while(index.before(end)){
			//System.out.println(current.getTime());
			days++;
			index.add(Calendar.DAY_OF_YEAR, 1);
		}
		//System.out.println(pDate1 + " -> " + pDate2 + " : " + days);
		log.debug(pDate1 + " -> " + pDate2 + " : " + days);
		return days;
    }

    /**
     * 获得两个时间（time1，time2）的净间隔天数，注意，不包含time1和time2所在的天。
     * 
     * @see #daysBetween(Date, Date, boolean)
     * @param pDate1
     * @param pDate2
     * @return
     */
    public static int daysBetween(java.util.Date pDate1, java.util.Date pDate2){
    	return daysBetween(pDate1, pDate2, false);
    }
    /**
     * 获得两个时间（time1，time2）的净间隔天数，注意，不包含time2所在的天,是否包含time1由参数
     * <code>includeFirstDay</code> 指定。
     * @param pDate1
     * @param pDate2
     * @param includeFirstDay 是否包含第一天
     * @return
     */
    public static int daysBetween(java.util.Date pDate1, java.util.Date pDate2, boolean includeFirstDay){
    	if(isSameDay(pDate1, pDate2)){
    		return 0;
    	}
    	
    	int days = daysDiff(pDate1, pDate2);
    	if(days > 0 && !includeFirstDay){
			days = days - 1;
		}
    	return days;
    }
    
    /**
     * 判断2个日期是否是同一天，时分秒可以不同。
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2){
    	if(date1 == null && date2 != null){
    		return false;
    	}
    	
    	if(date1 != null && date2 == null){
    		return false;
    	}
    	
    	if(EqualsUtils.equals(date1, date2)){
    		return true;
    	}
    	
    	Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
				&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
				&& (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR));
    }
    
    /**
     * 判断2个日期是否是同一月。
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameMonth(Date date1, Date date2){
    	if(date1 == null && date2 != null){
    		return false;
    	}
    	
    	if(date1 != null && date2 == null){
    		return false;
    	}
    	
    	if(EqualsUtils.equals(date1, date2)){
    		return true;
    	}
    	
    	Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
				&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
				//&& (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR));
    }
    
    /**
     * 判断2个日期是否是同一年。
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameYear(Date date1, Date date2){
    	if(date1 == null && date2 != null){
    		return false;
    	}
    	
    	if(date1 != null && date2 == null){
    		return false;
    	}
    	
    	if(EqualsUtils.equals(date1, date2)){
    		return true;
    	}
    	Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR));
    }
    
    
    /**
     * 获得两个时间（time1，time2）的间隔天数，用time1和time2所在的天的零点相减。
     * 
     * @param pDate1
     * @param pDate2
     * @return
     */
	public static int daysDiff(java.util.Date pDate1, java.util.Date pDate2) {
		// int sDays = 0;
		// SimpleDateFormat format = new SimpleDateFormat("D");
		// SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy"); // 当前年
		// int year1 = Integer.valueOf(yearFormat.format(pDate1)).intValue();
		// int year2 = Integer.valueOf(yearFormat.format(pDate2)).intValue();
		// int day1 = Integer.valueOf(format.format(pDate1)).intValue();
		// int day2 = Integer.valueOf(format.format(pDate2)).intValue();
		// int Invcount = 0;
		// // 判断是否跨越年限
		// if ((year2 - year1) > 0) {
		// int LeapYearCount = getLeapYearCount(year1, year2);
		// Invcount = (year2 - year1) * 365 + LeapYearCount;
		// }
		// sDays = day2 - day1 + Invcount;
		// return sDays;
		
		
		if(pDate1.after(pDate2)){
			return 0;
		}

		Calendar start = Calendar.getInstance();
		start.setTime(pDate1);
		DateUtils.toStartOfDay(start);

		Calendar end = Calendar.getInstance();
		end.setTime(pDate2);
		DateUtils.toStartOfDay(end);

		//24 * 60 * 60 * 1000L
		int days = (int) ((end.getTimeInMillis() - start.getTimeInMillis()) / MILLIS_OF_ONE_DAY);
		return days;
	}

    /**
     * 获得两个时间的间隔分钟数（前面的Date减后面的Date） 如果后面的Date晚于前面的Date，则返回负值
     * 
     * @param pDate1
     * @param pDate2
     * @return
     */
//    public static long getMinuteInterval(java.util.Date pDate1, java.util.Date pDate2) {
////      long ret = (pDate1.getTime() - pDate2.getTime()) / 1000 / 60;
////      return ret;
//      
//      return minutesBetween(pDate1, pDate2);
//    }

//    /**
//     * 计算指定Date是周几。返回值含义：SUNDAY = 1;MONDAY = 2;TUESDAY = 3;WEDNESDAY = 4;THURSDAY =
//     * 5;FRIDAY = 6;SATURDAY = 7;
//     * 
//     * @param date
//     * @return 范围为1~7。
//     */
//    public static int getDayOfWeek(java.util.Date date) {
//      GregorianCalendar cal = new GregorianCalendar();
//      cal.setTime(date);
//      int dayOfWeek = cal.get(GregorianCalendar.DAY_OF_WEEK);
//      return dayOfWeek;
//    }

    /**
     * 获取指定时间所在周的第一天（注意:美国人的习惯，把周日做为第一天）的Date。
     * 
     * @param date
     * @return
     */
    public static java.util.Date toSundayOfWeek(java.util.Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
      cal.setTime(date);
      cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
      java.util.Date ret = new Date(cal.getTimeInMillis());
      return ret;
    }

    /**
     * 获取两个年限直接有多少个闰年
     * 
     * @param year1
     * @param year2
     * @return
     */
    public static int getLeapYearCount(int year1, int year2) {
      int rncount = 0;
      for (int i = year1; i < year2; i++) {
        if (isLeapYear(i)) {
          rncount++;
        }
      }
      return rncount;
    }

//    /**
//     * 返回时间段自然日数目的函数，不计入第一天
//     * 
//     * @param fromDate
//     *            开始日期
//     * @param toDate
//     *            截至日期
//     * @return
//     * @throws Exception
//     */
//    public static int getDayNum(Timestamp fromDate, Timestamp toDate)
//        throws Exception {
////      java.sql.Date fromD = CDateTime.convertTimestamp2Date(fromDate);
////      java.sql.Date toD = CDateTime.convertTimestamp2Date(toDate);
//      int result = daysBetween(fromDate, toDate);
//
//      return result;
//    }
//    /**
//     
//    */
//    public static Date getDateWithSetTime(Date time,int hour,int minute,int second){
//  	  Calendar c=Calendar.getInstance();
//  	  c.setTime(time);
//  	  c.set(Calendar.HOUR_OF_DAY, hour);
//  	  c.set(Calendar.MINUTE,minute);
//  	  c.set(Calendar.SECOND,second);
//  	  return c.getTime();
//    }
    
	public static Date setAndGetTime(Date time, int hourOfDay, int minute, int second) {
		Calendar c = Calendar.getInstance();
		setTime(c, hourOfDay, minute, second);
		return c.getTime();
	}
    
	public static void setTime(Calendar c, int hourOfDay, int minute, int second) {
		c.set(Calendar.HOUR_OF_DAY, hourOfDay);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
	}
    
    public static String formatDate(Date d){
    	if(d == null){
    		return null;
    	}
    	return AppsGlobals.formatDate(d);
    }
    
    public static String formatDateTime(Date d){
    	if(d == null){
    		return null;
    	}
    	return AppsGlobals.formatDateTime(d);
    }
    
    public static String formatShortDateTime(Date d){
    	if(d == null){
    		return null;
    	}
    	return AppsGlobals.formatShortDateTime(d);
    }
    
    /**
     * 获取指定日期所在周一零点的时间。
     * @param d
     * @return
     */
    public static Date toStartOfWeek(Date d){
    	Calendar instance = Calendar.getInstance();
    	instance.setTime(d);
    	toStartOfWeek(instance);
    	return instance.getTime();
    }
    
    /**
     * 获取指定日期所在周一零点的时间。
     * @param c
     */
    public static void toStartOfWeek(Calendar c){
    	int i = c.get(Calendar.DAY_OF_WEEK) - 1;
    	//System.out.println(i + "ss" + c.get(Calendar.DATE));
    	if(i == 0){
    		c.add(Calendar.DATE, -6);
    	}else{
    		c.add(Calendar.DATE, -i + 1);
    	}
    	//System.out.println(c.getTime());
//    	System.out.println(i + "ss" + c.get(Calendar.DATE));
    	//c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	DateUtils.toStartOfDay(c);
    }
}
