package com.isuper.soft.home.config;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.isuper.soft.home.service.SystemUserDetailsService;

@Configuration
@EnableWebSecurity
// 禁用Boot的默认Security配置，配合@Configuration启用自定义配置（需要扩展WebSecurityConfigurerAdapter）
@EnableGlobalMethodSecurity(prePostEnabled = true)
// @Order(-20)
// 启用Security注解，例如最常用的@PreAuthorize
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class LoginConfig extends WebSecurityConfigurerAdapter {

	@Inject
	private SystemUserDetailsService userDetailsService;

	//http://demo:demo@localhost:9998/oauth/token?grant_type=password&username=admin@9isuper.com&password=administrator
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login").permitAll().and().requestMatchers()
				.antMatchers("/", "/login", "/oauth/authorize", "/oauth/confirm_access").and().authorizeRequests()
				.anyRequest().authenticated();
	}

	/**
	 * 身份验证配置，用于注入自定义身份验证Bean和密码校验规则
	 */
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		// 不删除凭据以便记住用户
		auth.eraseCredentials(false);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
}
