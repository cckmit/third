//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2021.09.13 时间 05:05:50 PM CST 
//


package com.tang.webserviceServer.pojo.subAccountPayFeedback;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}xzqh"/>
 *         &lt;element ref="{}hzlsh"/>
 *         &lt;element ref="{}jgzzh"/>
 *         &lt;element ref="{}htcode"/>
 *         &lt;element ref="{}hzjg"/>
 *         &lt;element ref="{}qkms"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "xzqh",
    "hzlsh",
    "jgzzh",
    "htcode",
    "hzjg",
    "qkms"
})
@XmlRootElement(name = "Request")
public class Request {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String xzqh;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String hzlsh;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String jgzzh;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String htcode;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String hzjg;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String qkms;

    /**
     * 获取xzqh属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXzqh() {
        return xzqh;
    }

    /**
     * 设置xzqh属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXzqh(String value) {
        this.xzqh = value;
    }

    /**
     * 获取hzlsh属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHzlsh() {
        return hzlsh;
    }

    /**
     * 设置hzlsh属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHzlsh(String value) {
        this.hzlsh = value;
    }

    /**
     * 获取jgzzh属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJgzzh() {
        return jgzzh;
    }

    /**
     * 设置jgzzh属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJgzzh(String value) {
        this.jgzzh = value;
    }

    /**
     * 获取htcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHtcode() {
        return htcode;
    }

    /**
     * 设置htcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHtcode(String value) {
        this.htcode = value;
    }

    /**
     * 获取hzjg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHzjg() {
        return hzjg;
    }

    /**
     * 设置hzjg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHzjg(String value) {
        this.hzjg = value;
    }

    /**
     * 获取qkms属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQkms() {
        return qkms;
    }

    /**
     * 设置qkms属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQkms(String value) {
        this.qkms = value;
    }

}
