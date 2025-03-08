package com.wjh.picturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 
 * @Date 2025/2/4 23:21:22
 * @Description 用户登录请求
 */

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -4708284366341984863L;
    /**
      用户账户
     */
    private String userAccount;

    /**
      用户密码
     */
    private String userPassword;
}
