package com.isuper.soft.home.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.isuper.soft.home.domain.system.entity.QSystemMenu;
import com.isuper.soft.home.domain.system.entity.SystemMenu;
import com.isuper.soft.home.repository.SystemMenuRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class SystemMenuService {

	@Inject
	private SystemMenuRepository systemMenuRepository;

	private QSystemMenu qSystemMenu = QSystemMenu.systemMenu;

	public SystemMenu findMenuById(String id) {
		List<String> ids = new ArrayList<String>();
		if (StringUtils.isNotEmpty(id)) {
			ids.add(id);
			for (SystemMenu menu : systemMenuRepository.findAllById(ids)) {
				if (!menu.getDelFlag() && menu.getEnableFlag()) {
					return menu;
				}
			}
		}
		return null;
	}

	public List<SystemMenu> findAllMenu() {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanBuilder orBuilder = new BooleanBuilder();
		booleanBuilder.and(qSystemMenu.delFlag.eq(false));
		return (List<SystemMenu>) systemMenuRepository.findAll(orBuilder.getValue());
	}
}
