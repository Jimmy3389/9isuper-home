package com.isuper.soft.home.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.isuper.soft.home.domain.system.entity.QSystemGroup;
import com.isuper.soft.home.domain.system.entity.SystemGroup;
import com.isuper.soft.home.repository.SystemGroupRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class SystemGroupService {

	@Inject
	private SystemGroupRepository systemGroupRepository;

	private QSystemGroup qSystemGroup = QSystemGroup.systemGroup;

	public SystemGroup findGroupById(String id) {
		List<String> ids = new ArrayList<String>();
		if (StringUtils.isNotEmpty(id)) {
			ids.add(id);
			for (SystemGroup group : systemGroupRepository.findAllById(ids)) {
				if (!group.getDelFlag() && group.getEnableFlag()) {
					return group;
				}
			}
		}
		return null;
	}

	public List<SystemGroup> findGroupById(String... ids) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanBuilder orBuilder = new BooleanBuilder();
		booleanBuilder.and(qSystemGroup.delFlag.eq(false)).and(qSystemGroup.enableFlag.eq(true));// 查询用户没有被删除的并且是开启状态的
		for (String id : ids) {
			orBuilder.or(qSystemGroup.id.equalsIgnoreCase(id));
		}
		booleanBuilder.and(orBuilder);
		return (List<SystemGroup>) systemGroupRepository.findAll(booleanBuilder.getValue());
	}
}
