package com.isuper.soft.home.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.isuper.soft.home.domain.system.entity.SystemUser;
import com.isuper.soft.home.utils.NetworkUtil;

public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/** 获取远程的IP信息 */
	public String remoteIp = null;

	protected SystemUser getCurrentUser() {
		Object pinciba = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (SystemUser) pinciba;
	}

	@ModelAttribute
	public void getRequestInfo(HttpServletRequest request) {
		// 设置IP
		remoteIp = NetworkUtil.getClientIpAddr(request);
		// 获取可以访问的目录
	}

	public void addMessage(Object object, RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		StringBuilder stringBuilder = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
			stringBuilder.append(message).append(messages.length > 1 ? "," : "");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
		logger.debug("message", sb.toString());
	}

}
