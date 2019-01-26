/*
package com.kq.auth.fifter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kq.auth.domain.SysUser;
import com.kq.auth.util.jwt.JWTInfo;
import com.kq.auth.util.jwt.JwtUtil;
import com.kq.auth.util.jwt.RsaKeyHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();

    @Value("${jwt.pri.path}")
    private String priKey;


*/
/**
     * 验证成功后需要调用的方法
     * @param request
     * @param res
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException*//*



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {


        //继续执行过滤器
        chain.doFilter(request, res);
    }



    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {


        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getParameter("username"),
                        req.getParameter("password"),
                        new ArrayList<>())
        );
    }
}
*/
