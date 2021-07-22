package org.opoo.apps.service.impl;

import java.util.List;
import java.util.Map;

import org.opoo.apps.QueryParameter;
import org.opoo.apps.QueryParameters;
import org.opoo.ndao.criterion.Order;


/**
 * 有效的查询参数类。
 * 
 * <p>
 * 在构造该对象时，剔除了无效的查询参数。无效的参数根据参数的值计算出来。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ValidQueryParameters implements QueryParameters {
//	private static final Log log = LogFactory.getLog(ValidQueryParameters.class);
	
	private static final long serialVersionUID = -238520487235450558L;
	private final QueryParameters params;
	private final List<QueryParameter> parameters;
	public ValidQueryParameters(QueryParameters params) {
		this.params = params;
		this.parameters = ValidQueryParametersUtils.removeInvalidParamerter(params.getParameters());
	}
	
	

	public String getDir() {
		return params.getDir();
	}

	public int getMaxResults() {
		return params.getMaxResults();
	}

	public Order getOrder() {
		return params.getOrder();
	}

	public List<QueryParameter> getParameters() {
		return parameters;
	}

	public Map<String, ?> getRawParameters() {
		return params.getRawParameters();
	}

	public String getSort() {
		return params.getSort();
	}

	public int getStart() {
		return params.getStart();
	}
}
