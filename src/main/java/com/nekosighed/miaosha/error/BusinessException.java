package com.nekosighed.miaosha.error;

/**
 * 包装器业务异常类实现
 */
public class BusinessException extends RuntimeException implements CommonError {
    private CommonError commonError;

    /**
     * 接收保存同样实现了 CommonError 的 BusinessErrorEnum
     *
     * @param commonError
     */
    public BusinessException(CommonError commonError) {
        super(commonError.getMsg());
        this.commonError = commonError;
    }

    /**
     * 接收保存同样实现了 CommonError 的 BusinessErrorEnum, 并且设置新的错误描述信息
     *
     * @param commonError
     * @param newMsg
     */
    public BusinessException(CommonError commonError, String newMsg) {
        super(newMsg);
        this.commonError = commonError;
        this.commonError.setMsg(newMsg);
    }

    @Override
    public int getCode() {
        return this.commonError.getCode();
    }

    @Override
    public String getMsg() {
        return this.commonError.getMsg();
    }

    @Override
    public CommonError setMsg(String msg) {
        this.commonError.setMsg(msg);
        return this.commonError;
    }
}
