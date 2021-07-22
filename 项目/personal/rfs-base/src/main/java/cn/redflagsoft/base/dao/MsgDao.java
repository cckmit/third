package cn.redflagsoft.base.dao;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Msg;

/**
 * @deprecated
 */
public interface MsgDao extends Dao<Msg, Long> {
	
	List<Msg> findAcceptMessageBySendTime(Long toId, Date start, Date end);
	
	List<Msg> findAcceptMessage(Long id, Long entityId, Byte readStatus);
	
	List<Msg> findUnsentMessage(Long fromId);
	
	List<Msg> findWaitSend(int maxTryCount);
}
