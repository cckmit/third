package org.opoo.web.tags;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.support.Page;
import org.opoo.ndao.support.Pageable;
import org.opoo.ndao.support.Paginator;
import org.opoo.util.Assert;
import org.opoo.util.StringManager;

public class PaginatorTag extends AbstractParamsTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2796186811458387402L;
	public static final String PARAM_START = "start";
	public static final String AMP = "&amp;";
	private static final StringManager messages = StringManager.getManager("org.opoo.web.tags");
	private static final Log log = LogFactory.getLog(PaginatorTag.class);

	private Paginator paginator;
	private String url;
	private String anchor;
	private String encoding = "UTF-8";
	private boolean set = false;
	private boolean pageEncoding = false;

	public PaginatorTag() {
	}

	public void setUrl(String url) {
		// log.debug("setting url: " + url);
		this.url = url;
	}

	public void setPath(String path) {
		// log.debug("setting path: " + path);
		this.url = path;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public void setPageable(Pageable pageable) {
		Assert.notNull(pageable);
		this.paginator = new Paginator(pageable);
	}

	public void setEncoding(String encoding) {
		if (encoding != null) {
			this.encoding = encoding;
		}
	}

	public void setPageEncoding(boolean pageEncoding) {
		this.pageEncoding = pageEncoding;
	}

	public void setParams(String p) {
		setParamNames(p);
	}

	public void setParamNames(String paramNameString) {
		if (paramNameString != null) {
			String[] pns = paramNameString.split(",");
			for (int i = 0; i < pns.length; i++) {
				if (StringUtils.isNotBlank(pns[i])) {
					addParameter(pns[i].trim(), null);
				}
			}
		}
	}

	public void addParameter(String name, String value) {
		if (!PARAM_START.equalsIgnoreCase(name)) {
			super.addParameter(name, value);
		}
		set = true;
	}

	/**
	 * After the body evaluation: do not reevaluate and continue with the page.
	 * By default nothing is done with the bodyContent data (if any).
	 * 
	 * @return SKIP_BODY
	 * @throws JspException
	 *             if an error occurred while processing this tag
	 * @see #doInitBody
	 * @see BodyTag#doAfterBody
	 */
	@SuppressWarnings("unchecked")
	public int doAfterBody() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

		if (pageEncoding || encoding == null) {
			encoding = request.getCharacterEncoding();
			log.debug("Get encoding from request character encoding: " + encoding);
		} else {
			log.debug("Using set up encoding: " + encoding);
		}

		String uri = url;
		// log.debug("src url: " + url);

		if (uri != null) {
			if (uri.charAt(0) == '/') {
				uri = request.getContextPath() + url;
			}
		}
		if (uri == null) { //
			uri = (String) request.getAttribute("javax.servlet.forward.request_uri");
		}
		if (uri == null) { // struts2
			uri = (String) request.getAttribute("struts.request_uri");
		}
		if (uri == null) { // webwork
			uri = (String) request.getAttribute("webwork.request_uri");
		}
		if (uri == null) {
			uri = request.getRequestURI();
		}
		// log.debug("uriuri uri: " + uri);

		StringBuffer sb = new StringBuffer(uri);

		if (sb.indexOf("?") == -1) {
			sb.append("?");
		} else {
			sb.append(AMP);
		}

		Map<String, String> params = getParameters();
		if (!set) {
			params = new HashMap<String, String>();
			Enumeration<String> en = request.getParameterNames();
			while (en.hasMoreElements()) {
				String name = en.nextElement();
				if (!PARAM_START.equalsIgnoreCase(name)) {
					params.put(name, null);
				}
			}
			// System.out.println(request.getParameterMap());
			// params.putAll(request.getParameterMap());
			// params.remove(PARAM_START);
		}

		for (Map.Entry<String, String> en : params.entrySet()) {
			String name = en.getKey();
			String value = en.getValue();
			if (value == null) {
				value = request.getParameter(name);
			}
			if (value != null) {
				value = response.encodeURL(value);
				value = encodeURL(value);
				sb.append(name).append("=").append(value).append(AMP);
			}
		}
		url = sb.toString();
		log.debug("baseurl: " + url);
		// return SKIP_BODY;
		return super.doAfterBody();
	}

	private String encodeURL(String url) throws JspException {
		try {
			return URLEncoder.encode(url, encoding);
		} catch (UnsupportedEncodingException ex) {
			throw new JspException(ex);
		}
	}

	private String buildURL(int start) throws JspException {
		StringBuffer sb = new StringBuffer(url);
		sb.append(PARAM_START).append("=").append(start);
		if (anchor != null) {
			sb.append("#").append(anchor);
		}
		return sb.toString();
	}

	public int doEndTag() throws JspException {
		Page[] pages = paginator.getPages();
		int start = paginator.getStartIndex();
		String prev = messages.getString("PaginatorTag.prevPage");
		String next = messages.getString("PaginatorTag.nextPage");

		StringBuffer sb = new StringBuffer("<div class='o-paginator'>");
		String value = this.bodyContent.getString();
		if (StringUtils.isNotBlank(value)) {
			sb.append("<span class='o-page-tip'>" + value + "</span>");
		}

		if (paginator.hasPreviousPage()) {
			sb.append("<span class='o-page-prev'><a href='").append(buildURL(paginator.getPreviousPageStartIndex()))
					.append("'>").append(prev).append("</a></span>");
		} else {
			sb.append("<span class='o-page-prev'><a href='#' onclick='return false;'>").append(prev)
					.append("</a></span>");
		}

		for (Page page : pages) {
			if (page == null) {
				sb.append("<span class='o-page-null'>...</span>");
			} else {
				if (page.getStart() == start) {
					sb.append("<span class='o-page-active'>" + page.getNumber() + "</span>");
				} else {
					sb.append("<span class='o-page'><a href='").append(buildURL(page.getStart())).append("'>")
							.append(page.getNumber()).append("</a></span>");

				}
			}
		}

		if (paginator.hasNextPage()) {
			sb.append("<span class='o-page-next'><a href='").append(buildURL(paginator.getNextPageStartIndex()))
					.append("'>").append(next).append("</a></span>");
		} else {
			sb.append("<span class='o-page-next'><a href='#' onclick='return false;'>").append(next)
					.append("</a></span>");
		}
		sb.append("</div>");

		PageContextUtils.write(pageContext, sb.toString());

		release();
		// return SKIP_BODY;
		return EVAL_PAGE;
	}

	public void release() {
		paginator = null;
		url = null;
		anchor = null;
		encoding = "UTF-8";
		set = false;
		pageEncoding = false;
		super.release();
		log.debug("tag released");
	}

	public static void main(String[] args) {
		// System.out.println(Locale.getDefault());
		System.out.println(PaginatorTag.messages.getString("PaginatorTag.prevPage"));
	}
}
