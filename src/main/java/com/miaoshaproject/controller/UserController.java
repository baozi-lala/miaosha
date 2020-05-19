package com.miaoshaproject.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaoshaproject.controller.viewobject.UserVO;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.dataobject.userPasswordDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //⽤户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telephone") String telephone,
                                     @RequestParam(name="otpCode") String otpCode,
                                     @RequestParam(name="name") String name,
                                     @RequestParam(name="gender") String gender,
                                     @RequestParam(name="age") String age,
                                     @RequestParam(name="password") String password)  throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException  {

        //验证⼿机号和对应的otpCode相符合
        String inSessionOtpCode=(String)this.httpServletRequest.getSession().getAttribute(telephone);
        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }

        //⽤户的注册流程
        UserModel userModel=new UserModel();
        userModel.setName(name);
        userModel.setAge(Integer.valueOf(age));
        userModel.setGender(Byte.valueOf(gender));
        userModel.setTelephone(telephone);
        userModel.setRegisterMode("byphone");

        userModel.setEncrptPassword(this.EncodeByMd5(password));


        //密码加密

        //注册
        userService.register(userModel);
        return CommonReturnType.create(null);
    }
    //⽤户注册接口
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="telephone") String telephone,
                                  @RequestParam(name="password") String password)  throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException  {

        //⼊参校验
        String inSessionOtpCode=(String)this.httpServletRequest.getSession().getAttribute(telephone);
        if(StringUtils.isEmpty(telephone)||StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);

        }
        //⽤户登录服务， ⽤来校验⽤户登录是否合法
        //⽤户加密后的密码
        UserModel userModel=userService.validateLogin(telephone,this.EncodeByMd5(password));

        //将登陆凭证加⼊到⽤户登录成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        return CommonReturnType.create(null);
    }

    //⽤户获取otp短信接⼝
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST, RequestMethod.GET},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telephone") String telephone)  throws BusinessException {
        //需要按照⼀定的规则⽣成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode=String.valueOf(randomInt);

        //将OTP验证码同对应⽤户的⼿机号关联， 使⽤httpsession的⽅式绑定⼿机号与OTPCDOE
        httpServletRequest.getSession().setAttribute(telephone,otpCode);

        //将OTP验证码通过短信通道发送给⽤户， 省略
        System.out.println("telphone=" + telephone + "&otpCode=" + otpCode);

        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id") Integer id)  throws BusinessException {
        //调⽤service服务获取对应id的⽤户对象并返回给前端
        UserModel userModel=userService.getUserById(id);

        //若获取的对应⽤户信息不存在
        if(userModel==null){
            userModel.setEncrptPassword("123");
//            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        //将核⼼领域模型⽤户对象转化为可供UI使⽤的viewobject
        UserVO userVO=convertFromModel(userModel);
        //返回通⽤对象
        return CommonReturnType.create(userVO);

    }
    private UserVO convertFromModel(UserModel userModel){
        if(userModel==null)return null;
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;

    }
    //密码加密
    public String EncodeByMd5(String str) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        //确定计算⽅法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
}
