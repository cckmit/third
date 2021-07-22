package org.opoo.ndao.hibernate3;

import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.ndao.support.SimpleQuerySupport;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.ObjectUtils;


/**
 * 部分支持泛型的查询方法定义。
 * 
 * @author Alex Lin
 *
 */
public class HibernateSimpleQuerySupport implements SimpleQuerySupport {
	private HibernateQuerySupport querySupport;
	public HibernateSimpleQuerySupport(HibernateTemplate template){
		this.querySupport = new HibernateQuerySupport(template);
	}
	
	public HibernateSimpleQuerySupport(HibernateQuerySupport querySupport){
		this.querySupport = querySupport;
	}
	
	public HibernateQuerySupport getQuerySupport(){
		return querySupport;
	}
	
	public int executeUpdate(String queryString, Object... args) {
		return (ObjectUtils.isEmpty(args) ? 
				getQuerySupport().executeUpdate(queryString) :
				getQuerySupport().executeUpdate(queryString, getArguments(args)));
	}

	public int executeUpdate(String queryString, Map<String, Object> args) {
		return ((args == null) ? 
				getQuerySupport().executeUpdate(queryString) :
				getQuerySupport().executeUpdate(queryString, 
						args.keySet().toArray(new String[0]), 
						args.values().toArray()));
		
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString, Object... args) {
		return (List<T>) (ObjectUtils.isEmpty(args) ? 
				getQuerySupport().find(queryString) :
				getQuerySupport().find(queryString, getArguments(args)));
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString, Map<String, Object> args) {
		return (List<T>) ((args == null) ? 
				getQuerySupport().find(queryString) :
				getQuerySupport().find(queryString, 
						args.keySet().toArray(new String[0]), 
						args.values().toArray()));
	}

	public int executeUpdate(String baseSql, Criterion c) {
		return getQuerySupport().executeUpdate(baseSql, c);
	}

	public <T> List<T> find(String baseSql, ResultFilter resultFilter) {
		return getQuerySupport().find(baseSql, resultFilter);
	}

	public <T> PageableList<T> find(String baseSelectSql, String baseCountSql,
			ResultFilter resultFilter) {
		return getQuerySupport().find(baseSelectSql, baseCountSql, resultFilter);
	}

	public int getInt(String baseSql, Criterion c) {
		return getQuerySupport().getInt(baseSql, c);
	}
	/**
	 * Considers an Object array passed into a varargs parameter as
	 * collection of arguments rather than as single argument.
	 */
	private Object[] getArguments(Object[] varArgs) {
		if (varArgs.length == 1 && varArgs[0] instanceof Object[]) {
			return (Object[]) varArgs[0];
		}
		else {
			return varArgs;
		}
	}
}
