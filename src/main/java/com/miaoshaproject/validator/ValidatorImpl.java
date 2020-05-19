package com.miaoshaproject.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

/**
 * @author bao
 * @version 1.00
 * @time 2020/5/14 17:37
 */
@Component
public class ValidatorImpl implements InitializingBean{
    private javax.validation.Validator validator;

    //实现校验方法并返回校验结果
    public ValidationResult validate(Object bean) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0) {
            //有错误
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation ->{
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrMsgMap().put(propertyName, errMsg);
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过⼯⼚的初始化⽅式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
