package org.opoo.web.tags;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class DateTimesTag extends TagSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7835177053570065987L;
	private long longTime = 0;
    private java.util.Date date;
    private String style = null;
    private String name = null;
    private String scope = null;
    private String property = null;

    public DateTimesTag() {
    }

    public long getLongTime() {
        return longTime;
    }

    public void setLongTime(long longTime) {
        this.longTime = longTime;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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

    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        if (style == null || style.length() == 0) {
            style = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        if (name != null && property != null) {
            Object value = PageContextUtils.lookup(pageContext, name, property,
                    scope);
            if (value == null) {
                return SKIP_BODY; // Nothing to output
            } else {
                date = ((Date) value);
            }
        } else {
            if (longTime == 0) {
                longTime = System.currentTimeMillis();
            }
            date = new Date(longTime);
        }
        String output = formatter.format(date);

        PageContextUtils.write(pageContext, output);

        return SKIP_BODY;
    }


    public void release() {
        super.release();
        longTime = 0;
        date = null;
        style = null;
        name = null;
        scope = null;
        property = null;
    }

}
