package cn.redflagsoft.base.dao;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Holiday;

public interface HolidayDao extends Dao<Holiday, Long> {
	
	/**
	 * ��ѯ��ʼʱ�����ʱ��֮��Ľڼ����б�������ʼʱ������ڣ�������������ʱ������ڡ�
	 * 
	 * @param startDate
	 * @param finishDate
	 * @return
	 */
	List<Date> findHolidaysDateList(Date startDate, Date finishDate);
	
	/**
	 * ��ѯ��ʼʱ�����ʱ��֮��Ľڼ����б�������ʼʱ������ڣ�������������ʱ������ڡ�
	 * 
	 * @param startDate
	 * @param finishDate
	 * @return
	 */
	List<Holiday> findHolidays(Date startDate, Date finishDate);
	
	/**
	 * ��ѯ��ʼʱ�����ʱ��֮��Ľڼ���������������ʼʱ������ڣ�������������ʱ������ڡ�
	 * @param startDate
	 * @param finishiDate
	 * @return
	 */
	int getHoliDays(Date startDate, Date finishiDate);
	
	/**
	 * ��ѯ�ڼ������ظ���������ڼ��ϡ�
	 * 
	 * @return
	 */
	List<Date> findRepeatedDays();
}
