package com.imooc.mall.service;

import com.imooc.mall.model.request.CreateOrderReq;

/**
 * @author dsw
 * @Description
 * @create 2021-07-13 12:48
 */
public interface OrderService {


    String create(CreateOrderReq createOrderReq);
}
