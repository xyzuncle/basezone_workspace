/*
package com.kq.perimission.redis;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;

*/
/**
 * 这个类会把维护的cleintID先放入reids,
 * 先查询redis，然后没找到在查询数据库
 * 同时数据库默认会维持的
 *//*

public class RedisClientDetailService extends JdbcClientDetailsService {

    private Logger logger = LoggerFactory.getLogger(RedisClientDetailService.class) ;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate ;

    */
/**
     * 缓存client的redis key，这里是hash结构存储
     *//*

    private static final String CACHE_CLIENT_KEY = "oauth_client_details";

    public RedisClientDetailService(DataSource dataSource) {
        super(dataSource);
    }

    */
/**
     * 保存client_id,现存redis娶，如果
     * @param clientId
     * @return
     * @throws InvalidClientException
     *//*

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails clientDetails = null;
        // 先从redis获取
        String value = (String) redisTemplate.boundHashOps(CACHE_CLIENT_KEY).get(clientId);
        if (StringUtils.isBlank(value)) {
            clientDetails = cacheAndGetClient(clientId);
        } else {
            clientDetails = JSONObject.parseObject(value, BaseClientDetails.class);
        }
        return clientDetails;
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        super.addClientDetails(clientDetails);
        cacheAndGetClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        super.updateClientDetails(clientDetails);
        cacheAndGetClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        super.updateClientSecret(clientId, secret);
        cacheAndGetClient(clientId);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        super.removeClientDetails(clientId);
        removeRedisCache(clientId);
    }

    */
/**
     * 缓存client并返回client
     *
     * @param clientId
     * @return
     *//*

    private ClientDetails cacheAndGetClient(String clientId) {
        // 从数据库读取
        ClientDetails clientDetails = null ;
        try {
            clientDetails = super.loadClientByClientId(clientId);
            if (clientDetails != null) {
                // 写入redis缓存
                redisTemplate.boundHashOps(CACHE_CLIENT_KEY).put(clientId, JSONObject.toJSONString(clientDetails));
                logger.info("缓存clientId:{},{}", clientId, clientDetails);
            }
        }catch (NoSuchClientException e){
            logger.info("clientId:{},{}", clientId, clientId );
        }catch (InvalidClientException e) {
            //
            e.printStackTrace();
        }

        return clientDetails;
    }

    */
/**
     * 删除redis缓存
     *
     * @param clientId
     *//*

    private void removeRedisCache(String clientId) {
        redisTemplate.boundHashOps(CACHE_CLIENT_KEY).delete(clientId);
    }

    */
/**
     * 将oauth_client_details全表刷入redis
     *//*

    public void loadAllClientToCache() {
        if (redisTemplate.hasKey(CACHE_CLIENT_KEY)) {
            return;
        }
        logger.info("将oauth_client_details全表刷入redis");

        List<ClientDetails> list = super.listClientDetails();
        if (CollectionUtils.isEmpty(list)) {
            logger.error("oauth_client_details表数据为空，请检查");
            return;
        }

        list.parallelStream().forEach(client -> {
            redisTemplate.boundHashOps(CACHE_CLIENT_KEY).put(client.getClientId(), JSONObject.toJSONString(client));
        });
    }
}
*/
