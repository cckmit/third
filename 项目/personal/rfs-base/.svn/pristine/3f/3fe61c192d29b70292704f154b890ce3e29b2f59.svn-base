package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.apps.bean.core.Attachment;

import cn.redflagsoft.base.bean.MsgAttachments;
import cn.redflagsoft.base.dao.MsgAttachmentsDao;

/**
 * @deprecated
 */
public class MsgAttachmentsHibernateDao extends AbstractBaseHibernateDao<MsgAttachments, Long> implements MsgAttachmentsDao {

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.MsgAttachmentsDao#findAttachmentsBySendingMsgId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Attachment> findAttachmentsBySendingMsgId(Long sendingMsgId) {
		String qs = "select a from MsgAttachments b, Attachment a where b.fileId=a.id and b.sendingMsgId=?";
		return getHibernateTemplate().find(qs, sendingMsgId);
	}

}
