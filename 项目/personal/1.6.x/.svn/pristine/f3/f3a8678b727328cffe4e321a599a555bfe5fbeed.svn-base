package org.opoo.apps.query.impl;

import static org.opoo.apps.query.util.QueryUtils.convertArrayValue;
import static org.opoo.apps.query.util.QueryUtils.convertSingleValue;
import static org.opoo.apps.query.util.QueryUtils.convertValue;
import static org.opoo.apps.query.util.QueryUtils.getStringValue;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.QueryParameter;
import org.opoo.apps.QueryParameter.Operator;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.query.ParametersBuilder;
import org.opoo.apps.service.impl.ValidQueryParametersUtils;
import org.opoo.apps.util.DateUtils;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.InitializingBean;

public class AbstractParametersBuilder implements ParametersBuilder, InitializingBean {
	private static final Log log = LogFactory.getLog(AbstractParametersBuilder.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	private Map<QueryParameter.Operator, CriterionBuilder> builders;
	
//	public Object[] buildParameters(Object call, QueryParameters parameters, Class<?>[] parameterTypes) {
//		if(parameterTypes.length == 1 && ResultFilter.class.equals(parameterTypes[0])){
//			return new Object[]{buildResultFilter(call, parameters)};
//		}else{
//			Object[] result = new Object[parameterTypes.length];
//			Arrays.fill(result, null);
//			
//			parameters.getRawParameters().get("");
//			return result;
//		}
//	}

	public Object[] buildParameters(Object bean, QueryParameters parameters, String[] parameterNames, Class<?>[] parameterTypes) {
		if(parameterTypes.length == 1 && ResultFilter.class.isAssignableFrom(parameterTypes[0])){
			return new Object[]{buildResultFilter(bean, parameters)};
		}else{
			return buildParametersInternal(bean, parameters, parameterNames, parameterTypes);
		}
	}

	private Object[] buildParametersInternal(Object bean, QueryParameters parameters, String[] parameterNames, Class<?>[] parameterTypes) {
		if(parameterNames.length != parameterTypes.length){
			throw new IllegalArgumentException("参数数目不一致");
		}
		//List<QueryParameter> list = parameters.getParameters();
		//ValidQueryParametersUtils.removeInvalidParamerter(list);
		
		if(parameterNames.length == 0){
			return new Object[0];
		}
		
		Object[] results = new Object[parameterNames.length];
		Arrays.fill(results, null);
		
		for(int i = 0 ; i < parameterNames.length ; i++){
			String name = parameterNames[i];
			if("start".equalsIgnoreCase(name)){
				results[i] = parameters.getStart();
			}else if("limit".equalsIgnoreCase(name)){
				results[i] = parameters.getMaxResults();
			}else if("sort".equalsIgnoreCase(name)){
				results[i] = parameters.getSort();
			}else if("dir".equalsIgnoreCase(name)){
				results[i] = parameters.getDir();
			}else if("order".equalsIgnoreCase(name)){
				results[i] = parameters.getOrder();
			}else{
				results[i] = buildParameter(bean, parameters, name, parameterTypes[i]);
			}
		}
		return results;
	}
	
	private Object buildParameter(Object bean, QueryParameters parameters, String name, Class<?> targetType) {
		//从原始参数中获取 &param=value
		String stringValue = getStringValue(parameters.getRawParameters(), name);
		if(IS_DEBUG_ENABLED){
			log.debug("从原始参数中获得指定参数：" + name + " -> " + stringValue);
		}
		
		if(stringValue == null){
			QueryParameter qp = lookupQueryParameter(parameters, name);
			if(qp != null){
				//&s[x].n=param&s[x].v=value
				stringValue = qp.getV();
				if(IS_DEBUG_ENABLED){
					log.debug("从查询参数集合获得指定参数：" + name + " -> " + qp);
				}
			}
		}
		
		/*
		QueryParameter qp = lookupQueryParameter(parameters, name);
		String stringValue = null;
		if(qp != null){
			//&s[x].n=param&s[x].v=value
			stringValue = qp.getV();
			if(IS_DEBUG_ENABLED){
				log.debug("从查询参数集合获得指定参数：" + name + " -> " + qp);
			}
		}else{
			//从原始参数中获取 &param=value
			stringValue = getStringValue(parameters.getRawParameters(), name);
			if(IS_DEBUG_ENABLED){
				log.debug("从原始参数中获得指定参数：" + name + " -> " + stringValue);
			}
		}*/
		
		if(stringValue == null){
			if(IS_DEBUG_ENABLED){
				log.debug("解析后参数值为空：" + name);
			}
			return null;
		}
		
		if(ValidQueryParametersUtils.isInvalidValue(stringValue)){
			if(IS_DEBUG_ENABLED){
				log.debug("剔除无效参数：" + name + " = " + stringValue);
			}
			return null;
		}
		
		return convertValue(stringValue, targetType);
	}
	
	/**
	 * 从参数集合中查找指定名称的参数对象。
	 * @param parameters
	 * @param name
	 * @return
	 */
	private QueryParameter lookupQueryParameter(QueryParameters parameters, String name){
		if(parameters == null){
			return null;
		}
		if(parameters.getParameters() == null){
			return null;
		}
		for (QueryParameter p : parameters.getParameters()) {
			if(name.equals(p.getN())){
				return p;
			}
		}
		return null;
	}
	
	public ResultFilter buildResultFilter(Object bean, QueryParameters parameters) {
		List<QueryParameter> list = parameters.getParameters();
		//剔除无效参数
		ValidQueryParametersUtils.removeInvalidParamerter(list);
		
		Criterion c = buildCriterion(bean, list);
		ResultFilter filter = new ResultFilter(c, parameters.getOrder(), parameters.getStart(), parameters.getMaxResults());
		filter.setRawParameters(parameters.getRawParameters());
		return filter;
	}

	private Criterion buildCriterion(Object bean, List<QueryParameter> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}

		Logic logic = null;
		for (QueryParameter parameter : list) {
			String n = parameter.getN();
			String v = parameter.getV();
			if (n == null) {
				log.warn("查询参数名称为空，忽略");
				continue;
			}

			Operator operator = parameter.getOperator();
			CriterionBuilder builder = getBuilders().get(operator);
			if (builder == null) {
				throw new QueryException("找不到正确的参数创建器：" + operator);
			}
			
			//获取准确的类型
			Class<?> type = getPropertyType(bean, n, v, parameter.getTypeClass());
			Criterion criterion = builder.buildCriterion(bean, n, v, type);
			if(criterion != null){
				logic = appendCriterion(logic, criterion);
				if (IS_DEBUG_ENABLED) {
					Object[] values = criterion.getValues();
					log.debug("查询条件：" + criterion.toString()
							+ (values != null ? Arrays.asList(values) : ""));
				}
			}
		}
		return logic;
	}
	/**
	 * 解析指定属性的准确类型。
	 * @param bean
	 * @param n
	 * @param v
	 * @param typeClass
	 * @return
	 */
	protected Class<?> getPropertyType(Object bean, String n, String v,	Class<?> typeClass) {
		return typeClass;
	}

	protected static Logic appendCriterion(Logic logic, Criterion c){
		if(logic == null){
			logic = Restrictions.logic(c);
		}else{
			logic.and(c);
		}
		return logic;
	}
	
	public static interface CriterionBuilder{
		Criterion buildCriterion(Object bean, String name, String value, Class<?> targetType);
	}
	
	class NullBuilder implements CriterionBuilder{
		public Criterion buildCriterion(Object bean, String name, String value,	Class<?> targetType) {
			return Restrictions.isNull(name);
		}
	}
	
	class NotNullBuilder implements CriterionBuilder{
		public Criterion buildCriterion(Object bean, String name, String value,	Class<?> targetType) {
			return Restrictions.isNotNull(name);
		}
	}
	
	abstract class AbstractCriterionBuilder implements CriterionBuilder{
		public Criterion buildCriterion(Object bean, String name, String value,	Class<?> targetType) {
			if(value == null){
				return null;
			}
			return build(bean, name, value, targetType);
		}
		protected abstract Criterion build(Object bean, String name, String val, Class<?> targetType);
	}
	
	class LikeBuilder extends AbstractCriterionBuilder implements CriterionBuilder{
		@Override
		protected Criterion build(Object bean, String name, String val,	Class<?> targetType) {
			return Restrictions.like(name, "%" + val + "%");
		}
	}
	
	class IlikeBuilder extends AbstractCriterionBuilder implements CriterionBuilder{
		@Override
		protected Criterion build(Object bean, String name, String val,	Class<?> targetType) {
			return Restrictions.ilike(name, "%" + val + "%");
		}
	}
	
	class EQBuilder implements CriterionBuilder{
		private NullBuilder nullBuilder;// = new NullBuilder();
		private NotNullBuilder notNullBuilder;// = new NotNullBuilder();

		EQBuilder(NullBuilder nullBuilder, NotNullBuilder notNullBuilder){
			this.nullBuilder = nullBuilder;
			this.notNullBuilder = notNullBuilder;
		}
		
		public Criterion buildCriterion(Object bean, String name, String value,	Class<?> targetType) {
			//null
			if(value == null){
				return null;
				//return nullBuilder.buildCriterion(bean, name, value, targetType);
			}
			//null
			if("__NULL__".equalsIgnoreCase(value) || "<null>".equalsIgnoreCase(value)){
				return nullBuilder.buildCriterion(bean, name, value, targetType);
			}
			//not null
			if ("__NOTNULL__".equalsIgnoreCase(value) || "<notnull>".equalsIgnoreCase(value)){
				return notNullBuilder.buildCriterion(bean, name, value, targetType);
			}	
			Object value2 = convertSingleValue(value, targetType);
			if(value2 == null){
				throw new IllegalArgumentException("查询参数不正确");
			}
			return Restrictions.eq(name, value2);
		}
	}
	
	class NEBuilder extends AbstractCriterionBuilder{
		@Override
		protected Criterion build(Object bean, String name, String val,	Class<?> targetType) {
			Object value2 = convertSingleValue(val, targetType);
			if(value2 == null){
				throw new IllegalArgumentException("查询参数不正确");
			}
			return Restrictions.ne(name, value2);
		}
	}
	
	class GEAndGTBuilder extends AbstractCriterionBuilder{
		private boolean isGE = false;
		GEAndGTBuilder(boolean isGE){
			this.isGE = isGE;
		}
		@Override
		protected Criterion build(Object bean, String name, String val,	Class<?> targetType) {
			Object value2 = convertSingleValue(val, targetType);
			if(value2 == null){
				throw new IllegalArgumentException("查询参数不正确");
			}
			if(isGE){
				return Restrictions.ge(name, value2);
			}else{
				return Restrictions.gt(name, value2);
			}
		}
	}
	class LEAndLTBuilder extends AbstractCriterionBuilder{
		private boolean isLE = false;
		LEAndLTBuilder(boolean isLE){
			this.isLE = isLE;
		}
		@Override
		protected Criterion build(Object bean, String name, String val,	Class<?> targetType) {
			Object value2 = convertSingleValue(val, targetType);
			if(value2 == null){
				throw new IllegalArgumentException("查询参数不正确");
			}
			//处理时间到当天结束的时刻
			if(Date.class.equals(targetType)){
				Date d = (Date) value2;
				value2 = DateUtils.toEndOfDay(d);
			}
			if(isLE){
				return Restrictions.le(name, value2);
			}else{
				return Restrictions.lt(name, value2);
			}
		}
	}
	
	class InAndNotInBuilder extends AbstractCriterionBuilder{
		private boolean isIn = false;
		InAndNotInBuilder(boolean isIn){
			this.isIn = isIn;
		}
		@Override
		protected Criterion build(Object bean, String name, String val,	Class<?> targetType) {
			Object[] values = convertArrayValue(val, targetType);
			if(values == null || values.length == 0){
				throw new IllegalArgumentException("查询参数不正确");
			}
			if(isIn){
				return Restrictions.in(name, values);
			}else{
				return Restrictions.notIn(name, values);
			}
		}
	}

	public void afterPropertiesSet() throws Exception {
		builders = new HashMap<Operator, CriterionBuilder>();
		
		NullBuilder nullBuilder = new NullBuilder();
		NotNullBuilder notNullBuilder = new NotNullBuilder();
		builders.put(Operator.EQ, new EQBuilder(nullBuilder, notNullBuilder));
		builders.put(Operator.GE, new GEAndGTBuilder(true));
		builders.put(Operator.GT, new GEAndGTBuilder(false));
		builders.put(Operator.ILIKE, new IlikeBuilder());
		builders.put(Operator.LIKE, new LikeBuilder());
		builders.put(Operator.IN, new InAndNotInBuilder(true));
		builders.put(Operator.NOTIN, new InAndNotInBuilder(false));
		builders.put(Operator.LE, new LEAndLTBuilder(true));
		builders.put(Operator.LT, new LEAndLTBuilder(false));
		builders.put(Operator.NE, new NEBuilder());
		builders.put(Operator.NOTNULL, notNullBuilder);
		builders.put(Operator.NULL, nullBuilder);
	}

	public Map<Operator, CriterionBuilder> getBuilders() {
		return builders;
	}
}
