package com.wjh.picturebackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@Data
public class SearchPictureByColorRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 图片主色调
     */
    private String picColor;
    /**
     * 空间 id
     */
    private Long spaceId;
}
