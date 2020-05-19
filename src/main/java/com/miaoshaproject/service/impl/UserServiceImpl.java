package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.userPasswordDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.dataobject.userPasswordDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserDOMapper userDOMapper;
    @Autowired
    private userPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        //调⽤UserDOMapper获取到对应的⽤户dataobject
        UserDO userDO= userDOMapper.selectByPrimaryKey(id);
        if(userDO==null)return null;
        //通过⽤户id获取对应的⽤户加密密码信息
        userPasswordDO userPasswordDO=userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO,userPasswordDO);
    }

    private UserModel convertFromDataObject(UserDO userDO,userPasswordDO userPasswordDO){
        if(userDO==null)return null;
        UserModel userModel=new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if(userPasswordDO!=null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return  userModel;

    }

    @Override
    @Transactional//声明事务
    public UserModel register(UserModel userModel) throws BusinessException {
//        //校验
//        if(userModel==null){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
//        if(StringUtils.isEmpty(userModel.getName())
//            ||userModel.getGender()==null
//            ||userModel.getAge()==null
//            ||StringUtils.isEmpty(userModel.getTelephone())){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,
                    result.getErrMsg());
        }
        //实现model->dataobject⽅法
        UserDO userDO=convertFromModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "⼿机号已注册");
        }


        userModel.setId(userDO.getId());

        userPasswordDO userPasswordDO=convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);

        return null;
    }

    @Override
    public UserModel validateLogin(String telephone, String encrptPassword) throws BusinessException {
        //通过⽤户⼿机获取⽤户信息
        UserDO userDO=userDOMapper.selectByTelephone(telephone);
        if(userDO==null){
            throw new BusinessException(EmBusinessError.USER_LOOGIN_FAIL);
        }
        userPasswordDO userPasswordDO= userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel=convertFromDataObject(userDO,userPasswordDO);
        //⽐对⽤户信息内加密的密码是否和传输进来的密码相匹配
        if(StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOOGIN_FAIL);
        }
        return userModel;
    }

    private UserDO convertFromModel(UserModel userModel){
        if(userModel==null)return null;
        UserDO userDO=new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return  userDO;
    }
    private userPasswordDO convertPasswordFromModel(UserModel userModel){
        if(userModel==null)return null;
        userPasswordDO userPasswordDO=new userPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return  userPasswordDO;
    }

}
