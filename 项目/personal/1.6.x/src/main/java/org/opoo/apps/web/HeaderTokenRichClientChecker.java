package org.opoo.apps.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opoo.util.Assert;


/**
 * 基于 HTTP 请求头的某指定名称的头来检查 富客户端的情况。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class HeaderTokenRichClientChecker extends RichClientCheckerAdaptor {
	private String headerName;
	private String headerValue;
	
	/**
	 * 通过头信息检查是否是富客户端。
	 */
	@Override
	public boolean isRichClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Assert.notNull(headerName, "必须指定 HeaderTokenRichClientChecker 的 headerName.");
		String header = request.getHeader(headerName);
		
		if(log.isDebugEnabled()){
			log.debug("Check rich client header [" + headerName + "] : " + header);
		}
		
		if(headerValue != null){
			return headerValue.equals(header);
		}else{
			return header != null;
		}
	}

	/**
	 * 通过响应头和状态返回将特定的信息返回给富客户端。
	 * 这个方法执行前应该执行isRichClient来检查客户端类型，
	 * 应该只在富客户端的情况下才调用这个方法。
	 */
	@Override
	public void sendToClient(HttpServletRequest request, HttpServletResponse response, int status, String location)
			throws IOException {
		super.sendToClient(request, response, status, location);
	}

	/**
	 * @return the headerName
	 */
	public String getHeaderName() {
		return headerName;
	}

	/**
	 * @param headerName the headerName to set
	 */
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	/**
	 * @return the headerValue
	 */
	public String getHeaderValue() {
		return headerValue;
	}

	/**
	 * @param headerValue the headerValue to set
	 */
	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}
}
