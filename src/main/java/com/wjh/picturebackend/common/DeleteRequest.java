package com.wjh.picturebackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 
 * @Date 2025/2/2 23:17:59
 * @Description 通用的删除请求类
 */
@Data
public class DeleteRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private long id;
}
