package com.isuper.soft.home.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.isuper.soft.home.domain.system.entity.SystemMenu;
import com.isuper.soft.home.domain.system.entity.SystemUser;
import com.isuper.soft.home.utils.NetworkUtil;

public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/** 获取远程的IP信息 */
	public String remoteIp = null;

	protected SystemUser getCurrentUser() {
		Object pinciba = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (pinciba == null || pinciba.equals("anonymousUser")) {
			return null;
		}
		return (SystemUser) pinciba;
	}

	@ModelAttribute
	public void getRequestInfo(HttpServletRequest request) {
		// 设置IP
		remoteIp = NetworkUtil.getClientIpAddr(request);
		SystemUser systemUser = this.getCurrentUser();
		if (systemUser != null) {
			logger.debug(systemUser.getLoginAccount());
		}
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

	protected List<String> getMenuTree(Collection<SystemMenu> allMenus, int count, String menuId, int deep,
			Integer allDeep) {
		List<String> menuList = new ArrayList<String>();
		List<SystemMenu> childMenus = allMenus.stream().filter(menu -> menu.getParentId().equals(menuId)).distinct()
				.collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(childMenus) && deep <= (allDeep == null || allDeep <= 0 ? 3 : allDeep)) {
			for (SystemMenu systemMenu : childMenus) {
				menuList.add(this.menuPrefix(count) + systemMenu.getMenuName() + "!" + systemMenu.getId()+"!"+systemMenu.getParentId());
				menuList.addAll(this.getMenuTree(allMenus, count + 1, systemMenu.getId(), deep + 1, allDeep));
			}
		}
		return menuList;
	}

	protected String menuPrefix(int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append("│&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return sb.toString() + "├─";
	}
}
