//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2021.09.13 时间 03:39:39 PM CST 
//


package com.tang.webserviceServer.pojo.masterAccountStatus;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;


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
 *         &lt;element ref="{}jgzh"/>
 *         &lt;element ref="{}jgzhmc"/>
 *         &lt;element ref="{}dqye"/>
 *         &lt;element ref="{}zhzt"/>
 *       &lt;/sequence>
 *       &lt;attribute name="msg" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="success" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "jgzh",
    "jgzhmc",
    "dqye",
    "zhzt"
})
@XmlRootElement(name = "Response")
public class Response {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String jgzh;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String jgzhmc;
    @XmlElement(required = true)
    protected BigDecimal dqye;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String zhzt;
    @XmlAttribute(name = "msg", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String msg;
    @XmlAttribute(name = "success", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String success;

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

    /**
     * 获取jgzhmc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJgzhmc() {
        return jgzhmc;
    }

    /**
     * 设置jgzhmc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJgzhmc(String value) {
        this.jgzhmc = value;
    }

    /**
     * 获取dqye属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDqye() {
        return dqye;
    }

    /**
     * 设置dqye属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDqye(BigDecimal value) {
        this.dqye = value;
    }

    /**
     * 获取zhzt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZhzt() {
        return zhzt;
    }

    /**
     * 设置zhzt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZhzt(String value) {
        this.zhzt = value;
    }

    /**
     * 获取msg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置msg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsg(String value) {
        this.msg = value;
    }

    /**
     * 获取success属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuccess() {
        return success;
    }

    /**
     * 设置success属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuccess(String value) {
        this.success = value;
    }

}
