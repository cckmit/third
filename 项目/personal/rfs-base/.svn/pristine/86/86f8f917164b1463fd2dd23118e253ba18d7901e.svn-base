package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.SendingMsg;
import cn.redflagsoft.base.dao.SendingMsgDao;

/**
 * @deprecated
 */
public class SendingMsgHibernateDao extends AbstractBaseHibernateDao<SendingMsg, Long> implements SendingMsgDao {
	private static final Log log = LogFactory.getLog(SendingMsgHibernateDao.class);
	
	public int getConfirmSendingMsgCount() {
		String sql = "select count(*) from SendingMsg where status=?";
		List<?> list = getHibernateTemplate().find(sql, new Object[]{SendingMsg.STATUS_CONFIRM});
		try {
			return ((Number)list.get(0)).intValue();
		} catch (Exception e) {
			log.error(e);
			return 0;
		}
	}
}
