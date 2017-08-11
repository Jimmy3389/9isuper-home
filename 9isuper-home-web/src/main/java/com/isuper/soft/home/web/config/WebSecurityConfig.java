package com.isuper.soft.home.web.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.isuper.soft.home.service.SystemUserDetailsService;
import com.isuper.soft.home.web.security.LoginSuccessHandler;

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

	@Bean
	public LoginSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 添加自定义的user detail service认证
		auth.userDetailsService(systemUserDetailsService()).passwordEncoder(passwordEncoder());
		auth.eraseCredentials(false);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated()
				// 所有的地址都需要验证权限
				.and().formLogin().loginPage("/login").failureUrl("/login?error").usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/", true).permitAll().successHandler(loginSuccessHandler())
				// 指定登录页是Login,以及定义失败的页面
				.and().logout().logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout")).invalidateHttpSession(true).deleteCookies("remember-me", "JSESSIONID").logoutSuccessUrl("/login?logout").permitAll()
				// 定义退出的URL
				.and().csrf()
				// 安全报文头
				.and().rememberMe().tokenValiditySeconds(1209600)
				// remember ME
				.and().sessionManagement().sessionFixation().changeSessionId().maximumSessions(3).expiredUrl("/login?expire")
				// 登录用户的数量限制
				.and().and().exceptionHandling().accessDeniedPage("/accessDenied");
	}

	/**
	 * Web层面的配置，一般用来配置无需安全检查的路径
	 */
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/upload/**", "/css/**", "/js/**", "/images/**", "/resources/**", "/skin/**", "/template/**", "/**/favicon.ico", "/static/**", "/**/*.css", "/**/*.js", "/**/*.woff", "/**/*.ttf");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
}
