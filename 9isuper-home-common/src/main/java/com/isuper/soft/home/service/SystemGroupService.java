package com.isuper.soft.home.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.isuper.soft.home.repository.SystemGroupRepository;

@Service
public class SystemGroupService {

	@Inject
	private SystemGroupRepository systemGroupRepository;

}
