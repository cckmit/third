/*
 * $Id: CacheTag.java 13 2010-11-26 05:04:02Z alex $
 *
 * Copyright 2006-2008 Alex Lin. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opoo.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CacheTag extends BodyTagSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2016633936011823871L;
	private static final Log log = LogFactory.getLog(CacheTag.class);
    private PageContext pageContext;
    private Tag parentTag;
    private BodyContent bodyContent;
    private boolean hadBody;
    private boolean bodyEvaluated;

    public CacheTag() {
        hadBody = false;
        bodyEvaluated = false;
    }

    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public Tag getParent() {
        return parentTag;
    }

    public void setParent(Tag parentTag) {
        this.parentTag = parentTag;
    }

    public BodyContent getBodyContent() {
        return bodyContent;
    }

    public void setBodyContent(BodyContent bodyContent) {
        if (bodyContent != null)
            hadBody = true;
        this.bodyContent = bodyContent;
    }

    public int doStartTag() throws JspException {
        if (pageContext.getAttribute(getId(), 2) != null) {
            String content = (String) pageContext.getAttribute(getId(), 2);
            JspWriter out = pageContext.getOut();
            try {
                if (content != null)
                    out.write(content);
                else
                    out.write("");
            } catch (IOException ioe) {
                throw new JspException(ioe.getMessage());
            }
            return SKIP_BODY;
        }
        if (!bodyEvaluated) {
            bodyEvaluated = true;
            return 2;
        } else {
            return SKIP_BODY;
        }
    }

    public int doAfterBody() {
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        try {
            if (bodyContent != null) {
                String content = bodyContent.getString();
                if (content != null)
                    pageContext.setAttribute(getId(), content, 2);
                if (hadBody)
                    bodyContent.writeOut(bodyContent.getEnclosingWriter());
            }
        } catch (Exception ioe) {
            log.warn(ioe);
        }
        bodyEvaluated = false;
        hadBody = false;
        return EVAL_PAGE;
    }

    public void release() {
	hadBody = false;
        bodyEvaluated = false;
	super.release();
	//log.debug("releasing cache tag...");
    }
}
