package org.opoo.apps.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.QueryParameter;
import org.opoo.apps.service.QueryService;

public abstract class ValidQueryParametersUtils {
	private static final Log log = LogFactory.getLog(ValidQueryParametersUtils.class);
	
	/**
	 * 从参数集合中删除无效的参数。
	 * @param parameters
	 * @return
	 */
	public static List<QueryParameter> removeInvalidParamerter(List<QueryParameter> parameters){
		if(parameters != null){
			List<QueryParameter> list = new ArrayList<QueryParameter>(parameters);
			Iterator<QueryParameter> iterator = list.iterator();
			while(iterator.hasNext()){
				QueryParameter p = iterator.next();
				if(isInvalidValue(p.getV())){
					parameters.remove(p);
					if(log.isInfoEnabled()){
						log.info(String.format("剔除无效的查询参数：%s %s %s (%s)", p.getN(), p.getO(), p.getV(), p.getT()));
					}
				}
			}
			if(log.isDebugEnabled()){
				log.debug("剔除后参数：" + parameters);
			}
		}
		return parameters;
	}
	/**
	 * 值是否有效。
	 * 
	 * @param v
	 * @return
	 */
	public static boolean isInvalidValue(String v) {
		return QueryService.INVALID_STRING.equals(v)
			|| "合计".equals(v)
			|| "总计".equals(v)
			|| "小计".equals(v);
	}
}
