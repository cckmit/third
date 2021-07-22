/*
 * $Id: QueryService.java 5413 2012-03-07 04:00:51Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.ws.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 通用查询 REST 服务。
 * @author Alex Lin(lcql@msn.com)
 */
public interface QueryService {

	@GET
	@Path("/{queryKey}")
	@Produces(MediaType.APPLICATION_JSON)
	Response execute(@PathParam("queryKey") String queryKey, @Context UriInfo info);
}
