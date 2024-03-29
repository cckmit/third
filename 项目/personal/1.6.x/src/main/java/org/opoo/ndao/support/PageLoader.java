/*
 * $Id: PageLoader.java 13 2010-11-26 05:04:02Z alex $
 *
 * Copyright 2006-2008 Alex Lin. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.048 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.048
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opoo.ndao.support;

import java.util.List;

/**
 * 也加载器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public interface PageLoader<T> {
    /**
     * 根据查询条件查询结果集合。
     * @param f
     * @return
     */
	List<T> find(ResultFilter f);
	
	/**
	 * 根据查询条件查询结果数量。
	 * @param f
	 * @return
	 */
    int getCount(ResultFilter f);
}
