package com.restfulbooker.api.model.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class BookingDates {
    public Date checkin;
    public Date checkout;

}
