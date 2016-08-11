package com.yougou.dao.jpa.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * <p>自定义基础接口</p>
 * <p>该接口不需要注册成RepositoryBean,通过@NoRepositoryBean指定</p>
 * <p>或者配置文件中指定</p>
 * <p>
 * jpa:repositories项中添加exclude-filter type="assignable" expression="com.yougou.dao.jpa.base.BaseDao"
 * </p>
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface BaseDao<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>,
		JpaSpecificationExecutor<T> {

	public T findOneById(String id);

	public T findOneByField(String field, Object value);

	public List<T> findAllByFieldEq(String field, Object value);

	public List<T> findAllByFieldLike(String field, String value);

	public List<T> findAllByFieldIn(String Field, Iterable<?> values);

	public Integer deleteById(String id);

	public Integer deleteByFieldEq(String Field, Object value);

	public Integer deleteByFieldLike(String Field, String value);

	public Integer deleteByFieldIn(String Field, Iterable<?> values);

	public Object findOneByHql(String hql);

	@SuppressWarnings("rawtypes")
	public List findAllByHql(String hql);

	public Object findOneBySql(String sql);

	@SuppressWarnings("rawtypes")
	public List findAllBySql(String sql);

    public List findAllByHql(String sql, Integer max);
    public List findAllByHql(String sql, Integer max, Integer startPosition);
}
