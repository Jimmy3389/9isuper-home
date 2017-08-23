package com.isuper.soft.home.web.controller;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.isuper.soft.home.domain.system.entity.SystemUser;

@Controller
public class LoginController extends BaseController {

	@RequestMapping(value = { "/login", "/" })
	@ResponseBody
	public ModelAndView index(Model model) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth instanceof AnonymousAuthenticationToken) {
				return new ModelAndView("login");
			} else {
				// 获取用户登录权限详细
				Object pinciba = auth.getPrincipal();
				if (pinciba instanceof UserDetails) {
					SystemUser systemUser = ((SystemUser) pinciba);
					model.addAttribute("systemUser", systemUser);
					model.addAttribute("systemMenu", systemUser.getUserMenu());
				}

				// 登录成功跳到主页
				return new ModelAndView("index");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("login");
		}
	}
}
