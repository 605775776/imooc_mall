package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.ProductListReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dsw
 * @Description  前台商品Controller
 * @create 2021-07-10 16:21
 */
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("product/detail")
    public ApiRestResponse<Object> detail(@RequestParam Integer id){
        Product product = productService.detial(id);
        return ApiRestResponse.success(product);
    }

    @PostMapping("product/list")
    public ApiRestResponse list(ProductListReq productListReq){
        PageInfo list = productService.list(productListReq);
        return ApiRestResponse.success(list);
    }


}
