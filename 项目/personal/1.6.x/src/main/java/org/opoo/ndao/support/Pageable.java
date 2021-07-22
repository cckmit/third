/*
 * $Id: Pageable.java 13 2010-11-26 05:04:02Z alex $
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
package org.opoo.ndao.support;

/**
 * 可分页的。
 *
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public interface Pageable {
	/**
	 * 总对象数。
	 * @return
	 */
    int getItemCount();
    /**
     * 页大小。
     * @return
     */
    int getPageSize();
    
    /**
     * 开始索引号。
     * @return
     */
    int getStartIndex();
}
