package com.zb.ws.po.fetchBizInfo;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "sxlx",
        "sjcs"
})
@XmlRootElement(name = "Request")
public class FetchBizInfoRequest {
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String sxlx;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String sjcs;

    public String getSxlx() {
        return sxlx;
    }

    public void setSxlx(String sxlx) {
        this.sxlx = sxlx;
    }

    public String getSjcs() {
        return sjcs;
    }

    public void setSjcs(String sjcs) {
        this.sjcs = sjcs;
    }
}
