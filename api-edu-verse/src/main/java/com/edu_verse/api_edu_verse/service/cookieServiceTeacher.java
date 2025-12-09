package com.edu_verse.api_edu_verse.service;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class cookieServiceTeacher {
    private static final String COOKIE_NAME = "auth_token";

    @Value("${cookie.secret.teacher}")
    private String secretKey;

    @Value("${cookie.expiration}")
    private int expiration;

    public Cookie genCookie_teacher(){
        Cookie cookie = new Cookie(COOKIE_NAME, secretKey);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(expiration);
        return cookie;
    }

    public boolean isCookieValid_teacher(String token){
        return token != null && token.equals(genCookie_teacher().getValue());
    }

}
