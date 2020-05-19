package com.miaoshaproject.error;

/**
 * @author bao
 * @version 1.00
 * @time 2020/5/12 21:37
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);

}
