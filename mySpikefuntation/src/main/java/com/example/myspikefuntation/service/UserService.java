package com.example.myspikefuntation.service;

import com.example.myspikefuntation.error.BusinessException;
import com.example.myspikefuntation.mbg.dao.dataObject.UserDO;
import com.example.myspikefuntation.service.model.UserModel;

/**
 * @author wangzhe
 * @version 1.0
 * @ClassName UserService
 * @create 2021-08-03 21:50
 * @description
 */
public interface UserService {
    /**
     * 获取用户
     * @Date 21:12 2021/8/4
     * @param userId
     * @return  com.example.myspikefuntation.service.model.UserModel
     **/
    public UserModel getUserById(Integer userId);

    /**
     * 用户注册流程
     * @Date 21:12 2021/8/4
     * @param userModel
     * @return  void
     * @throws BusinessException
     **/
    void register(UserModel userModel) throws BusinessException;

    /**
     * 校验用户登录信息是否正确
     * @Date 11:34 2021/8/5
     * @param telephone 用户手机号
     * @param EncryptPassword 用户加密后的密码
     * @return  void
     * @throws BusinessException
     **/
    UserModel validateLogin(String telephone, String EncryptPassword) throws BusinessException;
}
