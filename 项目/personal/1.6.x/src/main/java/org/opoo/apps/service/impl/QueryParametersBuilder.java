package org.opoo.apps.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.QueryParameter;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.service.impl.QueryServiceImpl2.ResolvedParameter;
import org.opoo.apps.util.DateUtils;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.XWorkConverter;


/**
 * ��ѯ������������
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class QueryParametersBuilder implements QueryServiceImpl2.ParametersBuilder{
	private static final Log log = LogFactory.getLog(QueryParametersBuilder.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
//	private static final List<String> OPERATORS_LESS_FAMILY = Arrays.asList("<,<=,lt,le".split(","));
	
	public ResultFilter buildResultFilter(Object bean, QueryParameters parameters){
		Logic logic = null;
		List<QueryParameter> list = parameters.getParameters();
		//�޳���Ч����
		ValidQueryParametersUtils.removeInvalidParamerter(list);
		if(list != null && !list.isEmpty()){
			for(QueryParameter parameter: list){
				//֧��null��not null�Ĳ�ѯ
				String v = parameter.getV();
				String o = parameter.getO().trim();
				if("null".equalsIgnoreCase(o) || "__NULL__".equalsIgnoreCase(v) || "<null>".equalsIgnoreCase(v)){
					logic = appendCriterion(logic, Restrictions.isNull(parameter.getN()));
					continue;
				}else if ("notnull".equalsIgnoreCase(o) || "__NOTNULL__".equalsIgnoreCase(v) || "<notnull>".equalsIgnoreCase(v)){
					logic = appendCriterion(logic, Restrictions.isNotNull(parameter.getN()));
					continue;
				}	
				
				ResolvedParameter rp = resolveParameter(bean, parameter);
				if(rp == null){
					if(IS_DEBUG_ENABLED){
						log.debug("���������ֵ�գ�" + parameter.getN());
					}
					//throw new new IllegalArgumentException("���������ֵ�գ�" + parameter.getN());
					continue;
				}

				Object[] values = rp.getValues();
				if(values == null || values.length == 0){
					throw new IllegalArgumentException("��ѯ��������ȷ");
				}

				if(IS_DEBUG_ENABLED){
					log.debug(String.format("��ѯ������%s %s %s", rp.getName(), rp.getOperator(), Arrays.asList(values)));
				}
//				//TODO: �Ƿ����޳���Чֵ��
//				if(values[0] != null && QueryService.INVALID_STRING.equals(values[0].toString())){
//					log.info(String.format("�޳���Ч�Ĳ�ѯ����: %s %s %s", rp.getName(), rp.getOperator(), Arrays.asList(values)));
//					continue;
//				}
				
				String name = rp.getName();
				//ȡԭʼ������
				String operation = rp.getQueryParameter().getO().trim();
				Criterion c;
				if("in".equalsIgnoreCase(operation)){
					c = Restrictions.in(name, values);
				}else if("notin".equalsIgnoreCase(operation)){
					c = Restrictions.notIn(name, values);
				}else if("ne".equalsIgnoreCase(operation)){
					c = Restrictions.ne(name, values[0]);
				}else if("gt".equalsIgnoreCase(operation)){
					c = Restrictions.gt(name, values[0]);
				}else if("lt".equalsIgnoreCase(operation)){
					c = Restrictions.lt(name, values[0]);
				}else if("ge".equalsIgnoreCase(operation)){
					c = Restrictions.ge(name, values[0]);
				}else if("le".equalsIgnoreCase(operation)){
					c = Restrictions.le(name, values[0]);
				}else if("null".equalsIgnoreCase(operation)){
					c = Restrictions.isNull(name);
				}else if("notnull".equalsIgnoreCase(operation)){
					c = Restrictions.isNotNull(name);
				}else{
					//ȡ�²����������� like ����ǰ��ӿո�
					c = new SimpleExpression(name, values[0], rp.getOperator());
				}
				
				if(logic == null){
					logic = Restrictions.logic(c);
				}else{
					logic.and(c);
				}
			}
		}
		
		ResultFilter filter = new ResultFilter(logic, parameters.getOrder(), parameters.getStart(), parameters.getMaxResults());
		filter.setRawParameters(parameters.getRawParameters());
		return filter;
	}
	
	private Logic appendCriterion(Logic logic, Criterion c){
		if(logic == null){
			logic = Restrictions.logic(c);
		}else{
			logic.and(c);
		}
		return logic;
	}
	
//	public Object[] extractValue(Object bean, QueryParameter parameter){
//		if(parameter == null || parameter.getN() == null || parameter.getV() == null){
//			return null;
//		}
//		
//		String name = parameter.getN();
//		String type = parameter.getT();
//		String operator = parameter.getO();
//		String stringValue = parameter.getV();
//		
//		if("like".equalsIgnoreCase(operator) || "ilike".equalsIgnoreCase(operator)){
//			operator = " " + operator + " ";
//			return new Object[]{"%" + stringValue + "%"};
//		}
//		
//		String[] inputs = valueToArray(stringValue, operator);
//		Object[] values = new Object[inputs.length];
//		for(int i = 0 ; i < inputs.length ; i++){
//			String value = inputs[i];
//			if("date".equalsIgnoreCase(type)){
//				try {
//					values[i] = AppsGlobals.parseDate(value);
//				} catch (ParseException e) {
//					throw new IllegalArgumentException(name + " : " + value, e);
//				}
//			}else{
//				Class<?> typeClass = getType(bean, parameter);
//				if(typeClass == null || String.class.equals(typeClass)){
//					values[i] = value;
//				}else{
//					values[i] = convertValue(value, typeClass);
//				}
//			}
//		}
//		return values;
//	}
	/**
	 * �����и���
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
	
	private boolean isInOrNotIn(String operation){
		return "in".equalsIgnoreCase(operation) || "notin".equalsIgnoreCase(operation);
	}
	
	public Object convertValue(String value, Class<?> clazz) {
		//boolean��ʽ������
//		if(Boolean.class.equals(clazz) || boolean.class.equals(clazz)){
//			return "1".equals(value) || "true".equals(value);
//		}
		return XWorkConverter.getInstance().convertValue(
				ActionContext.getContext().getContextMap(), value, clazz);
	}

	public ResolvedParameter resolveParameter(Object bean, QueryParameter parameter) {
		if(parameter == null || parameter.getN() == null || parameter.getV() == null){
			return null;
		}
		
		ResolvedParameter result = new ResolvedParameter(parameter);
		String name = parameter.getN();
		String type = parameter.getT();
		String operator = parameter.getO();
		String stringValue = parameter.getV();
		
		//like & ilike
		if("like".equalsIgnoreCase(operator) || "ilike".equalsIgnoreCase(operator)){
			operator = " " + operator + " ";
			result.setOperator(operator);
			result.setTypeClass(String.class);
			return result.setValues(new Object[]{"%" + stringValue + "%"});
		}
		
		Class<?> typeClass = String.class;
		
		if("date".equalsIgnoreCase(type)){
			typeClass = Date.class;
		}else if("boolean".equalsIgnoreCase(type)){
			typeClass = Boolean.class;
		}else{
		
		//boolean isStringType = false;
		//boolean isDateType = "date".equalsIgnoreCase(type);
		
//		if(isDateType){
//			typeClass = Date.class;
//			//isStringType = false;
//		}else{
			typeClass = getType(bean, parameter);
			if(typeClass == null/* || String.class.equals(typeClass)*/){
				typeClass = String.class;
//				isStringType = true;
//			}else{
//				isStringType = false;
			}
		}
		result.setTypeClass(typeClass);
		
		String[] inputs = valueToArray(stringValue, operator);
		Object[] values = new Object[inputs.length];
		for(int i = 0 ; i < inputs.length ; i++){
			String value = inputs[i];
			if(String.class.equals(typeClass)){
				values[i] = value;
			}else if(Boolean.class.equals(typeClass)){
				//boolean ������
				values[i] = "1".equals(value) || "true".equalsIgnoreCase(value);
			}else if(Date.class.equals(typeClass)){
				try {
					Date date = AppsGlobals.parseDate(value);
					//����С�ں�С�ڵ��ڵ�ʱ������
					if(date != null && "<=,lt,le".indexOf(parameter.getO()) != -1){
						date = DateUtils.toEndOfDay(date);
						
						if(log.isDebugEnabled()){
							log.debug("�������ǡ�" + operator + "�����������ʱ��Ϊ�������ʱ�̣�" + date);
						}
					}
					values[i] = date;//AppsGlobals.parseDate(value);
				} catch (ParseException e) {
					throw new IllegalArgumentException(name + " : " + value, e);
				}
			}else{
				values[i] = convertValue(value, typeClass);;
			}
		}
		
		return result.setValues(values);
	}
}
