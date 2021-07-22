package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.CautionCheck;


/**
 * 警示复核。
 * @author 
 */
public interface CautionCheckService{
	
	/**
	 * 查询警示复核
	 * @param cautionId
	 * @return
	 */
	CautionCheck getCautionCheckByCautionId(Long cautionId);
	
	
	
	/**
	 * 查询满足条件的警示复核
	 * @param filter
	 * @return
	 */
	List<CautionCheck> findCautionChecks(ResultFilter filter);
	
	/**
	 * 保存OR更新警示复核
	 * @param cautionCheck
	 * @return
	 */
	CautionCheck saveOrUpdateCautionCheck(Caution caution,CautionCheck cautionCheck);
	
	
	/**
	 * 删除警示复核
	 * @param cautionCheck
	 * @return
	 */
	int deleteCautionCheck(CautionCheck cautionCheck);
	
}
