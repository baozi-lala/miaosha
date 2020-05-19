package com.miaoshaproject.error;

/**
 * @author bao
 * @version 1.00
 * @time 2020/5/12 21:43
 */
public class BusinessException extends Exception implements CommonError {
    private CommonError commonError;
    public BusinessException(CommonError commonError){
        super();
        this.commonError=commonError;
    }
    public BusinessException(CommonError commonError,String errMsg){
        super();
        this.commonError=commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
