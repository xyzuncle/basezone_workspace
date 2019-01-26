/*
package com.kq.auth.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisTemplateStore implements TokenStore{

    private static final String ACCESS = "access:";
    private static final String AUTH_TO_ACCESS = "auth_to_access:";
    private static final String AUTH = "auth:";
    private static final String REFRESH_AUTH = "refresh_auth:";
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private static final String REFRESH = "refresh:";
    private static final String REFRESH_TO_ACCESS = "refresh_to_access:";
    private static final String CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    private static final String UNAME_TO_ACCESS = "uname_to_access:";

   private final RedisConnectionFactory connectionFactory;

    //设置这个构造方法的目的，是以后可能需要用到集群
    public RedisTemplateStore(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }



    //声明RedisTemplate 用来读取redis模板
    @Autowired
    private RedisTemplate redisTemplate ;



    //用于抽取key 比如 clientid username 等
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();


    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public AuthenticationKeyGenerator getAuthenticationKeyGenerator() {
        return authenticationKeyGenerator;
    }

    */
/**
     * 存储token的方法
     * @param token
     * @param authentication
     *//*


    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {

        this.redisTemplate.opsForValue().set(AUTH +token.getValue(), authentication);
        this.redisTemplate.opsForValue().set(ACCESS+ token.getValue(), token);

        //自己定义的token,用登录的名字作为token的key,来获取token的值
        this.redisTemplate.opsForValue().set(authentication.getName(),token.getValue());

        this.redisTemplate.opsForValue().set(AUTH_TO_ACCESS+authenticationKeyGenerator.extractKey(authentication), token);
        if (!authentication.isClientOnly()) {
            //存放的是clientID 和token的值
           redisTemplate.opsForList().rightPush(UNAME_TO_ACCESS+getApprovalKey(authentication), token) ;
        }

        redisTemplate.opsForList().rightPush(CLIENT_ID_TO_ACCESS+authentication.getOAuth2Request().getClientId(), token) ;

        if (token.getExpiration() != null) {

            int seconds = token.getExpiresIn();
            //int seconds = Long.valueOf((token.getExpiresIn() - System.currentTimeMillis()) / 1000L).intValue();
            redisTemplate.expire(AUTH+ token.getValue(), seconds, TimeUnit.SECONDS) ;
            redisTemplate.expire(ACCESS+ token.getValue(), seconds, TimeUnit.SECONDS) ;
            redisTemplate.expire(AUTH_TO_ACCESS+ token.getValue(), seconds, TimeUnit.SECONDS) ;
            redisTemplate.expire(CLIENT_ID_TO_ACCESS+ token.getValue(), seconds, TimeUnit.SECONDS) ;
            redisTemplate.expire(CLIENT_ID_TO_ACCESS+ token.getValue(), seconds, TimeUnit.SECONDS) ;
        }
        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (refreshToken!= null && refreshToken.getValue() != null) {

            this.redisTemplate.opsForValue().set( REFRESH_TO_ACCESS+   token.getRefreshToken().getValue(), token.getValue());
            this.redisTemplate.opsForValue().set(ACCESS_TO_REFRESH+token.getValue(), token.getRefreshToken().getValue());

            if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken)refreshToken;
                Date expiration = expiringRefreshToken.getExpiration();
                if (expiration != null) {
                    int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue();
                    this.redisTemplate.expire(REFRESH_TO_ACCESS + token.getRefreshToken().getValue(), seconds, TimeUnit.SECONDS);
                    this.redisTemplate.expire(ACCESS_TO_REFRESH+token.getValue(), seconds, TimeUnit.SECONDS);
                }
            }
        }
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    */
/**
     * 获取权限根据token
     * @param token
     * @return
     *//*

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return (OAuth2Authentication) this.redisTemplate.opsForValue().get(AUTH +  token);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return (OAuth2AccessToken) this.redisTemplate.opsForValue().get(ACCESS+tokenValue);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken oAuth2AccessToken) {
        removeAccessToken(oAuth2AccessToken.getValue());
    }

    public void removeAccessToken(String tokenValue) {
        OAuth2AccessToken removed = (OAuth2AccessToken) redisTemplate.opsForValue().get(ACCESS+tokenValue);
        // Don't remove the refresh token - it's up to the caller to do that
        OAuth2Authentication authentication = (OAuth2Authentication) this.redisTemplate.opsForValue().get(AUTH+tokenValue);

        this.redisTemplate.delete(AUTH+tokenValue);
        redisTemplate.delete(ACCESS+tokenValue);
        this.redisTemplate.delete(ACCESS_TO_REFRESH +tokenValue);

        if (authentication != null) {
            this.redisTemplate.delete(AUTH_TO_ACCESS+authenticationKeyGenerator.extractKey(authentication));

            String clientId = authentication.getOAuth2Request().getClientId();
// redisTemplate.opsForList().rightPush("UNAME_TO_ACCESS:"+getApprovalKey(authentication), token) ;
            //左侧出栈
            redisTemplate.opsForList().leftPop(UNAME_TO_ACCESS+getApprovalKey(clientId, authentication.getName()));
            //左侧出栈
            redisTemplate.opsForList().leftPop(CLIENT_ID_TO_ACCESS+clientId);

            this.redisTemplate.delete(AUTH_TO_ACCESS+authenticationKeyGenerator.extractKey(authentication));
        }
    }

    */
/**
     * 存储刷新的token 值
     * @param refreshToken
     * @param authentication
     *//*

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        //在redis里存储新的token
        this.redisTemplate.opsForValue().set(REFRESH+refreshToken.getValue(), refreshToken);
        //在redis里存储新的权限
        this.redisTemplate.opsForValue().set(REFRESH_AUTH + refreshToken.getValue(), authentication);
    }

    */
/**
     * 根据token 获取刷新的token的值
     * @param tokenValue
     * @return
     *//*

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return (OAuth2RefreshToken) this.redisTemplate.opsForValue().get(REFRESH+tokenValue);
    }

    */
/**
     * 根据刷新的toekn 来获取权限
     * @param token
     * @return
     *//*

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
        return (OAuth2Authentication) this.redisTemplate.opsForValue().get( REFRESH_AUTH+token);
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        removeRefreshToken(oAuth2RefreshToken.getValue());
    }

    public void removeRefreshToken(String tokenValue) {
        this.redisTemplate.delete( REFRESH + tokenValue);
        this.redisTemplate.delete( REFRESH_AUTH + tokenValue);
        this.redisTemplate.delete(REFRESH_TO_ACCESS +tokenValue);
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        removeAccessTokenUsingRefreshToken(oAuth2RefreshToken.getValue());
    }

    */
/**
     * 根据refreshToken 删除 accesstoken
     * @param refreshToken
     *//*

    private void removeAccessTokenUsingRefreshToken(String refreshToken) {

        String token = (String) this.redisTemplate.opsForValue().get( REFRESH_TO_ACCESS  +refreshToken) ;

        if (token != null) {
            redisTemplate.delete(ACCESS+ token);
        }
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication oAuth2Authentication) {
        String key = authenticationKeyGenerator.extractKey(oAuth2Authentication);
        OAuth2AccessToken accessToken = (OAuth2AccessToken) redisTemplate.opsForValue().get(AUTH_TO_ACCESS+key);
        if (accessToken != null
                && !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
            // Keep the stores consistent (maybe the same user is represented by this authentication but the details
            // have changed)
            storeAccessToken(accessToken, oAuth2Authentication);
        }
        return accessToken;

    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        List<Object> result =    redisTemplate.opsForList().range(UNAME_TO_ACCESS+ getApprovalKey(clientId, userName), 0, -1);

        if (result == null || result.size() == 0) {
            return Collections.<OAuth2AccessToken> emptySet();
        }
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>(result.size());

        for(Iterator<Object> it = result.iterator(); it.hasNext();){
            OAuth2AccessToken accessToken = (OAuth2AccessToken) it.next();
            accessTokens.add(accessToken);
        }

        return Collections.<OAuth2AccessToken> unmodifiableCollection(accessTokens);

    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<Object> result =    redisTemplate.opsForList().range((CLIENT_ID_TO_ACCESS+clientId), 0, -1);

        if (result == null || result.size() == 0) {
            return Collections.<OAuth2AccessToken> emptySet();
        }
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>(result.size());

        for(Iterator<Object> it = result.iterator();it.hasNext();){
            OAuth2AccessToken accessToken = (OAuth2AccessToken) it.next();
            accessTokens.add(accessToken);
        }

        return Collections.<OAuth2AccessToken> unmodifiableCollection(accessTokens);
    }

    private String getApprovalKey(OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication() == null ? "" : authentication.getUserAuthentication()
                .getName();
        return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
    }

    private String getApprovalKey(String clientId, String userName) {
        return clientId + (userName==null ? "" : ":" + userName);
    }
}
*/
