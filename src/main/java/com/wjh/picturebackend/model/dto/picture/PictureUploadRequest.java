package com.wjh.picturebackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 
 * @Date 2025/2/5 19:54:54
 * @Description 图片上传请求
 */
@Data
public class PictureUploadRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 图片 id（用于修改）
     */
    private Long id;
    /**
     * 文件地址
     */
    private String fileUrl;
    /**
     * 图片名称
     */
    private String picName;
    /**
     * 空间 id
     */
    private Long spaceId;
}