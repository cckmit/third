package cn.redflagsoft.base.service;

import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.bean.EventMsg;
import cn.redflagsoft.base.bean.Smsg;

public interface EventMsgService {
	
	List<EventMsg> findEventMsgCfg(int objectType,Long objectID,int jobType,int taskType,int workType,int processType,Long eventType);
	
	List<EventMsg> findEventMsgCfg(int objectType,Long objectID,int taskType,int workType,Long eventType);
	
	List<EventMsg> findEventMsgCfg(int objectType,Long objectID,Long eventType);
	/**
	 * 处理事件消息。
	 * @param objectType
	 * @param objectID
	 * @param taskType
	 * @param taskSN
	 * @param workType
	 * @param bizAction
	 * @return
	 */
	List<Smsg> dealEventMsg(int objectType,Long objectID,int taskType,Long taskSN,int workType,short bizAction);
	
	List<Smsg> dealEventMsg(int objectType,Long objectID,int taskType,Long taskSN,int workType,short bizAction, Map<String,Object> context);

	List<Smsg> createSmsgByEventMsg(EventMsg em,Long objectID, Long taskSN, Map<String,Object> context);
	
	/**
	 * 
	 * @param em
	 * @param receiverIds
	 * @param title 标题或者标题模板
	 * @param content 内容或者内容模板
	 * @param context
	 * @return
	 */
	List<Smsg> createBaseSmsg(EventMsg em,List<Long> receiverIds,String title,String content, Map<String,Object> context);
}
