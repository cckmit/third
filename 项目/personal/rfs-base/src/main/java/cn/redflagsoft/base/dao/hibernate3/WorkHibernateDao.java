package cn.redflagsoft.base.dao.hibernate3;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.dao.WorkDao;
import cn.redflagsoft.base.event2.ObjectNameChangeListener;


public class WorkHibernateDao extends AbstractBaseHibernateDao<Work, Long> 
			implements WorkDao,ObjectNameChangeListener {
	private static final Log log = LogFactory.getLog(WorkHibernateDao.class);

	public void workInvalid(RFSObject rfsObject) {
		final int objectType = rfsObject.getObjectType();
		final Long objectId = rfsObject.getId();
		final String sql= "insert into work_invalid (select * from work w where w.ref_obj_id=? and w.ref_obj_type=?)";
		
		int rows1 = (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
				Query q = session.createSQLQuery(sql).setLong(0, objectId).setInteger(1, objectType);
				return q.executeUpdate();
			}
		});

		SimpleExpression eq = Restrictions.eq("refObjectId",objectId);
		SimpleExpression eq2 = Restrictions.eq("refObjectType",objectType);
		int rows2 = remove(Restrictions.logic(eq).and(eq2));
		
		String s = String.format("对象(type=%s, id=%s)作废，转移Work数据%s项到work_invalid，删除%s项。", objectType, objectId, rows1, rows2);
		log.info(s);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.ObjectNameChangeListener#objectNameChange(cn.redflagsoft.base.bean.RFSObjectable, java.lang.String, java.lang.String)
	 */
	public void objectNameChange(RFSObjectable object, String oldValue,	String newValue) {
		log.debug("对象名称发生了变化，同步更新WORK：" + oldValue + " --> " + newValue);
		
		String sql = "update WORK set REF_OBJ_NAME=? where REF_OBJ_ID=? and REF_OBJ_TYPE=?";
		int rows = executeSQLUpdate(sql, newValue, object.getId(), object.getObjectType());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("清理了WORK 缓存");
		}
		log.debug("共更新WORK的相关对象名称 ‘" + rows + "’个");
	}
}
