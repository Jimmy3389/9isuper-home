package com.isuper.soft.home.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "system/user")
public class SystemUserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemUserController.class);

	@RequestMapping(value = { "", "list" })
	@ResponseBody
	private ModelAndView toList(Model model) {
		return new ModelAndView("system/userlist");
	}
}
