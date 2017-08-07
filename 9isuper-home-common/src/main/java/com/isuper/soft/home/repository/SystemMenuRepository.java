package com.isuper.soft.home.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.isuper.soft.home.domain.system.entity.SystemMenu;

@Repository
public interface SystemMenuRepository extends PagingAndSortingRepository<SystemMenu, String>, QuerydslPredicateExecutor<SystemMenu> {

}
