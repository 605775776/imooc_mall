package com.imooc.mall.service.impl;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: dsw
 * date:  2021/7/9 22:52
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public void addProduct(){
        int product = productMapper.insertSelective();

    }

    public void updateProduct(Integer id){
        int i = productMapper.updateByPrimaryKeySelective(id);
        if (i == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }

    }
}
