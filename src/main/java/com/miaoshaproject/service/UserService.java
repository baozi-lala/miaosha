package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;

public interface UserService {
     UserModel getUserById(Integer id);
     UserModel register(UserModel userModel) throws BusinessException;

     /*
    telphone:⽤户注册⼿机
    encrptPassowrd:⽤户加密后的密码
    */
     UserModel validateLogin(String telephone,String encrptPassword) throws BusinessException;
}
