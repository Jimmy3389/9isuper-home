package com.isuper.soft.home.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.isuper.soft.home.domain.system.entity.QSystemGroup;
import com.isuper.soft.home.domain.system.entity.SystemGroup;
import com.isuper.soft.home.domain.system.entity.SystemUser;
import com.isuper.soft.home.repository.SystemGroupRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class SystemGroupService {

	@Inject
	private SystemGroupRepository systemGroupRepository;

	@Inject
	private SystemUserService systemUserService;

	private QSystemGroup qSystemGroup = QSystemGroup.systemGroup;

	public List<SystemGroup> findAllGroup() {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		booleanBuilder.and(qSystemGroup.delFlag.eq(false));
		List<SystemGroup> groups = (List<SystemGroup>) systemGroupRepository.findAll(booleanBuilder.getValue());
		groups.stream().forEach(e -> e.setUsers(this.getGroupUser(e.getId())));
		return groups;
	}

	private String getGroupUser(String groupId) {
		StringBuffer sb = new StringBuffer();
		systemUserService.findAllUser().stream().forEach(e -> {
			if (e.getSystemGroups().stream().anyMatch(g -> g.getId().equals(groupId))) {
				sb.append(e.getLoginAccount()).append("(").append(e.getNickName()).append(")").append(",");
			}
		});
		return StringUtils.isBlank(sb)?"":sb.substring(0, sb.length()-1);
	}

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

	public SystemGroup findById(String id) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		booleanBuilder.and(qSystemGroup.delFlag.eq(false));// 查询用户没有被删除的并且是开启状态的
		booleanBuilder.and(qSystemGroup.id.equalsIgnoreCase(id));
		List<SystemGroup> groups = (List<SystemGroup>) systemGroupRepository.findAll(booleanBuilder.getValue());
		if(CollectionUtils.isNotEmpty(groups)) {
			return groups.get(0);
		}else {
			return new SystemGroup();
		}
	}

	public void delGroup(String id, String creater) {
		SystemGroup group = this.findGroupById(id);
		if (group != null && StringUtils.isNotBlank(group.getId())) {
			group.setDelFlag(true);
			group.setUpdater(creater);
			group.setUpdateDate(new Date());
			this.systemGroupRepository.save(group);
		}
	}

	public SystemGroup addGroup(SystemGroup group) {
		return this.systemGroupRepository.save(group);
	}
}
