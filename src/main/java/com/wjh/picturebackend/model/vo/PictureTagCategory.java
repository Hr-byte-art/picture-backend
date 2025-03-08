package com.wjh.picturebackend.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author 
 * @Date 2025/2/5 21:25:53
 * @Description 图片标签分类
 */
@Data
public class PictureTagCategory {
    /**
     * 标签列表
     */
    private List<String> tagList;

    /**
     * 分类列表
     */
    private List<String> categoryList;
}
