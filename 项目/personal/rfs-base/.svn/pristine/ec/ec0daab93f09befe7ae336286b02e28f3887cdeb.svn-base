package cn.redflagsoft.base.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.BizStatistics;
import cn.redflagsoft.base.bean.BizStatisticsDefinition;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.Task;

public interface BizStatisticsService {
//	BizStatistics save(BizStatistics bizStatistics);
//	
//	BizStatistics update(BizStatistics bizStatistics);
//	
//	int delete(BizStatistics bizStatistics);
//	
//	BizStatistics get(Long id);
//	
//	List<BizStatistics> find(ResultFilter resultFilter);
//	
//	List<BizStatistics> find();
	
	/**
	 * 查询业务统计定义对象，通常可以对这个进行缓存。
	 */
	BizStatisticsDefinition getDefinition(String dataId);
	/**
	 * 创建新的业务统计定义。
	 * @param def
	 * @return
	 */
	BizStatisticsDefinition saveDefinition(BizStatisticsDefinition def);
	
	/**
	 * 更新定义，同步缓存。
	 * @param def
	 * @return
	 */
	BizStatisticsDefinition updateDefinition(BizStatisticsDefinition def);
	
	/**
	 * 删除指定的业务统计定义，通常要清理缓存。
	 * @param dataId
	 */
	void removeDefinition(String dataId);
	
	/**
	 * 向统计表中增加一项统计值。一般在被统计值发生变化时（例如下达了新计划）调用。
	 * 当<code>dataId</code>指定的<code>BizStatisticsDefinition</code>不存在时，直接返回0，表示没有影响任何记录。
	 * 
	 * @param dataId 统计定义的ID
	 * @param value 要统计增加的值
	 * @param date 时间
	 * @param obj 相关的对象
	 * @param task 业务
	 * @return 影响的对象数量
	 * @see BizStatisticsService#addStatistics(BizStatisticsDefinition, BigDecimal, Date, RFSEntityObject, Task)
	 */
	int addStatistics(String dataId, BigDecimal value, Date date, RFSEntityObject obj, Task task);
	
	/**
	 * 向统计表中增加一项统计值。一般在被统计值发生变化时（例如下达了新计划）调用。
	 * 
	 * @param def 统计定义
	 * @param value 要统计增加的值
	 * @param date 时间
	 * @param obj 相关的对象
	 * @param task 业务
	 * @return 影响的对象数量
	 */
	int addStatistics(BizStatisticsDefinition def, BigDecimal value, Date date, RFSEntityObject obj, Task task);
	
	
	/**
	 * 删除一项统计值。一般在被统计值删除时（例如删除或者修改了某计划）调用。
	 * 
	 * @param dataId
	 * @param value
	 * @param date
	 * @param obj
	 * @param task
	 * @return
	 * @see #removeStatistics(BizStatisticsDefinition, BigDecimal, Date, RFSEntityObject, Task)
	 */
	int removeStatistics(String dataId, BigDecimal value, Date date, RFSEntityObject obj, Task task); 
	

	/**
	 * 删除一项统计值。一般在被统计值删除时（例如删除或者修改了某计划）调用。
	 * 
	 * @param def
	 * @param value
	 * @param date
	 * @param obj
	 * @param task
	 * @return 影响的对象数量
	 */
	int removeStatistics(BizStatisticsDefinition def, BigDecimal value, Date date, RFSEntityObject obj, Task task);
	
	/***
	 *  根据条件 查询对应的统计值
	 * @param def		统计定义
	 * @param objectId  对象的ID
	 * @param objectType	对象类型
	 * @param year			年度
	 * @param month			月份
	 * @param weekOfYear	周
	 * @param dayOfYear		天
	 * @return
	 */ 
	BizStatistics getBizStatistics(BizStatisticsDefinition def, long objectId, int objectType, 
			short year, byte month, byte weekOfYear, short dayOfYear);
	
	
	List<BizStatistics> findBizStatistics(ResultFilter filter);
}
