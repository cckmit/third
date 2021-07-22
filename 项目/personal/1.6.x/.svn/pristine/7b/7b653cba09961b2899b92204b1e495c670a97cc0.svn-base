package org.opoo.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.opoo.util.LocalString;


public class LengthLimitTag extends TagSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3166537014919087108L;
	private String name = null;
    private String scope = null;
    private String property = null;
    private String value = null;
    private String encoding = "GBK";
    private int length = 0;
    private boolean html = false;

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getValue() {
        return this.value;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int doStartTag() throws JspException {
        return (SKIP_BODY);
    }

    public int doEndTag() throws JspException {
        String str = null;
        if (name != null && property != null) {
            str = (String) PageContextUtils.lookup(pageContext, name, property,
                    scope);
        } else {
            str = value;
        }
        String ostr = str;

        if (str == null) {
            return SKIP_BODY; // Nothing to output
        }

        LocalString ls = new LocalString(str, encoding);
//        if (ls == null) {
//            return SKIP_BODY;
//        }
        if (ls.length() > length) {
            str = ls.left(length) + "...";
        } else {
            str = ls.toString();
        }
        if (html) {
            str = "<span title=\"" + ostr + "\">" + str + "</span>";
        }

        PageContextUtils.write(pageContext, str);

        return SKIP_BODY;
    }

    public void release() {
        super.release();
        this.length = 0;
        this.name = null;
        this.property = null;
        this.scope = null;
        this.value = null;
        this.encoding = "GBK";
	//System.out.println("releasing.....................................");
    }
}
