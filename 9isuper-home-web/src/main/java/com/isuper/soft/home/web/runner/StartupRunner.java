package com.isuper.soft.home.web.runner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.isuper.soft.home.domain.system.entity.SystemRole;
import com.isuper.soft.home.domain.system.entity.SystemUser;
import com.isuper.soft.home.repository.SystemUserRepository;
import com.isuper.soft.home.service.SystemRoleService;
import com.isuper.soft.home.service.SystemUserService;

@Component
public class StartupRunner implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(StartupRunner.class);

	@Inject
	private SystemUserService systemUserService;
	@Inject
	private SystemRoleService systemRoleService;
	@Inject
	private SystemUserRepository systemUserRepository;

	private static final String ROLE_ADMIN = "ROLE_ADMIN";

	private static final String ROLE_GUEST = "ROLE_GUEST";

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("服务启动执行,初始化数据[开始]......");
		this.checkDefaultUser(checkDefaultRole());
		logger.info("服务启动执行,初始化数据[完成]!");
	}

	private Map<String, SystemRole> checkDefaultRole() {
		Map<String, SystemRole> defaultRoleMap = new HashMap<String, SystemRole>();
		SystemRole systemRole = null;
		if (systemRoleService.queryByRoleName(ROLE_ADMIN) == null) {
			systemRole = new SystemRole();
			systemRole.setDelFlag(false);
			systemRole.setEnableFlag(true);
			systemRole.setExpireDate(new Date(4102415999999L));
			systemRole.setLockType(0);
			systemRole.setRemark("系统自动创建");
			systemRole.setRoleCnName("系统管理员组");
			systemRole.setRoleName(ROLE_ADMIN);
		}
		systemRole = systemRoleService.queryByRoleName(ROLE_ADMIN);
		if (systemRole != null) {
			defaultRoleMap.put(ROLE_ADMIN, systemRole);
		}
		if (systemRoleService.queryByRoleName(ROLE_GUEST) == null) {
			systemRole = new SystemRole();
			systemRole.setDelFlag(false);
			systemRole.setEnableFlag(true);
			systemRole.setExpireDate(new Date(4102415999999L));
			systemRole.setLockType(0);
			systemRole.setRemark("系统自动创建");
			systemRole.setRoleCnName("访客");
			systemRole.setRoleName(ROLE_GUEST);
		}
		systemRole = systemRoleService.queryByRoleName(ROLE_GUEST);
		if (systemRole != null) {
			defaultRoleMap.put(ROLE_GUEST, systemRole);
		}
		return defaultRoleMap;
	}

	private void checkDefaultUser(Map<String, SystemRole> defaultRoleMap) {
		SystemUser systemUser = null;
		List<SystemRole> roles = null;
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
			if (!defaultRoleMap.isEmpty() && defaultRoleMap.get(ROLE_ADMIN) != null) {
				roles = new ArrayList<SystemRole>();
				roles.add(defaultRoleMap.get(ROLE_ADMIN));
				systemUser.setSystemRoles(roles);
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
			if (!defaultRoleMap.isEmpty() && defaultRoleMap.get(ROLE_GUEST) != null) {
				roles = new ArrayList<SystemRole>();
				roles.add(defaultRoleMap.get(ROLE_GUEST));
				systemUser.setSystemRoles(roles);
			}
			systemUserRepository.save(systemUser);
		}
	}
}
