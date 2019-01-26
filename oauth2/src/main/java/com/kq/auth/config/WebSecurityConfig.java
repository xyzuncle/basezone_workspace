package com.kq.auth.config;


import com.kq.auth.authorize.AuthorizeManager;
import com.kq.auth.authorize.CustomAuthorizeManager;
import com.kq.auth.handle.LoginOutHandler;
import com.kq.auth.handle.MyAccessDeniedHandler;
import com.kq.auth.handle.SuccessHandler;

import com.kq.auth.service.CustomAuthenticationProvider;
import com.kq.auth.service.impl.BaseUserServiceImpl;
import com.kq.auth.validate.code.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{//1

	@Value("${spring.prefix}")
	private String prefix;

    private String forwardUrl = prefix+"/home";


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	/*@Bean
	UserDetailsService customUserService(){ //2
		return  new BaseUserServiceImpl();
	}*/

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private SpringSocialConfigurer socialSecurityConfig;

    @Autowired
    private AuthorizeManager authorizeConfigManager;

    @Autowired(required = false)
    private ValidateCodeFilter validateCodeFilter;

	//注册页面
	@Value("${qq.basezone.registerUrl}")
	String signinUrl;
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    if(validateCodeFilter!=null){
            //自己的过滤器来处理登录失败的行为
            validateCodeFilter.setAuthenticationFailureHandler(loginFaileHandel());
            http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
        }

	    //应用sosial的配置
        http.apply(socialSecurityConfig)

                //.and()
                //.sessionManagement()
                //session失效后的处理路径，
                //.invalidSessionUrl("/session/invalid")
                //session存在的最大数量，后者会替换前者
                //.maximumSessions(1)
                //当session登录超过最大数量，禁止后续的登录行为
                // .maxSessionsPreventsLogin(true)
                //记录后者替换前者登录的行为，需要一个实现类，这里没有写
                /*.expiredSessionStrategy("")*/
                //.and()
                .and()
                //在up前加入这个自己的过滤链条


               // .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                //覆盖默认的logout链接，让制定的链接登录退出
                /*  .logoutUrl("")*/
                //退出成功后返回的url
                //.logoutSuccessUrl("")
                //退出之后记录个日志什么的处理逻辑
                // .logoutSuccessHandler()
                //退出的时候，删除cookies，制定cookies的names
                //.deleteCookies("JSESSIONID")
                .and().httpBasic()
                .and().csrf().disable()
                .formLogin()
                .loginProcessingUrl("/loginToken")
                .successHandler(loginSuccessHandel());
                //解决iframe窗口问题
                http.headers().frameOptions().disable();
                //抽象出所有白名单
                authorizeConfigManager.config(http.authorizeRequests());

	}



	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider(userDetailsService);
		auth.authenticationProvider(authenticationProvider);
		auth.userDetailsService(userDetailsService) //设置访问数据库的请求
            .passwordEncoder(passwordEncoder())
		    ; //设计密码对比方式


        //auth.authenticationProvider(new CustomAuthenticationProvider(customUserService()));

	}

	/*@Override
	protected void configure(HttpSecurity http) throws Exception {



	}*/

/*	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
	}*/

	//不定义没有password grant_type
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

 /*   *//**
     * 解决静态资源被拦截的问题
     * @throws Exception
     *//*
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring(authorizeConfigManager.config(););
    }*/


    @Bean
	public SuccessHandler loginSuccessHandel(){
		return new SuccessHandler("/me");
	}

	@Bean
	public MyAccessDeniedHandler loginFaileHandel(){
		return new MyAccessDeniedHandler();
	}

	@Bean
	public LoginOutHandler loginOutHandler(){
		return new LoginOutHandler();
	}
}
