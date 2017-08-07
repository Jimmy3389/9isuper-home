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
		SystemMenu rootMenu = this.initDefaultMenu();
		List<SystemGroup> systemGroups = this.initDefaultGroup(rootMenu);
		this.checkDefaultUser(systemGroups);
		logger.info("服务启动执行,初始化数据[完成]!");
	}

	private SystemMenu initDefaultMenu() {
		SystemMenu systemMenu = systemMenuService.findMenuById("0");
		if (systemMenu == null) {
			systemMenu = new SystemMenu();
			systemMenu.setId("0");
			systemMenu.setCreater("0");// 设定系统管理员创建
			systemMenu.setDelFlag(false);// 设定未删除
			systemMenu.setEnableFlag(true);// 设定开启状态
			systemMenu.setIsShow(false);// 设定是可以显示的
			systemMenu.setMenuName("根目录");// 设定目录名称
			systemMenu.setMenuSort(999);// 设定目录排序未最后一项
			systemMenu.setMenuTile("根目录");//
			systemMenu.setParentId("");// 已经是跟目录了，所以未设定目录
			systemMenu.setRemark("系统自动创建");// 设定拥有GUEST权限即可访问
			systemMenu.setRoleTag(ROLE_GUEST);
			systemMenu.setSystemId("0");
			systemMenu.setUpdater("0");
			return systemMenuRepository.save(systemMenu);
		}
		return systemMenu;
	}

	private List<SystemGroup> initDefaultGroup(SystemMenu rootMenu) {
		List<SystemMenu> systemMenus = new ArrayList<SystemMenu>();
		if (rootMenu != null) {
			systemMenus.add(rootMenu);
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
