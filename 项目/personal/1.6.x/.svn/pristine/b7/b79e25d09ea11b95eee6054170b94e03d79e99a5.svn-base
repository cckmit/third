package org.opoo.apps.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RichClientCheckerAdaptor implements RichClientChecker{
	protected final Log log = LogFactory.getLog(getClass());
	
	
	public boolean isRichClient(HttpServletRequest request, HttpServletResponse response) throws IOException{
		return false;
	}

	/**
	 * 给富客户端响应特殊的信息。
	 * 富客户端一般响应状态嘛即可。
	 */
	public void sendToClient(HttpServletRequest request, HttpServletResponse response, int status, String location) throws IOException {
		if(location != null){
			response.setHeader("location", location);
		}
		response.sendError(status);
	}
	
}
