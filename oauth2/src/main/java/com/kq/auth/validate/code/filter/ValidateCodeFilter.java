package com.kq.auth.validate.code.filter;

import com.kq.auth.constants.CommonConstants;
import com.kq.auth.domain.ValidateCode;
import com.kq.auth.validate.code.exception.ValidateCodeException;
import com.kq.common.exception.BaseException;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.BindingType;
import java.io.IOException;

/**
 * 验证码拥有自己的过滤器，类似于usernamepasswordfilter
 * 该过滤器只会被调用一次
 * 同时提交用户名，密码和验证码
 */

@Configuration
@ConditionalOnProperty(prefix = "login.vaildate",name = "type",havingValue = "code",matchIfMissing = true)
public class ValidateCodeFilter extends OncePerRequestFilter {

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    //定义验证码成功的处理器和过滤器
    private AuthenticationFailureHandler authenticationFailureHandler;

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //判断请求是否是这个处理路径，如果是，就走这个处理器
        if(StringUtils.equals("/loginToken",request.getRequestURI()) &&
            StringUtils.equalsIgnoreCase(request.getMethod(),"post")) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request,response
                ,e);
                //出现异常一定要retrun 否则会继续调用过滤链条
                return;
            }
        }

        //如果不是，则调用后续的过滤链条
        filterChain.doFilter(request, response);
    }

    /**
     * 验证码的逻辑
     * @param request
     */
    public void validate(ServletWebRequest request){

        //获取session中的图形code
        ValidateCode codeInSession = (ValidateCode)sessionStrategy.getAttribute(request, CommonConstants.SESSION_KEY);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    "code");
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInRequest == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if(codeInSession==null){
            throw new ValidateCodeException("验证码不存在！");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, CommonConstants.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.toString(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

     //请求完毕，移除session中的code
     sessionStrategy.removeAttribute(request,CommonConstants.SESSION_KEY);

    }
}


