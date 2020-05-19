package com.miaoshaproject.response;

/**
 * @author bao
 * @version 1.00
 * @time 2020/5/12 20:46
 */
public class CommonReturnType {
    //表明对应请求的返回处理结果“success”或“fail”
    private String status;
    //若status=success， 则data内返回前端需要的json数据
    //若status=fail， 则data内使⽤通⽤的错误码格式
    private Object data;


    //定义⼀个通⽤的创建⽅法
    public static CommonReturnType create(Object result){
        return  CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(Object result, String status){
        CommonReturnType type=new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
