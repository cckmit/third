package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.CautionNotify;


/**
 * 警示告知。
 * @author 
 *
 */
public interface CautionNotifyService{
	
	/**
	 * 查询警示告知
	 * @param cautionId
	 * @return
	 */
	CautionNotify getCautionNotifyByCautionId(Long cautionId,int type);
	
	/**
	 * 查询满足条件的警示告知
	 * @param filter
	 * @return
	 */
	List<CautionNotify> findCautionNotifys(ResultFilter filter);
	
	/**
	 * 保存OR更新警示告知
	 * @param cautionNotify
	 * @return
	 */
	CautionNotify saveOrUpdateCautionNotify(Caution caution,CautionNotify cautionNotify);
	
	
	/**
	 * 删除警示告知
	 * @param cautionNotify
	 * @return
	 */
	int deleteCautionNotify(CautionNotify cautionNotify);
}
