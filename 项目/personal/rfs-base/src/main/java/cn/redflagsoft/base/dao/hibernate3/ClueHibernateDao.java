package cn.redflagsoft.base.dao.hibernate3;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.redflagsoft.base.bean.Clue;
import cn.redflagsoft.base.dao.ClueDao;

public class ClueHibernateDao extends AbstractBaseHibernateDao<Clue, Long> implements ClueDao {

	@SuppressWarnings("unchecked")
	public List<Clue> getActiveClue(Long objectId, byte category, int bizType, Long bizId) {
		String hql = "select a from Clue a where a.objectId=? and a.category=? and a.bizType=? and a.bizId=? and status<9 ";//order by creationTime desc
		return getHibernateTemplate().find(hql, new Object[]{objectId, category, bizType, bizId});
	}
	
	@SuppressWarnings("unchecked")
	public List<Clue> getActiveClue2(Long objectId, byte category, int bizType, Long bizId) {
		String hql = "select a from Clue a where a.objectId=? and a.category=? and a.bizType=? and a.bizId=? and status<9 order by creationTime desc";
		return getHibernateTemplate().find(hql, new Object[]{objectId, category, bizType, bizId});
	}
	
	public void deleteClueByBizSN(final Long bizSN){
		final String hql = "delete from Clue where bizSN=?";
	    getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery(hql)
				.setLong(0,bizSN)
				.executeUpdate();
				return null;
			}
	    });
	}
}
