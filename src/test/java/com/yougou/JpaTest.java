package com.yougou;

import com.yougou.dao.jpa.CategoryDao;
import com.yougou.domain.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 测试jpa相关方法
 * Created by fuwb on 16/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class JpaTest {

    @Autowired
    private CategoryDao categoryDao;

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

    /**
     * 测试 BaseDao中的方法
     */
    @Test
    public void findAllByFieldEq(){
        List<Category> categoryList = categoryDao.findAllByFieldEq("categoryName", "china");
        Assert.assertNotNull("对象不为空!",categoryList);
        Assert.assertEquals(categoryList.size(),1);
        Assert.assertEquals(categoryList.iterator().next().getId(),new Integer(1));
    }
}
