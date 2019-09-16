package com.nekosighed.miaosha.response;

import com.nekosighed.miaosha.enumtype.ResponseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonReturnType {
    private String status;
    private Object data;

    public static CommonReturnType success(Object data){
        return create(ResponseStatusEnum.SUCCESS.getCode(), data);
    }

    public static CommonReturnType fail(Object errMsg){
        return create(ResponseStatusEnum.FAIL.getCode(), errMsg);
    }

    public static CommonReturnType create(String status, Object data){
        return new CommonReturnType(status, data);
    }
}
