package com.imooc.mall.service;

import com.imooc.mall.model.vo.CartVO;

import java.util.List;

/**
 * Description:   购物车service
 * Author: dsw
 * date:  2021/7/6 22:36
 */

public interface CartService {

    List<CartVO> list(Integer userId);

    List<CartVO> add(Integer userId, Integer productId, Integer count);

    List<CartVO> update(Integer userId, Integer productId, Integer count);

    List<CartVO> delete(Integer userId, Integer productId);
}
