package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Cart;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Author: dsw
 * date:  2021/7/12 14:51
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVO> list(Integer userId){
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        System.out.println(userId);
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO =  cartVOS.get(i);
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;

    }


    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count){
        System.out.println(userId);

        validProduct(productId,count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 这个商品之前不在购物车里 需要新增记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            System.out.println("=========");
            System.out.println(cart.getId());
            cart.setQuantity(count);
            // 默认被选中
            cart.setSelected(Constant.Cart.CHECKED);
            int i = cartMapper.insertSelective(cart);
            if (i == 0) {
                throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
            }
        }else {
            // 这个商品已经在购物车里了 数量相加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            int i = cartMapper.updateByPrimaryKeySelective(cartNew);
            if (i == 0) {
                throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
            }
        }
        return this.list(userId);
    }

    private void validProduct(Integer productId, Integer count){
        Product product = productMapper.selectByPrimaryKey(productId);

        //判断商品是否存在 且上架状态
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
        }

        // 判断商品库存
        if (count > product.getStock()) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
        }
    }

    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count){
        validProduct(productId,count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 必须购物车里有这个商品才能更新数量 无法更新
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }else {
            // 这个商品已经在购物车里了 数量更新
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            int i = cartMapper.updateByPrimaryKeySelective(cartNew);
            if (i == 0) {
                throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
            }
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 必须购物车里有这个商品才能删除  否则无法删除
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }else{
            cartMapper.deleteByPrimaryKey(cart.getId());
            System.out.println("============");

        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 不在购物车 无法选中
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
        cartMapper.selectOrNot(userId, productId, selected);
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectAllOrNot(Integer userId, Integer selected){
        // 改变选中状态
        cartMapper.selectOrNot(userId, null, selected);
        return this.list(userId);


    }
}
