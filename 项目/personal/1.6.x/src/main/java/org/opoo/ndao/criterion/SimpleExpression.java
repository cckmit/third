/*
 * $Id: SimpleExpression.java 13 2010-11-26 05:04:02Z alex $
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
 * º††Î±íß_Ê½¡£
 *
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public class SimpleExpression implements Criterion , Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5625058334963030227L;
	private String qs;
    private Object value;
    public SimpleExpression(String name, Object value, String op) {
	Assert.notNull(name, "criterion name can not be null");
	Assert.notNull(value, "criterion value can not be null");
        qs = name + op + "?";
        this.value = value;
    }



    public Object[] getValues() {
        return new Object[] {value};
    }


    public String toString() {
        return qs;
    }

}
