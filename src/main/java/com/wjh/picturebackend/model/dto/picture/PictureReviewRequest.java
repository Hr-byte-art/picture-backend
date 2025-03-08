package com.wjh.picturebackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;
/**
 * @Author 
 * @Date 2025/2/7 23:50:47
 * @Description 图片审核请求
 */
@Data
public class PictureReviewRequest implements Serializable {
  
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 状态：0-待审核, 1-通过, 2-拒绝
     */
    private Integer reviewStatus;
    /**
     * 审核信息
     */
    private String reviewMessage;
}
