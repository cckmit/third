package cn.redflagsoft.base.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.opoo.ndao.hibernate3.HibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.redflagsoft.base.bean.InfoConfig;
import cn.redflagsoft.base.bean.InfoDetail;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.service.InfoStatHandler;

public class ItemPropertyInfoStatHandler extends HibernateDaoSupport implements InfoStatHandler {
	private static final Log log = LogFactory.getLog(ItemPropertyInfoStatHandler.class);
	
	public boolean supports(RFSObject o, InfoConfig config) {
		return (InfoConfig.TYPE_ITEM_PROPERTY == config.getType());
	}

	public byte getStatStatus(RFSObject o, InfoConfig config) {
		final String qs = config.getValue();
		//qs = "select amount from PrjBudget where prjId=? order by creationTime desc"
		final Long objectId = o.getId();
		
		if(log.isDebugEnabled()){
			log.debug("Executing hql: " + qs + " | " + objectId);
		}
		
//		List<?> list = getHibernateTemplate().find(qs, objectId/*o.getId()*/);
		
		List<?> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
				return session.createQuery(qs)
						.setLong(0, objectId)
						.setFirstResult(0)
						.setMaxResults(1)
						.list();
			}
		});
		
		if(list == null || list.isEmpty()){
			return InfoDetail.STATUS_INCOMPLETE;
		}
		
		Object object = list.iterator().next();
		return object != null ? InfoDetail.STATUS_COMPLETE : InfoDetail.STATUS_INCOMPLETE;
	}
}
