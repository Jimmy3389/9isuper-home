package com.isuper.soft.home.web.controller.system;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.isuper.soft.home.domain.system.entity.SystemMenu;
import com.isuper.soft.home.service.SystemMenuService;
import com.isuper.soft.home.web.controller.BaseController;

@Controller
@RequestMapping(value = "system/menu")
public class SystemMenuController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemMenuController.class);

	@Inject
	private SystemMenuService systemMenuService;

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_MENU_LIST')")
	@RequestMapping(value = { "", "list" })
	@ResponseBody
	public ModelAndView toList(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("allSystemMenus", this.systemMenuService.findMainMenu());
		model.addAttribute("hasAuthorityEdit", request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_SYSTEM_MENU_EDIT"));
		model.addAttribute("hasAuthorityDel", request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_SYSTEM_MENU_DEL"));
		model.addAttribute("hasAuthorityAdd", request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_SYSTEM_MENU_ADD"));
		return new ModelAndView("system/menulist");
	}

	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_MENU_ADD')")
	@RequestMapping(value = "toAdd", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView toAdd( HttpServletRequest request, HttpServletResponse response, Model model) {
		return new ModelAndView("system/menuAdd");
	}

	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_MENU_DEL')")
	@RequestMapping(value = { "delete", "doDelete" }, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView doDel(String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		this.systemMenuService.delMenu(id, super.getCurrentUser().getId());
		return this.toList(model, request, response);
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_MENU_EDIT')")
	@RequestMapping("doEdit")
	@ResponseBody
	public SystemMenu editSystemMenu(String id) {
		SystemMenu menu = new SystemMenu();
		if (StringUtils.isNotBlank(id)) {
			SystemMenu systemMenu = this.systemMenuService.findMenuById(id);
			if (systemMenu != null) {
				return systemMenu;
			}
		}
		return menu;
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_MENU_EDIT','ROLE_SYSTEM_MENU_LIST')")
	@RequestMapping("toSelectMenu")
	@ResponseBody
	public List<String> querySelectMenu() {
		List<SystemMenu> allMenus = this.systemMenuService.findAllMenu();
		return this.getMenuTree(allMenus, 0, "0", 1,3);
	}


	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_MENU_LIST')")
	@RequestMapping("queryChildMenus")
	@ResponseBody
	public List<SystemMenu> queryChildMenus(String parentId) {
		return this.systemMenuService.findByParentId(parentId);
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_MENU_ADD')")
	@RequestMapping(value = { "add", "doAdd" }, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView doAdd(SystemMenu systemMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (systemMenu.getEnableFlag() == null) {
			systemMenu.setEnableFlag(true);
		}
		systemMenu.setDelFlag(false);
		systemMenu.setCreater(super.getCurrentUser().getId());
		systemMenu.setUpdater(super.getCurrentUser().getId());
		this.systemMenuService.addMenu(systemMenu);
		return this.toList(model, request, response);
	}
}
