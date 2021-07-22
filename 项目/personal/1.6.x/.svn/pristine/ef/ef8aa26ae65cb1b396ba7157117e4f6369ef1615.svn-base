package org.opoo.apps.web.struts2.plugin.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;
import org.opoo.apps.util.SerializableUtils;
import org.opoo.apps.web.struts2.AbstractAppsAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.profiling.UtilTimerStack;

public class XmlResult implements Result {
	private static final long serialVersionUID = -5652838197457793391L;
	private static final Log log = LogFactory.getLog(XmlResult.class);
//	private static final JavaXMLFactory factory = JavaXMLFactory.getInstance();
	private String defaultEncoding = "GBK";
	private boolean enableGZIP = false;

	public XmlResult() {
	}

	public boolean isEnableGZIP() {
		return enableGZIP;
	}

	public void setEnableGZIP(boolean enableGZIP) {
		this.enableGZIP = enableGZIP;
	}
	/**
	 * 
	 */
	public void execute(ActionInvocation invocation) throws Exception {
		String profileKey = "execute XmlResult: ";
		
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext
				.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) actionContext
				.get(StrutsStatics.HTTP_RESPONSE);
		try {
			UtilTimerStack.push(profileKey);
			
			Object action = invocation.getAction();
			if (action instanceof AbstractAppsAction) {
				//String xml = factory.getJavaXML().toXML(((AbstractAppsAction) action).getModel());
				String xml = SerializableUtils.toXML(((AbstractAppsAction) action).getModel());
				boolean isGzip = enableGZIP && isGzipInRequest(request);
				writeXmlToResponse(response, defaultEncoding, xml, isGzip);
			}
		} catch (IOException ex) {
			log.error(ex);
			throw ex;
		}finally{
			UtilTimerStack.pop(profileKey);
		}
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	public static boolean isGzipInRequest(HttpServletRequest request) {
		String header = request.getHeader("Accept-Encoding");
		return header != null && header.indexOf("gzip") >= 0;
	}

	public static void writeXmlToResponse(HttpServletResponse response,
			String encoding, String serializedXml, boolean gzip)
			throws IOException {
		String xml = serializedXml == null ? "" : serializedXml;
		if (log.isDebugEnabled()) {
			log.debug("[XML]" + xml);
		}

		response.setContentType("text/xml;charset=" + encoding);
		if (gzip) {
			response.addHeader("Content-Encoding", "gzip");
			GZIPOutputStream out = null;
			InputStream in = null;
			try {
				out = new GZIPOutputStream(response.getOutputStream());
				in = new ByteArrayInputStream(xml.getBytes());
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} finally {
				if (in != null)
					in.close();
				if (out != null) {
					out.finish();
					out.close();
				}
			}
		} else {
			response.setContentLength(xml.getBytes(encoding).length);
			PrintWriter out = response.getWriter();
			out.print(xml);
		}
	}
}
