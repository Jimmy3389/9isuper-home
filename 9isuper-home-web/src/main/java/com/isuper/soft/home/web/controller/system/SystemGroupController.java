package com.isuper.soft.home.web.controller.system;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.isuper.soft.home.domain.system.entity.SystemGroup;
import com.isuper.soft.home.service.SystemGroupService;
import com.isuper.soft.home.web.controller.BaseController;

@Controller
@RequestMapping(value = "system/group")
public class SystemGroupController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemGroupController.class);

	@Inject
	private SystemGroupService systemGroupService;

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_GROUP_LIST')")
	@RequestMapping(value = { "", "list" })
	@ResponseBody
	public ModelAndView toList(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("systemGroups", this.systemGroupService.findAllGroup());
		return new ModelAndView("system/grouplist");
	}
	
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_GROUP_ADD')")
	@RequestMapping(value = "toAdd")
	@ResponseBody
	public ModelAndView toAdd(Model model, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("system/groupAdd");
	}


	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_GROUP_ADD')")
	@RequestMapping(value = "/add")
	@ResponseBody
	public ModelAndView AddSystemGroup(SystemGroup systemGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (systemGroup.getEnableFlag() == null) {
			systemGroup.setEnableFlag(true);
		}
		systemGroup.setDelFlag(false);
		systemGroup.setCreater(super.getCurrentUser().getId());
		systemGroup.setUpdater(super.getCurrentUser().getId());
		this.systemGroupService.addGroup(systemGroup);
		return this.toList(model, request, response);
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_GROUP_DEL')")
	@RequestMapping(value = "/del")
	@ResponseBody
	public ModelAndView doDel(String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		this.systemGroupService.delGroup(id, super.getCurrentUser().getId());
		return this.toList(model, request, response);
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_GROUP_EDIT')")
	@RequestMapping(value = "/edit")
	@ResponseBody
	public ModelAndView editSystemGroup(SystemGroup systemGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (systemGroup.getEnableFlag() == null) {
			systemGroup.setEnableFlag(true);
		}
		systemGroup.setDelFlag(false);
		systemGroup.setCreater(super.getCurrentUser().getId());
		systemGroup.setUpdater(super.getCurrentUser().getId());
		this.systemGroupService.addGroup(systemGroup);
		return this.toList(model, request, response);
	}
}
