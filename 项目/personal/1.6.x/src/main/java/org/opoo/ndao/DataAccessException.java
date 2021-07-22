/*
 * DataAccessException.java
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
package org.opoo.ndao;


/**
 * 数据访问异常。
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public class DataAccessException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3059425649998185940L;
	/**
     *
     * @param msg String
     */
    public DataAccessException(String msg) {
	super(msg);
    }
    /**
     *
     * @param e Exception
     */
    public DataAccessException(Exception e) {
	super(e);
    }
    /**
     *
     * @param msg String
     * @param ex Throwable
     */
    public DataAccessException(String msg, Throwable ex) {
	super(msg, ex);
    }
}
