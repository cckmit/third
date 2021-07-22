package cn.redflagsoft.base.service;

import java.math.BigDecimal;
import java.util.Date;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Risk;

/**
 * 警示处理类。
 * @author lcj
 *
 */
public interface CautionService extends RFSObjectService<Caution>{
//	/**
//	 * 保存Caution。
//	 * @param caution
//	 * @return
//	 */
//	Caution saveCaution(Caution caution);
//	
//		/**
//	 * 根据ID删除。
//	 * @param id
//	 */
//	void removeCaution(Long id);
//	
//	/**
//	 * 更新
//	 * @param caution
//	 * @return
//	 */
//	Caution updateCaution(Caution caution);
	
	/**
	 * 根据Risk信息创建Caution对象。
	 * @param risk 监察（风险），不能为空
	 * @param superviseClerk 警示发布者，不能为空，一般为监察单位监察者
	 * @return
	 */
	Caution createCaution(Risk risk, Clerk superviseClerk);
	
	/**
	 * 根据Risk信息创建Caution对象，并明确的指定了部分值。
	 * 
	 * @param risk 监察（风险），不能为空
	 * @param superviseClerk 警示发布者，不能为空，一般为监察单位监察者
	 * @param name 警示名称
	 * @param code 编码
	 * @param grade 监察界别
	 * @param objectAttrValue 被监察属性的值
	 * @param conclusion 结论
	 * @param happenTime 发生时间
	 * @return 警示对象
	 */
	Caution createCaution(Risk risk, Clerk superviseClerk, String name, String code, Byte grade, BigDecimal objectAttrValue, String conclusion, Date happenTime, String cautionSumary, String remark);
	
}
