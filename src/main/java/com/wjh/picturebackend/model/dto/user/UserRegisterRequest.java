package com.wjh.picturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 
 * @Date 2025/2/4 23:21:22
 * @Description 用户注册请求
 */

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -78920597072903264L;

    /**
      用户账户
     */
    private String userAccount;

    /**
      用户密码
     */
    private String userPassword;

    /**
      校验密码
     */
    private String checkPassword;
}
