/*
package com.kq.auth.token;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

*/
/**
 * 注意：Authentication details to Null
 * Note: DefaultAccessTokenConverter used to set Authentication details to Null.
 *
 * Let’s create CustomAccessTokenConverter and
 * set Authentication details with access token claims:
 * 把额外的索赔信息放入 Authentication 的详情里，
 *//*


public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication authentication
                = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }
}
*/
