//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2021.09.13 时间 03:49:33 PM CST 
//


package com.tang.webserviceServer.pojo.masterAccountStatus;

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
 *         &lt;element ref="{}jgzh"/>
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
    "jgzh"
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
    protected String jgzh;

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
     * 获取jgzh属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJgzh() {
        return jgzh;
    }

    /**
     * 设置jgzh属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJgzh(String value) {
        this.jgzh = value;
    }

}
