package cn.redflagsoft.base.dao;


import org.opoo.ndao.Dao;
import cn.redflagsoft.base.bean.SendingMsg;

/**
 * @deprecated
 */
public interface SendingMsgDao extends Dao<SendingMsg, Long> {
	int getConfirmSendingMsgCount();
}
	