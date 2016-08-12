package com.yougou.dao.jpa;

import com.yougou.domain.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 1.名称规则: CustomerRepository+Impl
 * 2.实现自定义接口CustomerDaoCustom
 * Created by fuwb on 16/8/10.
 */
public class CustomerRepositoryImpl implements CustomerDaoCustom {

    @PersistenceContext
    private EntityManager em;

    //自定义方法的实现
    @Override
    public Customer doCustomMethod() {
        return em.find(Customer.class, 1);
    }
}
