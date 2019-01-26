package com.kq.auth.social.qq.template;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;

import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 重新模板的目的就是为了 QQ返回对的是XML无法解析，
 * restTempalte模板增加相应的转换器，这样请求就不会报错。
 */
public class QQTemplate extends OAuth2Template{

    public QQTemplate(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        //只有返回True，才会带clientID 和 clientSrecet
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 根据QQ返回的结果，重新拼装成spring social可以识别的串
     * 并返回一个AccessGrant
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        //qq返回的是一个String class
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        //处理数据，重新拼装交给spring social
        String[] item = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        String accessToken = StringUtils.substringAfterLast(item[0],"=");
        Long expireTime = new Long(StringUtils.substringAfterLast(item[1], "="));
        String refreshToken = StringUtils.substringAfterLast(item[2],"=");
        return new AccessGrant(accessToken, null, refreshToken, expireTime);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
