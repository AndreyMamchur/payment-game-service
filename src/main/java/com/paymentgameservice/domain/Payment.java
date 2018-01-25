package com.paymentgameservice.domain;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "payment")
public class Payment {

    @XmlAttribute private long id;
    @XmlAttribute private int sum;
    @XmlAttribute private int check;
    @XmlAttribute private int service;
    @XmlAttribute private String account;
    @XmlJavaTypeAdapter(ZonedDateTimeXmlAdapter.class)
    @XmlAttribute private ZonedDateTime date;
    @XmlElement(name = "attribute") private List<Attribute> attributes;

    public Payment() {
    }

    public Payment(long id, int sum, int check, int service, String account, ZonedDateTime date) {
        this.id = id;
        this.sum = sum;
        this.check = check;
        this.service = service;
        this.account = account;
        this.date = date;
    }

    public Payment(long id, int sum, int check, int service, String account, ZonedDateTime date, List<Attribute> attributes) {
        this.id = id;
        this.sum = sum;
        this.check = check;
        this.service = service;
        this.account = account;
        this.date = date;
        this.attributes = attributes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
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

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

        public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", sum=" + sum +
                ", check=" + check +
                ", service=" + service +
                ", account='" + account + '\'' +
                ", date=" + date +
                ", attributes=" + attributes +
                '}';
    }
}
