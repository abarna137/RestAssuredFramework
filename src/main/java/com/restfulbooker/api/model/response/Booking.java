package com.restfulbooker.api.model.response;

import com.restfulbooker.api.model.request.BookingDates;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    public String firstname;
    public String lastname;
    public Integer totalprice;
    public Boolean depositpaid;
    public BookingDates bookingdates;
    public String additionalneeds;
}
