package com.restfulbooker.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BookingIdByNameRequest {
    public String firstname;
    public String lastname;
}
