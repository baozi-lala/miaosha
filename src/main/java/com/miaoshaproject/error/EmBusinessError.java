package com.miaoshaproject.error;

/**
 * @author bao
 * @version 1.00
 * @time 2020/5/12 21:38
 */
public enum EmBusinessError implements CommonError{
    //通⽤错误类型10001,错误码修改为10001因为属于int在浏览器里只会保留一位
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),
    //20000开头为⽤户信息相关错误定义
    USER_NOT_EXIST(20001, "⽤户不存在"),
    USER_LOOGIN_FAIL(20002, "用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003, "用户还未登陆"),

    // 30000开头为交易信息相关错误定义
    STOCK_NOT_ENOUGH(30001,"库存不足")
    ;

    private EmBusinessError(int errCode, String errMsg){
        this.errCode=errCode;
        this.errMsg=errMsg;
    }
    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;
    }
}
