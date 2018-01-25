package com.paymentgameservice.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "response")
public class Response {
    @XmlElement(name = "result")
    private List<Result> resultList;

    public Response() {

    }

    public Response(List<Result> resultList) {
        this.resultList = resultList;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    @Override
    public String toString() {
        return "Response{" +
                "resultList=" + resultList +
                "}\n";
    }
}
