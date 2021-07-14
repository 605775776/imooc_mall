package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.model.vo.OrderVO;
import com.imooc.mall.service.CartService;
import com.imooc.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dsw
 * @Description     订单Contorller
 * @create 2021-07-13 12:44
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;


    @PostMapping("/create")
    @ApiOperation("创建订单")
    public ApiRestResponse create(@RequestBody CreateOrderReq createOrderReq){
        String orderNo = orderService.create(createOrderReq);
        return ApiRestResponse.success(orderNo);

    }

    @GetMapping("/detail")
    @ApiOperation("前台订单详情")
    public ApiRestResponse orderDetail(@RequestParam String orderNo){
        OrderVO orderVO = orderService.detail(orderNo);
        return ApiRestResponse.success(orderVO);
    }

    @GetMapping("/list")
    @ApiOperation("前台订单列表")
    public ApiRestResponse list(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        PageInfo pageInfo = orderService.listForCustomer(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }


    @PostMapping("/cancel")
    @ApiOperation("取消订单")
    public void cancelOrder(@RequestParam Integer orderNo){

    }

    @PostMapping("/qrcode")
    @ApiOperation("取消订单")
    public void createQrcode(@RequestParam Integer orderNo){

    }

    @GetMapping("/pay")
    @ApiOperation("支付订单")
    public void payOrder(@RequestParam Integer orderNo){

    }

    @PostMapping("/admin/order/list")
    @ApiOperation("后台订单列表")
    public void adminList(@RequestParam Integer pageNum, @RequestParam Integer pageSize){

    }


    @PostMapping("/admin/order/delivered")
    @ApiOperation("订单发货")
    public void orderDelivered(@RequestParam Integer orderNo){

    }

    @PostMapping("/finish")
    @ApiOperation("订单完结")
    public void orderFinish(@RequestParam Integer orderNo){

    }


}
