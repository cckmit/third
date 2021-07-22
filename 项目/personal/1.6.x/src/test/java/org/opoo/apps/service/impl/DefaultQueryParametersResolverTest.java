package org.opoo.apps.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.opoo.apps.QueryParameter;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.service.impl.QueryServiceImpl2.ResolvedParameter;
import org.opoo.ndao.Dao;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.support.ResultFilter;

public class DefaultQueryParametersResolverTest {

	@Test
	public void testResolve() {
		//fail("Not yet implemented");
		
		List<QueryParameter> qs = new ArrayList<QueryParameter>();
		QueryParameter q = new QueryParameter();
		q.setN("abcd");
		q.setV("value");
		qs.add(q);
		
		
		DefaultQueryParametersResolver resolver = new DefaultQueryParametersResolver();
		Criterion resolve = resolver.resolve(qs, (Dao)null);
		System.out.println(resolve);
	}
	
	@Test
	public void testExtractValue(){
		final List<QueryParameter> qs = new ArrayList<QueryParameter>();
		QueryParameter q = new QueryParameter();
		q.setN("abcd");
		q.setV("value,value2,value3");
		q.setO("like");
		qs.add(q);
		
		
		QueryParametersBuilder pb = new QueryParametersBuilder();
		ResolvedParameter rp = pb.resolveParameter(null, q);
		Object[] values = rp.getValues();
		for(Object value: values){
			System.out.println(value + "]");
		}
		
		ResultFilter filter = pb.buildResultFilter(null, new QueryParameters(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 6099703886469938945L;

			public int getStart() {
				return 0;
			}
			
			public String getSort() {
				return null;
			}
			
			public List<QueryParameter> getParameters() {
				return qs;
			}
			
			public Order getOrder() {
				return null;
			}
			
			public int getMaxResults() {
				return 10;
			}
			
			public String getDir() {
				return null;
			}

			public Map<String, ?> getRawParameters() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		
		System.out.println(filter.getCriterion());
	}


}
