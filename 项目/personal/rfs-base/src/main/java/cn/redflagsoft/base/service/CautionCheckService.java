package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.CautionCheck;


/**
 * ��ʾ���ˡ�
 * @author 
 */
public interface CautionCheckService{
	
	/**
	 * ��ѯ��ʾ����
	 * @param cautionId
	 * @return
	 */
	CautionCheck getCautionCheckByCautionId(Long cautionId);
	
	
	
	/**
	 * ��ѯ���������ľ�ʾ����
	 * @param filter
	 * @return
	 */
	List<CautionCheck> findCautionChecks(ResultFilter filter);
	
	/**
	 * ����OR���¾�ʾ����
	 * @param cautionCheck
	 * @return
	 */
	CautionCheck saveOrUpdateCautionCheck(Caution caution,CautionCheck cautionCheck);
	
	
	/**
	 * ɾ����ʾ����
	 * @param cautionCheck
	 * @return
	 */
	int deleteCautionCheck(CautionCheck cautionCheck);
	
}
