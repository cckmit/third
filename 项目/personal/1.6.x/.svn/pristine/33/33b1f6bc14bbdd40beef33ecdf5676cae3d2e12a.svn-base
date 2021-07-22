package org.opoo.ndao.support;

import org.apache.commons.lang.StringUtils;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;

/**
 * 查询辅助类。
 *
 */
public abstract class QueryHelper {
    public static final String WHERE = " where ";

    /**
     * 创建查询语句。将查询条件和排序规则合并在基本查询语句之后。
     * 
     * @param baseSql
     * @param c
     * @param order
     * @return String
     */
    public static String buildQueryString(String baseSql, Criterion c, Order order) {
    	String lowerSql = baseSql.toLowerCase();
    	int index = lowerSql.lastIndexOf(" group by ");
    	String endSql = null;
    	String startSql = baseSql;
    	if(index != -1){
    		startSql = baseSql.substring(0, index);
    		endSql = baseSql.substring(index);
    	}
    	
        if (c != null) {
            String cs = c.toString();
            if (StringUtils.isNotBlank(cs)) {
                int i = startSql.toLowerCase().indexOf(WHERE);
                if (i == -1) {
                	startSql += WHERE;
                } else {
                	startSql += " and ";
                }
                startSql += cs;
            }
        }

        if(endSql != null){
        	startSql += endSql;
        }
        if (order != null) {
        	startSql += " order by " + order.toString();
        }

        return startSql;
    }

    /**
     * 创建查询语句。
     * 
     * @param baseSql
     * @param c
     * @return String
     */
    public static String buildQueryString(String baseSql, Criterion c) {
        return buildQueryString(baseSql, c, null);
    }
}
