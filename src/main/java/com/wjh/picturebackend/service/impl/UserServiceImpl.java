package com.wjh.picturebackend.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjh.picturebackend.constant.UserConstant;
import com.wjh.picturebackend.exception.BusinessException;
import com.wjh.picturebackend.exception.ErrorCode;
import com.wjh.picturebackend.mapper.UserMapper;
import com.wjh.picturebackend.model.dto.user.UserQueryRequest;
import com.wjh.picturebackend.model.entity.User;
import com.wjh.picturebackend.model.enums.UserRoleEnum;
import com.wjh.picturebackend.model.vo.LoginUserVO;
import com.wjh.picturebackend.model.vo.UserVO;
import com.wjh.picturebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-02-04 23:05:52
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    /**
     * 用户注册
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param checkPassword 确认密码
     * @return 新用户id
     */
    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {
    // 1 校验参数
        if(StrUtil.hasBlank(userAccount , userPassword , checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR , "参数不能为空");
        }
        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR , "用户账号过短");
        }
        if(userPassword.length() < 8 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR , "用户密码过短");
        }
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR , "两次输入的密码不一致");
        }
    // 2 检查用户账号是否个数据库中已有的重复
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserAccount, userAccount);
        long count = this.count(wrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR , "用户账号重复");
        }
    // 3 密码一定要加密
        String encryptedPassword = getEncryptPassword(userPassword);
    // 4 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        user.setUserName("图友");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean saveResult = this.save(user);
        if (!saveResult){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR , "注册失败");
        }
        return user.getId();
    }

    /**
     * 获取当前登录用户信息
     * @param request 请求
     * @return 当前登录用户信息
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) attribute;
        if(currentUser == null || currentUser.getId() == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库中查询(追求性能的话可以注释，直接返回上述结果)
        User currentUserFromDB = this.getById(currentUser.getId());
        if(currentUserFromDB == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1 校验参数
        if(StrUtil.hasBlank(userAccount , userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR , "参数不能为空");
        }
        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR , "用户账号过短");
        }
        if(userPassword.length() < 8 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR , "用户密码过短");
        }

        // 2 对用户传递的密码是否已经加密
        String encryptedPassword = getEncryptPassword(userPassword);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, encryptedPassword);
        User user = this.baseMapper.selectOne(wrapper);
        // 3 查询数据库中是否已经存在  ---  不存在，抛异常
        if(user == null){
            log.info("user login failed , userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR , "用户不存在或密码错误");
        }
        // 4 保存用户的登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        return this.getLoginUserVO(user);
    }

    /**
     * 获取加密后的密码
     * @param userPassword 原始密码
     * @return 加密后的密码
     */
    @Override
    public String getEncryptPassword(String userPassword) {
        // 加盐 ， 混淆密码
        final String SALT = "zjrwjh";
        return DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
    }

    /**
     * 获取脱敏的用户信息
     * @param user 用户信息
     * @return 脱敏的用户信息
     */
    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if(user == null){
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public Boolean userLogout(HttpServletRequest request) {
        if(request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE) == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR , "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    /**
     * 获取脱敏的用户信息
     * @param user 用户信息
     * @return 脱敏的用户信息
     */
    @Override
    public UserVO getUserVO(User user) {
        if(user == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 获取脱敏的用户信息列表
     * @param userList 用户信息
     * @return 脱敏的用户信息列表
     */
    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if(CollectionUtil.isEmpty(userList)){
            return Collections.emptyList();
        }
        return userList.stream()
                .map(this::getUserVO)
                .collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjUtil.isNotNull(id), "id", id);
        queryWrapper.eq(StrUtil.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.like(StrUtil.isNotBlank(userAccount), "userAccount", userAccount);
        queryWrapper.like(StrUtil.isNotBlank(userName), "userName", userName);
        queryWrapper.like(StrUtil.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.orderBy(StrUtil.isNotEmpty(sortField), sortOrder.equals("ascend"), sortField);
        return queryWrapper;
    }

    /**
     * 是否为管理员
     *
     * @param user  用户
     * @return 是否管理员
     */
    @Override
    public boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

}




