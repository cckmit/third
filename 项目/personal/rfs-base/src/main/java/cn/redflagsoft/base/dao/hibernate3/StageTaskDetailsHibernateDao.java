package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.ndao.NonUniqueResultException;

import cn.redflagsoft.base.bean.StageTaskDetails;
import cn.redflagsoft.base.dao.StageTaskDetailsDao;

public class StageTaskDetailsHibernateDao extends
		AbstractBaseHibernateDao<StageTaskDetails, Long> implements StageTaskDetailsDao {
	
	/**
	 * ���ݶ���id���������͡��׶ε�taskType������Ψһһ���׶�������Ϣ��
	 * <p>
	 * ��ʹ��select��ѯʱ���صĽ��Ӧ����0������1��������1��ʱ�׳��쳣��
	 * @param objectId
	 * @param objectType
	 * @param taskType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public StageTaskDetails getStageTaskDetail(long objectId, int objectType,	int taskType) {
		String qs = "from StageTaskDetails where objectId=? and objectType=? and taskType=?";
		List<StageTaskDetails> list = getHibernateTemplate().find(qs, new Object[]{objectId, objectType, taskType});
		int size = list.size();
		if(size == 0){
			return null;
		}else if(size == 1){
			return list.get(0);
		}
		
		throw new NonUniqueResultException(size);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.StageTaskDetailsDao#updateActualTimeToExpectedTime()
	 */
	public int updateAllActualTimesToExpectedTimes() {
		String[] qs = new String[6];
		qs[0] = "update StageTaskDetails set expectedStartTime=actualStartTime where actualStartTime is not null";
		qs[1] = "update StageTaskDetails set expectedFinishTime=actualFinishTime where actualFinishTime is not null";
		qs[2] = "update StageTaskDetails set expectedBzStartTime=bzStartTime where bzStartTime is not null";
		qs[3] = "update StageTaskDetails set expectedBzFinishTime=bzFinishTime where bzFinishTime is not null";
		qs[4] = "update StageTaskDetails set expectedSbTime=sbTime where sbTime is not null";
		qs[5] = "update StageTaskDetails set expectedPfTime=pfTime where pfTime is not null";
		int rows = 0;
		for(String q: qs){
			rows += getQuerySupport().executeUpdate(q);
		}
		return rows;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.StageTaskDetailsDao#updatePfTimeToFinishTime()
	 */
	public int updatePfTimeToFinishTime() {
		String qs1 = "update StageTaskDetails set actualFinishTime=pfTime where pfTime is not null";
		String qs2 = "update StageTaskDetails set expectedFinishTime=expectedPfTime where expectedPfTime is not null";
		return getQuerySupport().executeUpdate(qs1) + getQuerySupport().executeUpdate(qs2);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.StageTaskDetailsDao#updateBzStartTimeToStartTime()
	 */
	public int updateBzStartTimeToStartTime() {
		String qs1 = "update StageTaskDetails set actualStartTime=bzStartTime where bzStartTime is not null";
		String qs2 = "update StageTaskDetails set expectedStartTime=expectedBzStartTime where expectedBzStartTime is not null";
		return getQuerySupport().executeUpdate(qs1) + getQuerySupport().executeUpdate(qs2);
	}
}
