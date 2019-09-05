package com.mtx.common.util.secret;

import java.util.regex.Pattern;

/**
 * Web防火墙工具类
 */
public class WafKit {
    private static Pattern XSS_1 = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private static Pattern XSS_2 = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    private static Pattern XSS_3 = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern XSS_4 = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern XSS_5 = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern XSS_6 = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
    private static Pattern XSS_7 = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
    private static Pattern XSS_8 = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);
    /**
     * @Description 过滤XSS脚本内容
     * @param value
     * 				待处理内容
     * @return
     */
    public static String stripXSS(String value) {
        String rlt = null;

        if (null != value) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);

            // Avoid null characters
            rlt = value.replaceAll("", "");

            // Avoid anything between script tags
            rlt = WafKit.XSS_1.matcher(rlt).replaceAll("");

            // Remove any lonesome </script> tag
            rlt = WafKit.XSS_2.matcher(rlt).replaceAll("");

            // Remove any lonesome <script ...> tag
            rlt = WafKit.XSS_3.matcher(rlt).replaceAll("");

            // Avoid eval(...) expressions
            rlt = WafKit.XSS_4.matcher(rlt).replaceAll("");

            // Avoid expression(...) expressions
            rlt = WafKit.XSS_5.matcher(rlt).replaceAll("");

            // Avoid javascript:... expressions
            rlt = WafKit.XSS_6.matcher(rlt).replaceAll("");

            // Avoid vbscript:... expressions
            rlt = WafKit.XSS_7.matcher(rlt).replaceAll("");

            // Avoid onload= expressions
            rlt = WafKit.XSS_8.matcher(rlt).replaceAll("");
        }

        return rlt;
    }

    /**
     * @Description 过滤SQL注入内容
     * @param value
     * 				待处理内容
     * @return
     */
    public static String stripSqlInjection(String value) {
        return (null == value) ? null : value.replaceAll("('.+--)|(--)|(%7C)", ""); //value.replaceAll("('.+--)|(--)|(\\|)|(%7C)", "");
    }

    /**
     * @Description 过滤SQL/XSS注入内容
     * @param value
     * 				待处理内容
     * @return
     */
    public static String stripSqlXSS(String value) {
        return stripXSS(stripSqlInjection(value));
    }
}
