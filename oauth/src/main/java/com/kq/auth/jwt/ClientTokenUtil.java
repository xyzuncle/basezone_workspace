package com.kq.auth.jwt;




import com.kq.common.util.jwt.IJWTInfo;
import com.kq.common.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ace on 2017/9/10.
 */
@Component
public class ClientTokenUtil {


    @Value("${client.expire}")
    private int expire;
    @Value("${client.pri-key.path}")
    private String priKeyPath;
    @Value("${client.pub-key.path}")
    private String pubKeyPath;

    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return JwtUtil.genToken(jwtInfo,priKeyPath,expire);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        return JwtUtil.getInfoFromToken(token,pubKeyPath);
    }

}
