package com.kq.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */

public class LoginFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return null;
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
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String contextPath = request.getContextPath();
        if(contextPath.equals("uaa")){
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
            ctx.setResponseBody("need login....");
        }else{
            String token = request.getParameter("access_token");
            if(StringUtils.isNotEmpty(token)){
                ctx.setSendZuulResponse(true);
                ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
                ctx.setResponseBody("token is ok!");
            }else{
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                ctx.setResponseBody("The times of usage is limited");
            }
        }
        return null;
    }
}
