package com.isuper.soft.home.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.isuper.soft.home.domain.system.entity.QSystemMenu;
import com.isuper.soft.home.repository.SystemMenuRepository;

@Service
public class SystemMenuService {

	@Inject
	private SystemMenuRepository systemMenuRepository;

	private QSystemMenu qSystemMenu = QSystemMenu.systemMenu;

}
