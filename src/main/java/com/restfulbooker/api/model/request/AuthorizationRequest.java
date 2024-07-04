package com.restfulbooker.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorizationRequest {
    public String username;
    public String password;
}
