package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.ProductListReq;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Description:
 * Author: dsw
 * date:  2021/7/9 22:53
 */

public interface ProductService {


    void addProduct(AddProductReq addProductReq);

    void updateProduct(Product updateProduct);

    void deleteProduct(Integer id);

    void batchUpdateSellStatus(@RequestParam Integer[] ids, @RequestParam Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    // 前台商品详情
    Product detial(Integer id);


    PageInfo list(ProductListReq productListReq);
}
