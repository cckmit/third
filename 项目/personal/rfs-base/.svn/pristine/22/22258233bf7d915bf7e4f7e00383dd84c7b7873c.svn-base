package cn.redflagsoft.base.service;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Holiday;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.dao.hibernate3.HolidayHibernateDao;
import cn.redflagsoft.base.test.BaseTests;
import cn.redflagsoft.base.util.DateUtil;
import cn.redflagsoft.base.util.EqualsUtils;


public class RiskServiceTest extends  BaseTests {
	protected RiskService riskService;
	protected RiskCalculator riskCalculator;
	protected HolidayHibernateDao holidayDao;
	
	
	public void etestCheckGrade() throws Exception{	
		Risk r=riskService.getRiskById(1254249764594L);
		riskService.checkRiskGrade(r);
		super.setComplete();
	}
	
	public void etestGradeChangeRemind() throws Exception{	
		riskService.gradeChangeRemind(0);
		super.setComplete();
	}
	
	public void etestWatch() throws Exception{	
		super.setComplete();
		riskService.watch();
	}
	
	public void etestRiskCalculator(){
		System.out.println(riskCalculator);
		Logic logic = Restrictions.logic(Restrictions.eq("status", Risk.STATUS_ON_USE))
				.and(Restrictions.eq("pause", Risk.PAUSE_NO))
				.and(Restrictions.eq("valueSource", RiskRule.VALUE_SOURCE_TIMER_ADD));
			
		//每次处理10条
		ResultFilter filter = new ResultFilter(logic, null, 0, 100);
		List<Risk> risks = riskService.findRisks(filter);
		for(Risk risk: risks){
			BigDecimal oldValue = risk.getValue();
			BigDecimal value = riskCalculator.calculateValue(risk, new Date());
			System.out.println("计算后value：" + value + ", 原值: " + oldValue);
			if(!EqualsUtils.equals(value, oldValue)){
				System.err.println("mmmmmmmmmmmmmmmmmmmmmm");
			}
		}
	}
	
	public void etestHolidays(){
		int days = holidayDao.getHoliDays(new Date(), new Date());
		System.out.println(days);
		
		Calendar c = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c.set(2011, 11, 9);
		
		days = holidayDao.getHoliDays(new Date(), c.getTime());
		System.out.println(days);
		
		c.set(2011, 11, 10);
		days = holidayDao.getHoliDays(new Date(), c.getTime());
		System.out.println(days);
		
		c.set(2011, 11, 10);
		c2.set(2011, 11, 11);
		days = holidayDao.getHoliDays(c.getTime(), c2.getTime());
		System.out.println(days);
		
		c.set(2011, 11, 12);
		days = holidayDao.getHoliDays(new Date(), c.getTime());
		System.out.println(days);
	}
	
	public void testGetWorkdays(){
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		begin.set(2011, Calendar.DECEMBER, 29);
		end.set(2011, Calendar.DECEMBER, 29);
		System.out.println(DateUtil.workdaysBetween(begin.getTime(), end.getTime()));
		
		end.set(2011, Calendar.DECEMBER, 30);
		System.out.println(DateUtil.workdaysBetween(begin.getTime(), end.getTime()));
		
		end.set(2011, Calendar.DECEMBER, 31);
		System.out.println(DateUtil.workdaysBetween(begin.getTime(), end.getTime()));
		
		end.set(2012, Calendar.JANUARY, 1);
		System.out.println(DateUtil.workdaysBetween(begin.getTime(), end.getTime()));
		
		end.set(2012, Calendar.JANUARY, 2);
		System.out.println(DateUtil.workdaysBetween(begin.getTime(), end.getTime()));
		
		end.set(2012, Calendar.JANUARY, 3);
		System.out.println(DateUtil.workdaysBetween(begin.getTime(), end.getTime()));
		
		
		
		//Calendar begin = Calendar.getInstance();
		//Calendar end = Calendar.getInstance();
		int year = 2011;
		begin.set(year, Calendar.JANUARY, 1);
		end.set(year + 1, Calendar.JANUARY, 1);

		List<Holiday> list = holidayDao.findHolidays(begin.getTime(), end.getTime());
		for (Holiday holiday : list) {
			Date date = holiday.getHolDate();
			//log.debug("添加节假日：" + date);
			//days.addHoliday(date);
			System.out.println(date);
		}
	}
}
