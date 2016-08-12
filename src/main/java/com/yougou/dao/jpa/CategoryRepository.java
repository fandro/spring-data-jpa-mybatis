package com.yougou.dao.jpa;

import com.yougou.dao.jpa.base.BaseRepository;
import com.yougou.domain.Category;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 1. Repository 是一个空接口. 即是一个标记接口
 * 2. 若我们定义的接口继承了 Repository接口或它的子接口, 则该接口会被 IOC 容器识别为一个 Repository Bean.
 * 纳入到 IOC 容器中. 进而可以在该接口中定义满足一定规范的方法.
 *
 * 3. 实际上, 也可以通过 @RepositoryDefinition 注解来替代继承 Repository 接口
 */
/**
 * 在 Repository 子接口中声明方法
 * 1. 不是随便声明的. 而需要符合一定的规范
 * 2. 查询方法以 find | read | get 开头
 * 3. 涉及条件查询时，条件的属性用条件关键字连接
 * 4. 要注意的是：条件属性以首字母大写。
 * 5. 支持属性的级联查询. 若当前类有符合条件的属性, 则优先使用, 而不使用级联属性.
 * 若需要使用级联属性, 则属性之间使用 _ 进行连接.
 */
//@RepositoryDefinition(domainClass=Category.class,idClass=Integer.class)
public interface CategoryRepository extends BaseRepository<Category, Integer> {

    //根据 categoryName 来获取对应的 Person
    Category getByCategoryName(String categoryName);

    //WHERE CategoryName LIKE ?% AND id < ?
    List<Category> getByCategoryNameStartingWithAndIdLessThan(String categoryName, Integer id);

    //WHERE CategoryName LIKE %? AND id < ?
    List<Category> getByCategoryNameEndingWithAndIdLessThan(String categoryName, Integer id);

    //WHERE email IN (?, ?, ?) OR birth < ?
    List<Category> getByCategoryNameInAndCreationTimeLessThan(List<String> categoryNames, Date creationTime);

    //WHERE a.id > ?
    //List<Category> getByAddress_IdGreaterThan(Integer id);

    //查询 id 值最大的那个 Category
    //使用 @Query 注解可以自定义 JPQL 语句以实现更灵活的查询
    @Query("SELECT p FROM Category p WHERE p.id = (SELECT max(p2.id) FROM Category p2)")
    Category getMaxIdCategory();

    //为 @Query 注解传递参数的方式1: 使用占位符.
    @Query("SELECT p FROM Category p WHERE p.categoryName = ?1 AND p.createdByUser = ?2")
    List<Category> testQueryAnnotationParams1(String categoryName, String createdByUser);

    //为 @Query 注解传递参数的方式1: 命名参数的方式.
    @Query("SELECT p FROM Category p WHERE p.categoryName = :categoryName AND p.createdByUser = :createdByUser")
    List<Category> testQueryAnnotationParams2(@Param("createdByUser") String createdByUser, @Param("categoryName") String categoryName);

    //SpringData 允许在占位符上添加 %%.
    @Query("SELECT p FROM Category p WHERE p.categoryName LIKE %?1% OR p.createdByUser LIKE %?2%")
    List<Category> testQueryAnnotationLikeParam(String categoryName, String createdByUser);

    //SpringData 允许在占位符上添加 %%.
    @Query("SELECT p FROM Category p WHERE p.categoryName LIKE %:categoryName% OR p.createdByUser LIKE %:createdByUser%")
    List<Category> testQueryAnnotationLikeParam2(@Param("createdByUser") String createdByUser, @Param("categoryName") String categoryName);

    //设置 nativeQuery=true 即可以使用原生的 SQL 查询
    @Query(value="SELECT count(id) FROM category", nativeQuery=true)
    long getTotalCount();

    //可以通过自定义的 JPQL 完成 UPDATE 和 DELETE 操作. 注意: JPQL 不支持使用 INSERT
    //在 @Query 注解中编写 JPQL 语句, 但必须使用 @Modifying 进行修饰. 以通知 SpringData, 这是一个 UPDATE 或 DELETE 操作
    //UPDATE 或 DELETE 操作需要使用事务, 此时需要定义 Service 层. 在 Service 层的方法上添加事务操作.
    //默认情况下, SpringData 的每个方法上有事务, 但都是一个只读事务. 他们不能完成修改操作!
    @Modifying
    @Query("UPDATE Category p SET p.categoryName = :categoryName WHERE id = :id")
    void updateCategory(@Param("id") Integer id, @Param("categoryName") String categoryName);

}
