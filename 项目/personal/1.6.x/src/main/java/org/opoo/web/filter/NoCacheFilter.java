/*
 * $Id: NoCacheFilter.java 13 2010-11-26 05:04:02Z alex $
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
package org.opoo.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public class NoCacheFilter extends HttpServlet implements Filter
{

  /**
	 * 
	 */
	private static final long serialVersionUID = -3626690949429145037L;

/**
   * destroy
   *
   */
  public void destroy()
  {
  }

  /**
   * doFilter
   *
   * @param servletRequest ServletRequest
   * @param servletResponse ServletResponse
   * @param filterChain FilterChain
   * @throws IOException
   * @throws ServletException
   */
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse, FilterChain filterChain) throws
      IOException, ServletException
  {
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Expires", "0"); //½ûÖ¹»º´æ

    filterChain.doFilter(servletRequest, servletResponse);
  }

  /**
   * init
   *
   * @param filterConfig FilterConfig
   * @throws ServletException
   */
  public void init(FilterConfig filterConfig) throws ServletException
  {
  }
}
