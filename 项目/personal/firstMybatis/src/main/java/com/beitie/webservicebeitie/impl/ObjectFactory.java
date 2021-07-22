
package com.beitie.webservicebeitie.impl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.beitie.webservicebeitie.impl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetMsgResponse_QNAME = new QName("http://impl.webserviceBeiTie.beitie.com/", "getMsgResponse");
    private final static QName _GetMsg_QNAME = new QName("http://impl.webserviceBeiTie.beitie.com/", "getMsg");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.beitie.webservicebeitie.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMsgResponse }
     * 
     */
    public GetMsgResponse createGetMsgResponse() {
        return new GetMsgResponse();
    }

    /**
     * Create an instance of {@link GetMsg }
     * 
     */
    public GetMsg createGetMsg() {
        return new GetMsg();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMsgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.webserviceBeiTie.beitie.com/", name = "getMsgResponse")
    public JAXBElement<GetMsgResponse> createGetMsgResponse(GetMsgResponse value) {
        return new JAXBElement<GetMsgResponse>(_GetMsgResponse_QNAME, GetMsgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMsg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.webserviceBeiTie.beitie.com/", name = "getMsg")
    public JAXBElement<GetMsg> createGetMsg(GetMsg value) {
        return new JAXBElement<GetMsg>(_GetMsg_QNAME, GetMsg.class, null, value);
    }

}
