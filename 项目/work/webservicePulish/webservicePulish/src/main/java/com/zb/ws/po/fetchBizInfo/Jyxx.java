package com.zb.ws.po.fetchBizInfo;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "ywbh",
        "sqrxm",
        "sqrsfzhm",
        "zjnje",
        "fwzl",
        "jzmj"
})
@XmlRootElement(name = "jyxx")
public class Jyxx {
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String ywbh;
    @XmlElement(required = true)
    @XmlSchemaType(name = "NCName")
    protected String sqrxm;
    @XmlElement(required = true)
    @XmlSchemaType(name = "NCName")
    protected String sqrsfzhm;
    @XmlElement(required = true)
    @XmlSchemaType(name = "NCName")
    protected BigDecimal zjnje;
    @XmlElement(required = true)
    @XmlSchemaType(name = "NCName")
    protected String fwzl;
    @XmlElement(required = true)
    @XmlSchemaType(name = "NCName")
    protected BigDecimal jzmj;

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
    }

    public String getSqrxm() {
        return sqrxm;
    }

    public void setSqrxm(String sqrxm) {
        this.sqrxm = sqrxm;
    }

    public String getSqrsfzhm() {
        return sqrsfzhm;
    }

    public void setSqrsfzhm(String sqrsfzhm) {
        this.sqrsfzhm = sqrsfzhm;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public BigDecimal getZjnje() {
        return zjnje;
    }

    public void setZjnje(BigDecimal zjnje) {
        this.zjnje = zjnje;
    }

    public BigDecimal getJzmj() {
        return jzmj;
    }

    public void setJzmj(BigDecimal jzmj) {
        this.jzmj = jzmj;
    }
}
