package com.yougou.dao.mapper;

import com.yougou.domain.Category;

/**
 * mybatis 会把同目录同名称的类和xml文件自动绑定,即接口中方法对应xml中配置sql的Id名
 * CategoryMapper.java接口中方法对应CategoryMapper.xml中配置sql的Id名
 * Created by fuwb on 16/8/9.
 */
public interface CategoryMapper {

    void insertCategory(Category category);
    void updateCategory(Category category);
    void getCategoryById(int id);
}
