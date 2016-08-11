package com.yougou.dao.jpa.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

	private EntityManager entityManager;

	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		entityManager = em;
	}

	@Override
	public T findOneById(String id) {
		return findOneByField("id", id);
	}

	@Override
	public T findOneByField(String Field, Object value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(getDomainClass());
		Root<T> e = query.from(getDomainClass());
		query.where(cb.equal(e.get(Field), value));
		return entityManager.createQuery(query).getSingleResult();
	}

	@Override
	public List<T> findAllByFieldEq(String Field, Object value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(getDomainClass());
		Root<T> e = query.from(getDomainClass());
		query.where(cb.equal(e.get(Field), value));
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<T> findAllByFieldLike(String Field, String value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(getDomainClass());
		Root<T> e = query.from(getDomainClass());
		query.where(cb.like(e.get(Field).as(String.class), value));
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<T> findAllByFieldIn(String Field, Iterable<?> values) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(getDomainClass());
		Root<T> e = query.from(getDomainClass());
		query.where(cb.in(e.get(Field)).value(values));
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public Integer deleteById(String id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaDelete<T> delete = cb.createCriteriaDelete(getDomainClass());
		Root<T> e = delete.from(getDomainClass());
		delete.where(cb.equal(e.get("id"), id));
		return entityManager.createQuery(delete).executeUpdate();
	}

	@Override
	public Integer deleteByFieldEq(String Field, Object value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaDelete<T> delete = cb.createCriteriaDelete(getDomainClass());
		Root<T> e = delete.from(getDomainClass());
		delete.where(cb.equal(e.get(Field), value));
		return entityManager.createQuery(delete).executeUpdate();
	}

	@Override
	public Integer deleteByFieldLike(String Field, String value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaDelete<T> delete = cb.createCriteriaDelete(getDomainClass());
		Root<T> e = delete.from(getDomainClass());
		delete.where(cb.like(e.get(Field).as(String.class), value));
		return entityManager.createQuery(delete).executeUpdate();
	}

	@Override
	public Integer deleteByFieldIn(String Field, Iterable<?> values) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaDelete<T> delete = cb.createCriteriaDelete(getDomainClass());
		Root<T> e = delete.from(getDomainClass());
		delete.where(cb.in(e.get(Field)).value(values));
		return entityManager.createQuery(delete).executeUpdate();
	}

	@Override
	public Object findOneByHql(String hql) {
		return entityManager.createQuery(hql).getSingleResult();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findAllByHql(String hql) {
		return entityManager.createQuery(hql).getResultList();
	}

    @Override
	public List findAllByHql(String hql,Integer max) {
        return entityManager.createQuery(hql).setMaxResults(max).getResultList();
    }
    @Override
	public List findAllByHql(String hql,Integer max,Integer startPosition) {
        return entityManager.createQuery(hql).setFirstResult(startPosition).setMaxResults(max).getResultList();
    }
	@Override
	public Object findOneBySql(String sql) {
		return entityManager.createNativeQuery(sql).getSingleResult();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findAllBySql(String sql) {
		return entityManager.createNativeQuery(sql).getResultList();
	}

}
