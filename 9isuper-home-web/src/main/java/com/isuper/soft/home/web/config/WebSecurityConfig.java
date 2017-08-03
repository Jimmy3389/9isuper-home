package com.isuper.soft.home.web.config;

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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// 注册CustomUserService的Bean
	@Bean
	SystemUserDetailsService systemUserDetailsService() {
		return new SystemUserDetailsService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 添加自定义的user detail service认证
		auth.userDetailsService(systemUserDetailsService()).passwordEncoder(passwordEncoder());
		auth.eraseCredentials(false);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated()// 所有请求需要认证才能访问
				.and().formLogin()// 定制登陆行为
				.loginPage("/login").failureUrl("/login?error").permitAll()// 登录页面可任意访问
				.and().logout().permitAll();// 定制注销行为，注销请求可任意访问
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
}
