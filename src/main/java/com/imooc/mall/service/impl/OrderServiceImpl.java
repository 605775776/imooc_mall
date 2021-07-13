package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.filter.UserFilter;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.OrderItemMapper;
import com.imooc.mall.model.dao.OrderMapper;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Order;
import com.imooc.mall.model.pojo.OrderItem;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import com.imooc.mall.service.OrderService;
import com.imooc.mall.util.OrderCodeFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.awt.image.ImagingOpException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author dsw
 * @Description
 * @create 2021-07-13 12:47
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    CartService cartService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public String create(CreateOrderReq createOrderReq){
        // 拿到用户id
        Integer userId = UserFilter.currentUser.getId();
        // 拿到购物车信息
        List<CartVO> cartVOList = cartService.list(userId);
        ArrayList<CartVO> cartVOListTemp = new ArrayList<>();

        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO =  cartVOList.get(i);
            if (cartVO.getSelected().equals(Constant.Cart.CHECKED)) {
                cartVOListTemp.add(cartVO);
            }
        }
        cartVOList = cartVOListTemp;
        // 购物车没有已勾选的商品 报错  被选中的
        if (CollectionUtils.isEmpty(cartVOList)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CART_EMPTY);
        }
        // 判断商品是否存在 上下架状态 库存
        validSaleStatusAndStock(cartVOList);
        // 购物车对象 转化为订单item能接受的对象
        List<OrderItem> orderItemList = cartVOListToOrderItemList(cartVOList);
        // 更新库存信息 校验
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem =  orderItemList.get(i);
            System.out.println("*****************");
            System.out.println(orderItem.getQuantity());
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            System.out.println("========");
            System.out.println(product.getStock());
            System.out.println(orderItem.getQuantity());
            System.out.println("========");
            int stock = product.getStock() - orderItem.getQuantity();
            if ((stock < 0)) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
            }
            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);

            // 加入订单的商品从购物车中删除
            cleanCart(cartVOList);
        }
        // 生成订单 处理订单号 规则
        Order order = new Order();
        String orderNo = OrderCodeFactory.getOrderCode(Long.valueOf(userId));
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice(orderItemList));

        // 收件信息
        order.setReceiverName(createOrderReq.getReceiverName());
        order.setReceiverMobile(createOrderReq.getReceiverMobile());
        order.setReceiverAddress(createOrderReq.getReceiverAddress());
        // 订单状态初始化
        order.setOrderStatus(Constant.OrderStatusEnum.NOT_PAID.getCode());

        // 包邮
        order.setPostage(0);
        order.setPaymentType(1);

        // 插入到order表中
        int i = orderMapper.insertSelective(order);
        if (i == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
        }
        // 循环保存每个商品到orderItem表
        for (int j = 0; j < orderItemList.size(); j++) {
            OrderItem orderItem =  orderItemList.get(j);
            orderItem.setOrderNo(order.getOrderNo());
            int count = orderItemMapper.insertSelective(orderItem);
            if (count == 0){
                throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
            }
        }
        return orderNo;
    }



    private void validSaleStatusAndStock(List<CartVO> cartVOList) {
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            //判断商品是否存在 且上架状态
            if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
            }
            // 判断商品库存
            if (cartVO.getQuantity() > product.getStock()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
            }

        }
    }
        private List<OrderItem> cartVOListToOrderItemList(List<CartVO> cartVOList) {
            List<OrderItem> orderItemList = new ArrayList<>();
            for (int i = 0; i < cartVOList.size(); i++) {
                CartVO cartVO =  cartVOList.get(i);
                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(cartVO.getProductId());
                // 记录商品快照信息
                orderItem.setProductName(cartVO.getProductName());
                orderItem.setProductImg(cartVO.getProductImage());
                orderItem.setUnitPrice(cartVO.getPrice());
                orderItem.setQuantity(cartVO.getQuantity());
                orderItem.setTotalPrice(cartVO.getTotalPrice());
                orderItemList.add(orderItem);
            }
            return orderItemList;
        }

        // 扣完库存 清空购物车
        private void cleanCart(List<CartVO> cartVOList) {
            for (int i = 0; i < cartVOList.size(); i++) {
                CartVO cartVO =  cartVOList.get(i);
                cartMapper.deleteByPrimaryKey(cartVO.getId());
            }
    }
    // 所有商品总价
    private Integer totalPrice(List<OrderItem> orderItemList) {
        Integer totalPrice = 0;
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem =  orderItemList.get(i);
            totalPrice += orderItem.getTotalPrice();

        }
        return totalPrice;

    }






}
