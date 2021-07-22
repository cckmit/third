package org.opoo.apps.web.struts2.plugin.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.StrutsStatics;
import org.opoo.apps.Model;
import org.opoo.apps.json.JSONUtils;
import org.opoo.apps.util.SerializableUtils;
import org.opoo.apps.web.struts2.AbstractAppsAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.profiling.UtilTimerStack;

/**
 * <!-- START SNIPPET: description --> <p/> This result serializes an action
 * into JSON. <p/> <!-- END SNIPPET: description --> <p/> <p/> <u>Result
 * parameters:</u> <p/> <!-- START SNIPPET: parameters --> <p/>
 * <ul>
 * <p/>
 * <li>excludeProperties - list of regular expressions matching the properties
 * to be excluded. The regular expressions are evaluated against the OGNL
 * expression representation of the properties. </li>
 * <p/>
 * </ul>
 * <p/> <!-- END SNIPPET: parameters --> <p/> <b>Example:</b> <p/>
 * <p/>
 * <pre>
 * &lt;!-- START SNIPPET: example --&gt;
 * &lt;result name=&quot;success&quot; type=&quot;json&quot; /&gt;
 * &lt;!-- END SNIPPET: example --&gt;
 * </pre>
 */
public class JSONResult implements Result {
	private static final long serialVersionUID = 8380350515123354761L;

	private static final String DEFAULT_CONTENT_TYPE = "application/json";

    private static final Log LOG = LogFactory.getLog(JSONResult.class);

    private String defaultEncoding = "UTF-8";
    private List<Pattern> includeProperties;
    private List<Pattern> excludeProperties;
    private String root;
    private boolean wrapWithComments;
    private boolean prefix;
    private boolean enableGZIP = false;
    private boolean noCache = false;
    private int statusCode;
    private int errorCode;
    private String callbackParameter;
    private String contentType = DEFAULT_CONTENT_TYPE;
    private String wrapPrefix;
    private String wrapSuffix;

    @Inject(StrutsConstants.STRUTS_I18N_ENCODING)
    public void setDefaultEncoding(String val) {
        this.defaultEncoding = val;
    }

    /**
     * Gets a list of regular expressions of properties to exclude from the JSON
     * output.
     *
     * @return A list of compiled regular expression patterns
     */
    public List<Pattern> getExcludePropertiesList() {
        return this.excludeProperties;
    }

    /**
     * Sets a comma-delimited list of regular expressions to match properties
     * that should be excluded from the JSON output.
     *
     * @param commaDelim A comma-delimited list of regular expressions
     */
    public void setExcludeProperties(String commaDelim) {
    }

    /**
     * Sets a comma-delimited list of wildcard expressions to match properties
     * that should be excluded from the JSON output.
     * 
     * @param commaDelim A comma-delimited list of wildcard patterns
     */
    public void setExcludeWildcards(String commaDelim) {
    }

    /**
     * @return the includeProperties
     */
    public List<Pattern> getIncludePropertiesList() {
        return includeProperties;
    }

    /**
     * Sets a comma-delimited list of regular expressions to match properties
     * that should be included in the JSON output.
     *
     * @param commaDelim A comma-delimited list of regular expressions
     */
    public void setIncludeProperties(String commaDelim) {
    }

    /**
     * Sets a comma-delimited list of wildcard expressions to match properties
     * that should be included in the JSON output.
     *
     * @param commaDelim A comma-delimited list of wildcard patterns
     */
    public void setIncludeWildcards(String commaDelim) {
    }

    public void execute(ActionInvocation invocation) throws Exception {
    	String profileKey = "execute JSONResult: ";
    	
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);

        try {
        	UtilTimerStack.push(profileKey);
        	
        	Object action = invocation.getAction();
			if (action instanceof AbstractAppsAction) {
				Model rootObject = ((AbstractAppsAction) action).getModel();
				JSONUtils.toJSONList(rootObject.getRows());
				writeToResponse(response, createJSONString(request, rootObject), enableGzip(request));
			}
        	
//            Object rootObject;
//            rootObject = readRootObject(invocation);
//            writeToResponse(response, createJSONString(request, rootObject), enableGzip(request));
        } catch (IOException exception) {
            LOG.error(exception.getMessage(), exception);
            throw exception;
        }finally{
			UtilTimerStack.pop(profileKey);
		}
    }

    private String createJSONString(HttpServletRequest request, Object rootObject) throws Exception {
        String json = SerializableUtils.toJSON(rootObject);
//        json = JSONUtil.serialize(rootObject, excludeProperties, includeProperties, ignoreHierarchy,
//                enumAsBean, excludeNullProperties);
        json = addCallbackIfApplicable(request, json);
        /*
        if(LOG.isDebugEnabled()){
        	LOG.debug("JSONResult: " + json);
        }*/
        return json;
    }

    private boolean enableGzip(HttpServletRequest request) {
        return enableGZIP && JSONUtils.isGzipInRequest(request);
    }

    protected void writeToResponse(HttpServletResponse response, String json, boolean gzip) throws IOException {
//        JSONUtil.writeJSONToResponse(new SerializationParams(response, getEncoding(), isWrapWithComments(),
//                json, false, gzip, noCache, statusCode, errorCode, prefix, contentType, wrapPrefix,
//                wrapSuffix));
    	writeJSONToResponse(response, getEncoding(), isWrapWithComments(),
    			json, false, gzip, noCache, statusCode, errorCode, prefix, contentType, wrapPrefix,
              wrapSuffix);
    }


    /**
     * Retrieve the encoding <p/>
     *
     * @return The encoding associated with this template (defaults to the value
     *         of 'struts.i18n.encoding' property)
     */
    protected String getEncoding() {
        String encoding = this.defaultEncoding;

        if (encoding == null) {
            encoding = System.getProperty("file.encoding");
        }

        if (encoding == null) {
            encoding = "UTF-8";
        }

        return encoding;
    }

    protected String addCallbackIfApplicable(HttpServletRequest request, String json) {
        if ((callbackParameter != null) && (callbackParameter.length() > 0)) {
            String callbackName = request.getParameter(callbackParameter);
            if ((callbackName != null) && (callbackName.length() > 0))
                json = callbackName + "(" + json + ")";
        }
        return json;
    }

    /**
     * @return OGNL expression of root object to be serialized
     */
    public String getRoot() {
        return this.root;
    }

    /**
     * Sets the root object to be serialized, defaults to the Action
     *
     * @param root OGNL expression of root object to be serialized
     */
    public void setRoot(String root) {
        this.root = root;
    }

    /**
     * @return Generated JSON must be enclosed in comments
     */
    public boolean isWrapWithComments() {
        return this.wrapWithComments;
    }

    /**
     * Wrap generated JSON with comments
     *
     * @param wrapWithComments
     */
    public void setWrapWithComments(boolean wrapWithComments) {
        this.wrapWithComments = wrapWithComments;
    }





    public boolean isEnableGZIP() {
        return enableGZIP;
    }

    public void setEnableGZIP(boolean enableGZIP) {
        this.enableGZIP = enableGZIP;
    }

    public boolean isNoCache() {
        return noCache;
    }

    /**
     * Add headers to response to prevent the browser from caching the response
     *
     * @param noCache
     */
    public void setNoCache(boolean noCache) {
        this.noCache = noCache;
    }


    /**
     * Status code to be set in the response
     *
     * @param statusCode
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Error code to be set in the response
     *
     * @param errorCode
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setCallbackParameter(String callbackParameter) {
        this.callbackParameter = callbackParameter;
    }

    public String getCallbackParameter() {
        return callbackParameter;
    }

    /**
     * Prefix JSON with "{} &&"
     *
     * @param prefix
     */
    public void setPrefix(boolean prefix) {
        this.prefix = prefix;
    }

    /**
     * Content type to be set in the response
     *
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getWrapPrefix() {
        return wrapPrefix;
    }

    /**
     * Text to be inserted at the begining of the response
     */
    public void setWrapPrefix(String wrapPrefix) {
        this.wrapPrefix = wrapPrefix;
    }

    public String getWrapSuffix() {
        return wrapSuffix;
    }

    /**
     * Text to be inserted at the end of the response
     */
    public void setWrapSuffix(String wrapSuffix) {
        this.wrapSuffix = wrapSuffix;
    }

    
    
    public static void writeJSONToResponse(HttpServletResponse response, String encoding, boolean wrapWithComments,
            String serializedJSON, boolean smd, boolean gzip, boolean noCache, int statusCode, int errorCode,
            boolean prefix, String contentType, String wrapPrefix, String wrapSuffix) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(serializedJSON))
            stringBuilder.append(serializedJSON);

        if (StringUtils.isNotBlank(wrapPrefix))
            stringBuilder.insert(0, wrapPrefix);
        else if (wrapWithComments) {
            stringBuilder.insert(0, "/* ");
            stringBuilder.append(" */");
        } else if (prefix)
            stringBuilder.insert(0, "{}&& ");

        if (StringUtils.isNotBlank(wrapSuffix))
            stringBuilder.append(wrapSuffix);

        String json = stringBuilder.toString();

        if (LOG.isDebugEnabled()) {
            LOG.debug("[JSON]" + json);
        }

        //HttpServletResponse response = serializationParams.getResponse();

        // status or error code
        if (statusCode > 0)
            response.setStatus(statusCode);
        else if (errorCode > 0)
            response.sendError(errorCode);

        // content type
        if (smd)
            response.setContentType("application/json-rpc;charset=" + encoding);
        else
            response.setContentType(contentType + ";charset=" + encoding);

        if (noCache) {
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Pragma", "No-cache");
        }

        if (gzip) {
            response.addHeader("Content-Encoding", "gzip");
            GZIPOutputStream out = null;
            InputStream in = null;
            try {
                out = new GZIPOutputStream(response.getOutputStream());
                in = new ByteArrayInputStream(json.getBytes(encoding));
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
            response.setContentLength(json.getBytes(encoding).length);
            PrintWriter out = response.getWriter();
            out.print(json);
        }
    }
}
