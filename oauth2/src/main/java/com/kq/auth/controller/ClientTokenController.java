/*
package com.kq.auth.controller;

import com.kq.auth.DTO.ClientInfo;

import com.kq.auth.jwt.ClientTokenUtil;



import com.kq.common.DTO.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.util.cldr.CLDRLocaleDataMetaInfo;

import java.lang.reflect.Method;
import java.util.List;

@RestController
@RequestMapping("client")
public class ClientTokenController {


    @Autowired
    ClientTokenUtil clientTokenUtil;



    */
/**
     * 请求token的服务 生成token
     * @param clientId
     * @param secrect
     * @return
     * @throws Exception
     *//*

    @RequestMapping(value = "/token",method = RequestMethod.POST)
    public ObjectRestResponse applyToken(String clientId,String secrect) throws Exception {
        //ClientInfo clientInfo = clientService.apply(clientId,secrect);

        return new ObjectRestResponse<String>().data(clientTokenUtil.generateToken(clientInfo));
    }

    */
/**
     * 根据服务ID和秘钥获取token，
     * 同时获得 该服务能访问到的服务组合串
     * @param serviceId
     * @param secret
     * @return
     *//*

    @RequestMapping(value = "/myClient")
    public ObjectRestResponse getAllowedClient(String serviceId, String secret) {
        return new ObjectRestResponse<List<String>>().data(clientService.getAllowClients(serviceId,secret));
    }



}
*/
