package com.yougou;

import com.yougou.dao.jpa.CategoryRepository;
import com.yougou.domain.Category;
import com.yougou.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 测试jpa相关方法
 * Created by fuwb on 16/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JpaTest {

    @Autowired
    private CategoryRepository categoryDao;

    @Autowired
    private CategoryService categoryService;

    /*
    *  --------- spring-data-jpa 方法 测试 --------------
    *  org.springframework.data.repository.CrudRepository
    *  org.springframework.data.repository.PagingAndSortingRepository
    */

    @Test
    public void testSave() {
        // org.springframework.data.repository.CrudRepository 中的方法
        Category entity = new Category();
        entity.setCategoryName("brazil");
        entity.setCreatedByUser("leita");
        entity.setModifiedByUser("leita");
        entity.setCreationTime(new Date());
        Category category = categoryService.save(entity);
        Assert.assertNotNull("is not null", category);
        Assert.assertEquals("brazil",category.getCategoryName());
    }

    @Test
    public void testFindOne() {
        // org.springframework.data.repository.CrudRepository 中的方法
        Category category = categoryDao.findOne(1);
        Assert.assertNotNull("is not null", category);
        Assert.assertEquals("china",category.getCategoryName());
    }

    // 测试分页
    @Test
    public void testPaging1() {
        // org.springframework.data.repository.PagingAndSortingRepository 中的方法
        int pageNo = 1;
        int pageSize = 2;
        PageRequest pagable = new PageRequest(pageNo,pageSize);
        Page<Category> page = categoryDao.findAll(pagable);

        System.out.println("总记录数: " + page.getTotalElements());
        System.out.println("当前第几页: " + (page.getNumber() + 1));
        System.out.println("总页数: " + page.getTotalPages());
        System.out.println("当前页面的 List: " + page.getContent());
        System.out.println("当前页面的记录数: " + page.getNumberOfElements());

        Assert.assertNotNull("is not null", page);
        Assert.assertEquals(3,page.getTotalPages());
    }
    @Test
    public void testPaging2() {
        //pageNo 从 0 开始.
        int pageNo = 2-1;
        int pageSize = 2;
        //Pageable 接口通常使用的其 PageRequest 实现类. 其中封装了需要分页的信息
        //排序相关的. Sort 封装了排序的信息
        //Order 是具体针对于某一个属性进行升序还是降序.
        Order order1 = new Order(Direction.DESC, "id");
        Order order2 = new Order(Direction.ASC, "categoryName");
        Sort sort = new Sort(order1, order2);

        PageRequest pageable = new PageRequest(pageNo, pageSize, sort);
        Page<Category> page = categoryDao.findAll(pageable);

        System.out.println("总记录数: " + page.getTotalElements());
        System.out.println("当前第几页: " + (page.getNumber() + 1));
        System.out.println("总页数: " + page.getTotalPages());
        System.out.println("当前页面的 List: " + page.getContent());
        System.out.println("当前页面的记录数: " + page.getNumberOfElements());
    }

    // 测试分页
    @Test
    public void testPaging3() {
        // org.springframework.data.repository.PagingAndSortingRepository 中的方法

        int pageNo = 1;
        int pageSize = 2;
        String sortName = "createdByUser";

        Category category = new Category();
        category.setCategoryName("apple");
        category.setCreatedByUser("qianwei");
        Page<Category> page = categoryService.search(category,pageNo,pageSize,sortName);

        System.out.println("总记录数: " + page.getTotalElements());
        System.out.println("当前第几页: " + (page.getNumber() + 1));
        System.out.println("总页数: " + page.getTotalPages());
        System.out.println("当前页面的 List: " + page.getContent());
        System.out.println("当前页面的记录数: " + page.getNumberOfElements());
    }

    @Test
    public void testDelete() {
        // org.springframework.data.repository.CrudRepository 中的方法
        List<Category> categoryList = categoryDao.findAllByFieldEq("categoryName", "ship");
        categoryService.delete(categoryList);
        List<Category> categoryList2 = categoryDao.findAllByFieldEq("categoryName", "ship");
        Assert.assertNull("is null", categoryList2);
    }

    /*
    *  --------- 自定义BaseDao方法 测试 --------------
    *  com.yougou.dao.jpa.base.BaseRepository
    */

    @Test
    public void findAllByFieldEq(){
        List<Category> categoryList = categoryDao.findAllByFieldEq("categoryName", "china");
        Assert.assertNotNull("对象不为空!",categoryList);
        Assert.assertEquals(categoryList.size(),1);
        Assert.assertEquals(categoryList.iterator().next().getId(),new Integer(1));
    }

    /*
    *  --------- 自定义方法 测试 --------------
    */


    @Test
    public void getByCategoryName(){
        Category category = categoryDao.getByCategoryName("china");
        Assert.assertNotNull("对象不为空!",category);
        Assert.assertEquals(category.getCategoryName(),"china");
        Assert.assertEquals(category.getCreatedByUser(),"fuwb");
    }

    @Test
    public void getByCategoryNameStartingWithAndIdLessThan(){
        List<Category> categoryList = categoryDao.getByCategoryNameStartingWithAndIdLessThan("ch",2);
        Assert.assertNotNull("对象不为空!",categoryList);
        Assert.assertEquals(categoryList.size(),1);
        Assert.assertEquals(categoryList.iterator().next().getCreatedByUser(),"fuwb");
    }

    @Test
    public void getByCategoryNameEndingWithAndIdLessThan(){
        String categoryName = "a";
        Integer id = 10;
        List<Category> categoryList = categoryDao.getByCategoryNameEndingWithAndIdLessThan(categoryName, id);
        Assert.assertNotNull("对象不为空!",categoryList);
        Assert.assertEquals(categoryList.size(),2);
    }

    @Test
    public void getByCategoryNameInAndCreationTimeLessThan(){
        List<String> categoryNames = Arrays.asList("china","apple","ship");
        List<Category> categoryList = categoryDao.getByCategoryNameInAndCreationTimeLessThan(categoryNames, new Date());
        Assert.assertNotNull("对象不为空!",categoryList);
        Assert.assertEquals(categoryList.size(),3);
        Assert.assertEquals(categoryList.iterator().next().getId(),new Integer(1));
    }

    @Test
    public void getByAddress_IdGreaterThan(){
      /*  List<Category> categoryList = categoryDao.getByAddress_IdGreaterThan(id);
        Assert.assertNotNull("对象不为空!",categoryList);
        Assert.assertEquals(categoryList.size(),1);
        Assert.assertEquals(categoryList.iterator().next().getId(),new Integer(1));*/
    }

    @Test
    public void getMaxIdCategory(){
        Category idCategory = categoryDao.getMaxIdCategory();
        Assert.assertNotNull("对象不为空!",idCategory);
        Assert.assertEquals(idCategory.getId(),new Integer(6));
    }

    @Test
    public void testQueryAnnotationParams1(){
        String createdByUser = "zhangping";
        String categoryName = "canada";
        List<Category> categoryList = categoryDao.testQueryAnnotationParams1(categoryName, createdByUser);
        Assert.assertNotNull("对象不为空!",categoryList);
        Assert.assertEquals(categoryList.size(),1);
        Assert.assertEquals(categoryList.iterator().next().getId(),new Integer(2));
    }

    @Test
    public void testQueryAnnotationParams2(){
        String createdByUser = "zhangping";
        String categoryName = "canada";
        List<Category> categoryList = categoryDao.testQueryAnnotationParams2(createdByUser, categoryName);
        Assert.assertNotNull("对象不为空!",categoryList);
        Assert.assertEquals(categoryList.size(),1);
        Assert.assertEquals(categoryList.iterator().next().getId(),new Integer(2));
    }

    @Test
    public void testQueryAnnotationLikeParam(){
        String createdByUser = "ing";
        String categoryName = "c";
        List<Category> categoryList = categoryDao.testQueryAnnotationLikeParam(categoryName, createdByUser);
        Assert.assertNotNull("对象不为空!",categoryList);
        Assert.assertEquals(categoryList.size(),4);
    }
    @Test
    public void testQueryAnnotationLikeParam2(){
        String createdByUser = "ing";
        String categoryName = "canada";
        List<Category> categoryList = categoryDao.testQueryAnnotationLikeParam2(createdByUser,categoryName);
        Assert.assertNotNull("对象不为空!",categoryList);
        Assert.assertEquals(categoryList.size(),3);
    }

    @Test
    public void getTotalCount(){
        long totalCount = categoryDao.getTotalCount();
        Assert.assertNotNull("对象不为空!",totalCount);
        Assert.assertEquals(totalCount,6);
    }

    @Test
    public void updateCategoryEmail(){
        String categoryName = "feng";
        Integer id = 1;
        categoryService.updateCategory(id,categoryName);
        Category category = categoryDao.findOne(1);
        Assert.assertNotNull("对象不为空!",category);
        Assert.assertEquals(category.getCategoryName(),categoryName);
    }


}
