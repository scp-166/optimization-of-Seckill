package com.nekosighed.miaosha.validation;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


/**
 * 验证器
 *
 * @author lyl
 */
@Component
public class ValidatorImpl implements InitializingBean {
    private Validator validator;

    /**
     * 在 bean 实例化后进行操作
     * 绑定 validator 为 工厂实例化的 validator
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 实现校验方法并且返回校验结果
     *
     * @param bean
     * @return
     */
    public ValidationResult validate(Object bean) {
        final ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolations = this.validator.validate(bean);
        // 验证有错误
        if (constraintViolations.size() > 0) {
            // 设置为有错误
            validationResult.setHaveErrors(true);
            // 设置错误信息
            constraintViolations.forEach(constraintViolation -> {
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                validationResult.getErrorsMap().put(propertyName, errMsg);
            });
        }
        return validationResult;
    }
}
