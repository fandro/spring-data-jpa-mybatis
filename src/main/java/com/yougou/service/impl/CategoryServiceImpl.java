package com.yougou.service.impl;

import com.yougou.dao.jpa.CategoryRepository;
import com.yougou.domain.Category;
import com.yougou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by fuwb on 16/8/10.
 */
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void updateCategory(Integer id, String categoryName) {
        categoryRepository.updateCategory(id,categoryName);
    }

    @Override
    public Category save(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public void delete(List<Category> categoryList) {
        categoryRepository.delete(categoryList);
    }

    /**
     * 目标: 实现带查询条件的分页.
     *
     * 调用 JpaSpecificationExecutor 的 Page<T> findAll(Specification<T> spec, Pageable pageable);
     * Specification: 封装了 JPA Criteria 查询的查询条件
     * Pageable: 封装了请求分页的信息: 例如 pageNo, pageSize, Sort
     */
    @Override
    public Page<Category> findAll(int id, int pageNo, int pageSize) {

        // 过程:创建builder => 创建Query => 构造条件 => 查询

        PageRequest pagable = new PageRequest(pageNo,pageSize);

        //通常使用 Specification 的匿名内部类
        Specification<Category> spec = new Specification<Category>() {
            /**
             * @param root: 代表查询的实体类(主体类).
             * @param query: 可以从中得到 Root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类. 还可以
             * 来添加查询条件, 还可以结合 EntityManager 对象得到最终查询的 TypedQuery 对象.
             * @param cb: CriteriaBuilder 对象. 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到 Predicate 对象
             *          过程:创建builder => 创建Query => 构造条件 => 查询
             * @return: Predicate 类型, 代表一个查询条件.
             */
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path path = root.get("id");
                Predicate predicate = cb.ge(path, id);
                return predicate;
            }
        };

        Page<Category> page = categoryRepository.findAll(spec, pagable);
        return page;
    }

    @Override
    public Page<Category> search(final Category category, int pageNo, int pageSize, String sortName) {
        PageRequest pagable = new PageRequest(pageNo - 1, pageSize, new Sort(Direction.DESC, sortName));

        Specification<Category> spec = new Specification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate categoryNameLike = null;
                if(null != category && !StringUtils.isEmpty(category.getCategoryName())) {
                    categoryNameLike = cb.like(root.<String> get("categoryName"), "%" + category.getCategoryName() + "%");
                }

                Predicate createdByUserLike = null;
                if(null != category && null != category.getCreatedByUser()&& !StringUtils.isEmpty(category.getCreatedByUser())) {
                    createdByUserLike = cb.like(root.<String>get("createdByUser"), "%" + category.getCreatedByUser() + "%");
                }
                if (null != categoryNameLike && null != createdByUserLike){
                    query.where(cb.or(categoryNameLike,createdByUserLike));
                }else if (null != categoryNameLike){
                    query.where(categoryNameLike);
                }else if (null != createdByUserLike){
                    query.where(createdByUserLike);
                }
                return null;
            }
        };

        return categoryRepository.findAll(spec,pagable);
    }



}
