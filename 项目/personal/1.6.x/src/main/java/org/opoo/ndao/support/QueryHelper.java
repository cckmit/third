package org.opoo.ndao.support;

import org.apache.commons.lang.StringUtils;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;

/**
 * ��ѯ�����ࡣ
 *
 */
public abstract class QueryHelper {
    public static final String WHERE = " where ";

    /**
     * ������ѯ��䡣����ѯ�������������ϲ��ڻ�����ѯ���֮��
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
     * ������ѯ��䡣
     * 
     * @param baseSql
     * @param c
     * @return String
     */
    public static String buildQueryString(String baseSql, Criterion c) {
        return buildQueryString(baseSql, c, null);
    }
}
