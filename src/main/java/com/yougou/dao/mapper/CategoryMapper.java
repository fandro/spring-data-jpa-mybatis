package com.yougou.dao.mapper;

import com.yougou.model.Category;

/**
 * Created by fuwb on 16/8/9.
 */
public interface CategoryMapper {

    void insertCategory(Category category);
    void updateCategory(Category category);
    void getCategoryById(int id);
}
