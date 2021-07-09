package com.imooc.mall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author dsw
 * @Description  后台商品管理Controller
 * @create 2021-07-07 10:47
 */
@RestController
public class ProductAdminController {


    @Autowired
    ProductService productService;


    @ApiOperation("后台添加商品")
    @PostMapping("admin/add/product")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq){
        productService.addProduct();
        return ApiRestResponse.success();
    }


    @ApiOperation("上传图片接口")
    @PostMapping("admin/upload/file")
    public ApiRestResponse uploadFile(@Param url){
        return ApiRestResponse.success();
    }

    @ApiOperation("更新商品接口")
    @PostMapping("admin/update/product")
    public ApiRestResponse updateProduct(@RequestBody AddProductReq){
        return ApiRestResponse.success();
    }


    @ApiOperation("删除商品")
    @PostMapping("admin/delete/product")
    public ApiRestResponse deleteProduct(Integer id){
        return ApiRestResponse.success();
    }


    @ApiOperation("批量上架/下架商品")
    @PostMapping("admin/product/batchUpdateSellingStatus")
    public ApiRestResponse batchUpdateSellingStatus(List list){
        return ApiRestResponse.success();
    }

    @ApiOperation("后台商品列表")
    @PostMapping("admin/product/list")
    public ApiRestResponse productListForAdmin(Integer PageNum, Integer PageSize){
        Page<Object> objects = PageHelper.startPage(PageNum, PageSize);
        return ApiRestResponse.success();
    }

    @ApiOperation("前台商品列表")
    @PostMapping("product/list")
    public ApiRestResponse productListForCustomer(){
        return ApiRestResponse.success();
    }

    @ApiOperation("商品详情")
    @PostMapping("product/detail")
    public ApiRestResponse productDetail(Integer id){

        return ApiRestResponse.success();
    }
}
