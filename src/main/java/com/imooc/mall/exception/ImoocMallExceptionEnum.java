package com.imooc.mall.exception;

import org.omg.CORBA.OBJECT_NOT_EXIST;

/**
 * @author dsw
 * @Description           异常枚举
 * @create 2021-07-06 14:34
 */
public enum ImoocMallExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),
    PASSWORD_TOO_SHORT(10003,"密码长度不得少于8位"),
    NAME_EXISTED(10004, "不允许重名"),
    INSERT_FAILED(10005, "插入失败，请重试"),
    WRONG_PASSWORD(10006,"密码错误"),
    NEED_LOGIN(10007,"用户未登录"),
    UPDATE_FAILED(10008,"更新失败"),
    NEED_ADMIN(10009,"非管理员无权操作"),
    NAME_NOT_NULL(10010, "参数不能为空"),
    CREATE_FAILED(10011, "新增数据失败请重试"),
    REQUEST_PARAM_ERROR(10012,"参数错误"),
    DELETE_FAILED(10013,"删除失败"),
    MKDIR_FAILED(10014,"文件夹创建失败"),
    UPLOAD_FAILED(10015,"图片上传失败"),
    NOT_SALE(10016,"商品状态不可售"),
    NOT_ENOUGH(10017,"商品库存不足"),
    CART_EMPTY(10018,"购物车已勾选的商品为空"),
    NO_ENUM(10019, "未找到对应的枚举类"),
    NO_ORDER(10020,"订单不存在"),
    NOT_YOUR_ORDER(10021,"订单不是本人"),
    WRONG_ORDER_STATUS(10022, "订单状态不符"),



    SYSTEM_ERROR(20000, "系统异常"),
    OBJECT_NOT_EXIST(30000, "未查询到对象");
    Integer code;

    String msg;

    ImoocMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
