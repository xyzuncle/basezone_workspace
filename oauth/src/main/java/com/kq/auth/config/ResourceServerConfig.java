package com.kq.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * 配置outh认证的资源服务器，用于配置能访问哪些资源
 *  EnableResourceServer 继承了 security的http的配置
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

/*  // 设置资源的唯一ID
    private static final String RESOURCE_ID = "my_rest_api";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }*/




    /**
     * tokenServices：ResourceServerTokenServices 类的实例，用来实现令牌服务。
        resourceId：这个资源服务的ID，这个属性是可选的，但是推荐设置并在授权服务中进行验证。
        其他的拓展属性例如 tokenExtractor 令牌提取器用来提取请求中的令牌。
        请求匹配器，用来设置需要进行保护的资源路径，默认的情况下是受保护资源服务的全部路径。
        受保护资源的访问规则，默认的规则是简单的身份验证（plain authenticated）。
        其他的自定义权限保护规则通过 HttpSecurity 来进行配置。
      @param http
      @throws Exception
     */

    /**
     * http.

     authorizeRequests()
     .anyRequest().authenticated()//所有的请求都必须认证，这里的认证指的是数据库的认证
     .and()
     .formLogin() // 通过formLogin的方式定制操作，是不是就是表单方式提交
     .loginPage("/login") //定制登录页面的访问地址
     .failureUrl("/login?error") // 制定登录失败后的访问地址
     .defaultSuccessUrl("/") // 默认成功后跳转的路径 是根目录，如果不写的话
     .permitAll()
     .and()
     .csrf().disable()
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http    .cors() // 跨域支持
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                //经过测试是被outh认证拦截的资源，需要认证
                //测试资源的拦截请求
                .and()
                .formLogin() // 通过formLogin的方式定制操作，是不是就是表单方式提交
                .loginPage("/login") //定制登录页面的访问地址
                .failureUrl("/login?error") // 制定登录失败后的访问地址
                .defaultSuccessUrl("/home") // 默认成功后跳转的路径 是根目录，如果不写的话
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                .and()
                .csrf().disable()
               .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .httpBasic();

    }




}
