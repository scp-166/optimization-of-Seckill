package com.nekosighed.miaosha.controller.exceptionhandler;

import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕获到 Exception
     * 设置响应状态为200
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK) // 指定返回状态码为 200
    public CommonReturnType getException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ex.printStackTrace();
        Map<String, Object> errData;
        // 如果是属于业务异常， 封装业务异常的内容
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            errData = fillResponse(businessException.getCode(), businessException.getMsg());
        } else if (ex instanceof ServletRequestBindingException) {
            errData = fillResponse(BusinessErrorEnum.UNKNOWN_ERROR.getCode(), "url绑定访问方法错误");
        } else if (ex instanceof NoHandlerFoundException) {
            errData = fillResponse(BusinessErrorEnum.UNKNOWN_ERROR.getCode(), "没有找到对应的访问路径");
        } else {
            // 否则封装为未知错误异常
            errData = fillResponse(BusinessErrorEnum.UNKNOWN_ERROR.getCode(),
                    BusinessErrorEnum.UNKNOWN_ERROR.getMsg());
        }
        return CommonReturnType.fail(errData);
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
