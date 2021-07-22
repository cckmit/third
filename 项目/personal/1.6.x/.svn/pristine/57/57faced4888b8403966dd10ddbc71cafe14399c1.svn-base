/*
 * $Id: Restrictions.java 13 2010-11-26 05:04:02Z alex $
 *
 * Copyright 2006-2008 Alex Lin. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opoo.ndao.criterion;

import java.util.Collection;


/**
 * 查询条件限制类。
 *
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public abstract class Restrictions { //implements Criterion
    /**
     * 创建一个“等于”表达式。
     *
     * @param name String
     * @param value Object
     * @return SimpleExpression
     */
    public static SimpleExpression eq(String name, Object value) {
        return new SimpleExpression(name, value, "=");
    }
    /**
     * 创建一个“不等于”的表达式。
     * @param name String
     * @param value Object
     * @return SimpleExpression
     */
    public static SimpleExpression ne(String name, Object value) {
        return new SimpleExpression(name, value, "<>");
    }

    /**
     * 大于等于。
     *
     * @param name String
     * @param value Object
     * @return SimpleExpression
     */
    public static SimpleExpression ge(String name, Object value) {
        return new SimpleExpression(name, value, ">=");
    }

    /**
     * 大于。
     * @param name String
     * @param value Object
     * @return SimpleExpression
     */
    public static SimpleExpression gt(String name, Object value) {
        return new SimpleExpression(name, value, ">");
    }

    /**
     * 小于等于。
     * @param name String
     * @param value Object
     * @return SimpleExpression
     */
    public static SimpleExpression le(String name, Object value) {
        return new SimpleExpression(name, value, "<=");
    }

    /**
     * 小于。
     * @param name String
     * @param value Object
     * @return SimpleExpression
     */
    public static SimpleExpression lt(String name, Object value) {
        return new SimpleExpression(name, value, "<");
    }

    /**
     * 在。。。范围内。
     * @param name String
     * @param values Object[]
     * @return Criterion
     */
    public static Criterion in(String name, Object[] values) {
        return new In(name, values);
    }

    /**
     * 在。。。范围内。
     * @param name String
     * @param values Collection
     * @return Criterion
     */
    public static Criterion in(String name, Collection<?> values) {
        return new In(name, values.toArray());
    }
    
    
    /**
     * 不在。。。范围内。
     * @param name String
     * @param values Object[]
     * @return Criterion
     */
    public static Criterion notIn(String name, Object[] values) {
        return new NotIn(name, values);
    }

    /**
     * 不在。。。范围内。
     * @param name String
     * @param values Collection
     * @return Criterion
     */
    public static Criterion notIn(String name, Collection<?> values) {
        return new NotIn(name, values.toArray());
    }

    /**
     * 模糊条件。
     *
     * @param name String
     * @param value Object
     * @return SimpleExpression
     */
    public static SimpleExpression like(String name, Object value) {
        return new SimpleExpression(name, value, " like ");
    }

    /**
     * 模糊条件。
     *
     * @param name String
     * @param value Object
     * @return SimpleExpression
     */
    public static SimpleExpression ilike(String name, Object value) {
        return new SimpleExpression(name, value, " ilike ");
    }

    /**
     * 为空值。
     *
     * @param name String
     * @return Criterion
     */
    public static Criterion isNull(String name) {
        return new Null(name);
    }
    /**
     * 不为空值。
     *
     * @param name String
     * @return Criterion
     */
    public static Criterion isNotNull(String name) {
        return new NotNull(name);
    }

    /**
     * sql 表达式。
     *
     * @param sql String
     * @param values Object[]
     * @return Criterion
     */
    public static Criterion sql(String sql, Object[] values) {
        return new SqlCriterion(sql, values);
    }
    /**
     * sql表达式。
     *
     * @param sql String
     * @return Criterion
     */
    public static Criterion sql(String sql) {
    	return new SqlCriterion(sql, null);
    }
    /**
     * 逻辑表达式。
     *
     * @param c Criterion
     * @return Logic
     */
    public static Logic logic(Criterion c) {
        return new Logic(c);
    }
}
