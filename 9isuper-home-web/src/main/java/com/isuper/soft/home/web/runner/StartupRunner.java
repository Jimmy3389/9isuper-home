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
		//根菜单
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("0");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(false);// 设定是可以显示的
			systemMenu.setMenuName("根目录");// 设定目录名称
			systemMenu.setMenuSort(990000);// 设定目录排序未最后一项
			systemMenu.setMenuTile("根目录");//
			systemMenu.setParentId("-1");// 已经是跟目录了，所以未设定目录
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag(ROLE_GUEST);
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		//系统管理主菜单
		systemMenu = systemMenuService.findMenuById("100");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("100");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("系统管理");// 设定目录名称
			systemMenu.setMenuSort(991000);// 设定目录排序未最后一项
			systemMenu.setMenuTile("系统管理");//
			systemMenu.setParentId("0");// 已经是跟目录了，所以未设定目录
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_BASE");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-gears");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		//用户管理
		systemMenu = systemMenuService.findMenuById("110");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("110");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("用户管理");// 设定目录名称
			systemMenu.setMenuSort(991010);// 设定目录排序未最后一项
			systemMenu.setMenuTile("用户管理");//
			systemMenu.setParentId("100");// 已经是跟目录了，所以未设定目录
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_USER");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-user");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		
		systemMenu = systemMenuService.findMenuById("111");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("111");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("用户列表");// 设定目录名称
			systemMenu.setMenuSort(991011);// 设定目录排序未最后一项
			systemMenu.setMenuTile("用户列表");//
			systemMenu.setParentId("110");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_USER_LIST");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setSourceUrl("/system/user/list");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-th-list");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		
		systemMenu = systemMenuService.findMenuById("112");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("112");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("添加用户");// 设定目录名称
			systemMenu.setMenuSort(991012);// 设定目录排序未最后一项
			systemMenu.setMenuTile("添加用户");//
			systemMenu.setParentId("110");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_USER_ADD");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setSourceUrl("/system/user/toAdd");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-plus");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}

		systemMenu = systemMenuService.findMenuById("113");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("113");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(false);// 设定是可以显示的
			systemMenu.setMenuName("修改用户");// 设定目录名称
			systemMenu.setMenuSort(991013);// 设定目录排序未最后一项
			systemMenu.setMenuTile("修改用户");//
			systemMenu.setParentId("111");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_USER_EDIT");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-pencil");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		
		systemMenu = systemMenuService.findMenuById("114");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("114");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(false);// 设定是可以显示的
			systemMenu.setMenuName("删除用户");// 设定目录名称
			systemMenu.setMenuSort(991014);// 设定目录排序未最后一项
			systemMenu.setMenuTile("删除用户");//
			systemMenu.setParentId("111");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_USER_DEL");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-times");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		
		//角色管理
		systemMenu = systemMenuService.findMenuById("120");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("120");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("角色管理");// 设定目录名称
			systemMenu.setMenuSort(991020);// 设定目录排序未最后一项
			systemMenu.setMenuTile("角色管理");//
			systemMenu.setParentId("100");// 已经是跟目录了，所以未设定目录
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_GROUP");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-users");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		
		systemMenu = systemMenuService.findMenuById("121");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("121");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("角色列表");// 设定目录名称
			systemMenu.setMenuSort(991021);// 设定目录排序未最后一项
			systemMenu.setMenuTile("角色列表");//
			systemMenu.setParentId("120");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_GROUP_LIST");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setSourceUrl("/system/group/list");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-th-list");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		
		systemMenu = systemMenuService.findMenuById("122");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("122");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("添加角色");// 设定目录名称
			systemMenu.setMenuSort(991022);// 设定目录排序未最后一项
			systemMenu.setMenuTile("添加角色");//
			systemMenu.setParentId("120");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_GROUP_ADD");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setSourceUrl("/system/group/toAdd");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-plus");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}

		systemMenu = systemMenuService.findMenuById("123");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("123");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(false);// 设定是可以显示的
			systemMenu.setMenuName("修改角色");// 设定目录名称
			systemMenu.setMenuSort(991023);// 设定目录排序未最后一项
			systemMenu.setMenuTile("修改角色");//
			systemMenu.setParentId("121");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_GROUP_EDIT");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-pencil");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		
		systemMenu = systemMenuService.findMenuById("124");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("124");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(false);// 设定是可以显示的
			systemMenu.setMenuName("删除角色");// 设定目录名称
			systemMenu.setMenuSort(991024);// 设定目录排序未最后一项
			systemMenu.setMenuTile("删除角色");//
			systemMenu.setParentId("121");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_GROUP_DEL");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-times");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		//菜单管理
		systemMenu = systemMenuService.findMenuById("130");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("130");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("菜单管理");// 设定目录名称
			systemMenu.setMenuSort(991030);// 设定目录排序未最后一项
			systemMenu.setMenuTile("菜单管理");//
			systemMenu.setParentId("100");// 已经是跟目录了，所以未设定目录
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_MENU");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-book");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}

		systemMenu = systemMenuService.findMenuById("131");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("131");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("菜单列表");// 设定目录名称
			systemMenu.setMenuSort(991031);// 设定目录排序未最后一项
			systemMenu.setMenuTile("菜单列表");//
			systemMenu.setParentId("130");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_MENU_LIST");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			systemMenu.setSourceUrl("/system/menu/list");
			systemMenu.setMenuIco("fa-th-list");
			rootMenus.add(systemMenuRepository.save(systemMenu));
		}
		systemMenu = systemMenuService.findMenuById("132");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("132");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(true);// 设定是可以显示的
			systemMenu.setMenuName("添加菜单");// 设定目录名称
			systemMenu.setMenuSort(991032);// 设定目录排序未最后一项
			systemMenu.setMenuTile("添加菜单");//
			systemMenu.setParentId("130");
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag("ROLE_SYSTEM_MENU_ADD");// 具有ADMIN权限才能使用该菜单
			systemMenu.setSystemId("0");
			systemMenu.setSourceUrl("/system/menu/toAdd");
			systemMenu.setUpdater("0");
			systemMenu.setMenuIco("fa-plus");
			rootMenus.add(systemMenuRepository.save(systemMenu));

			systemMenu = systemMenuService.findMenuById("133");
			if (systemMenu == null) {
				systemMenu = new SystemMenu();
				systemMenu.setId("133");
				systemMenu.setCreater("0");// 设定系统管理员创建
				systemMenu.setDelFlag(false);// 设定未删除
				systemMenu.setEnableFlag(true);// 设定开启状态
				systemMenu.setIsShow(false);// 设定是可以显示的
				systemMenu.setMenuName("修改菜单");// 设定目录名称
				systemMenu.setMenuSort(991033);// 设定目录排序未最后一项
				systemMenu.setMenuTile("修改菜单");//
				systemMenu.setParentId("131");
				systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
				systemMenu.setRoleTag("ROLE_SYSTEM_MENU_EDIT");// 具有ADMIN权限才能使用该菜单
				systemMenu.setSystemId("0");
				systemMenu.setUpdater("0");
				systemMenu.setMenuIco("fa-pencil");
				rootMenus.add(systemMenuRepository.save(systemMenu));
			}
			
			systemMenu = systemMenuService.findMenuById("134");
			if (systemMenu == null) {
				systemMenu = new SystemMenu();
				systemMenu.setId("134");
				systemMenu.setCreater("0");// 设定系统管理员创建
				systemMenu.setDelFlag(false);// 设定未删除
				systemMenu.setEnableFlag(true);// 设定开启状态
				systemMenu.setIsShow(false);// 设定是可以显示的
				systemMenu.setMenuName("删除菜单");// 设定目录名称
				systemMenu.setMenuSort(991034);// 设定目录排序未最后一项
				systemMenu.setMenuTile("删除菜单");//
				systemMenu.setParentId("131");
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
			systemUser.setUserPic("https://avatars2.githubusercontent.com/u/16607231?v=4&s=460");
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
		userGroups = new ArrayList<SystemGroup>();
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
