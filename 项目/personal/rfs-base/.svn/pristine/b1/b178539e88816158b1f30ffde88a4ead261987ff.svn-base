package cn.redflagsoft.base.dao.hibernate3;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.opoo.ndao.DataAccessException;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.redflagsoft.base.bean.MenuItem;
import cn.redflagsoft.base.dao.MenuItemDao;

public class MenuItemHibernateDao extends AbstractBaseHibernateDao<MenuItem, Long> implements MenuItemDao {

	/* (non-Javadoc)
	 * @see org.opoo.ndao.hibernate3.CachedHibernateDao#remove(java.io.Serializable)
	 */
	@Override
	public int remove(Long id) throws DataAccessException {
		String qs = "update MenuItem set parentId=null where parentId=?";
		getQuerySupport().executeUpdate(qs, id);
		return super.remove(id);
	}

	/* (non-Javadoc)
	 * @see org.opoo.ndao.hibernate3.CachedHibernateDao#remove(org.opoo.ndao.criterion.Criterion)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int remove(Criterion c) throws DataAccessException {
		if(c == null){
			return super.remove(c);
		}
		
		final String qs = "select id from MenuItem";
		final List<Long> list = getQuerySupport().find(qs, new ResultFilter(c, null));
		if(list == null || list.isEmpty()){
			return 0;
		}
		
		//修改依赖本对象的
		final String hql = "update MenuItem set parentId=null where parentId in(:ids)";
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql).setParameterList("ids", list).executeUpdate();
			}});

		return super.remove(c);
	}

	/* (non-Javadoc)
	 * @see org.opoo.ndao.hibernate3.CachedHibernateDao#remove(K[])
	 */
	@Override
	public int remove(final Long[] ids) throws DataAccessException {
		final String hql = "update MenuItem set parentId=null where parentId in(:ids)";
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql).setParameterList("ids", ids).executeUpdate();
			}});
		return super.remove(ids);
	}
	


	/**
	 * 清空。
	 */
	public void removeAll(){
		String hql = "update MenuItem set parentId=null";
		getQuerySupport().executeUpdate(hql);
		hql = "delete from MenuItem";
		getQuerySupport().executeUpdate(hql);
	}
	
//	
//	public int remove(Long id, boolean cascade) {
//		
//		if(cascade){
//			//找到所有继承对象
//			String qs = "from MenuItem where parentId=?";
//			List<MenuItem> list = getHibernateTemplate().find(qs, id);
//			if(list != null){
//				for(MenuItem item: list){
//					remove(item.getId(), true);
//				}
//			}
//			super.remove(id);
//		}else{
//			String qs = "update MenuItem set parentId=null where parentId=?";
//			getQuerySupport().executeUpdate(qs, id);
//			super.remove(id);
//		}
//		return 1;
//	}

//	/**
//	 * 仅仅简单的修改action为空，不删除。
//	 */
//	public int removeByAction(Long actionId) {
//		String qs = "update MenuItem set actionId=null where actionId=?";
//		return getQuerySupport().executeUpdate(qs, actionId);
//	}

	@SuppressWarnings("unchecked")
	public List<Long> findIds(){
		return getHibernateTemplate().find("select id from MenuItem order by label");
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findIds(ResultFilter filter){
		return getQuerySupport().find("select id from MenuItem", filter);
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findSuperIds(){
		String qs = "select id from MenuItem where id not in (select distinct subId from MenuItemRelation) order by label";
		return getHibernateTemplate().find(qs);
	}
}
