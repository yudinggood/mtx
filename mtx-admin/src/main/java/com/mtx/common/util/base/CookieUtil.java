package com.mtx.common.util.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Cookie工具
 */
@Slf4j
public class CookieUtil {
    /**
     * 设置cookie域
     */
    private final static String COOKIE_DOMAIN = "mtx.com";
    /**
     * 设置默认路径：/，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
     */
    private final static String COOKIE_PATH = "/";
    /**
     * 设置cookie有效期，根据需要自定义[本系统设置为7天]
     */
    private final static int COOKIE_MAX_AGE = 60 * 60 * 24 * 7;

    /**
     * 添加 cookie.
     */
    public static void setCookie(String name, String value, Integer maxAge, HttpServletResponse response) {
        log.debug("setCookie - 设置cookie. name={}, value={}. maxAge={}", name, value, maxAge);
        Cookie cookie;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Cookie转码异常");
        }
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(maxAge == null ? COOKIE_MAX_AGE : maxAge);
        response.addCookie(cookie);
        log.debug("setCookie - 设置cookie. [OK]");
    }

    /**
     * 根据Cookie的key得到Cookie的值.
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * 根据Cookie的名称得到Cookie对象.
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || StringUtils.isBlank(name)) {
            return null;
        }
        Cookie cookie = null;
        for (Cookie cooky : cookies) {
            if (!cooky.getName().equals(name) || StringUtils.isBlank(cooky.getDomain())) {
                continue;
            }
            cookie = cooky;
            if (request.getServerName().contains(cookie.getDomain())) {
                break;
            }
        }
        return cookie;
    }


    /**
     * 删除指定名称的Cookie.
     */
    public static void removeCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, null);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
