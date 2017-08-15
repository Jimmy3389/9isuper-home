package com.isuper.soft.home.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.isuper.soft.home.domain.system.entity.SystemUser;

@Controller
@RequestMapping(value = "dashboard")
public class DashboardController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@RequestMapping(value = { "notf" }, method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, String> notification(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		SystemUser currentUser = super.getCurrentUser();
		Random random = new Random();
		logger.debug("--recive requeust from user: " + currentUser.getName());
		map.put("inboxCount", String.valueOf(random.nextInt(20)));
		map.put("newMsgCount", String.valueOf(random.nextInt(20)));
		map.put("notifiCount", String.valueOf(random.nextInt(20)));
		return map;
	}

	@RequestMapping(value = { "/view", "/" })
	@ResponseBody
	public ModelAndView index(Model model) {
		return new ModelAndView("dashboard/dashView");
	}
}
