package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Description:
 * Author: dsw
 * date:  2021/7/6 22:14
 */
@RestController
public class CategoryController {

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @PostMapping("/admin/category/add")
    public ApiRestResponse addCategory(HttpSession session,
                                    @RequestBody AddCategoryReq addCategoryReq){
        if (addCategoryReq.getName() == null || addCategoryReq.getType() == null || addCategoryReq.getOrderNum() == null || addCategoryReq.getParentId() == null){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NAME_NOT_NULL);
        }
        User currentUser = (User)session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        // 校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole) {
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        }else
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
    }
    }
