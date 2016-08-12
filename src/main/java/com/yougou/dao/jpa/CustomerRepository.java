package com.yougou.dao.jpa;

import com.yougou.dao.jpa.base.BaseRepository;
import com.yougou.domain.Customer;

/**
 * 1. Repository 是一个空接口. 即是一个标记接口
 * 2. 若我们定义的接口继承了 Repository接口或它的子接口, 则该接口会被 IOC 容器识别为一个 Repository Bean.
 * 纳入到 IOC 容器中. 进而可以在该接口中定义满足一定规范的方法.
 * 3. 实际上, 也可以通过 @RepositoryDefinition 注解来替代继承 Repository 接口
 * -----------------------
 * 在 Repository 子接口中声明方法
 * 1. 不是随便声明的. 而需要符合一定的规范
 * 2. 查询方法以 find | read | get 开头
 * 3. 涉及条件查询时，条件的属性用条件关键字连接
 * 4. 要注意的是：条件属性以首字母大写。
 * 5. 支持属性的级联查询. 若当前类有符合条件的属性, 则优先使用, 而不使用级联属性.
 * 若需要使用级联属性, 则属性之间使用 _ 进行连接.
 * ------------------------
 * 自定义实现Repository
 * 1.定义一个接口CustomerRepositoryCustom,添加自定义方法
 * 2.定义一个实现类CustomerRepositoryImpl,实现CustomerRepositoryCustom接口,CustomerRepositoryImpl名称命名规则: 接口名 + Impl
 * 3.CustomerRepository 继承CustomerRepositoryCustom接口
 * 通过以上步骤CustomerDao中就访问自定义方法了。
 */

//@RepositoryDefinition(domainClass=Customer.class,idClass=Integer.class)
public interface CustomerRepository extends BaseRepository<Customer,Integer>, CustomerRepositoryCustom {

}
