package cn.redflagsoft.base.dao.hibernate3;

import org.opoo.ndao.criterion.Restrictions;

import cn.redflagsoft.base.bean.InfoDetail;
import cn.redflagsoft.base.dao.InfoDetailDao;

public class InfoDetailHibernateDao  extends AbstractBaseHibernateDao<InfoDetail, Long> implements InfoDetailDao {

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.InfoDetailDao#removeByObjectType(int)
	 */
	public void removeByObjectType(int objectType) {
		remove(Restrictions.eq("objectType", objectType));
	}
	
}
