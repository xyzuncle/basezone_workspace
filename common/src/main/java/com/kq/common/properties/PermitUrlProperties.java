package com.kq.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 配置文件中抽象公共的URL白名单
 * @Author: yerui
 * @CreateDate : 2018/11/19 9:55
 * @Version: 1.0
 *
 */
@ConfigurationProperties(prefix = "permit")
public class PermitUrlProperties {
    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {"/**/actuator/health", "/**/actuator/env", "/**/actuator/metrics/**", "/**/actuator/trace", "/**/actuator/dump",
            "/**/actuator/jolokia", "/**/actuator/info", "/**/actuator/logfile", "/**/actuator/refresh", "/**/actuator/flyway", "/**/actuator/liquibase",
            "/**/actuator/heapdump", "/**/actuator/loggers", "/**/actuator/auditevents", "/**/actuator/env/PID", "/**/actuator/jolokia/**",
            "/**/actuator/archaius/**", "/**/actuator/beans/**",  "/**/actuator/httptrace",
            "/**/v2/api-docs/**", "/**/swagger-ui.html", "/**/swagger-resources/**",
            "/**/webjars/**" ,"/**/druid/**","/qqLogin/**","/loginToken","/images/captcha"};

    //设置http的URL地址，如果为空默认返回ENDPOINTS
    private String[]  http_urls;

    public String[] getHttp_urls() {
        if (http_urls == null || http_urls.length == 0) {
            return ENDPOINTS;
        }

        List<String> list = new ArrayList<>();
        for (String url : ENDPOINTS) {
            list.add(url);
        }
        for (String url : http_urls) {
            list.add(url);
        }

        return list.toArray(new String[list.size()]);
    }


    public void setHttp_urls(String[] http_urls) {
        this.http_urls = http_urls;
    }

    //设置oauth地址，，如果为空默认返回ENDPOINTS
    private String[]  oauth_urls;

    public String[] getOauth_urls() {
        if (oauth_urls == null || oauth_urls.length == 0) {
            return ENDPOINTS;
        }

        List<String> list = new ArrayList<>();
        for (String url : ENDPOINTS) {
            list.add(url);
        }
        for (String url : oauth_urls) {
            list.add(url);
        }

        return list.toArray(new String[list.size()]);
    }

    /**
     * 需要放开权限的url
     *
     * @param oauth_urls 自定义的url
     * @return 自定义的url和监控中心需要访问的url集合
     */


    public void setOauth_urls(String[] oauth_urls) {
        this.oauth_urls = oauth_urls;
    }

}
