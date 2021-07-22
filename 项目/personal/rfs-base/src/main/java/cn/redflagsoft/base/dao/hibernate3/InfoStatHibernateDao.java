package cn.redflagsoft.base.dao.hibernate3;

import org.opoo.ndao.criterion.Restrictions;

import cn.redflagsoft.base.bean.InfoStat;
import cn.redflagsoft.base.dao.InfoStatDao;

public class InfoStatHibernateDao extends AbstractBaseHibernateDao<InfoStat, Long>
		implements InfoStatDao {

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.InfoStatDao#removeByObjectType(int)
	 */
	public void removeByObjectType(int objectType) {
		remove(Restrictions.eq("objectType", objectType));
	}
	
}
