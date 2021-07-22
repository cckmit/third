package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.CautionSurvey;


/**
 * 警示调查约谈。
 * @author 
 *
 */
public interface CautionSurveyService{
	
	/**
	 * 查询警示调查约谈
	 * @param cautionId
	 * @return
	 */
	CautionSurvey getCautionSurveyByCautionId(Long cautionId);
	
	/**
	 * 保存更新调查约谈
	 * @param caution
	 * @param cautionSurvey
	 * @return
	 */
	CautionSurvey saveOrUpdateCautionSurvey(Caution caution,CautionSurvey cautionSurvey);
	
	/**
	 * 查询满足条件的调查约谈集合
	 * @param filter
	 * @return
	 */
	List<CautionSurvey> findCautionSurveys(ResultFilter filter);
	
	/**
	 * 删除调查约谈
	 * @param cautionSurvey
	 * @return
	 */
	int deleteCautionSurvey(CautionSurvey cautionSurvey);
}
