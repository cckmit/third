package org.opoo.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.opoo.util.StringManager;

public class ParamTag extends BodyTagSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 424440139172508621L;
	private static StringManager messages = StringManager.getManager("org.opoo.web.tags");
    /**
     * The name of the query parameter.
     */
    protected String name = null;

    /**
     * The value of the query parameter or body content of this tag (if any).
     */
    protected String value = null;

    // ----------------------------------------------------- Constructor

    public ParamTag() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // --------------------------------------------------------- Public Methods

    /**
     *
     * @return int
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    /**
     * Save the associated from the body content.
     *
     * @throws JspException if a JSP exception has occurred
     * @return int
     */
    public int doAfterBody() throws JspException {
        if (this.bodyContent != null) {
            String value = this.bodyContent.getString().trim();
            if (value.length() > 0) {
                this.value = value;
            }
        }
        return SKIP_BODY;
    }

    /**
     * Render the end of the hyperlink.
     *
     * @throws JspException if a JSP exception has occurred
     * @return int
     */
    public int doEndTag() throws JspException {
        Tag tag = findAncestorWithClass(this, AbstractParamsTag.class);
        if (tag != null) {
            ((AbstractParamsTag) tag).addParameter(name, value);
        } else {
            throw new JspException(messages.getString("ParamTag.linkParam"));
        }
        return EVAL_PAGE;
    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
        this.name = null;
        this.value = null;
	//System.out.println("releasing paramtag...............");
    }
}
