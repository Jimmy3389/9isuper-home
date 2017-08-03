package com.isuper.soft.home.service;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.isuper.soft.home.domain.system.entity.SystemUser;

@Service
public class SystemUserDetailsService implements UserDetailsService {

	@Inject
	private SystemUserService systemUserService;

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		if (StringUtils.isBlank(arg0)) {
			throw new UsernameNotFoundException("用户名为空");
		}
		SystemUser systemUser = systemUserService.queryByLoginAccount(arg0);
		// TODO 以后这个地方将调用用户统一认证接口获取到用户信息
		if (systemUser == null) {
			throw new UsernameNotFoundException(String.format("User %s was not found", arg0));
		}
		return systemUser;
	}
}
