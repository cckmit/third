package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.BizRouteNode;

public interface BizRouteNodeService {
	
	/**
	 * ��ȡҵ�����̽ڵ��б�
	 * 
	 * @param routeId
	 * @return List<BizRouteNode>
	 */
	List<BizRouteNode> findBizRouteNodeList(Long routeId);
}
