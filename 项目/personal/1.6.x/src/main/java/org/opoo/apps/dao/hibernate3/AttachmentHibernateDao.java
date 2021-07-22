package org.opoo.apps.dao.hibernate3;

import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.dao.AttachmentDao;

public class AttachmentHibernateDao extends AbstractAppsHibernateDao<Attachment, Long> implements AttachmentDao{

	public void updateFetchCount(Long id) {
		String queryString = "update Attachment set fetchCount=fetchCount+1 where id=?";
		getQuerySupport().executeUpdate(queryString, id);
	}
}
