package com.wjh.picturebackend.common;

import com.wjh.picturebackend.exception.ErrorCode;
import lombok.Data;

/**
 * @Author 
 * @Date 2025/2/2 23:00:26
 */
@Data
public class ResultUtils {
    /**
     * 成功
     * @param data 数据
     * @return 响应
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data , "ok");
    }
    /**
     * 失败
     * @param errorCode 错误码
     * @return 响应
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }
    /**
     * 失败
     * @param errorCode 错误码
     * @param message 错误信息
     * @return 响应
     */
    public static BaseResponse<?> error(int errorCode, String message) {
        return new BaseResponse<>(errorCode,null, message);
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @param message 错误信息
     * @return 响应
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(),null, message);
    }
}
