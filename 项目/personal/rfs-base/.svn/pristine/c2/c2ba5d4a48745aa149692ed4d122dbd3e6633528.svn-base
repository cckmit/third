package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.MenuItemRelation;
import cn.redflagsoft.base.dao.MenuItemRelationDao;

public class MenuItemRelationHibernateDao extends AbstractBaseHibernateDao<MenuItemRelation, Long> implements MenuItemRelationDao {

	@SuppressWarnings("unchecked")
	public List<MenuItemRelation> findBySupId(long supId) {
		String qs = "from MenuItemRelation where supId=? order by displayOrder";
		return getHibernateTemplate().find(qs, supId);
	}

	/**
	 * 
	 */
	public void remove(Long supId, Long subId) {
		String qs = "delete from MenuItemRelation where supId=? and subId=?";
		getQuerySupport().executeUpdate(qs, new Object[]{supId, subId});
	}

	@SuppressWarnings("unchecked")
	public MenuItemRelation get(Long supId, Long subId) {
		String qs = "from MenuItemRelation where supId=? and subId=?";
		List<MenuItemRelation> list = getHibernateTemplate().find(qs, new Object[]{supId, subId});
		if(list.isEmpty()){
			return null;
		}else if(list.size() == 1){
			return list.get(0);
		}else{
			throw new IllegalStateException("返回结果不唯一: " + list.size());
		}
	}
}
