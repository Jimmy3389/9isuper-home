package com.isuper.soft.home.web.controller.system;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.isuper.soft.home.service.SystemMenuService;
import com.isuper.soft.home.service.SystemUserService;
import com.isuper.soft.home.web.controller.BaseController;

@Controller
@RequestMapping(value = "system/menu")
public class SystemMenuController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemMenuController.class);

	@Inject
	private SystemMenuService systemMenuService;

	@PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_MENU_LIST')")
	@RequestMapping(value = { "", "list" })
	@ResponseBody
	public ModelAndView toList(Model model) {
		model.addAttribute("allSystemMenus", this.systemMenuService.findAllMenu());
		return new ModelAndView("system/menulist");
	}

	@PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_MENU_DEL')")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> deleteSystemMenu(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		try {
			result.put("status", true);
			result.put("message", "删除成功");
		} catch (Exception e) {
			result.put("status", false);
			result.put("message", "删除异常");
		}
		return result;
	}

	@PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_MENU_EDIT')")
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> editSystemMenu(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		try {
			result.put("status", true);
			result.put("message", "删除成功");
		} catch (Exception e) {
			result.put("status", false);
			result.put("message", "删除异常");
		}
		return result;
	}
}
