package org.opoo.apps.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.UserHolder;


/**
 * 
 * ÏÂÔØ¡£
 * downloadServlet/file_id/fileName
 *
 */
public class DownloadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7003132260994878026L;
	private long startUp = 0;
	


	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		startUp = System.currentTimeMillis();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check auth
		UserHolder.getUser();
		
		
		String pathInfo = request.getPathInfo();
		if("true".equalsIgnoreCase(request.getParameter("preview"))){
			// servlet/990129-400-300/09.png
			int secondSep = pathInfo.indexOf('/', 1);
			if(secondSep > 1){
				String sid = pathInfo.substring(1, secondSep);
				
				//int tmp = pathInfo.indexOf('/', secondSep + 1);
				//if(tmp == -1){
				//	tmp = pathInfo.length();
				//}
				//String params = pathInfo.substring(secondSep + 1, tmp);
				
				StringTokenizer st = new StringTokenizer(sid, "-");
				long id = Long.parseLong(st.nextToken());
				int width = Integer.parseInt(st.nextToken());
				int height = Integer.parseInt(st.nextToken());
				
				//InputStream inputStream = Application.getContext().getAttachmentManager().fetch(id);
			}
		}
	}
}
