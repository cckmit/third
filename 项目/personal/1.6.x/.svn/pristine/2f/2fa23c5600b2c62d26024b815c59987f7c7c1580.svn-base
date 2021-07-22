/*
 * $Id: NotIn.java 13 2010-11-26 05:04:02Z alex $
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
import org.opoo.util.StringUtils;

/**
 * 
 * @author Alex Lin
 * 
 */
public class NotIn implements Criterion, Serializable {
	private static final long serialVersionUID = -5956495405190584727L;
	private final String qs;
	private final Object[] values;

	/**
	 * ÔÊÐí¿ÕÊý×é¡£
	 * 
	 * @param name
	 *            String
	 * @param values
	 *            Object[]
	 */
	public NotIn(String name, Object[] values) {
		Assert.notNull(name, "criterion name can not be null");
		Assert.notNull(values, "criterion values cannot be null");
		// Assert.notEmpty();
		this.values = values;
		qs = name + " not in (" + StringUtils.fillToString("?", ", ", values.length) + ")";
	}

	public Object[] getValues() {
		return values;
	}

	public String toString() {
		return qs;
	}
}
