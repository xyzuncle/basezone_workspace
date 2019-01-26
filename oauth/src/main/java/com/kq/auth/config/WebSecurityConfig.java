package com.kq.auth.config;


import com.kq.auth.handle.LoginOutHandler;
import com.kq.auth.handle.LoginOutSuccessHandler;
import com.kq.auth.handle.MyAccessDeniedHandler;
import com.kq.auth.handle.SuccessHandler;

import com.kq.auth.service.CustomAuthenticationProvider;
import com.kq.auth.service.impl.BaseUserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;


import javax.annotation.Resource;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{//1

	@Value("${spring.prefix}")
	private String prefix;

	private String forwardUrl = prefix+"/home";

	@Resource
	FindByIndexNameSessionRepository sessionRepository;


	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService customUserService(){ //2
		return  new BaseUserServiceImpl();
	}


	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.antMatcher("/**").anonymous().disable()
				 .exceptionHandling().accessDeniedPage("/login.html?error=123");
	}*/

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider(customUserService());
		auth.authenticationProvider(authenticationProvider);
		auth.userDetailsService(customUserService()) //设置访问数据库的请求
				.passwordEncoder(passwordEncoder()); //设计密码对比方式

		//auth.authenticationProvider(new CustomAuthenticationProvider(customUserService()));

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.sessionManagement()
				//sesion失效后的处理路径
				.invalidSessionUrl("/invalidSessionAction")
				.maximumSessions(10)
				//禁止超过session数量的登录行为
				.maxSessionsPreventsLogin(false)
				.sessionRegistry(sessionRegistry());

		http
				.csrf().disable()
				.formLogin() // 通过formLogin的方式定制操作，是不是就是表单方式提交
				/*.loginPage("/login") //定制登录处理的逻辑,默认处理表单login请求*/
				.loginProcessingUrl("/loginToken")
				//增加成功跳转的处理器
				.successHandler(loginSuccessHandel())
				.failureHandler(loginFaileHandel())
				/*   .loginProcessingUrl() 默认处理login,加上这个后，改变处理的action*/
				//.successForwardUrl(prefix+"/home")
				// 默认成功后跳转的路径 是根目录，如果不写的话
				//这个defalutSuccess跟自定义的是冲突的，源码看过后
				//.defaultSuccessUrl(prefix+"/home")
				.permitAll()
				.and()
				.authorizeRequests()
				.antMatchers("/client/**","/uaa/loginToken", "/uaa/**", "/home",
						"/uaa/v2/api-docs/**", "/v2/api-docs/**","/invalidSessionAction",
						"/token/genJwtToken","/uaa/token/genJwtToken","/session/asyncByUsername").permitAll()
				.and()
				.authorizeRequests()
				.anyRequest()
				.authenticated()
				.and()
				.logout()
				.logoutUrl("/cuslogout")
                .addLogoutHandler(loginOutHandler())
                .logoutSuccessHandler(loginOutSuccessHandler())

		//这里可以定义错误页面跳转的页面。


                /*.and()
                    .exceptionHandling()
                    .accessDeniedPage(prefix+"/login")*/
		/*  .and()*/
		/*  .addFilter(new JWTLoginFilter(authenticationManager()))*/
		/* .addFilter(new JWTAuthenticationFilter(authenticationManager()))*/;

	}

/*	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
	}*/

	/*//不定义没有password grant_type
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}*/
	/**
	 * 解决静态资源被拦截的问题
	 * @param web
	 * @throws Exception
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		//解决静态资源被拦截的问题
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**");
	}


	@Bean
	public SuccessHandler loginSuccessHandel(){
		return new SuccessHandler("/uc/user/home");
	}

	@Bean
	public MyAccessDeniedHandler loginFaileHandel(){
		return new MyAccessDeniedHandler();
	}

	@Bean
	public LoginOutSuccessHandler loginOutSuccessHandler(){
		return new LoginOutSuccessHandler();
	}

	@Bean
	public LoginOutHandler loginOutHandler(){
		return new LoginOutHandler();
	}

	/**
	 * 通过此配置来获取redis session
	 * 与security的配置
	 * @return
	 */
	@Bean
	CustomSessionRegistry sessionRegistry() {
		return new CustomSessionRegistry(this.sessionRepository);
	}
}
