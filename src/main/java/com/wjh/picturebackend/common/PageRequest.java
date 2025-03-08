package com.wjh.picturebackend.common;

import lombok.Data;

/**
 * @Author 
 * @Date 2025/2/2 23:16:07
 *
 * 通用的分页请求类
 */
@Data
public class PageRequest {
    /**
     * 当前页码
     */
    private int current = 1;
    /**
     * 页面大小
     */
    private int pageSize = 10;
    /**
     * 排序字段
     */
    private String sortField;
    /**
     * 排序方式(默认升序)
     */
    private String sortOrder = "descend";
}
