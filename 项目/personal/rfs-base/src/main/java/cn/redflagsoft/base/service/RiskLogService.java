package cn.redflagsoft.base.service;

import java.util.Date;

import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskLog;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.vo.BizVO;
import cn.redflagsoft.base.vo.MonitorVO;


/**
 * 监察日志服务。
 *
 */
public interface RiskLogService {
	/**
	 * 根据监察信息保存监察日志。
	 * 
	 * @param risk
	 * @param t
	 * @param type 业务操作
	 * @return
	 */
	RiskLog saveLogByRisk(Risk risk,Task t,int type);
	void riskRemindCopySend(Date start,Date end);
	MonitorVO getMonitorVOByRiskLogID(Long id);
	BizVO getTaskBusinessByRiskLogID(Long id);
}

