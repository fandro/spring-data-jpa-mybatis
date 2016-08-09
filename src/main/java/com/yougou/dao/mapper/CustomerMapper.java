package com.yougou.dao.mapper;

import com.yougou.model.Customer;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by fuwb on 16/8/9.
 * 定义sql映射的接口，使用注解指明方法要执行的SQL
 */
public interface CustomerMapper {

    //使用@Insert注解指明add方法要执行的SQL
    @Insert("insert into customer(name, age) values(#{name}, #{age})")
    public int add(Customer Customer);

    //使用@Delete注解指明deleteById方法要执行的SQL
    @Delete("delete from Customer where id=#{id}")
    public int deleteById(int id);

    //使用@Update注解指明update方法要执行的SQL
    @Update("update Customer set age=#{age},birth=#{birth} where id=#{id}")
    public int update(Customer Customer);

    //使用@Select注解指明getById方法要执行的SQL
    @Select("select * from Customer where id=#{id}")
    public Customer getById(int id);

    //使用@Select注解指明getAll方法要执行的SQL
    @Select("select * from Customer")
    public List<Customer> getAll();

    // 通过注解指定sql,但是mapper.xml也会被使用
    @Select("SELECT * FROM customer WHERE id = #{customerId}")
    Customer getCustomerById(@Param("customerId") String customerId);
}
