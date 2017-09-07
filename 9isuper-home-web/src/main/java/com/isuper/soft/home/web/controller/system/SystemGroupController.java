package com.isuper.soft.home.web.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.isuper.soft.home.domain.system.entity.SystemGroup;
import com.isuper.soft.home.domain.system.entity.SystemUser;
import com.isuper.soft.home.service.SystemGroupService;
import com.isuper.soft.home.service.SystemUserService;
import com.isuper.soft.home.web.controller.BaseController;

@Controller
@RequestMapping(value = "system/group")
public class SystemGroupController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemGroupController.class);

	@Inject
	private SystemGroupService systemGroupService;

	@Inject
	private SystemUserService systemUserService;

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
		model.addAttribute("allSystemUsers", this.systemUserService.findAllUser());
		return new ModelAndView("system/groupAdd");
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_GROUP_ADD')")
	@RequestMapping(value = "/add")
	@ResponseBody
	public ModelAndView AddSystemGroup(SystemGroup systemGroup, String[] groupUsers, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (systemGroup.getEnableFlag() == null) {
			systemGroup.setEnableFlag(true);
		}
		systemGroup.setDelFlag(false);
		systemGroup.setCreater(super.getCurrentUser().getId());
		systemGroup.setUpdater(super.getCurrentUser().getId());
		systemGroup = this.systemGroupService.addGroup(systemGroup);
		if (groupUsers != null && groupUsers.length > 0) {
			this.systemUserService.setUserGroup(systemGroup, getCurrentUser().getId(), groupUsers);
		}
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

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_GROUP_LIST')")
	@RequestMapping("queryById")
	@ResponseBody
	public SystemGroup queryById(String id) {
		return this.systemGroupService.findById(id);
	}

	@RequestMapping("checkSameGroup")
	@ResponseBody
	public Boolean checkSameGroup(String groupCode, String groupId) {
		List<SystemGroup> groups = this.systemGroupService.findByGroupCode(groupCode);
		if (CollectionUtils.isNotEmpty(groups)) {
			return groups.stream().filter(e -> !e.getId().equals(groupId)).count() > 0L;
		}
		return false;
	}

	@RequestMapping("getGroupUsersSelect")
	@ResponseBody
	public Map<String, Map<String, String>> getGroupUsersSelect(String groupId) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Map<String, String> userHasGroup = new HashMap<String, String>();
		Map<String, String> userNotHasGroup = new HashMap<String, String>();
		List<SystemUser> allUser = this.systemUserService.findAllUser();
		allUser.stream().forEach(u -> {
			u.getSystemGroups().stream().forEach(g -> {
				if (g.getId().equals(groupId)) {
					userHasGroup.put(u.getId(), u.getLoginAccount() + "(" + u.getNickName() + ")");
				}
			});
		});
		allUser.stream().forEach(u -> {
			if (StringUtils.isBlank(userHasGroup.get(u.getId()))) {
				userNotHasGroup.put(u.getId(), u.getLoginAccount() + "(" + u.getNickName() + ")");
			}
		});
		map.put("notInGroupUser", userNotHasGroup);
		map.put("userInGroup", userHasGroup);
		return map;
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM_GROUP_EDIT')")
	@RequestMapping("editUserGroup")
	@ResponseBody
	public ModelAndView editUserGroup(String id, String[] selectedUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SystemUser> allUser = this.systemUserService.findAllUser();// 所有的用户查找出来
		SystemGroup systemGroup = this.systemGroupService.findById(id);// 查找出对应ID的角色
		List<SystemUser> needUpdateUser = new ArrayList<SystemUser>();
		if (systemGroup != null && CollectionUtils.isNotEmpty(allUser)) {
			allUser.stream().forEach(u -> {
				if(idInList(u.getId(),selectedUser)) {
					if(!u.getSystemGroups().stream().anyMatch(g -> g.getId().equals(id))) {
						u.getSystemGroups().add(systemGroup);
						u.setUpdateDate(new Date());
						u.setUpdater(super.getCurrentUser().getId());
						needUpdateUser.add(u);
					}
				}else {
					if(u.getSystemGroups().stream().anyMatch(g -> g.getId().equals(id))) {
						u.setSystemGroups(u.getSystemGroups().stream().filter(g -> !g.getId().equals(id)).collect(Collectors.toList()));
						u.setUpdateDate(new Date());
						u.setUpdater(super.getCurrentUser().getId());
						needUpdateUser.add(u);
					}
				}
			});
		}
		if (CollectionUtils.isNotEmpty(needUpdateUser)) {
			this.systemUserService.saveAll(needUpdateUser);
		}
		return this.toList(model, request, response);
	}
	
	private boolean idInList(String id,String[] ids) {
		if(ids!=null&&ids.length>0) {
			for(String s : ids) {
				if(s.equalsIgnoreCase(id)) {
					return true;
				}
			}
		}
		return false;
	}

}
