package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.LifeStage;
import cn.redflagsoft.base.dao.LifeStageDao;

public class LifeStageHibernateDao extends AbstractBaseHibernateDao<LifeStage, Long> implements LifeStageDao/*,
	ObjectNameChangeListener, ObjectSnChangeListener, ObjectCodeChangeListener*/{
	public static final Log log = LogFactory.getLog(LifeStageHibernateDao.class);
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.LifeStageDao#synchronizeLifeStageStatusForObject(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public void synchronizeLifeStageStatusForObject(Long id) {
		String hql = "select c.lifeStageFieldName, a.status from Task a, ObjectTask b, TaskDefinition c where a.sn=b.taskSN and a.type=c.id and c.lifeStageFieldName is not null and b.objectID=?";
		List<Object[]> list = getHibernateTemplate().find(hql, id);
		if(!list.isEmpty()){
			StringBuffer sb = new StringBuffer("update LifeStage set ");
			Object[] values = new Object[list.size() + 1];
			for(int i = 0, n = list.size() ; i < n ; i++){
				Object[] objects = list.get(i);
				String name = (String) objects[0];
				byte status = (Byte) objects[1];
				sb.append(name + "=?");
				if(i < (n - 1)){
					sb.append(", ");
				}
				values[i] = status;
			}
			
			sb.append(" where id=?");
			values[list.size()] = id;
			getQuerySupport().executeUpdate(sb.toString(), values);
		}
	}

	/*
	public void objectCodeChange(RFSObjectable object, String oldValue,	String newValue) {
		log.debug("����code�����˱仯��ͬ������LIFE_STAGE��" + oldValue + " --> " + newValue);
		
		String sql = "update LIFE_STAGE set CODE=? where ID=? and OBJ_TYPE=?";
		int rows = executeSQLUpdate(sql, newValue, object.getId(), object.getObjectType());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("������ LIFE_STAGE ����");
		}
		log.debug("������LIFE_STAGE ����ض���CODE ��" + rows + "����");
	}

	public void objectSnChange(RFSObjectable object, String oldValue, String newValue) {
		log.debug("����sn�����˱仯��ͬ������LIFE_STAGE��" + oldValue + " --> " + newValue);
		
		String sql = "update LIFE_STAGE set SN=? where ID=? and OBJ_TYPE=?";
		int rows = executeSQLUpdate(sql, newValue, object.getId(), object.getObjectType());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("������ LIFE_STAGE ����");
		}
		log.debug("������LIFE_STAGE ����ض���SN ��" + rows + "����");
	}

	public void objectNameChange(RFSObjectable object, String oldValue,	String newValue) {
		log.debug("�������Ʒ����˱仯��ͬ������LIFE_STAGE��" + oldValue + " --> " + newValue);
		
		String sql = "update LIFE_STAGE set NAME=? where ID=? and OBJ_TYPE=?";
		int rows = executeSQLUpdate(sql, newValue, object.getId(), object.getObjectType());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("������ LIFE_STAGE ����");
		}
		log.debug("������LIFE_STAGE ����ض������� ��" + rows + "����");
	}
	*/
}
