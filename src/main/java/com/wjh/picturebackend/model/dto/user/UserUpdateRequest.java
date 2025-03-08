package com.wjh.picturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 
 * @Date 2025/2/5 01:21:22
 * @Description 用户修改请求
 */
@Data
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 简介
     */
    private String userProfile;
    /**
     * 用户角色：user/admin
     */
    private String userRole;
}
