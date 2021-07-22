package cn.redflagsoft.base.dao.hibernate3;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.opoo.apps.util.DateUtils;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.redflagsoft.base.bean.Holiday;
import cn.redflagsoft.base.dao.HolidayDao;

public class HolidayHibernateDao extends AbstractBaseHibernateDao<Holiday,Long> implements HolidayDao{
	private boolean useHSQL = false;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<Date> findHolidaysDateList(Date startDate, Date finishDate){
		return findByField("a.holDate", startDate, finishDate);
	}
	
	public List<Holiday> findHolidays(Date startDate, Date finishDate){
		return findByField("a", startDate, finishDate);
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> findByField(String field, Date startDate, Date finishDate){
		//HSQLDB 的日期比较条件不太相同
		if(useHSQL){
			String start = format.format(startDate);
			String end = format.format(finishDate);
			String qs = "select " + field + " from Holiday a where a.status=? and a.holDate>='" +
					start + "' and a.holDate<'" +
					end + "'";
			return getHibernateTemplate().find(qs, Byte.valueOf("1"));
		}
			
		//用 Query#setDate 也会自动截断时分秒
		final Date start = DateUtils.toStartOfDay(startDate);
		final Date end = DateUtils.toStartOfDay(finishDate);//toEndOfDay
		final String hql = "select " + field + " from Holiday a where a.status=? and a.holDate>=? and a.holDate<? ";

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
				return session.createQuery(hql)
					.setByte(0, (byte)1)
					.setDate(1, start)
					.setDate(2, end)
					.list();
			}
		});
	}
	
	public int getHoliDays(Date startDate, Date finishDate) {
		//HSQLDB 的日期比较条件不太相同
		if(useHSQL){
			String start = format.format(startDate);
			String end = format.format(finishDate);
			String qs = "select count(*) from Holiday a where a.status=? and a.holDate>='" +
					start + "' and a.holDate<'" +
					end + "'";
			return Integer.parseInt(getHibernateTemplate().find(qs, Byte.valueOf("1")).get(0).toString());
		}
		
		//用 Query#setDate 也会自动截断时分秒
		final Date start = DateUtils.toStartOfDay(startDate);
		final Date end = DateUtils.toStartOfDay(finishDate);//toEndOfDay
		final String hql = "select count(*) from Holiday a where a.status=? and a.holDate>=? and a.holDate<? ";
		//List<?> list = getHibernateTemplate().find(hql, new Object[]{(byte)1, startDate, finishDate});
		//return ((Number)list.iterator().next()).intValue();

		return ((Number)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
				return session.createQuery(hql)
					.setByte(0, (byte)1)
					.setDate(1, start)
					.setDate(2, end)
					.uniqueResult();
			}
		})).intValue();
		
		
//		return Integer.parseInt(getHibernateTemplate().find(hql, new Object[]{Byte.valueOf("1"), startDate, finishDate}).get(0).toString());

//		Logic logic = Restrictions.logic(Restrictions.eq("status", (byte)1))
//			.and(Restrictions.ge("holDate", startDate))
//			.and(Restrictions.le("holDate", finishDate));
//		return getQuerySupport().getInt("select count(*) from Holiday", logic);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.hibernate3.AbstractBaseHibernateDao#initDao()
	 */
	@Override
	protected void initDao() throws Exception {
		super.initDao();
//		String dbDriverClassName = Globals.getSetupProperty("datasource.driverClassName");
//		if(dbDriverClassName != null && dbDriverClassName.indexOf("hsql") != -1){
//			//useHSQL = true;
//		}
	}

	@SuppressWarnings("unchecked")
	public List<Date> findRepeatedDays() {
		String hql = "select a.holDate from Holiday a group by a.holDate having count(*)>1";
		return getHibernateTemplate().find(hql);
	}
}
