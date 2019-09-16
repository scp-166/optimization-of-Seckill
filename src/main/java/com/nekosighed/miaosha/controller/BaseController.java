package com.nekosighed.miaosha.controller;

import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    /**
     * 捕获到 Exception
     * 设置响应状态为200
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonReturnType getException(HttpServletRequest request, Exception ex) {
        Map<String, Object> errData;
        // 如果是属于业务异常， 封装业务异常的内容
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            errData = fillResponse(businessException.getCode(), businessException.getMsg());
        } else {
            // 否则封装为未知错误异常
            errData = fillResponse(BusinessErrorEnum.UNKNOWN_ERROR.getCode(),
                    BusinessErrorEnum.UNKNOWN_ERROR.getMsg());
        }
        return CommonReturnType.success(errData);
    }

    /**
     * 封装错误信息
     *
     * @param errCode
     * @param errMsg
     * @return
     */
    private Map<String, Object> fillResponse(int errCode, String errMsg) {
        return new HashMap<String, Object>(2) {
            {
                put("errCode", errCode);
                put("errMsg", errMsg);
            }
        };
    }
}
