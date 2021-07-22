/*
 * $Id: SqlCriterion.java 13 2010-11-26 05:04:02Z alex $
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

import java.io.Serializable;

import org.opoo.util.Assert;

/**
 * Sql表達式組成的查詢條件。
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public class SqlCriterion implements Criterion, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5805949118121932855L;
	private final String sql;
    private final Object[] values;
    public SqlCriterion(String sql, Object[] values) {
	Assert.notNull(sql);
        this.sql = sql;
        this.values = values;
	if(values != null){
            if(values.length != 0){
            }else{
		values = null;
	    }
	}
    }




    public Object[] getValues() {
        return values;
    }

    public String toString() {
        return sql;
    }
}
