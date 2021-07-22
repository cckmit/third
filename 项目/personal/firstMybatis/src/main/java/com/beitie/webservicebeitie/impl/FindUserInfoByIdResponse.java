
package com.beitie.webservicebeitie.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>findUserInfoByIdResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="findUserInfoByIdResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://impl.webserviceBeiTie.beitie.com/}userInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findUserInfoByIdResponse", propOrder = {
    "_return"
})
public class FindUserInfoByIdResponse {

    @XmlElement(name = "return")
    protected UserInfo _return;

    /**
     * 获取return属性的值。
     * 
     * @return
     *     possible object is
     *     {@link UserInfo }
     *     
     */
    public UserInfo getReturn() {
        return _return;
    }

    /**
     * 设置return属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link UserInfo }
     *     
     */
    public void setReturn(UserInfo value) {
        this._return = value;
    }

}
