package com.kq.api.jwt;


import com.kq.common.util.jwt.IJWTInfo;
import com.kq.common.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ace on 2017/9/10.
 */
@Component
public class JWTHelp {
    @Value("${jwt.pub-key}")
    private String pubKeyPath;

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        return JwtUtil.getInfoFromToken(token,pubKeyPath);
    }

}
