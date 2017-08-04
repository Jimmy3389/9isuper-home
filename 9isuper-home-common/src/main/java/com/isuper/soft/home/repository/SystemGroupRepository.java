package com.isuper.soft.home.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.isuper.soft.home.domain.system.entity.SystemGroup;

@Repository
public interface SystemGroupRepository extends PagingAndSortingRepository<SystemGroup, Long>, QuerydslPredicateExecutor<SystemGroup> {

}
