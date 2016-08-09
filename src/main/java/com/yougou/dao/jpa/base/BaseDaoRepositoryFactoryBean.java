package com.yougou.dao.jpa.base;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class BaseDaoRepositoryFactoryBean<T extends PagingAndSortingRepository<Object, Serializable>> extends
		JpaRepositoryFactoryBean<T, Object, Serializable> {

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new BaseDaoRepositoryFactory(entityManager);
	}

}
