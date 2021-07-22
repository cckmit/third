package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.CautionDecide;


/**
 * 警示处理决定。
 * @author 
 */
public interface CautionDecideService{
	
	/**
	 * 查询警示处理决定
	 * @param cautionId
	 * @return
	 */
	CautionDecide getCautionDecideByCautionId(Long cautionId);
	
	/**
	 * 查询满足条件的处理决定
	 * @param filter
	 * @return
	 */
	List<CautionDecide> findCautionDecides(ResultFilter filter);
	
	/**
	 * 保存OR更新处理决定
	 * @param cautionDecide
	 * @return
	 */
	CautionDecide saveOrUpdateCautionDecide(Caution caution,CautionDecide cautionDecide);
	
	
	/**
	 * 删除处理决定
	 * @param cautionDecide
	 * @return
	 */
	int deleteCautionDecide(CautionDecide cautionDecide);
	
	
}
