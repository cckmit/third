package org.opoo.apps.web.servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.web.jasper.AppsJspServlet;
import org.opoo.apps.web.resource.ResourceManager;
import org.opoo.apps.web.resource.ResourceWrapper;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ResourceLoaderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7126438562365134253L;
       
    private static final Log log = LogFactory.getLog(ResourceLoaderServlet.class);

    private List<String> supportServletPaths = new ArrayList<String>();
//    private String rootPath = "/resources";
//    private String charset = "GBK";
//    private boolean stripComments = true;
    private boolean disableCompression = true;
    private long startUp = 0L;
    private MimetypesFileTypeMap typeMap = new MimetypesFileTypeMap();;
//    private static String SCRIPT_PARAM_PREFIX = "script.";
//    private Map<String,String> aliases = new HashMap<String,String>();
    private boolean serveStaticBrowserCache = true;
    private ResourceManager resourceManager;// = Application.getContext().get("resourceManager", ResourceManager.class);

    
    private AppsJspServlet jspServlet;
	public ResourceLoaderServlet() {
		super();
		if(ClassUtils.isPresent(AppsJspServlet.JSP_SERVLET_CLASSNAME/*"org.apache.jasper.servlet.JspServlet"*/, Thread.currentThread().getContextClassLoader())){
			jspServlet = new AppsJspServlet();
			log.info("'" + AppsJspServlet.JSP_SERVLET_CLASSNAME 
					//+ "org.apache.jasper.servlet.JspServlet" 
					+ "' is present, using jasper to serve JSP.");
		}
	}
    
    
    public ResourceManager getResourceManager(){
    	if(resourceManager == null){
    		resourceManager = Application.getContext().get("resourceManager", ResourceManager.class);
    	}
    	return resourceManager;
    }
    
    
    public void init(ServletConfig config)  throws ServletException
    {
    	if(jspServlet != null){
    		jspServlet.init(config);
    	}
    	
    	
    	
    	supportServletPaths.add("/resources");
    	supportServletPaths.add("/modules");
        
    	
    	startUp = System.currentTimeMillis();
//        String rootPathParam = config.getInitParameter("rootPath");
//        if(rootPathParam != null)
//            rootPath = rootPathParam;
//        String charsetParam = config.getInitParameter("charset");
//        if(charsetParam != null)
//            charset = charsetParam;
//        if(config.getInitParameter("stripComments") != null)
//            stripComments = Boolean.valueOf(config.getInitParameter("stripComments")).booleanValue();
        
        if(config.getInitParameter("disableCompression") != null)
            disableCompression = Boolean.valueOf(config.getInitParameter("disableCompression")).booleanValue();
//        Enumeration e = config.getInitParameterNames();
//        do
//        {
//            if(!e.hasMoreElements())
//                break;
//            String initparam = (String)e.nextElement();
//            if(initparam.indexOf(SCRIPT_PARAM_PREFIX) == 0)
//            {
//                String script = config.getInitParameter(initparam);
//                String alias = initparam.substring(initparam.indexOf(SCRIPT_PARAM_PREFIX) + SCRIPT_PARAM_PREFIX.length());
//                aliases.put(alias, script);
//            }
//        } while(true);
    }

    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
	    String errorText = "不支持POST访问";
	    response.sendError(405, errorText);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
    	String path = request.getPathInfo();
    	if(path == null || path.length() == 0 || request.getRequestURI().indexOf("..") > -1){
    		log.debug("Can not found pathInfo in request.");
    		response.sendError(HttpServletResponse.SC_NOT_FOUND);
    		return;
    	}
    	
    	String servletPath = request.getServletPath();
    	//System.out.println(servletPath);
    	if(!supportServletPaths.contains(servletPath)){
    		log.debug("Unsupported servletPath: " + servletPath);
    		response.sendError(HttpServletResponse.SC_NOT_FOUND);
    		return;
    	}
    	
    	boolean compress = isCompressionEnabled(request);
        if(response.containsHeader("Content-Encoding")){
            compress = false;
        }
    	
    	Resource resource = findResource(path, servletPath, compress);
    	
    	
    	if(resource != null && resource.exists() && resource.isReadable()){
    		if(log.isDebugEnabled()){
    			log.debug("process context for path " + path + " using resource " + resource);
    		}
    		process(resource, path, request, response, compress);
    		return;
    	}
    	
//    	if(is != null){
//    		 process(is, path, request, response);
//    		 return;
//    	}
    	
    	if(log.isDebugEnabled()){
    		log.debug((new StringBuilder()).append("Unable to load resource '").append(path).append("'").toString());
    	}
    	response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}


    protected void process(Resource resource, String path, HttpServletRequest request, HttpServletResponse response,
			boolean compress) throws IOException{
    	Calendar cal = Calendar.getInstance();
        long now = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        long expires = cal.getTimeInMillis();

//        if (ifModifiedSince > 0 && ifModifiedSince <= lastModifiedMillis) {
//            // not modified, content is not sent - only basic
//            // headers and status SC_NOT_MODIFIED
//            response.setDateHeader("Expires", expires);
//            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
//            return;
//        }
        
        //静态文件，可缓存
        if(!AppsGlobals.isDevMode() && !checkIfHeaders(request, response, resource)){
        	return;
        }
        
        
        // set the content-type header
        String contentType = getContentType(path);
        if (contentType != null) {
            response.setContentType(contentType);
        }
        
        if (serveStaticBrowserCache ) {
            // set heading information for caching static content
            response.setDateHeader("Date", now);
            response.setDateHeader("Expires", expires);
            response.setDateHeader("Retry-After", expires);
            response.setHeader("Cache-Control", "public");
            response.setDateHeader("Last-Modified", resource.lastModified());
            response.setHeader("ETag", getETag(resource));
        } else {
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "-1");
        }
        
        response.setDateHeader("startup", startUp);
        
        
        if(!(resource instanceof GzipFileReousource) 
        		&& (contentType.startsWith("text/") || contentType.equals("application/x-javascript"))){
        	if(compress && contentType.indexOf("html") == -1){
        		setCompressionHeaders(response);
        		byte[] bytes = getGzipBytes(resource);
        		response.setContentLength(bytes.length);
        		copy(new ByteArrayInputStream(bytes), response.getOutputStream());
        	}else{
        		InputStream is = resource.getInputStream();
        		copy(resource.getInputStream(), response.getOutputStream());
        		IOUtils.closeQuietly(is);
        	}
        }else{
        	if(resource instanceof GzipFileReousource){
        		setCompressionHeaders(response);
        	}
        	InputStream is = resource.getInputStream();
    		copy(resource.getInputStream(), response.getOutputStream());
    		IOUtils.closeQuietly(is);
        }
	}
    
    
/*
	protected void process(InputStream is, String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (is != null) {
            Calendar cal = Calendar.getInstance();

            // check for if-modified-since, prior to any other headers
            long ifModifiedSince = 0;
            try {
                ifModifiedSince = request.getDateHeader("If-Modified-Since");
            } catch (Exception e) {
                log.warn("Invalid If-Modified-Since header value: '"
                        + request.getHeader("If-Modified-Since") + "', ignoring");
            }
            long lastModifiedMillis = lastModifiedCal.getTimeInMillis();
            long now = cal.getTimeInMillis();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            long expires = cal.getTimeInMillis();

            if (ifModifiedSince > 0 && ifModifiedSince <= lastModifiedMillis) {
                // not modified, content is not sent - only basic
                // headers and status SC_NOT_MODIFIED
                response.setDateHeader("Expires", expires);
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                is.close();
                return;
            }

            // set the content-type header
            String contentType = getContentType(path);
            if (contentType != null) {
                response.setContentType(contentType);
            }

            if (serveStaticBrowserCache ) {
                // set heading information for caching static content
                response.setDateHeader("Date", now);
                response.setDateHeader("Expires", expires);
                response.setDateHeader("Retry-After", expires);
                response.setHeader("Cache-Control", "public");
                response.setDateHeader("Last-Modified", lastModifiedMillis);
                
            } else {
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Expires", "-1");
            }

            try {
                copy(is, response.getOutputStream());
            } finally {
                is.close();
            }
        }
    }
*/    
    
    protected String getContentType(String name) {
    	name = name.toLowerCase();
    	if (name.endsWith(".js")) {
            return "text/javascript";
        } else if (name.endsWith(".css")) {
            return "text/css";
        } else if (name.endsWith(".html")) {
            return "text/html";
        } else if (name.endsWith(".txt")) {
            return "text/plain";
        } else if (name.endsWith(".gif")) {
            return "image/gif";
        } else if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (name.endsWith(".png")) {
            return "image/png";
        }  else if (name.endsWith(".xml")) {
            return "text/xml";
        } else {
            return typeMap.getContentType(name);
        }
    }
    
    /**
     * Copy bytes from the input stream to the output stream.
     *
     * @param input The input stream
     * @param output The output stream
     * @throws IOException If anything goes wrong
     */
    protected void copy(InputStream input, OutputStream output) throws IOException {
        final byte[] buffer = new byte[4096];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        output.flush(); // WW-1526
    }
    
	
	protected Resource findResource(String path, String servletPath, boolean compress){
		ResourceManager resourceManager = getResourceManager();
		if("/resources".equals(servletPath)){
			if(compress){
				Resource resource = resourceManager.getPublicResource(path + ".gzip");
				if(resource != null && resource.exists()){
					return new GzipFileReousource(resource);
				}
			}
			return resourceManager.getPublicResource(path);
		}else if("/modules".equals(servletPath)){
			return resourceManager.getModuleResource(path);
		}
		return null;
	}
	

	
	
	
	
	
	
	
	/*
	protected void doGet2(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        StringBuilder path;
        InputStream in;
        ServletOutputStream out;
        boolean compress;
        path = buildPath(request);
        if(path == null || path.length() == 0 || request.getRequestURI().indexOf("..") > -1)
        {
            response.sendError(404, "Not Found");
            return;
        }
        if(path.charAt(0) != '/')
            path.insert(0, '/');
        in = null;
        out = null;
        compress = isCompressionEnabled(request);
        if(response.containsHeader("Content-Encoding"))
            compress = false;
        LoadedFile bytes = null;
        try
        {
           // bytes = getFileBytesStream(path, request, compress);
            if(bytes == null)
            {
                log.debug((new StringBuilder()).append("Unable to load resource '").append(path).append("'").toString());
                response.sendError(404);
                return;
            }
        }
        finally
        {
            try
            {
                if(in != null)
                    in.close();
            }
            catch(Exception ignored)
            {
                log.debug((new StringBuilder()).append("ignored exception ").append(ignored.getMessage()).toString(), ignored);
            }
            try
            {
                if(out != null)
                    out.close();
            }
            catch(Exception ignored)
            {
                log.debug((new StringBuilder()).append("ignored exception ").append(ignored.getMessage()).toString(), ignored);
            }
        }
        if(!AppsGlobals.isDevMode() && !ServletUtils.isModified(request, bytes.fileData, startUp))
        {
            response.setStatus(304);
            return;
        }
        try
        {
            String fileName = getFileName(path.toString());
            System.out.println(fileName);
            String contentType = path.toString().indexOf("|") != -1 ? "text/javascript" : typeMap.getContentType(fileName);
            System.out.println(contentType);
            if(contentType.equalsIgnoreCase("application/x-javascript"))
                contentType = "text/javascript";
            if(fileName.endsWith(".css"))
                contentType = "text/css";
            System.out.println(contentType);
            response.setContentType(contentType);
            if(!bytes.isGzipped && (contentType.startsWith("text/") || contentType.equals("application/x-javascript")))
            {
                if(!compress || contentType.indexOf("html") >= 0)
                {
                    response.getOutputStream().write(bytes.fileData);
                } else
                {
                    setCompressionHeaders(response);
                    response.setContentLength(gzipContent(response.getOutputStream(), bytes.fileData));
                }
            } else
            {
                if(bytes.isGzipped)
                    setCompressionHeaders(response);
                in = new ByteArrayInputStream(bytes.fileData);
                response.setContentLength(bytes.fileData.length);
                out = response.getOutputStream();
                IOUtils.copy(in, out);
                out.flush();
            }
        }
        catch(IOException e)
        {
            log.error(e);
        }
    }

    private StringBuilder buildPath(HttpServletRequest request)
    {
        String uri = request.getRequestURI();
        StringBuilder path;
        if("/resources".equalsIgnoreCase(request.getServletPath()))
        {
            String resources = (new StringBuilder()).append(request.getContextPath()).append("/resources").toString();
            path = new StringBuilder(StringUtils.replace(URLDecoder.decode(uri), resources, ""));
        } else
        {
            String modules = (new StringBuilder()).append(request.getContextPath()).append("/modules").toString();
            path = new StringBuilder(StringUtils.replace(URLDecoder.decode(uri), modules, ""));
        }
        System.out.println(uri + ":::" + path + " >>> pathinfo-> " + request.getPathInfo());
        return path;
    }
*/
    private void setCompressionHeaders(HttpServletResponse response)
    {
        response.setHeader("Content-Encoding", "gzip");
        response.setHeader("Vary", "Accept-Encoding");
    }
/*
    private int gzipContent(OutputStream out, byte bytes[])
        throws IOException
    {
        ByteArrayOutputStream baos = null;
        GZIPOutputStream gzos = null;
        int length = 0;
        try
        {
            baos = new ByteArrayOutputStream();
            gzos = new GZIPOutputStream(baos);
            gzos.write(bytes);
            gzos.finish();
            gzos.flush();
            gzos.close();
            byte gzippedBytes[] = baos.toByteArray();
            length = gzippedBytes.length;
            IOUtils.copy(new ByteArrayInputStream(gzippedBytes), out);
            out.flush();
        }
        finally
        {
            try
            {
                if(gzos != null)
                    gzos.close();
            }
            catch(Exception ignored) { }
            try
            {
                if(baos != null)
                    baos.close();
            }
            catch(Exception ignored) { }
        }
        return length;
    }
    */
    
    /**
     * 
     * @param Resource
     * @return
     * @throws IOException
     */
    private byte[] getGzipBytes(Resource Resource) throws IOException {
		ByteArrayOutputStream baos = null;
		GZIPOutputStream gzos = null;
		InputStream is = null;
		try {
			is = Resource.getInputStream();
			baos = new ByteArrayOutputStream();
			gzos = new GZIPOutputStream(baos);
			copy(is, gzos);
			gzos.finish();
			gzos.close();

			return baos.toByteArray();
		} finally {
			IOUtils.closeQuietly(gzos);
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(is);
		}
	}

    private boolean isCompressionEnabled(HttpServletRequest request)
    {
        boolean compress = false;
        if(!disableCompression)
        {
            if(request.getAttribute("apps.compressionFilter.applied") != null)
                compress = false;
            else
            if(request.getHeader("accept-encoding") != null && request.getHeader("accept-encoding").indexOf("gzip") != -1)
                compress = true;
            else
            if(request.getHeader("---------------") != null)
                compress = true;
            if(isLesserIE(request))
                compress = false;
        }
        return compress;
    }

    private boolean isLesserIE(HttpServletRequest request)
    {
        String userAgent = request.getHeader("User-Agent");
        if(userAgent == null || "".equals(userAgent))
            return false;
        userAgent = userAgent.toLowerCase();
        if(userAgent.indexOf("msie") == -1)
            return false;
        if(userAgent.indexOf("msie") != -1 && userAgent.indexOf("opera") == -1 && userAgent.indexOf("windows") != -1)
        {
            int verStart = userAgent.indexOf("msie ");
            int verEnd = userAgent.indexOf(";", verStart);
            try
            {
                String rv = userAgent.substring(verStart + 5, verEnd);
                if(Character.isLetter(rv.charAt(rv.length() - 1)))
                    rv = rv.substring(0, rv.length() - 1);
                double ver = Double.parseDouble(rv);
                return ver < 7D;
            }
            catch(Exception e)
            {
                log.debug((new StringBuilder()).append("Exception parsing UserAgent: ").append(userAgent).toString(), e);
            }
            return true;
        } else
        {
            return false;
        }
    }

    /*
    private String[] parseAliases(StringBuilder url)
    {
        url.delete(0, "/merge".length());
        List<String> files = new ArrayList<String>();
        String tokens[] = url.toString().split("\\|");
        addPaths(files, tokens);
        return files.toArray(new String[files.size()]);
    }

    private void addPaths(List<String> files, String paths[])
    {
        String arr$[] = paths;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String path = arr$[i$];
            if("".equals(path))
                continue;
            String alias = (String)aliases.get(path);
            if(alias != null)
                addPaths(files, alias.split("\\|"));
            else
                files.add(path);
        }

    }
    */
    
    
/*
    private LoadedFile getFileBytesStream(StringBuilder allPaths, HttpServletRequest request, boolean compress)
        throws IOException
    {
        LoadedFile bytes = new LoadedFile();
        String paths[];
        if(allPaths.indexOf("/merge") == 0)
            paths = parseAliases(allPaths);
        else
            paths = (new String[] {
                allPaths.toString()
            });
        if(paths == null || paths.length == 0)
            return bytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ResourceManager resourceManager = new ResourceManagerImpl();//(ResourceManager)JiveApplication.getContext().getSpringBean("resourceManager");
        String arr$[] = paths;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String path = arr$[i$];
            System.out.println("LASLKASLKALS--> " + path);
            InputStream inputStream;
            if(resourceManager != null && request.getServletPath() != null && "/modules".equals(request.getServletPath()))
                inputStream = resourceManager.loadModuleResource(path);
            else
            if(resourceManager != null && resourceManager.contains(path))
            {
                if(compress && resourceManager.contains((new StringBuilder()).append(path).append(".gzip").toString()))
                {
                    path = (new StringBuilder()).append(path).append(".gzip").toString();
                    bytes.isGzipped = true;
                }
                inputStream = new FileInputStream(resourceManager.get(path));
            } else
            {
                path = (new StringBuilder()).append(rootPath).append(path).toString();
                if(path.charAt(0) == '/')
                    path = path.substring(1);
                inputStream = loadResource(path);//ClassUtilsStatic.getResourceAsStream(path);
            }
            try
            {
                readInputStream(inputStream, baos, paths.length > 1);
            }
            finally
            {
                try
                {
                    if(inputStream != null)
                        inputStream.close();
                }
                catch(IOException e)
                {
                    log.error(e);
                }
            }
        }

        bytes.fileData = baos.toByteArray();
        return bytes;
    }

    private void readInputStream(InputStream inputStream, OutputStream output, boolean addLineBreak)
        throws IOException
    {
        if(inputStream != null)
        {
            IOUtils.copy(inputStream, output);
            if(addLineBreak)
                output.write(10);
        }
    }

    private static String getFileName(String path)
    {
        int i = path.lastIndexOf('/');
        if(i < 0)
            return path;
        else
            return path.substring(i + 1);
    }

    
    private InputStream loadResource(String name)
    {
        InputStream in = getClass().getResourceAsStream(name);
        if(in == null)
        {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
            if(in == null)
                in = getClass().getClassLoader().getResourceAsStream(name);
        }
        if(in == null)
            try
            {
                ModuleManager mm = Application.getContext().getModuleManager();
                if(mm != null && mm.isInitialized())
                {
                	Collection<Module<?>> modules = mm.getModules();
                	for(Module<?> m : modules){
                		ModuleClassLoader loader = mm.getModuleClassloader(m);
                		in = loader.getClassLoader().getResourceAsStream(name);
                		if(in != null){
                			break;
                		}
                	}
                }
            }
            catch(Throwable e)
            {
                log.warn((new StringBuilder()).append("Attempt to Load the resource ").append(name).append(" from module classloaders has failed, ").append("perhaps the application has not been initialized.").toString());
            }
        return in;
    }
    */
    
    
    
    protected boolean checkIfHeaders(HttpServletRequest request, HttpServletResponse response, Resource resource)
			throws IOException {

		return checkIfMatch(request, response, resource) 
				&& checkIfModifiedSince(request, response, resource)
				&& checkIfNoneMatch(request, response, resource) 
				&& checkIfUnmodifiedSince(request, response, resource);
	}
	    /**
	     * Check if the if-match condition is satisfied.
	     *
	     * @param request The servlet request we are processing
	     * @param response The servlet response we are creating
	     * @param resource File object
	     * @return boolean true if the resource meets the specified condition,
	     * and false if the condition is not satisfied, in which case request
	     * processing is stopped
	     */
	    protected boolean checkIfMatch(HttpServletRequest request,
	                                 HttpServletResponse response,
	                                 Resource resource)
	        throws IOException {

	        String eTag = getETag(resource);
	        String headerValue = request.getHeader("If-Match");
	        if (headerValue != null) {
	            if (headerValue.indexOf('*') == -1) {

	                StringTokenizer commaTokenizer = new StringTokenizer
	                    (headerValue, ",");
	                boolean conditionSatisfied = false;

	                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
	                    String currentToken = commaTokenizer.nextToken();
	                    if (currentToken.trim().equals(eTag))
	                        conditionSatisfied = true;
	                }

	                // If none of the given ETags match, 412 Precodition failed is
	                // sent back
	                if (!conditionSatisfied) {
	                    response.sendError
	                        (HttpServletResponse.SC_PRECONDITION_FAILED);
	                    return false;
	                }

	            }
	        }
	        return true;

	    }


	    /**
	 * Check if the if-modified-since condition is satisfied.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param resource
	 *            File object
	 * @return boolean true if the resource meets the specified condition, and
	 *         false if the condition is not satisfied, in which case request
	 *         processing is stopped
	 */
	protected boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, Resource resource)
			throws IOException {
		try {
			long headerValue = request.getDateHeader("If-Modified-Since");
			long lastModified = resource.lastModified();
			if (headerValue != -1) {

				// If an If-None-Match header has been specified, if modified
				// since
				// is ignored.
				if ((request.getHeader("If-None-Match") == null) && (lastModified < headerValue + 1000)) {
					// The entity has not been modified since the date
					// specified by the client. This is not an error case.
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					response.setHeader("ETag", getETag(resource));

					return false;
				}
			}
		} catch (IllegalArgumentException illegalArgument) {
			return true;
		}
		return true;

	}

	/**
	 * Check if the if-none-match condition is satisfied.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param
	 * @return boolean true if the resource meets the specified condition, and
	 *         false if the condition is not satisfied, in which case request
	 *         processing is stopped
	 */
	protected boolean checkIfNoneMatch(HttpServletRequest request, HttpServletResponse response, Resource resource)
			throws IOException {

		String eTag = getETag(resource);
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {

			boolean conditionSatisfied = false;

			if (!headerValue.equals("*")) {

				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(eTag))
						conditionSatisfied = true;
				}

			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {

				// For GET and HEAD, we should respond with
				// 304 Not Modified.
				// For every other method, 412 Precondition Failed is sent
				// back.
				if (("GET".equals(request.getMethod())) || ("HEAD".equals(request.getMethod()))) {
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					response.setHeader("ETag", getETag(resource));

					return false;
				} else {
					response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
					return false;
				}
			}
		}
		return true;

	}

	/**
	 * Check if the if-unmodified-since condition is satisfied.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @return boolean true if the resource meets the specified condition, and
	 *         false if the condition is not satisfied, in which case request
	 *         processing is stopped
	 */
	protected boolean checkIfUnmodifiedSince(HttpServletRequest request, HttpServletResponse response, Resource resource)
			throws IOException {
		try {
			long lastModified = resource.lastModified();
			long headerValue = request.getDateHeader("If-Unmodified-Since");
			if (headerValue != -1) {
				if (lastModified >= (headerValue + 1000)) {
					// The entity has not been modified since the date
					// specified by the client. This is not an error case.
					response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
					return false;
				}
			}
		} catch (IllegalArgumentException illegalArgument) {
			return true;
		}
		return true;
	}
	

	protected String getETag(Resource resource) throws IOException {
		return ServletUtils.getEtag(resource.getFile().length());
	}

	private static class GzipFileReousource extends ResourceWrapper implements Resource {
		private GzipFileReousource(Resource resource) {
			super(resource);
		}
	}
	    
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doHead(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}   
	
	
	


	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		String pathInfo = (String) request.getAttribute("javax.servlet.include.path_info");
		if(path != null || pathInfo != null){
			path = path != null ? path.toLowerCase() : pathInfo.toLowerCase();
			log.debug("Lower case path: " + path);
			if(path.endsWith(".jsp") || path.endsWith(".jspx")){
				if(jspServlet != null){
					//log.debug(">>>>: " + Thread.currentThread().getContextClassLoader());
					jspServlet.service(request, response);
				}else{
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
				return;
			}
		}
		
		super.service(request, response);
	}


	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		super.destroy();
		if(jspServlet != null){
			jspServlet.destroy();
		}
	}


	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		if(jspServlet != null){
			jspServlet.init();
		}
	}


    public static void main(String[] args){
    	MimetypesFileTypeMap map = new MimetypesFileTypeMap();
    	System.out.println(map.getContentType("C:\\um\\module.xml"));
    }

}
