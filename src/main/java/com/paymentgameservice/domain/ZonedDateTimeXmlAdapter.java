package com.paymentgameservice.domain;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeXmlAdapter extends TemporalAccessorXmlAdapter<ZonedDateTime> {
    public ZonedDateTimeXmlAdapter() {
        super(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ssxxxx"), ZonedDateTime::from);
    }
}
