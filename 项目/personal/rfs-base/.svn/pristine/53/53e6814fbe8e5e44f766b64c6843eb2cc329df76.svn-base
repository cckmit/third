package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.commons.ObjectAdmin;
import cn.redflagsoft.base.dao.ObjectAdminDao;
import cn.redflagsoft.base.event2.ObjectNameChangeListener;

public class ObjectAdminHibernateDao extends AbstractBaseHibernateDao<ObjectAdmin,Long> 
		implements ObjectAdminDao,ObjectNameChangeListener {
	private static final Log log = LogFactory.getLog(ObjectAdminHibernateDao.class);
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.ObjectNameChangeListener#objectNameChange(cn.redflagsoft.base.bean.RFSObjectable, java.lang.String, java.lang.String)
	 */
	public void objectNameChange(RFSObjectable object, String oldValue,	String newValue) {
		log.debug("对象名称发生了变化，同步更新OBJECT_ADMIN：" + oldValue + " --> " + newValue);
		
		String sql = "update OBJECT_ADMIN set OBJ_NAME=? where OBJ_ID=? and OBJ_TYPE=?";
		int rows = executeSQLUpdate(sql, newValue, object.getId(), object.getObjectType());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("清理了 OBJECT_ADMIN 缓存");
		}
		log.debug("共更新OBJECT_ADMIN 的相关对象名称 ‘" + rows + "’个");
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ObjectAdmin> T get(long id, Class<T> clazz){
		return (T)getHibernateTemplate().get(clazz, id);
	}
	
	public <T extends ObjectAdmin> T getLast(RFSEntityObject object, Class<T> clazz){
		return getLast(object.getObjectType(), object.getId(), clazz);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ObjectAdmin> T getLast(int objType, long objId, Class<T> clazz){
		String qs = "select a from " + clazz.getSimpleName() + " a";
		ResultFilter filter = ResultFilter.createPageableResultFilter(0, 1);
		filter.setOrder(Order.desc("a.creationTime"));
		filter.setCriterion(Restrictions.logic(Restrictions.eq("a.objType", objType)).and(Restrictions.eq("a.objId", objId)));
		List<Object> list = getQuerySupport().find(qs, filter);
		if(!list.isEmpty()){
			return (T) list.iterator().next();
		}
		return null;
	}
}
