package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.InfoDetail;
import cn.redflagsoft.base.bean.RFSObject;

public interface InfoStatService {
	/***
	 *  重新统计对象相关信息项办理情况
	 *  @param objectType 指定类型
	 */
	void stat(int objectType);
	
	/**
	 * 计算指定对象的各个信息项的填报情况。
	 * @param o
	 * @return 该对象的各个信息项填报情况
	 */
	List<InfoDetail> stat(RFSObject o);
	
	
	void statForDevDebug(int objectType, int limit);
}
