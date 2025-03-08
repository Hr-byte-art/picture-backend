package com.wjh.picturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 
 * @Date 2025/2/5 01:21:22
 * @Description 用户添加请求
 */
@Data
public class UserAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 用户简介
     */
    private String userProfile;
    /**
     * 用户角色: user, admin
     */
    private String userRole;
}
