package com.kq.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;

import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 *
 */
@Configuration
public class LoginFilter extends ZuulFilter{


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否产生过滤请求 true 就是需要过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     *
     * @return  过滤的逻辑需要的地方
     * 过滤器只是返回true或false来保证过滤器是否执行
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String accessUrl = request.getRequestURI().split("/")[1];
        if(StringUtils.isNotBlank(accessUrl) && "uaa".equals(accessUrl)){
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("ogurl",request.getRequestURI());
            //ctx.setResponseBody("没有token,授信失败");
            return null;
        }else{
            String token = request.getParameter("accessToken");
            if(StringUtils.isNotBlank(token)){
                ctx.setSendZuulResponse(true);
                ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
                ctx.setResponseBody("token is ok!");
                System.out.println("token 是好的");
            }
        }

        return null;
    }
}
