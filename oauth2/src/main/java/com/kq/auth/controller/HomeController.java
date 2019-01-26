package com.kq.auth.controller;



import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.exception.BaseException;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;


@Controller
public class HomeController {

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

/*	@Autowired
	RedisTemplate redisTemplate;*/

	@Autowired
	ProviderSignInUtils providerSignInUtils;
	
	@RequestMapping("/home")
	public String index(Model model, HttpServletRequest request, Principal user){
	    //String tokenvalue = (String)redisTemplate.opsForValue().get(user.getName());

		//System.out.println("这是我存储的token=="+tokenvalue);
		//Msg msg =  new Msg("测试标题","测试内容","额外信息，只对管理员显示");
		//model.addAttribute("msg", msg);
		throw new BaseException("失效了", 500);

	}


	@RequestMapping("/jwt/token")
	public String login(String username,String password){
		 //1校验合法性
		// 2合法的话 代用工具类
		// 不合法 设置状态码设置为401
		return "home";
	}

	@RequestMapping(value = "/usersave",method = RequestMethod.POST)
	public String login(HttpServletRequest request){

		providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		providerSignInUtils.doPostSignUp("1",new ServletWebRequest(request));

        return "forward:/me";
	}

    /**
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/me",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user, HttpSession session) {
        System.out.println(user);
        System.out.println(user.getUsername());
        return user;
    }

	/**
	 *
	 * session 失效的处理
	 * @return
	 */
	@RequestMapping(value = "/session/invalid",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Object sessionInvalid(@AuthenticationPrincipal UserDetails user, HttpSession session) {
		return  new ObjectRestResponse().data("false").message("session失效，请重新登录");
	}

	/**
	 * 测试生成验证码
	 */
	@RequestMapping(value = "/images/captcha",method = RequestMethod.GET)
	public void testImg(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //默认会放session放入一个key
        CaptchaUtil.out(request, response);
    }


}
