package com.wjh.picturebackend.model.dto.picture;

import lombok.Data;

/**
 * @author 
 */
@Data
public class PictureUploadByBatchRequest {  
  
    /**  
     * 搜索词  
     */  
    private String searchText;  
  
    /**  
     * 抓取数量  
     */  
    private Integer count;
    /**
     * 名称前缀
     */
    private String namePrefix;

}
