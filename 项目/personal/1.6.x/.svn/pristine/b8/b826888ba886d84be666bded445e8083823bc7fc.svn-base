package org.opoo.apps.service.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.QueryParameter;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.service.QueryParametersResolver;
import org.opoo.ndao.Dao;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.XWorkConverter;

import edu.emory.mathcs.backport.java.util.Arrays;

public class DefaultQueryParametersResolver implements QueryParametersResolver {
	private static final Log log = LogFactory.getLog(DefaultQueryParametersResolver.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();

	public Serializable convert(String string, Class<?> targetClass) {
		return (Serializable) XWorkConverter.getInstance().convertValue(ActionContext.getContext().getContextMap(), string, targetClass);
	}

	public Criterion resolve2(List<QueryParameter> queryParameters, Dao<?, ?> dao) {
		if(queryParameters == null || queryParameters.isEmpty()){
			return null;
		}
		
		Logic logic = null;
		for(QueryParameter p: queryParameters){
			String name = p.getN();
			String type = p.getT();
			String operation = p.getO();
			String stringValue = p.getV();
			Object value = stringValue;
			
			//log.debug(name + " " + operation + " " + value + " : " + type);
			
			if(StringUtils.isBlank(name) || StringUtils.isBlank(stringValue)){
				continue;
			}
			if("like".equalsIgnoreCase(operation) || "ilike".equalsIgnoreCase(operation)){
				operation = " " + operation + " ";
				value = "%" + stringValue + "%";
			}else if("date".equalsIgnoreCase(type)){
				try {
					value = AppsGlobals.parseDate(stringValue);
				} catch (ParseException e) {
					log.error("类型转化失败，忽略此查询条件：" + name, e);
					//e.printStackTrace();
					continue;
				}
			}else{
				Class<?> clazz = getType(p, dao);
				/*不是string，需要转换。*/
				if(clazz != null && !String.class.equals(clazz)){
					value = XWorkConverter.getInstance().convertValue(ActionContext.getContext().getContextMap(), stringValue, clazz);
					if(IS_DEBUG_ENABLED){
						log.debug("转化类型：" + clazz + " for [" + name + "]: " + value);
					}
				}
			}
			
			if(IS_DEBUG_ENABLED){
				log.debug("查询条件：" + name + operation + value);
			}
			
			
			Criterion c = new SimpleExpression(name, value, operation);
			if(logic == null){
				logic = Restrictions.logic(c);
			}else{
				logic.and(c);
			}
		}
		return logic;
	}
	public Criterion resolve(List<QueryParameter> queryParameters, Dao<?, ?> dao) {
		if(queryParameters == null || queryParameters.isEmpty()){
			return null;
		}
		
		Logic logic = null;
		for(QueryParameter p: queryParameters){
			Collection<?> values = convertValues(p, dao);
			if(values.isEmpty()){
				throw new IllegalArgumentException("参数转化不正确，没有正确的值。");
			}
			
			if(IS_DEBUG_ENABLED){
				log.debug("查询条件：" + p.getN() + p.getO() + values);
			}
			
			String name = p.getN();
			String operation = p.getO();
			Criterion c;
			if("in".equalsIgnoreCase(operation)){
				c = Restrictions.in(name, values);
			}else if("notin".equalsIgnoreCase(operation)){
				c = Restrictions.notIn(name, values);
			}else{
				c = new SimpleExpression(name, values.iterator().next(), " " + operation + " ");
			}
			
			if(logic == null){
				logic = Restrictions.logic(c);
			}else{
				logic.and(c);
			}
		}
		return logic;
	}
	
	
	private Collection<?> convertValues(QueryParameter p, Dao<?,?> dao){
		String name = p.getN();
		String type = p.getT();
		String operation = p.getO();
		String stringValue = p.getV();
		Collection<Object> values = new ArrayList<Object>();
		
		if("like".equalsIgnoreCase(operation) || "ilike".equalsIgnoreCase(operation)){
			operation = " " + operation + " ";
			values.add("%" + stringValue + "%");
			return values;
		}else if("date".equalsIgnoreCase(type)){
			String[] inputs = getInputStrings(stringValue, operation);
			for(String value: inputs){
				try {
					values.add(AppsGlobals.parseDate(value));
				} catch (ParseException e) {
					log.error("类型转化失败，忽略此查询条件：" + name + "=" + value, e);
					//e.printStackTrace();
					continue;
				}
			}
			return values;
		}else{
			Class<?> clazz = getType(p, dao);
			String[] inputs = getInputStrings(stringValue, operation);
			
			/*不是string，需要转换。*/
			if(clazz != null && !String.class.equals(clazz)){
				for(String value: inputs){
					values.add(XWorkConverter.getInstance().convertValue(ActionContext.getContext().getContextMap(), stringValue, clazz));
					if(IS_DEBUG_ENABLED){
						log.debug("转化类型：" + clazz + " for [" + name + "]: " + value);
					}
				}
			}else{
				for(String value: inputs){
					values.add(value);
				}
			}
			
			return values;
		}
	}
	
	
	private String[] getInputStrings(String stringValue, String operation){
		if(isInOrNotIn(operation)){
			StringTokenizer st = new StringTokenizer(stringValue, ",; ");
			List<String> list = new ArrayList<String>();
			while(st.hasMoreTokens()){
				list.add(st.nextToken());
			}
			return list.toArray(new String[list.size()]);
		}else{
			return new String[]{stringValue};
		}
	}
	
	private boolean isInOrNotIn(String operation){
		return "in".equalsIgnoreCase(operation) || "notin".equalsIgnoreCase(operation);
	}
	
	
	
	
	
	public Class<?> getType(QueryParameter param, Dao<?,?> dao){
		return param.getTypeClass();
	}

	public Criterion resolve(List<QueryParameter> parameters, Queryable queryable) {
		return resolve(parameters, (Dao<?,?>)null);
	}
	
	
	
	public ResultFilter buildResultFilter(Object bean, Method method, QueryParameters parameters){
		Logic logic = null;
		List<QueryParameter> list = parameters.getParameters();
		if(list != null && !list.isEmpty()){
			for(QueryParameter p: list){
				Object[] values = extractValue(bean, method, p);
				if(values == null || values.length == 0){
					throw new IllegalArgumentException("参数转化不正确，没有正确的值。");
				}
				
				if(IS_DEBUG_ENABLED){
					log.debug("查询条件：" + p.getN() + p.getO() + Arrays.asList(values));
				}
				
				String name = p.getN();
				String operation = p.getO();
				Criterion c;
				if("in".equalsIgnoreCase(operation)){
					c = Restrictions.in(name, values);
				}else if("notin".equalsIgnoreCase(operation)){
					c = Restrictions.notIn(name, values);
				}else{
					c = new SimpleExpression(name, values[0], operation);
				}
				
				if(logic == null){
					logic = Restrictions.logic(c);
				}else{
					logic.and(c);
				}
			}
		}
		
		
		return new ResultFilter(logic, parameters.getOrder(), parameters.getStart(), parameters.getMaxResults());
	}
	
	public Object[] extractValue(Object bean, Method method, QueryParameter parameter){
		if(parameter == null || parameter.getN() == null || parameter.getV() != null){
			return null;
		}
		
		String name = parameter.getN();
		String type = parameter.getT();
		String operator = parameter.getO();
		String stringValue = parameter.getV();
		
		if("like".equalsIgnoreCase(operator) || "ilike".equalsIgnoreCase(operator)){
			operator = " " + operator + " ";
			return new Object[]{"%" + stringValue + "%"};
		}
		
		String[] inputs = valueToArray(stringValue, operator);
		Object[] values = new Object[inputs.length];
		for(int i = 0 ; i < inputs.length ; i++){
			String value = inputs[i];
			if("date".equalsIgnoreCase(type)){
				try {
					values[i] = AppsGlobals.parseDate(value);
				} catch (ParseException e) {
					throw new IllegalArgumentException(name + " : " + value, e);
				}
			}else{
				Class<?> typeClass = getType(bean, parameter);
				if(typeClass == null || String.class.equals(typeClass)){
					values[i] = value;
				}else{
					values[i] = XWorkConverter.getInstance().convertValue(
							ActionContext.getContext().getContextMap(), value, typeClass);
				}
			}
		}
		return values;
	}
	/**
	 * 子类中覆盖
	 * @param bean
	 * @param parameter
	 * @return
	 */
	protected Class<?> getType(Object bean, QueryParameter parameter){
		return parameter.getTypeClass();
	}
	
	private String[] valueToArray(String stringValue, String operation){
		if(isInOrNotIn(operation)){
			StringTokenizer st = new StringTokenizer(stringValue, ",; ");
			List<String> list = new ArrayList<String>();
			while(st.hasMoreTokens()){
				list.add(st.nextToken());
			}
			return list.toArray(new String[list.size()]);
		}else{
			return new String[]{stringValue};
		}
	}
	
	
	
	public static void main(String[] args){
		DefaultQueryParametersResolver d = new DefaultQueryParametersResolver();
		String[] strings = d.getInputStrings("aksj,23kjlk;23 9384093,990,", "in");
		if(strings != null){
			for(String s: strings){
				System.out.println(s + "]");
			}
		}
	}
}
