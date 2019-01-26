package com.kq.auth.validate.username.filter;

import com.kq.auth.constants.CommonConstants;
import com.kq.auth.validate.code.exception.ValidateCodeException;
import com.kq.auth.validate.username.token.UPAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserNameAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private boolean postOnly = true;

    /**
     * 重塑构造方法，这里可以定义什么路径，什么方法来进行提交
     * 拦截该路径下的方法进行处理
     * @param requiresAuthenticationRequestMatcher
     */
    protected UserNameAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(new AntPathRequestMatcher(CommonConstants.UP_LOGIN,"POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(postOnly && !request.getMethod().equalsIgnoreCase("post")){
            throw new ValidateCodeException("Authentication method not supported: " +request.getMethod());
        }

        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
        if(StringUtils.isBlank(username)){
            username = "";
        }
        if (StringUtils.isBlank(password)) {
            password = "";
        }

        username = StringUtils.trimToEmpty(username);

        UPAuthenticationToken authRequest = new UPAuthenticationToken(username,password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        //让manager管理这个provider
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request,
                              UPAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
