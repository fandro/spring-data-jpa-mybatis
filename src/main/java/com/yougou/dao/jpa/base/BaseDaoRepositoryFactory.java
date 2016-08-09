package com.yougou.dao.jpa.base;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;

@SuppressWarnings("unchecked")
public class BaseDaoRepositoryFactory extends JpaRepositoryFactory {

	public BaseDaoRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected SimpleJpaRepository getTargetRepository(RepositoryMetadata metadata, EntityManager entityManager) {
		return new BaseDaoImpl<>((Class<Object>) metadata.getDomainType(), entityManager);
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		return BaseDao.class;
	}

}
