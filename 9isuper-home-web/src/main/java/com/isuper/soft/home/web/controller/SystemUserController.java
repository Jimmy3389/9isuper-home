package com.isuper.soft.home.web.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.isuper.soft.home.service.SystemUserService;

@Controller
@RequestMapping(value = "system/user")
public class SystemUserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemUserController.class);

	@Inject
	private SystemUserService systemUserService;

	@RequestMapping(value = { "", "list" })
	@ResponseBody
	private ModelAndView toList(Model model) {
		model.addAttribute("allSystemUsers", this.systemUserService.findAllUser());
		return new ModelAndView("system/userlist");
	}
}
