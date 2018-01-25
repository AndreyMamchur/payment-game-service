package com.paymentgameservice.domain;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "request")
public class Request {
    @XmlAttribute(required = true) private int point = 327;

    @XmlElement(required = false) private Verify verify;
    @XmlElement(required = false, name = "payment") private List<Payment> paymentList;
    @XmlElement(required = false) private Status status;

    public Request() {
    }

    public Request(Verify verify, List<Payment> paymentList, Status status) {
        this.verify = verify;
        this.paymentList = paymentList;
        this.status = status;
    }

    public Request(int point, Verify verify, List<Payment> paymentList, Status status) {
        this.point = point;
        this.verify = verify;
        this.paymentList = paymentList;
        this.status = status;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Verify getVerify() {
        return verify;
    }

    public void setVerify(Verify verify) {
        this.verify = verify;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" +
                "point=" + point +
                ", verify=" + verify +
                ", paymentList=" + paymentList +
                ", status=" + status +
                '}';
    }
}
