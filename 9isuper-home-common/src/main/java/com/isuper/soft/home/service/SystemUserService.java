package com.isuper.soft.home.service;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.isuper.soft.home.domain.system.entity.QSystemUser;
import com.isuper.soft.home.domain.system.entity.SystemUser;
import com.isuper.soft.home.repository.SystemUserRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class SystemUserService {

	@Inject
	private SystemUserRepository systemUserRepository;

	private QSystemUser qSystemUser = QSystemUser.systemUser;
	

	public SystemUser queryByLoginAccount(String loginAccount) {
		if (StringUtils.isBlank(loginAccount)) {
			return null;
		}
		loginAccount = loginAccount.trim().toLowerCase();
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		booleanBuilder.and(qSystemUser.delFlag.eq(false)).and(qSystemUser.enableFlag.eq(true));// 查询用户没有被删除的并且是开启状态的
		booleanBuilder.andAnyOf(qSystemUser.loginAccount.eq(loginAccount), qSystemUser.mobile.eq(loginAccount),
				qSystemUser.email.eq(loginAccount));
		// 登陆账号、登陆的手机、登陆的邮箱都可以当作登陆账号来使用
		for (SystemUser systemUser : systemUserRepository.findAll(booleanBuilder.getValue())) {
			return systemUser;
		}
		return null;
	}
}
