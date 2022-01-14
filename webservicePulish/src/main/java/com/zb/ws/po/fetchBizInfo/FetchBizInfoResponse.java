package com.zb.ws.po.fetchBizInfo;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "jyxx"
})
@XmlRootElement(name = "Response")
public class FetchBizInfoResponse {
    @XmlAttribute(name = "msg", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String msg;
    @XmlAttribute(name = "success", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String success;
    @XmlSchemaType(name = "NCName")
    protected List<Jyxx> jyxx;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Jyxx> getJyxx() {
        return jyxx;
    }

    public void setJyxx(List<Jyxx> jyxx) {
        this.jyxx = jyxx;
    }
}
