package com.wjh.picturebackend.common;

import com.wjh.picturebackend.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 
 * @Date 2025/2/2 22:47:49
 */

@Data
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;

    public BaseResponse(int code, T data) {
        this(code , data , "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode() , null , errorCode.getMessage());
    }

}
