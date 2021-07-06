package com.imooc.mall.exception;

/**
 * @author dsw
 * @Description          service层统一异常
 * @create 2021-07-06 15:33
 */
public class ImoocMallException extends RuntimeException{

    private final Integer code;
    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ImoocMallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ImoocMallException(ImoocMallExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }
}
