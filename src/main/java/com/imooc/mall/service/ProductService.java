package com.imooc.mall.service;

import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;

/**
 * Description:
 * Author: dsw
 * date:  2021/7/9 22:53
 */

public interface ProductService {


    void addProduct(AddProductReq addProductReq);

    void updateProduct(Product updateProduct);

    void deleteProduct(Integer id);
}
