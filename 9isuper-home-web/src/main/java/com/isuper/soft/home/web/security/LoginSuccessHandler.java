package com.isuper.soft.home.web.security;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.isuper.soft.home.domain.system.entity.SystemUser;
import com.isuper.soft.home.service.SystemUserService;
import com.isuper.soft.home.utils.NetworkUtil;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

	/** 获取远程的IP信息 */
	public String remoteIp = null;

	@Inject
	private SystemUserService systemUserService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		SystemUser userDetails = (SystemUser) authentication.getPrincipal();
		remoteIp = NetworkUtil.getClientIpAddr(request);
		userDetails.setLastLoginTime(userDetails.getCurrentLoginTime() == null ? new Date() : userDetails.getCurrentLoginTime());
		userDetails.setCurrentLoginTime(new Date());
		userDetails.setLastLoginIp(StringUtils.isBlank(userDetails.getCurrentloginIp()) ? remoteIp : userDetails.getCurrentloginIp());
		userDetails.setCurrentloginIp(remoteIp);
		userDetails.setLoginCount(userDetails.getLoginCount() == null || userDetails.getLoginCount() < 1 ? 1 : userDetails.getLoginCount() + 1);
		// 保存登陆信息
		logger.info(userDetails.getUsername() + " from " + userDetails.getCurrentloginIp() + " Login !");
		systemUserService.save(userDetails);
		super.onAuthenticationSuccess(request, response, authentication);
	}

}