package com.vdaoyun.systemapi.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vdaoyun.systemapi.web.base.user.model.SysUser;
import com.vdaoyun.systemapi.web.base.user.service.SysUserService;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {
	
	@Autowired
	private SysUserService sysUserService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> authorities = new ArrayList<>();
		SysUser sysUser = sysUserService.loadUserByUsername(username);
		if (username.equals("admin")) {
			authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
		} else {
			authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
		}
		return new User(username, sysUser.getPassword(), authorities);
	}

}
