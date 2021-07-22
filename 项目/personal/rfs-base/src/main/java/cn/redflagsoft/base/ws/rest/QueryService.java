/*
 * $Id: QueryService.java 5413 2012-03-07 04:00:51Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * ͨ�ò�ѯ REST ����
 * @author Alex Lin(lcql@msn.com)
 */
public interface QueryService {

	@GET
	@Path("/{queryKey}")
	@Produces(MediaType.APPLICATION_JSON)
	Response execute(@PathParam("queryKey") String queryKey, @Context UriInfo info);
}
