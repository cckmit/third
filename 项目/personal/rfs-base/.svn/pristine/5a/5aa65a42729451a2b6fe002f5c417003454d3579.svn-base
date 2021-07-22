package cn.redflagsoft.base.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.cache.Cache;
import org.opoo.cache.NullCache;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.BizStatistics;
import cn.redflagsoft.base.bean.BizStatisticsDefinition;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.dao.BizStatisticsDao;
import cn.redflagsoft.base.dao.BizStatisticsDefinitionDao;
import cn.redflagsoft.base.service.BizStatisticsService;

public class BizStatisticsServiceImpl implements BizStatisticsService {
	private static final Log log = LogFactory.getLog(BizStatisticsServiceImpl.class);
	
	private Cache<String,BizStatisticsDefinition> bizStatisticsDefinitionCache = new NullCache<String, BizStatisticsDefinition>();
	private BizStatisticsDao bizStatisticsDao;
	private BizStatisticsDefinitionDao bizStatisticsDefinitionDao;
	
	public BizStatisticsDao getBizStatisticsDao() {
		return bizStatisticsDao;
	}

	public void setBizStatisticsDao(BizStatisticsDao bizStatisticsDao) {
		this.bizStatisticsDao = bizStatisticsDao;
	}
	
	public BizStatisticsDefinitionDao getBizStatisticsDefinitionDao() {
		return bizStatisticsDefinitionDao;
	}

	public void setBizStatisticsDefinitionDao(
			BizStatisticsDefinitionDao bizStatisticsDefinitionDao) {
		this.bizStatisticsDefinitionDao = bizStatisticsDefinitionDao;
	}
	
	/**
	 * @param definitionCache the definitionCache to set
	 */
	public void setBizStatisticsDefinitionCache(
			Cache<String, BizStatisticsDefinition> definitionCache) {
		this.bizStatisticsDefinitionCache = definitionCache;
	}

	public BizStatistics get(Long id) {
		return bizStatisticsDao.get(id);
	}

	public BizStatistics save(BizStatistics bizStatistics) {
		return bizStatisticsDao.save(bizStatistics);
	}

	public BizStatistics update(BizStatistics bizStatistics) {
		return bizStatisticsDao.update(bizStatistics);
	}
	
	public List<BizStatistics> find(ResultFilter resultFilter) {
		return bizStatisticsDao.find(resultFilter);
	}
	
	public List<BizStatistics> find() {
		return bizStatisticsDao.find();
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizStatisticsService#getDefinition(java.lang.String)
	 */
	public BizStatisticsDefinition getDefinition(String dataId) {
		BizStatisticsDefinition definition = bizStatisticsDefinitionCache.get(dataId);
//		注入DAO后去掉注释
		if(definition == null){
			definition = bizStatisticsDefinitionDao.get(dataId);
			if(definition != null){
				bizStatisticsDefinitionCache.put(dataId, definition);
			}
		}
		return definition;

	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizStatisticsService#saveDefinition(cn.redflagsoft.base.bean.BizStatisticsDefinition)
	 */
	public BizStatisticsDefinition saveDefinition(BizStatisticsDefinition def) {
		BizStatisticsDefinition definition = bizStatisticsDefinitionDao.save(def);
		bizStatisticsDefinitionCache.put(definition.getDataId(), definition);
		return definition;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizStatisticsService#updateDefinition(cn.redflagsoft.base.bean.BizStatisticsDefinition)
	 */
	public BizStatisticsDefinition updateDefinition(BizStatisticsDefinition def) {
		BizStatisticsDefinition definition = bizStatisticsDefinitionDao.update(def);
		bizStatisticsDefinitionCache.put(definition.getDataId(), definition);
		return definition;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizStatisticsService#removeDefinition(java.lang.String)
	 */
	public void removeDefinition(String dataId) {
		bizStatisticsDefinitionDao.remove(dataId);
		bizStatisticsDefinitionCache.remove(dataId);
	}

	/* 
	 * DayOfYear
	 * WeekOfYear
	 *              dataId | value | year | month | w/y  | d/y  | obj_id | obj_type
	 * day+all      d        v       x      0       0      x     0        0
	 * week+all     d        v       x      0       x      0     0        0
	 * month+all    d        v       x      x       0      0     0        0
	 * year+all     d        v       x      0       0      0     0        0
	 * all+all      d        v       0      0       0      0     0        0
	 * day+single   d        v       x      0       0      x     i        t
	 * week+single  d        v       x      0       x      0     i        t
	 * month+single d        v       x      x       0      0     i        t
	 * year+single  d        v       x      0       0      0     i        t
	 * all+single   d        v       0      0       0      0     i        t
	 * 
	 * (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizStatisticsService#addStatistics(cn.redflagsoft.base.bean.BizStatisticsDefinition, java.math.BigDecimal, java.util.Date, cn.redflagsoft.base.bean.RFSEntityObject, cn.redflagsoft.base.bean.Task)
	 */
	public synchronized int addStatistics(BizStatisticsDefinition def, BigDecimal value,	Date date, RFSEntityObject obj, Task task) {
		Assert.notNull(def, "业务统计定义不能为空");
		Assert.notNull(value, "统计值不能为空");
		
		if(!def.isSingleObjectEnabled() && !def.isAllObjectEnabled()){
			log.warn("根据业务统计定义，无需保存统计值，定义可能有错误：" + def.getDataId());
			return 0;
		}
		if(date == null){
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		short year = (short) calendar.get(Calendar.YEAR);
		byte month = (byte) (calendar.get(Calendar.MONTH) + 1);
		byte week = (byte) calendar.get(Calendar.WEEK_OF_YEAR);
		short day = (short) calendar.get(Calendar.DAY_OF_YEAR);
		int n = 0;
		
		//针对每个数据项，必须设置synchronized
		//String lock = buildLockObject(def, obj);
		//synchronized(lock/*def.getDataId()*/){//直接把方法同步
			
		// 一次查询，多次使用
		/*
		List<BizStatistics> oldList = findBizStatistics(def, obj);
		if(oldList == null){
			oldList = new ArrayList<BizStatistics>();
		}
		*/
		//artf1425 由于出现数据重复，所以不优化每次都查
		List<BizStatistics> oldList = null;
		if(def.isAllObjectEnabled()){
			if(def.isDayEnabled()){
				saveOrUpdateStatistics(def, null, year, (byte)0, (byte)0, day, value, task, oldList);
				n++;
			}
			if(def.isWeekEnabled()){
				saveOrUpdateStatistics(def, null, year, (byte)0, week, (short)0, value, task, oldList);
				n++;
			}
			if(def.isMonthEnabled()){
				saveOrUpdateStatistics(def, null, year, month, (byte)0, (short)0, value, task, oldList);
				n++;
			}
			if(def.isYearEnabled()){
				saveOrUpdateStatistics(def, null, year, (byte)0, (byte)0, (short)0, value, task, oldList);
				n++;
			}
			if(def.isAllTimeEnabled()){
				//记录总数
				saveOrUpdateStatistics(def, null, (short)0, (byte)0, (byte)0, (short)0, value, task, oldList);
				n++;
			}
		}
		if(def.isSingleObjectEnabled()){
			if(obj == null){
				log.warn("按统计定义，单个对象相关，但未指定具体对象，忽略这部分数据的处理：" + def.getDataId());
			}else{
				if(def.isDayEnabled()){
					saveOrUpdateStatistics(def, obj, year, (byte)0, (byte)0, day, value, task, oldList);
					n++;
				}
				if(def.isWeekEnabled()){
					saveOrUpdateStatistics(def, obj, year, (byte)0, week, (short)0, value, task, oldList);
					n++;
				}
				if(def.isMonthEnabled()){
					saveOrUpdateStatistics(def, obj, year, month, (byte)0, (short)0, value, task, oldList);
					n++;
				}
				if(def.isYearEnabled()){
					saveOrUpdateStatistics(def, obj, year, (byte)0, (byte)0, (short)0, value, task, oldList);
					n++;
				}
				if(def.isAllTimeEnabled()){
					//记录总数
					saveOrUpdateStatistics(def, obj, (short)0, (byte)0, (byte)0, (short)0, value, task, oldList);
					n++;
				}
			}
		}
		//}
		return n;
	}
	/*
	private String buildLockObject(BizStatisticsDefinition def, RFSEntityObject obj){
		if(def.isAllObjectEnabled()){
			return def.getDataId();
		}
		long objectId = 0;
		int objectType = 0;
		if(obj != null){
			objectId = obj.getId();
			objectType = obj.getObjectType();
		}
		return def.getDataId() + "_" + objectId + "_" + objectType;
	}
	*/
	
	/**
	 * 查询指定业务统计已有的数据记录。
	 * @param def
	 * @param obj 
	 * @return
	 */
	public List<BizStatistics> findBizStatistics(BizStatisticsDefinition def, RFSEntityObject obj){
		Criterion c = Restrictions.eq("definition", def);
		Logic base = Restrictions.logic(c);
		Logic allObject = null;
		Logic singleObject = null;
		
		if(def.isAllObjectEnabled()){
			allObject = Restrictions.logic(Restrictions.eq("refObjectId", 0L))
					.and(Restrictions.eq("refObjectType", 0));
		}
		if(def.isSingleObjectEnabled() && obj != null){
			singleObject = Restrictions.logic(Restrictions.eq("refObjectId", obj.getId()))
					.and(Restrictions.eq("refObjectType", obj.getObjectType()));
		}
		
		if(allObject != null && singleObject != null){
			Logic or = Restrictions.logic(allObject).or(singleObject);
			base = base.and(or);
		}else if(allObject != null && singleObject == null){
			base = base.and(allObject);
		}else if(allObject == null && singleObject != null){
			base = base.and(singleObject);
		}else{
			//不应该2个条件都为空，至少应该有一个
			base = base.and(Restrictions.sql("1=2"));
		}
		ResultFilter filter = new ResultFilter(base, null);
		return bizStatisticsDao.find(filter);
	}
	
	
	/**
	 * 单条处理，可提前查询进行缓存，也可即时查询。
	 * 
	 * @param def 相关定义
	 * @param obj 相关对象
	 * @param year 年
	 * @param month 月
	 * @param weekOfYear 周
	 * @param dayOfYear 天
	 * @param value 统计值
	 * @param task 相关的业务
	 * @param oldList 可为null或者空集合。 指定dataId对应的已有记录的集合，如果没有则应该设置空集合，如果传递null，则会
	 * 	再次查询数据库，可能导致该方法效率低下。
	 * @return 处理后的统计对象
	 */
	private BizStatistics saveOrUpdateStatistics(BizStatisticsDefinition def, RFSEntityObject obj, 
			short year, byte month, byte weekOfYear, short dayOfYear, BigDecimal value, Task task,
			List<BizStatistics> oldList){
		long objectId = 0;
		int objectType = 0;
		if(obj != null){
			objectId = obj.getId();
			objectType = obj.getObjectType();
		}
		//Flag
		boolean isNew = false;
		
		BizStatistics s = (oldList == null) ? getBizStatistics(def, objectId, objectType, year, month, weekOfYear, dayOfYear)
			: lookupBizStatistics(oldList, def, obj, year, month, weekOfYear, dayOfYear);
		if(s == null){
			s = new BizStatistics();
			s.setDataSum(value);
			s.setDefinition(def);
			
			s.setRefObjectId(objectId);
			s.setRefObjectType(objectType);
			if(obj != null && obj instanceof RFSObjectable){
				s.setRefObjectName(((RFSObjectable)obj).getName());
			}
			
			s.setStatus((byte) 1);
			s.setTheDay(dayOfYear);
			s.setTheWeek(weekOfYear);
			s.setTheMonth(month);
			s.setTheYear(year);
			isNew = true;
		}else{
			s.setDataSum(s.getDataSum().add(value));
		}
		if(task != null){
			s.setLastBizSn(task.getSn());
			s.setLastBizGetTime(task.getBeginTime());
			s.setLastBizTime(task.getBeginTime());
		}
		if(s.getLastBizGetTime() == null){
			s.setLastBizGetTime(new Date());
		}
		if(s.getLastBizTime() == null){
			s.setLastBizTime(new Date());
		}
		return isNew ? bizStatisticsDao.save(s) : bizStatisticsDao.update(s);
	}
	
	public BizStatistics getBizStatistics(BizStatisticsDefinition def, long objectId, int objectType, 
			short year, byte month, byte weekOfYear, short dayOfYear){
		SimpleExpression c = Restrictions.eq("definition", def);
		Logic logic = Restrictions.logic(c)
			.and(Restrictions.eq("refObjectId", objectId))
			.and(Restrictions.eq("refObjectType", objectType))
			.and(Restrictions.eq("theYear", year))
			.and(Restrictions.eq("theMonth", month))
			.and(Restrictions.eq("theWeek", weekOfYear))
			.and(Restrictions.eq("theDay", dayOfYear));
		List<BizStatistics> list = bizStatisticsDao.find(new ResultFilter(logic, null));
		if(list != null){
			int size = list.size();
			if(size > 1){
				log.error("***查询指定条件的业务统计数据不唯一*** ：" + list);
			}
			if(size > 0){
				return list.iterator().next();
			}
		}
		return null;
	}
	
	private static BizStatistics lookupBizStatistics(List<BizStatistics> list, 
			BizStatisticsDefinition def, RFSEntityObject obj, 
			short year, byte month, byte weekOfYear, short dayOfYear){
		if(list == null || list.isEmpty()){
			return null;
		}
		String dataId = def.getDataId();
		long objectId = 0;
		int objectType = 0;
		if(obj != null){
			objectId = obj.getId();
			objectType = obj.getObjectType();
		}
		
		BizStatistics result = null;
		for(BizStatistics s: list){
			if(s.getDefinition().getDataId().equals(dataId) 
					&& year == s.getTheYear()
					&& month == s.getTheMonth()
					&& weekOfYear == s.getTheWeek()
					&& dayOfYear == s.getTheDay()
					&& objectId == s.getRefObjectId()
					&& objectType == s.getRefObjectType()){
				if(result == null){
					result = s;
				}else{
					log.warn("***查询指定条件的业务统计数据不唯一*** \n #1: " + s + "\n #2: " + result);
				}
			}
		}
		return result;
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizStatisticsService#removeStatistics(cn.redflagsoft.base.bean.BizStatisticsDefinition, java.math.BigDecimal, java.util.Date, cn.redflagsoft.base.bean.RFSEntityObject, cn.redflagsoft.base.bean.Task)
	 */
	public int removeStatistics(BizStatisticsDefinition def, BigDecimal value,
			Date date, RFSEntityObject obj, Task task) {
		throw new UnsupportedOperationException("removeStatistics()");
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizStatisticsService#addStatistics(java.lang.String, java.math.BigDecimal, java.util.Date, cn.redflagsoft.base.bean.RFSEntityObject, cn.redflagsoft.base.bean.Task)
	 */
	public int addStatistics(String dataId, BigDecimal value, Date date,RFSEntityObject obj, Task task) {
		BizStatisticsDefinition def = getDefinition(dataId);
		if(def == null){
			log.warn("找不到业务统计定义：" + dataId);
			return 0;
		}
		return addStatistics(def, value, date, obj, task);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizStatisticsService#removeStatistics(java.lang.String, java.math.BigDecimal, java.util.Date, cn.redflagsoft.base.bean.RFSEntityObject, cn.redflagsoft.base.bean.Task)
	 */
	public int removeStatistics(String dataId, BigDecimal value, Date date,	RFSEntityObject obj, Task task) {
		throw new UnsupportedOperationException("removeStatistics()");
	}

	public List<BizStatistics> findBizStatistics(ResultFilter filter) {
		return bizStatisticsDao.find(filter);
	}

	public static void main(String[] args){
//		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.set(2011, 0, 1);
		Date time = c.getTime();
		System.out.println(time);
		System.out.println(c.get(Calendar.YEAR));
		System.out.println(c.get(Calendar.MONTH));
		System.out.println(c.get(Calendar.WEEK_OF_YEAR));
		System.out.println(c.get(Calendar.DAY_OF_YEAR));
		
		
		String s1 = "a,v";
		String s2 = "a," + "v";
		System.out.println(s1 == s2);
	}
}
