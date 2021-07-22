/*
 * $Id: RiskChecker.java 6410 2014-05-29 03:47:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.math.BigDecimal;
import java.util.Date;

import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskValue;

/**
 * Risk计算、定时检查等处理器。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface RiskChecker {
	
//
//	/**
//	 * 获取用于计算的value值。
//	 * 如果value值是时间间隔且定时增长的，则通过一定的算法才能获得value。
//	 * 
//	 * 这个方法一般配合 {@link #calculateRiskGrade(Risk, BigDecimal, boolean)} 一起使用。
//	 * 
//	 * @param risk Risk对象
//	 * @param checkTime 监察时间，用于计算value值的时间
//	 * @return 计算得到的value值
//	 */
//	//BigDecimal calculateRiskValue(Risk risk, Date checkTime);
//	
//	/**
//	 * 检查Risk的value的变化，如果value变化了，将计算risk的grade。
//	 * 
//	 * @see #calculateRiskGrade(Risk, boolean)
//	 * @param risk  Risk对象
//	 * @param newValue 用于计算的value值，不可以为空。可使用 {@link #calculateRiskValue(Risk, Date)}先计算这个值。
//	 * @param updateIfValueChanged 是否在value变化时保存risk更新
//	 * @return value是否变化了
//	 */
//	//boolean calculateRiskGrade(Risk risk, BigDecimal newValue, boolean updateIfValueChanged);
//	
//	/**
//	 * 计算监察级别并根据需要更新监察对象。用risk的value计算其grade值。
//	 * 
//	 * @param risk Risk对象
//	 * @param updateIfGradeChanged updateIfValueChanged 是否在grade变化时保存risk更新
//	 * @return grade是否变化了
//	 */
//	//boolean calculateRiskGrade(Risk risk, boolean updateIfGradeChanged);

	
	/**
	 * 检查Risk的value，如果value变化了，将计算risk的grade，如果grade也变化了，
	 * 则继续更新risk的grade值。
	 * 
	 * <p>当risk的value值是随时间变化的，并且risk相关的时间都记录在risk对象中时，
	 * 调用这个方法来计算risk的vlaue、grade等。
	 * 
	 * <p><b>注意，使用这个方法计算时，计算value值和计算grade值是一起执行的，
	 * 且risk的value一定来源于risk中的相关属性或者属性的计算结果。</b>
	 * 
	 * @param risk - Risk 对象
	 * @param calculateTime - Date 参与计算时间，可以为空，为空取当前时间
	 * @return value变化并且更新了返回true，否则返回false。
	 * @see #updateRiskValue(Risk, BigDecimal, Date)
	 * @deprecated 推荐使用计算value和计算grade分离的方法 {@link #calculateAndUpdateRiskValue(Risk)}
	 */
	boolean calculateAndUpdateRiskValue(Risk risk, Date calculateTime);
	
	/**
	 * 从被监控对象中查找被监控的属性，以此属性值作为risk的value，检查value的
	 * 变化并根据需要计算grade。
	 * 
	 * <p>如果被监控对象的被监控属性是随时间变化的，通常需要被监控对象自己定时
	 * 处理自己的被监控值。如Task。
	 *
	 * <p>这个方法分离了risk值的计算（来源），简化了逻辑。
	 * 
	 * @param risk
	 * @return
	 * @see #updateRiskValue(Risk, BigDecimal, Date)
	 * @see #updateRiskValue(Risk, RiskValue)
	 */
	boolean calculateAndUpdateRiskValue(Risk risk);
	
	/**
	 * 
	 * @param risk
	 * @param value
	 * @return
	 * @see #updateRiskValue(Risk, BigDecimal, Date)
	 */
	boolean updateRiskValue(Risk risk, RiskValue value);
	
	/**
	 * 更新Risk的value值，如果value值与原值相等，则不做处理。否则更新value并计算grade，
	 * 如果grade也变化了， 则继续更新risk的grade值。
	 * 
	 * @param risk - Risk 对象
	 * @param newValue - 新的value值
	 * @param valueChangedTime - Date 值变化的时候，可以为空，为空取当前时间
	 * @return value变化并且更新了返回true，否则返回false。
	 * @see #calculateAndUpdateRiskGrade(Risk)
	 */
	boolean updateRiskValue(Risk risk, BigDecimal newValue, Date valueChangedTime);
	
	/**
	 * 计算Risk的grade，如果grade变化了，则更新risk的grade值。
	 *@param risk - Risk 对象
	 * @return grade变化并且更新了返回true，否则返回false。
	 * @see #updateRiskGrade(Risk, byte)
	 */
	boolean calculateAndUpdateRiskGrade(Risk risk);
	
	/**
	 * 更新Risk的grade值，如果grade值与原值相等，则不做处理。否则更新grade。
	 * 
	 * @param risk - Risk 对象
	 * @param newGrade - byte 监察级别
	 * @return grade变化并且更新了返回true，否则返回false。
	 */
	boolean updateRiskGrade(Risk risk, byte newGrade);
	
	/**
	 * 获取所有value正在运行（没有暂停）的risk的value，并
	 * 根据value结果需要计算grade。
	 * 
	 * <p>这个方法一般供定时执行的调度任务调用。
	 * 
	 * <p>该方法会保存risk的变化。
	 * 
	 * @return 运算更新的risk数量
	 */
	int calculateAllRunningRisks();
	
	
	/**
	 * 在多线程中计算。
	 * 
	 * @return 运算更新的risk数量
	 * @see RiskChecker#calculateAllRunningRisks()
	 */
	int calculateAllRunningRisksInThreads();
	
//	/**
//	 * 重新计算所有risk的grade值，一般用于grade修正。
//	 * 
//	 * <p>一般在通过非程序修改了risk的value时（例如数据库直接修改），
//	 * 或者在risk暂停时仍然需要计算时调用这个方法。
//	 * 
//	 * <p>该方法会计算所有risk，无论risk是否暂停，无论risk处于何种状态。
//	 * 
//	 * <p>该方法会保存risk的变化。
//	 * 
//	 * @param force - boolean 是否强制计算，如果参数为true，则不采用优化算法，强制计算所有risk的grade。
//	 * @return 运算更新的risk数量
//	 */
//	int recalculateAllRisksGrade(boolean force);
}
