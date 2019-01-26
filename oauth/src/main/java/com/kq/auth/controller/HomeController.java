package com.kq.auth.controller;



import com.alibaba.fastjson.JSON;
import com.kq.auth.config.CustomSessionRegistry;
import com.kq.auth.domain.BaseUser;
import com.kq.auth.service.impl.RemainingNumberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Controller
public class HomeController {

	@Autowired
    CustomSessionRegistry sessionRegistry;

	@Autowired
	RemainingNumberServiceImpl remainingNumberService;

	@RequestMapping("/home")
	public String index(Model model, HttpServletRequest request, Principal user){
	    //String tokenvalue = (String)redisTemplate.opsForValue().get(user.getName());

		//System.out.println("这是我存储的token=="+tokenvalue);
		//Msg msg =  new Msg("测试标题","测试内容","额外信息，只对管理员显示");
		//model.addAttribute("msg", msg);
		return "home";
	}


	@RequestMapping("/jwt/token")
	public String login(String username,String password){
		 //1校验合法性
		// 2合法的话 代用工具类
		// 不合法 设置状态码设置为401
		return "home";
	}

	/**
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/me")
	@ResponseBody
	public String getCurrentUser() {
		BaseUser user = (BaseUser) SecurityContextHolder.getContext().getAuthentication();
		return ((BaseUser)user).getName();
	}

	/**
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/me/testmap")
	@ResponseBody
	public void TestDateMap() {
		//BaseUser user = (BaseUser) SecurityContextHolder.getContext().getAuthentication();
		remainingNumberService.putReidsDateRule("1","38d921647c9543eb86b799ce5a713d14");

	}

	/**
	 * 测试session的数量
	 * @return
	 */
	@RequestMapping(value = "/me/session")
	@ResponseBody
	public String testSessionSize(HttpServletRequest request){
		int time = request.getSession().getMaxInactiveInterval();
		System.out.println(time);

        return time+"";
	}

    /**
     * 测试sessio
     * @return
     */
    @RequestMapping(value = "/me/allsession")
    @ResponseBody
    public String testAllSession(){




        return JSON.toJSONString("");
    }


}
