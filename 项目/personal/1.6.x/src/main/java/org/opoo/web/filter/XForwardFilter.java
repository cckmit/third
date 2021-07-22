/*
 * $Id: XForwardFilter.java 13 2010-11-26 05:04:02Z alex $
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 使用代理或者反向代理，无法直接获得IP时，使用这个过滤器。
 *
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public class XForwardFilter implements Filter
{
  public XForwardFilter()
  {
  }

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
    class XForwardHttpServletRequest extends HttpServletRequestWrapper
    {
      private XForwardHttpServletRequest(HttpServletRequest request)
      {
        super(request);
      }

      public String getRemoteAddr()
      {
        return super.getHeader("X-Forward-For");
      }
    }
    filterChain.doFilter(new XForwardHttpServletRequest((HttpServletRequest)servletRequest), servletResponse);
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
