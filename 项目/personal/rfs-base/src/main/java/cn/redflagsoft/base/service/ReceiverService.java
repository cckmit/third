package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.Receiver;

public interface ReceiverService {
	
	/**
	 * 前台显示收文列表
	 * 
	 * @param taskType
	 * @param workType
	 * @param processType
	 * @param clerkID
	 * @return List<Receiver>
	 */
	List<Receiver> findReceiverByTypes(int taskType, int workType, int processType, Long clerkID);

	List<Receiver> findReceiver(Integer taskType, Integer workType, Integer processType, Long clerkID); 
}
