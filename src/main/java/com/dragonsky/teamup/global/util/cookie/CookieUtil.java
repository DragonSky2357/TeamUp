package com.dragonsky.teamup.global.util.cookie;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CookieUtil {

    @Value("${jwt.expire.access}")
    private long accessExpire;

    @Value("${jwt.expire.refresh}")
    private long refreshExpire;

    private static long accessExpireStatic;
    private static long refreshExpireStatic;

    @PostConstruct
    public void init(){
        accessExpireStatic = accessExpire;
        refreshExpireStatic = refreshExpire;
    }
    public static Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);

        if(key.equals("access"))
            cookie.setMaxAge((int) accessExpireStatic);
        else if(key.equals("refresh"))
            cookie.setMaxAge((int) refreshExpireStatic);

        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
