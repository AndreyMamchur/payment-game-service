package com.paymentgameservice.dto;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
    private final static QName QNAME = new QName(XMLConstants.NULL_NS_URI, "data");

    @XmlElementDecl(name = "request")
    public JAXBElement<Response> createData(Response value){
        return new JAXBElement<Response>(QNAME, Response.class, null, value);
    }
}
