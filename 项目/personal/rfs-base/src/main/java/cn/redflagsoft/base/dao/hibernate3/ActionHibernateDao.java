package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.ndao.NonUniqueResultException;

import cn.redflagsoft.base.bean.Action;
import cn.redflagsoft.base.dao.ActionDao;

public class ActionHibernateDao extends AbstractBaseHibernateDao<Action, Long> implements ActionDao {

	@SuppressWarnings("unchecked")
	public List<Long> findIds(){
		String qs = "select id from Action where status=1 order by name Asc";
		return getHibernateTemplate().find(qs);
	}
	
	
	@Override
	public int remove(Long id){
		String qs = "update MenuItem set actionId=null where actionId=?";
		getQuerySupport().executeUpdate(qs, id);
		return super.remove(id);
	}


	@SuppressWarnings("unchecked")
	public Action getByUid(String uid) {
		String qs = "from Action where uid=?";
		List<Action> list = getHibernateTemplate().find(qs, uid);
		if(list.isEmpty()){
			return null;
		}else if(list.size() == 1){
			return list.get(0);
		}else{
			throw new NonUniqueResultException(list.size());//IllegalStateException("返回结果不唯一: " + list.size());
		}
	}
}
