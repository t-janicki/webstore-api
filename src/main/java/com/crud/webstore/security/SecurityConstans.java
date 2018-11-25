package com.crud.webstore.security;

import com.crud.webstore.SpringApplicationContext;

public class SecurityConstans {
    public static final long EXPIRATION_TIME = 864000000; //10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/v1/users/createUser";
    public static final String USER_ID = "userId";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
        return appProperties.getTokenSecret();
    }
}