package com.restfulbooker.api.routes;

import java.net.URI;

public class RestRoutes {
    public static final String BOOKING = "/booking";
    public static final String AUTH = "/auth";

    public static String getBookingById(Integer bookingid) {
        return BOOKING+"/"+bookingid;
    }
}
