package com.yougou.dao.jpa;

import com.yougou.dao.jpa.base.BaseDao;
import com.yougou.domain.Customer;

import java.util.List;

/**
 * 自定义dao,在这里可以实现自己的方法
 *
 * Created by fuwb on 16/8/10.
 */
public interface CustomerDaoCustom{

    // 自定义方法
    public List<Customer> doCustomMethod();
}
