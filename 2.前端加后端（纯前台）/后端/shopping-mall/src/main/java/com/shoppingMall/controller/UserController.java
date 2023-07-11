package com.shoppingMall.controller;

import com.shoppingMall.entity.bo.TokenBo;
import com.shoppingMall.entity.vo.MixUserAndEmailVo;
import com.shoppingMall.entity.vo.ModifyPasswordVo;
import com.shoppingMall.entity.vo.ModifyUserInfoVo;
import com.shoppingMall.entity.vo.UserVo;
import com.shoppingMall.service.UserService;
import com.shoppingMall.utils.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注册和登录
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/4
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户注册
     *
     * @param mixUserAndEmailVo
     */
    @PostMapping("/register")
    public TokenBo register(@RequestBody MixUserAndEmailVo mixUserAndEmailVo) {
        String userName = mixUserAndEmailVo.getUserName();
        String userEmail = mixUserAndEmailVo.getUserEmail();
        int userSex = mixUserAndEmailVo.getUserSex();
        String password = mixUserAndEmailVo.getPassword();
        String emailKey = mixUserAndEmailVo.getEmailKey();
        String emailCode = mixUserAndEmailVo.getEmailCode();
        Assert.isNotBlank(userName, "用户名字");
        Assert.isNotBlank(userEmail, "用户邮箱账号");
        Assert.isNotNull(userSex, "用户性别");
        Assert.isNotBlank(password, "用户密码");
        Assert.isNotBlank(emailKey, "邮箱id");
        Assert.isNotBlank(emailCode, "邮箱验证码");
        return userService.register(emailKey, emailCode, userName, userEmail, userSex, password);
    }

    /**
     * 账号密码登录
     *
     * @param userVo
     * @return
     */
    @PostMapping("/login_email_password")
    public TokenBo loginOnEmailAndPassword(@RequestBody UserVo userVo) {
        String userEmail = userVo.getUserEmail();
        String password = userVo.getPassword();
        Assert.isNotBlank(userEmail, "邮箱账号");
        Assert.isNotBlank(password, "密码");
        return userService.loginOnEmailAndPassword(userEmail, password);
    }

    /**
     * 账号验证码登录
     *
     * @param mixUserAndEmailVo
     * @return
     */
    @PostMapping("/login_email_code")
    public TokenBo loginOnEmailCode(@RequestBody MixUserAndEmailVo mixUserAndEmailVo) {
        String userEmail = mixUserAndEmailVo.getUserEmail();
        String emailKey = mixUserAndEmailVo.getEmailKey();
        String emailCode = mixUserAndEmailVo.getEmailCode();
        Assert.isNotBlank(userEmail, "邮箱账号");
        Assert.isNotBlank(emailKey, "邮箱id");
        Assert.isNotBlank(emailCode, "邮箱验证码");
        return userService.loginOnEmailCode(userEmail, emailKey, emailCode);
    }

    /**
     * 修改当前用户密码
     *
     * @param modifyPasswordVo
     * @return
     */
    @PostMapping("/modify_password")
    public void modifyPassword(@RequestBody ModifyPasswordVo modifyPasswordVo) {
        String token = modifyPasswordVo.getToken();
        String oldPassword = modifyPasswordVo.getOldPassword();
        String newPassword = modifyPasswordVo.getNewPassword();
        Assert.isNotBlank(token, "用户令牌");
        Assert.isNotBlank(oldPassword, "旧密码");
        Assert.isNotBlank(newPassword, "新密码");
        userService.modifyPassword(token, oldPassword, newPassword);
    }

    /**
     * 修改用户信息
     *
     * @param modifyUserInfoVo
     * @return
     */
    @PostMapping("/modify_user_info")
    public TokenBo modifyUserInfo(@RequestBody ModifyUserInfoVo modifyUserInfoVo) {
        String token = modifyUserInfoVo.getToken();
        String imgData = modifyUserInfoVo.getImgData();
        String userEmail = modifyUserInfoVo.getUserEmail();
        String userName = modifyUserInfoVo.getUserName();
        int userSex = modifyUserInfoVo.getUserSex();
        Assert.isNotBlank(token, "用户性别");
        Assert.isNotBlank(imgData, "头像数据");
        Assert.isNotBlank(userEmail, "用户账号'");
        Assert.isNotBlank(userName, "用户名字");
        Assert.isNotNull(userSex, "用户性别");
        return userService.modifyUserInfo(token, imgData, userEmail, userName, userSex);
    }
}
