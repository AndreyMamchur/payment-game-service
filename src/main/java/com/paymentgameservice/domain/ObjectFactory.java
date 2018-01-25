package com.paymentgameservice.domain;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
    private final static QName QNAME = new QName(XMLConstants.NULL_NS_URI, "data");

    @XmlElementDecl(name = "request")
    public JAXBElement<Request> createData(Request value){
        return new JAXBElement<Request>(QNAME, Request.class, null, value);
    }
}
