package com.mtx.common.util.wrapper;

import com.mtx.common.util.secret.WafKit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * Request请求过滤包装
 */
public class WafRequestWrapper extends HttpServletRequestWrapper {
    private boolean filterXSS = true;

    private boolean filterSQL = true;


    public WafRequestWrapper(HttpServletRequest request, boolean filterXSS, boolean filterSQL) {
        super(request);
        this.filterXSS = filterXSS;
        this.filterSQL = filterSQL;
    }


    public WafRequestWrapper(HttpServletRequest request) {
        this(request, true, true);
    }

    /**
     * @Description 数组参数过滤
     * @param parameter
     * 				过滤参数
     * @return
     */
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if ( values == null ) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];
        for ( int i = 0 ; i < count ; i++ ) {
            encodedValues[i] = filterParamString(values[i]);
        }

        return encodedValues;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map getParameterMap() {
        Map<String, String[]> primary = super.getParameterMap();
        Map<String, String[]> result = new HashMap<String, String[]>(primary.size());
        for ( Map.Entry<String, String[]> entry : primary.entrySet() ) {
            result.put(entry.getKey(), filterEntryString(entry.getValue()));
        }
        return result;

    }

    /**
     * @Description 参数过滤
     * @param parameter
     * 				过滤参数
     * @return
     */
    @Override
    public String getParameter(String parameter) {
        return filterParamString(super.getParameter(parameter));
    }

    /**
     * @Description 请求头过滤
     * @param name
     * 				过滤内容
     * @return
     */
    @Override
    public String getHeader(String name) {
        return filterParamString(super.getHeader(name));
    }

    /**
     * @Description Cookie内容过滤
     * @return
     */
    @Override
    public Cookie[] getCookies() {
        Cookie[] existingCookies = super.getCookies();
        if (existingCookies != null) {
            for (int i = 0 ; i < existingCookies.length ; ++i) {
                Cookie cookie = existingCookies[i];
                cookie.setValue(filterParamString(cookie.getValue()));
            }
        }
        return existingCookies;
    }

    protected String[] filterEntryString(String[] rawValue) {
        for ( int i = 0 ; i < rawValue.length ; i++ ) {
            rawValue[i] = filterParamString(rawValue[i]);
        }
        return rawValue;
    }

    private String filterParamString(String rawValue) {
        if (null == rawValue) {
            return null;
        }
        String tmpStr = rawValue;
        if (this.filterXSS) {
            tmpStr = WafKit.stripXSS(rawValue);
        }
        if (this.filterSQL) {
            tmpStr = WafKit.stripSqlInjection(tmpStr);
        }
        return tmpStr;
    }
}
