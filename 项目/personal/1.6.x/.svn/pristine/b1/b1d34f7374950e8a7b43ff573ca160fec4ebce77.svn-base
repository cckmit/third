package org.opoo.apps.web.servlet;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opoo.apps.io.ContentRange;
import org.opoo.apps.io.LimitedBandwidthInputStream;
import org.opoo.apps.io.RandomAccessFileBufferedInputStream;
import org.opoo.apps.io.SeekableInputStream;

public class RangeDownloadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303139058236039308L;
	
	private long bandwidthLimit = 0;//2 * 1024 * 1024L;
    private long firstBurst = 5000000L;
	
	private File getFileHome(){
		return new File("F:/Downloads");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		System.out.println("---------------------");
		System.out.println(pathInfo);
        System.out.println(req);
        Enumeration<?> names = req.getHeaderNames();
        while(names.hasMoreElements()){
        	String name = (String) names.nextElement();
        	System.out.println(name + " : " + req.getHeader(name));
        }
		
        if(pathInfo == null || "/".equals(pathInfo)){
        	resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        	return;
        }
        
        File file = new File(getFileHome(), pathInfo);
        if (!file.exists() || !file.canRead() || !file.isFile()) {
            resp.sendError(404);
            return;
        }
        long len = file.length();

        resp.setDateHeader("Expires", 0);
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-store,private,no-cache");
        resp.setHeader("Accept-Ranges", "bytes");
        
        String range = req.getHeader("Range");
        SeekableInputStream baseIn = new RandomAccessFileBufferedInputStream(file);
        InputStream is = baseIn;
        try {
            if (range == null) {
                resp.setHeader("Content-Length", String.valueOf(len));
            } else {
                resp.setStatus(206);
                ContentRange r = ContentRange.parseRange(range, len);
                resp.setHeader("Content-Range", r.toHeaderValue());
                resp.setHeader("Content-Length", String.valueOf(r.getContentLength()));
                is = r.limitedInputStream(baseIn);
            }
            copy(limitBandwidth(is), resp.getOutputStream());
        } finally {
            closeQuietly(is);
        }
	}

	/**
	 * ÏÞÖÆ´ø¿í
	 * @param in
	 * @return
	 */
	private InputStream limitBandwidth(InputStream in) {
        //long bandwidthLimit = config.getBandwidthLimit();
        if (bandwidthLimit > 0) {
            return new LimitedBandwidthInputStream(in, bandwidthLimit, firstBurst /*config.getFirstBurst()*/);
        }
        return in;
    }
}
