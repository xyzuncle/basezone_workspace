package com.kq.auth.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Entity
@Table(name="sec_user")
public class SysUser implements UserDetails{ // 这个实体实现了UserDetails的接口，这个用户就变成了
												// spring security所使用的用户
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String password;
	@ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
	private List<SysRole> roles;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //2 把用户的角色放到这个这个集合里
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		List<SysRole> roles=this.getRoles();
		for(SysRole role:roles){
			auths.add(new SimpleGrantedAuthority(role.getName()));
		}
		return auths;
	}
	@Override
	public boolean isAccountNonExpired() {  //账户是否过期
		return true;
	}
	@Override
	public boolean isAccountNonLocked() { // 账户是否 锁定
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() { // 认证是否过期
		return true;
	}
	@Override
	public boolean isEnabled() { // 账户是不是被激活的
		return true;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
	
	
	
	
}
