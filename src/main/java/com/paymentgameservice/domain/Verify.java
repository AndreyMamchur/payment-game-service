package com.paymentgameservice.domain;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verify")
public class Verify {
    @XmlAttribute private int service;
    @XmlAttribute private String account;
    @XmlElement(name = "attribute ") private List<Attribute> attributes;

    public Verify() {
    }

    public Verify(int service, String account) {
        this.service = service;
        this.account = account;
    }

    public Verify(int service, String account, List<Attribute> attributes) {
        this.service = service;
        this.account = account;
        this.attributes = attributes;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Verify{" +
                "service=" + service +
                ", account='" + account + '\'' +
                ", attributes=" + attributes +
                '}';
    }

}
