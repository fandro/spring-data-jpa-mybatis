package com.yougou.dao.jpa;

import com.yougou.domain.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by fuwb on 16/8/10.
 */
public class CustomerDaoImpl implements CustomerDaoCustom {

    @PersistenceContext
    private EntityManager em;

    //自定义方法的实现
    @Override
    public List<Customer> doCustomMethod() {
        CriteriaQuery<Customer> criteriaQuery =
                em.getCriteriaBuilder().createQuery(Customer.class);

        return em.createQuery(criteriaQuery).getResultList();

    }
}
