package com.imooc.mall.service;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.model.dao.UserMapper;
import com.imooc.mall.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author dsw
 * @Description
 * @create 2021-07-06 10:57
 */

public interface UserService {


    User getUser();

    void register(String username, String password) throws ImoocMallException;


    User login(String username, String password) throws ImoocMallException;

    void updateInformation(User user) throws ImoocMallException;

    boolean checkAdminRole(User user);
}
