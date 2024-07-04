package com.restfulbooker.api.model.request;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookingRequest {
    public String firstname;
    public String lastname;
    public Integer totalprice;
    public Boolean depositpaid;
    public BookingDates bookingdates;
    public String additionalneeds;
}
