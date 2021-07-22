package cn.redflagsoft.base.dao;

import java.util.List;

import cn.redflagsoft.base.bean.EventMsg;

public interface EventMsgDao extends org.opoo.ndao.Dao<EventMsg, Long>{
	List<EventMsg> findEventMsgCfg(int objectType,Long objectID,int jobType,int taskType,int workType,int processType,Long eventType);
	
	List<EventMsg> findEventMsgCfg(int objectType,Long objectID,int taskType,int workType,Long eventType);
	
	List<EventMsg> findEventMsgCfg(int objectType,Long objectID,Long eventType);
}
