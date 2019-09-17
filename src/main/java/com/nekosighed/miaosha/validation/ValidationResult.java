package com.nekosighed.miaosha.validation;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.buf.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证结果类
 *
 * @author lyl
 */
@Getter
@Setter
public class ValidationResult {
    /**
     * 是否有错误的标志
     */
    private boolean haveErrors = false;

    /**
     * 错误接口
     */
    private Map<String, String> errorsMap = new HashMap<>();

    /**
     * 通过格式化字符串信息获取错误结果的 msg 方法
     *
     * @return 带有错误结果的String， 如果有多个错误，则以 , 分割
     */
    public String getErrorsMsg() {
        return StringUtils.join(errorsMap.values(), ',');
    }
}
