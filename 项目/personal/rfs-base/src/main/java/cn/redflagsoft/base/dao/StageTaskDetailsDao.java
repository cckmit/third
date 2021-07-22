package cn.redflagsoft.base.dao;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.StageTaskDetails;

public interface StageTaskDetailsDao extends Dao<StageTaskDetails,Long> {
	/**
	 * ���ݶ���id���������͡��׶ε�taskType������Ψһһ���׶�������Ϣ��
	 * <p>
	 * ��ʹ��select��ѯʱ���صĽ��Ӧ����0������1��������1��ʱ�׳��쳣��
	 * @param objectId
	 * @param objectType
	 * @param taskType
	 * @return
	 */
	StageTaskDetails getStageTaskDetail(long objectId, int objectType, int taskType);
	
	/**
	 * ����ͬ��ʵ��ʱ�䵽Ԥ��ʱ�䡣
	 * 
	 * @return ��ͬ���ļ�¼����
	 */
	int updateAllActualTimesToExpectedTimes();
	
	/**
	 * ����ͬ����Ԥ�ƣ�����ʱ�䵽��Ԥ�ƣ�����ʱ�䡣
	 * 
	 * @return ��ͬ���ļ�¼����
	 */
	int updatePfTimeToFinishTime();
	
	/**
	 * ����ͬ����Ԥ�ƣ����ƿ�ʼʱ�䵽��Ԥ�ƣ���ʼʱ�䡣
	 * @return ��ͬ���ļ�¼����
	 */
	int updateBzStartTimeToStartTime();
}
