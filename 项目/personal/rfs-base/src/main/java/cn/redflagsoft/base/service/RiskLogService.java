package cn.redflagsoft.base.service;

import java.util.Date;

import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskLog;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.vo.BizVO;
import cn.redflagsoft.base.vo.MonitorVO;


/**
 * �����־����
 *
 */
public interface RiskLogService {
	/**
	 * ���ݼ����Ϣ��������־��
	 * 
	 * @param risk
	 * @param t
	 * @param type ҵ�����
	 * @return
	 */
	RiskLog saveLogByRisk(Risk risk,Task t,int type);
	void riskRemindCopySend(Date start,Date end);
	MonitorVO getMonitorVOByRiskLogID(Long id);
	BizVO getTaskBusinessByRiskLogID(Long id);
}

