package com.isuper.soft.home.runner;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.isuper.soft.home.domain.system.entity.SystemUser;
import com.isuper.soft.home.repository.SystemUserRepository;
import com.isuper.soft.home.service.SystemUserService;

@Component
public class StartupRunner implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(StartupRunner.class);

	@Inject
	private SystemUserService systemUserService;
	@Inject
	private SystemUserRepository systemUserRepository;

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("服务启动执行,初始化数据[开始]......");
		this.checkDefaultUser();
		logger.info("服务启动执行,初始化数据[完成]!");
	}

	private void checkDefaultUser() {
		SystemUser systemUser = null;
		if (systemUserService.queryByLoginAccount("admin") == null && systemUserRepository.findById(-1L).equals(Optional.empty())) {
			systemUser = new SystemUser();
			systemUser.setLoginAccount("admin");
			systemUser.setDelFlag(false);
			systemUser.setCountryCode("86");
			systemUser.setEmail("admin@9isuper.com");
			systemUser.setEnableFlag(true);
			systemUser.setExpireDate(new Date(4102415999999L));
			systemUser.setId(-1L);
			systemUser.setLockType(0);
			systemUser.setLoginPwd(new BCryptPasswordEncoder(4).encode("administrator"));
			systemUser.setMobile("18616819768");
			systemUser.setNickName("Jimmy3389");
			systemUser.setRealName("系统管理员");
			systemUser.setRemark("系统自动创建");
			systemUserRepository.save(systemUser);
		}
		if (systemUserService.queryByLoginAccount("guest") == null && systemUserRepository.findById(-2L).equals(Optional.empty())) {
			systemUser = new SystemUser();
			systemUser.setLoginAccount("guest");
			systemUser.setDelFlag(false);
			systemUser.setCountryCode("86");
			systemUser.setEmail("guest@9isuper.com");
			systemUser.setEnableFlag(true);
			systemUser.setExpireDate(new Date(4102415999999L));
			systemUser.setId(-1L);
			systemUser.setLockType(0);
			systemUser.setLoginPwd(new BCryptPasswordEncoder(4).encode("guest"));
			systemUser.setMobile("13034298791");
			systemUser.setNickName("访客");
			systemUser.setRealName("访客");
			systemUser.setRemark("系统自动创建");
			systemUserRepository.save(systemUser);
		}
	}
}
