package com.isuper.soft.home.web.runner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.isuper.soft.home.domain.system.entity.SystemGroup;
import com.isuper.soft.home.domain.system.entity.SystemMenu;
import com.isuper.soft.home.domain.system.entity.SystemUser;
import com.isuper.soft.home.repository.SystemGroupRepository;
import com.isuper.soft.home.repository.SystemMenuRepository;
import com.isuper.soft.home.repository.SystemUserRepository;
import com.isuper.soft.home.service.SystemGroupService;
import com.isuper.soft.home.service.SystemMenuService;
import com.isuper.soft.home.service.SystemUserService;

@Component
public class StartupRunner implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(StartupRunner.class);

	@Inject
	private SystemUserService systemUserService;
	@Inject
	private SystemGroupService systemGroupService;
	@Inject
	private SystemMenuService systemMenuService;
	@Inject
	private SystemUserRepository systemUserRepository;
	@Inject
	private SystemMenuRepository systemMenuRepository;
	@Inject
	private SystemGroupRepository systemGroupRepository;

	private static final String ROLE_GUEST = "ROLE_GUEST";

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("服务启动执行,初始化数据[开始]......");
		// 初始化目录
		List<SystemMenu> rootMenus = this.initDefaultMenu();
		List<SystemGroup> systemGroups = this.initDefaultGroup(rootMenus);
		this.checkDefaultUser(systemGroups);
		logger.info("服务启动执行,初始化数据[完成]!");
	}

	private List<SystemMenu> initDefaultMenu() {
		List<SystemMenu> rootMenus = new ArrayList<SystemMenu>();
		SystemMenu systemMenu = systemMenuService.findMenuById("0");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("0");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(false);// 设定是可以显示的
			systemMenu.setMenuName("根目录");// 设定目录名称
			systemMenu.setMenuSort(99999);// 设定目录排序未最后一项
			systemMenu.setMenuTile("根目录");//
			systemMenu.setParentId("-1");// 已经是跟目录了，所以未设定目录
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag(ROLE_GUEST);
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		systemMenu = systemMenuService.findMenuById("1");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("1");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("系统管理");// 设定目录名称
			systemMenu.setMenuSort(98000);// 设定目录排序未最后一项
			systemMenu.setMenuTile("系统管理");//
			systemMenu.setParentId("0");// 已经是跟目录了，所以未设定目录
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_BASE");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-gears");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		systemMenu = systemMenuService.findMenuById("2");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("2");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("用户管理");// 设定目录名称
			systemMenu.setMenuSort(98100);// 设定目录排序未最后一项
			systemMenu.setMenuTile("用户管理");//
			systemMenu.setParentId("1");// 已经是跟目录了，所以未设定目录
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_USER");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-users");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		systemMenu = systemMenuService.findMenuById("3");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("3");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("用户列表");// 设定目录名称
			systemMenu.setMenuSort(98110);// 设定目录排序未最后一项
			systemMenu.setMenuTile("用户列表");//
			systemMenu.setParentId("2");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_USER_LIST");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setSourceUrl("/system/user/list");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-th-list");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		systemMenu = systemMenuService.findMenuById("4");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("4");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(false);// 设定是可以显示的
			systemMenu.setMenuName("添加用户");// 设定目录名称
			systemMenu.setMenuSort(98111);// 设定目录排序未最后一项
			systemMenu.setMenuTile("添加用户");//
			systemMenu.setParentId("3");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_USER_ADD");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-plus");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		systemMenu = systemMenuService.findMenuById("5");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("5");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(false);// 设定是可以显示的
			systemMenu.setMenuName("添加用户");// 设定目录名称
			systemMenu.setMenuSort(98112);// 设定目录排序未最后一项
			systemMenu.setMenuTile("添加用户");//
			systemMenu.setParentId("3");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_USER_EDIT");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-pencil");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		systemMenu = systemMenuService.findMenuById("6");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("6");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(false);// 设定是可以显示的
			systemMenu.setMenuName("删除用户");// 设定目录名称
			systemMenu.setMenuSort(98113);// 设定目录排序未最后一项
			systemMenu.setMenuTile("删除用户");//
			systemMenu.setParentId("3");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_USER_DEL");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-times");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}

		systemMenu = systemMenuService.findMenuById("7");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("7");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("菜单管理");// 设定目录名称
			systemMenu.setMenuSort(98200);// 设定目录排序未最后一项
			systemMenu.setMenuTile("菜单管理");//
			systemMenu.setParentId("1");// 已经是跟目录了，所以未设定目录
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_MENU");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setRoleTag("fa-book");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}

		systemMenu = systemMenuService.findMenuById("8");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("8");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("菜单列表");// 设定目录名称
			systemMenu.setMenuSort(98210);// 设定目录排序未最后一项
			systemMenu.setMenuTile("菜单列表");//
			systemMenu.setParentId("7");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_MENU_LIST");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-times");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		systemMenu = systemMenuService.findMenuById("9");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("9");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(false);// 设定是可以显示的
			systemMenu.setMenuName("添加菜单");// 设定目录名称
			systemMenu.setMenuSort(98211);// 设定目录排序未最后一项
			systemMenu.setMenuTile("添加菜单");//
			systemMenu.setParentId("8");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_MENU_ADD");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-times");
			rootMenus.add(systemMenuRepository.save(systemMenu));

			systemMenu = systemMenuService.findMenuById("10");
			if (systemMenu == null) {
				systemMenu = new SystemMenu();
				systemMenu.setId("10");
				systemMenu.setCreater("0");// 设定系统管理员创建
				systemMenu.setDelFlag(false);// 设定未删除
				systemMenu.setEnableFlag(true);// 设定开启状态
				systemMenu.setIsShow(false);// 设定是可以显示的
				systemMenu.setMenuName("修改菜单");// 设定目录名称
				systemMenu.setMenuSort(98212);// 设定目录排序未最后一项
				systemMenu.setMenuTile("修改菜单");//
				systemMenu.setParentId("8");
				systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
				systemMenu.setRoleTag("ROLE_SYSTEM_MENU_EDIT");// 具有ADMIN权限才能使用该菜单
				systemMenu.setSystemId("0");
				systemMenu.setUpdater("0");
				systemMenu.setMenuIco("fa-times");
				rootMenus.add(systemMenuRepository.save(systemMenu));
			}
			
			systemMenu = systemMenuService.findMenuById("11");
			if (systemMenu == null) {
				systemMenu = new SystemMenu();
				systemMenu.setId("11");
				systemMenu.setCreater("0");// 设定系统管理员创建
				systemMenu.setDelFlag(false);// 设定未删除
				systemMenu.setEnableFlag(true);// 设定开启状态
				systemMenu.setIsShow(false);// 设定是可以显示的
				systemMenu.setMenuName("删除菜单");// 设定目录名称
				systemMenu.setMenuSort(98213);// 设定目录排序未最后一项
				systemMenu.setMenuTile("删除菜单");//
				systemMenu.setParentId("8");
				systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
				systemMenu.setRoleTag("ROLE_SYSTEM_MENU_DEL");// 具有ADMIN权限才能使用该菜单
				systemMenu.setSystemId("0");
				systemMenu.setUpdater("0");
				systemMenu.setMenuIco("fa-times");
				rootMenus.add(systemMenuRepository.save(systemMenu));
			}
		}
		return rootMenus;
	}

	private List<SystemGroup> initDefaultGroup(List<SystemMenu> rootMenu) {
		List<SystemMenu> systemMenus = new ArrayList<SystemMenu>();
		if (rootMenu != null) {
			for (SystemMenu systemMenu : systemMenus) {
				if (systemMenu.getId().equalsIgnoreCase("0")) {
					systemMenus.add(systemMenu);
				}
			}
		}
		SystemGroup group = null;
		if (systemGroupService.findGroupById("-1") == null) {
			// 游客
			group = new SystemGroup();
			group.setId("-1");
			group.setCreater("0");
			group.setDelFlag(false);
			group.setEnableFlag(true);
			group.setGroupCode("guest");
			group.setGroupName("游客");
			group.setRemark("系统自动创建");
			if (CollectionUtils.isNotEmpty(systemMenus)) {
				group.setSystemMenus(systemMenus);
			}
			group.setUpdater("0");
			systemGroupRepository.save(group);
		}
		systemMenus = (List<SystemMenu>) systemMenuRepository.findAll();
		if (systemGroupService.findGroupById("0") == null) {
			// 系统管理员
			group = new SystemGroup();
			group.setId("0");
			group.setCreater("0");
			group.setDelFlag(false);
			group.setEnableFlag(true);
			group.setGroupCode("admin");
			group.setGroupName("系统管理员");
			group.setRemark("系统自动创建");
			if (CollectionUtils.isNotEmpty(systemMenus)) {
				group.setSystemMenus(systemMenus);
			}
			group.setUpdater("0");
			systemGroupRepository.save(group);
		}
		return systemGroupService.findGroupById("-1", "0");
	}

	private void checkDefaultUser(List<SystemGroup> groups) {
		SystemUser systemUser = null;
		List<SystemGroup> userGroups = new ArrayList<SystemGroup>();
		if (systemUserService.queryByLoginAccount("admin") == null) {
			systemUser = new SystemUser();
			systemUser.setLoginAccount("admin");
			systemUser.setDelFlag(false);
			systemUser.setCountryCode("86");
			systemUser.setEmail("admin@9isuper.com");
			systemUser.setEnableFlag(true);
			systemUser.setExpireDate(new Date(4102415999999L));
			systemUser.setLockType(0);
			systemUser.setLoginPwd(new BCryptPasswordEncoder(4).encode("administrator"));
			systemUser.setMobile("18616819768");
			systemUser.setNickName("Jimmy3389");
			systemUser.setRealName("系统管理员");
			systemUser.setRemark("系统自动创建");
			if (CollectionUtils.isNotEmpty(groups)) {
				for (SystemGroup systemGroup : groups) {
					if (systemGroup.getId().equals("0")) {
						userGroups.add(systemGroup);
					}
				}
				if (CollectionUtils.isNotEmpty(userGroups)) {
					systemUser.setSystemGroups(userGroups);
				}
			}
			systemUserRepository.save(systemUser);
		}
		if (systemUserService.queryByLoginAccount("guest") == null) {
			systemUser = new SystemUser();
			systemUser.setLoginAccount("guest");
			systemUser.setDelFlag(false);
			systemUser.setCountryCode("86");
			systemUser.setEmail("guest@9isuper.com");
			systemUser.setEnableFlag(true);
			systemUser.setExpireDate(new Date(4102415999999L));
			systemUser.setLockType(0);
			systemUser.setLoginPwd(new BCryptPasswordEncoder(4).encode("guest"));
			systemUser.setMobile("13034298791");
			systemUser.setNickName("访客");
			systemUser.setRealName("访客");
			systemUser.setRemark("系统自动创建");
			if (CollectionUtils.isNotEmpty(groups)) {
				for (SystemGroup systemGroup : groups) {
					if (systemGroup.getId().equals("-1")) {
						userGroups.add(systemGroup);
					}
				}
				if (CollectionUtils.isNotEmpty(userGroups)) {
					systemUser.setSystemGroups(userGroups);
				}
			}
			systemUserRepository.save(systemUser);
		}
	}

}
