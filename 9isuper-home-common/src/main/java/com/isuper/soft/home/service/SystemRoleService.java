package com.isuper.soft.home.service;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.isuper.soft.home.domain.system.entity.QSystemRole;
import com.isuper.soft.home.domain.system.entity.SystemRole;
import com.isuper.soft.home.repository.SystemRoleRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class SystemRoleService {

	@Inject
	private SystemRoleRepository systemRoleRepository;

	private QSystemRole qSystemRole = QSystemRole.systemRole;

	public SystemRole queryByRoleName(String roleName) {
		if (StringUtils.isBlank(roleName)) {
			return null;
		}
		roleName = roleName.trim().toUpperCase();
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		booleanBuilder.and(qSystemRole.delFlag.eq(false)).and(qSystemRole.enableFlag.eq(true));// 查询用户没有被删除的并且是开启状态的
		booleanBuilder.andAnyOf(qSystemRole.roleName.eq(roleName));
		// 登陆账号、登陆的手机、登陆的邮箱都可以当作登陆账号来使用
		for (SystemRole systemRole : systemRoleRepository.findAll(booleanBuilder.getValue())) {
			return systemRole;
		}
		return null;
	}
}
