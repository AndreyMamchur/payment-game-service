package com.paymentgameservice.dto;

import com.paymentgameservice.domain.Attribute;
import com.paymentgameservice.domain.ZonedDateTimeXmlAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "result")
public class Result {
    @XmlAttribute private long id;
    @XmlAttribute private short state;
    @XmlAttribute private short substate;
    @XmlAttribute private short code;
    @XmlAttribute(name = "final") private short finalStatus;
    @XmlAttribute private int trans;
    @XmlAttribute private int service;
    @XmlAttribute private int sum;
    @XmlAttribute private int commission;
    @XmlAttribute(name = "sum_prov") private int sumProv;
    @XmlJavaTypeAdapter(ZonedDateTimeXmlAdapter.class)
    @XmlAttribute(name = "server_time") private ZonedDateTime serverTime;
    @XmlElement(name = "attribute") private List<Attribute> attributes;

    public Result() {
    }

    public Result(short finalStatus) {
        this.finalStatus = finalStatus;
    }

    public Result(long id, short state, short substate, short code, short finalStatus, int trans, int service, int sum, int commission, int sumProv, ZonedDateTime serverTime) {
        this.id = id;
        this.state = state;
        this.substate = substate;
        this.code = code;
        this.finalStatus = finalStatus;
        this.trans = trans;
        this.service = service;
        this.sum = sum;
        this.commission = commission;
        this.sumProv = sumProv;
        this.serverTime = serverTime;
    }

    public Result(long id, short state, short substate, short code, short finalStatus, int trans, int service, int sum, int commission, int sumProv, ZonedDateTime serverTime, List<Attribute> attributes) {
        this.id = id;
        this.state = state;
        this.substate = substate;
        this.code = code;
        this.finalStatus = finalStatus;
        this.trans = trans;
        this.service = service;
        this.sum = sum;
        this.commission = commission;
        this.sumProv = sumProv;
        this.serverTime = serverTime;
        this.attributes = attributes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public short getSubstate() {
        return substate;
    }

    public void setSubstate(short substate) {
        this.substate = substate;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public short getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(short finalStatus) {
        this.finalStatus = finalStatus;
    }

    public int getTrans() {
        return trans;
    }

    public void setTrans(int trans) {
        this.trans = trans;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public int getSumProv() {
        return sumProv;
    }

    public void setSumProv(int sumProv) {
        this.sumProv = sumProv;
    }

    public ZonedDateTime getServerTime() {
        return serverTime;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setServerTime(ZonedDateTime serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public String toString() {
        return "\nResult{" +
                "id=" + id +
                ", state=" + state +
                ", substate=" + substate +
                ", code=" + code +
                ", finalStatus=" + finalStatus +
                ", trans=" + trans +
                ", service=" + service +
                ", sum=" + sum +
                ", commission=" + commission +
                ", sumProv=" + sumProv +
                ", serverTime=" + serverTime +
                ", attributes=" + attributes +
                '}';
    }
}
