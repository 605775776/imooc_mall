package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.vo.CategoryVO;

import java.util.List;

/**
 * Description:   分类目录service
 * Author: dsw
 * date:  2021/7/6 22:36
 */

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    //      前台分页展示目录列表
    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
