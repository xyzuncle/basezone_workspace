package com.kq.auth.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 继承这个类，并重写这个配置类，是为了让在QQ互联的回调地址
 * 和自己本地配置的一样，同时需要修改host文件，以方便解析
 *
 * 同时bean return 的时候，返回自己重写过url的配置
 */
public class CustomSpringSocialConfigurer extends SpringSocialConfigurer{

    private String filterUrl;

    public CustomSpringSocialConfigurer(String filterUrl) {
        this.filterUrl = filterUrl;
    }


    @Override
    protected <T> T postProcess(T object) {
        //这里的object 就是 SpringSocialConfigurer 加入到security 链条里的过滤器 SocialAuthenticationFilter
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl(filterUrl);
        return (T) filter;
    }
}
