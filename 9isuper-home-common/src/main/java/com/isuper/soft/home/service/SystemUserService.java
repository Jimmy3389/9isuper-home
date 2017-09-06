package com.isuper.soft.home.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.isuper.soft.home.domain.system.entity.QSystemUser;
import com.isuper.soft.home.domain.system.entity.SystemGroup;
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
		booleanBuilder.andAnyOf(qSystemUser.loginAccount.eq(loginAccount), qSystemUser.mobile.eq(loginAccount), qSystemUser.email.eq(loginAccount));
		// 登陆账号、登陆的手机、登陆的邮箱都可以当作登陆账号来使用
		for (SystemUser systemUser : systemUserRepository.findAll(booleanBuilder.getValue())) {
			return systemUser;
		}
		return null;
	}

	public SystemUser save(SystemUser systemUser) {
		systemUser.setLoginPwd(this.findByUserId(systemUser.getId()).getLoginPwd());
		return systemUserRepository.save(systemUser);
	}

	public boolean modifyPassword(String useId, String updater, String password) {
		if (systemUserRepository.findById(useId).isPresent()) {
			SystemUser systemUser = new SystemUser();
			systemUser.setId(useId);
			systemUser.setUpdater(StringUtils.isBlank(updater) ? useId : updater);
			systemUser.setLoginPwd(new BCryptPasswordEncoder(4).encode(password));
			systemUser.setModifyPwdDate(new Date());
			systemUserRepository.save(systemUser);
			return true;
		} else
			return false;
	}

	public SystemUser findByUserId(String userId) {
		return this.systemUserRepository.findById(userId).get();
	}

	public List<SystemUser> findAllUser() {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		booleanBuilder.and(qSystemUser.delFlag.eq(false));
		return (List<SystemUser>) this.systemUserRepository.findAll(booleanBuilder.getValue());
	}

	public void setUserGroup(SystemGroup group, String updater, String... userIds) {
		List<SystemUser> systemUsers = this.findAllUser();// 查找出所有的用户
		List<SystemUser> needUpdateUsers = new ArrayList<SystemUser>();
		for (SystemUser systemUser : systemUsers) {
			 List<SystemGroup> userHasGroup = systemUser.getSystemGroups();
			if(this.userInGroupUpdateList(systemUser.getId(), userIds)) {
				//说明这些用户需要有这个分组
				if(!this.groupIdInList(userHasGroup, group.getId())) {
					systemUser.getSystemGroups().add(group);
					systemUser.setUpdater(updater);
					systemUser.setUpdateDate(new Date());
					needUpdateUsers.add(systemUser);
				}
			}else {
				//说明这些用户如果有这个分组则需要去掉
				if(this.groupIdInList(userHasGroup, group.getId())) {
					systemUser.setSystemGroups(this.removeGroupId(systemUser.getSystemGroups(), group.getId()));
					systemUser.setUpdater(updater);
					systemUser.setUpdateDate(new Date());
					needUpdateUsers.add(systemUser);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(needUpdateUsers)) {
			this.systemUserRepository.saveAll(needUpdateUsers);
		}
	}

	private boolean userInGroupUpdateList(String userId, String... userIds) {
		if (userIds != null && userIds.length > 1) {
			for (String string : userIds) {
				if (string.equals(userId)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean groupIdInList(List<SystemGroup> groups,String groupId) {
		return groups.stream().anyMatch(e -> e.getId().equals(groupId));
	}
	
	private List<SystemGroup> removeGroupId(List<SystemGroup> groups,String groupId) {
		return groups.stream().filter(e -> !e.getId().equals(groupId)).collect(Collectors.toList());
	}

}
