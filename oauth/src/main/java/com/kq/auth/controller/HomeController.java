package com.kq.auth.controller;

import com.kq.auth.domain.Msg;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class HomeController {
	
	@RequestMapping("/home")
	public String index(Model model,HttpServletRequest request){
		String url = request.getParameter("ogurl");
		System.out.println("这是我获取到的URL");
		Msg msg =  new Msg("测试标题","测试内容","额外信息，只对管理员显示");
		model.addAttribute("msg", msg);
		return "home";
	}

}
