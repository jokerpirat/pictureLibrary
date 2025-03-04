package com.yupi.yupicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.yupicturebackend.model.dto.user.UserQueryRequest;
import com.yupi.yupicturebackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yupicturebackend.model.vo.LoginUserVO;
import com.yupi.yupicturebackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 蝙蝠侠告诉我回哥谭吧不抓我了
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-01-22 14:15:17
*/
public interface UserService extends IService<User> {
        /**
         * 用户注册
         *
         * @param userAccount   用户名
         * @param userPassword  密码
         * @param checkPassword 校验密码
         * @return user.getId()
         */
        long userRegister(String userAccount, String userPassword, String checkPassword);

        /**
         * 获取加密后的密码
         *
         * @param userPassword 用户密码
         * @return 加密后的密码
         */
        String getEncryptPassword(String userPassword);

        /**
         * 用户登录
         *
         * @param userAccount  用户账户
         * @param userPassword 用户密码
         * @param request      请求
         * @return 脱敏后的用户信息
         */
        LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

        /**
         * 获取脱敏类用户信息
         *
         * @param user 用户
         * @return 脱敏后的用户信息
         */
        LoginUserVO getLoginUserVO(User user);

        /**
         * 获取当前用户（因为不用返回给前端，不是封装类，所以返回User）
         *
         * @param request
         * @return
         */
        User getLoginUser(HttpServletRequest request);

        /**
         * 用户注销
         *
         * @param request
         * @return
         */
        boolean userLogout(HttpServletRequest request);

        /**
         * 返回脱敏后的用户信息
         *
         * @param user
         * @return
         */
        UserVO getUserVO(User user);

        /**
         * 返回脱敏后的用户列表
         *
         * @param userList
         * @return
         */
        List<UserVO> getUserVOList(List<User> userList);

        /**
         * 获取查询条件
         * @param userQueryRequest
         * @return
         */
        QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

        /**
         * 是否为管理员
         *
         * @param user
         * @return
         */
        boolean isAdmin(User user);

}
