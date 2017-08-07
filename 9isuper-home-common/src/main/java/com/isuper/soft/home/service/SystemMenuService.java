package com.isuper.soft.home.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.isuper.soft.home.domain.system.entity.QSystemMenu;
import com.isuper.soft.home.domain.system.entity.SystemMenu;
import com.isuper.soft.home.repository.SystemMenuRepository;

@Service
public class SystemMenuService {

	@Inject
	private SystemMenuRepository systemMenuRepository;

	private QSystemMenu qSystemMenu = QSystemMenu.systemMenu;

	public SystemMenu findMenuById(String id) {
		List<String> ids = new ArrayList<String>();
		if (StringUtils.isNotEmpty(id)) {
			ids.add(id);
			for(SystemMenu menu:systemMenuRepository.findAllById(ids)){
				if (!menu.getDelFlag() && menu.getEnableFlag()) {
					return menu;
				}
			}
		}
		return null;
	}
}
