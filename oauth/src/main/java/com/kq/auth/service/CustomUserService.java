package com.kq.auth.service;

import com.kq.auth.dao.SysUserRepository;
import com.kq.auth.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserService")
public class CustomUserService implements UserDetailsService { //1 自定义实现userService接口
	@Autowired
	SysUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) { //2 重写方法 获取用户
		
		SysUser user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("用户名不存在");
		}
		
		return user; //3
	}

}
