package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.BizRouteNode;

public interface BizRouteNodeService {
	
	/**
	 * 获取业务流程节点列表
	 * 
	 * @param routeId
	 * @return List<BizRouteNode>
	 */
	List<BizRouteNode> findBizRouteNodeList(Long routeId);
}
