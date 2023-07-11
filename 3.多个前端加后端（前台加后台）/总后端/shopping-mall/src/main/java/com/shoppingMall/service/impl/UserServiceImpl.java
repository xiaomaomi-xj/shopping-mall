package com.shoppingMall.service.impl;

import cn.hutool.core.util.IdUtil;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.constant.LoginStateEnum;
import com.shoppingMall.constant.UserStateEnum;
import com.shoppingMall.dao.UserDao;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.bo.TokenBo;
import com.shoppingMall.entity.bo.UserAndWechatBo;
import com.shoppingMall.entity.po.User;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.manager.ManagerTokenService;
import com.shoppingMall.properties.SelfConfigPropertiesBean;
import com.shoppingMall.service.EmailService;
import com.shoppingMall.service.TokenService;
import com.shoppingMall.service.UserService;
import com.shoppingMall.utils.PasswordUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作用户
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@ZyhService
@ZyhDataSourceRead
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final EmailService emailService;
    private final SelfConfigPropertiesBean selfConfigPropertiesBean;
    private final TokenService tokenService;
    private final ManagerTokenService managerTokenService;

    public UserServiceImpl(UserDao userDao, EmailService emailService, SelfConfigPropertiesBean selfConfigPropertiesBean, TokenService tokenService, ManagerTokenService managerTokenService) {
        this.userDao = userDao;
        this.emailService = emailService;
        this.selfConfigPropertiesBean = selfConfigPropertiesBean;
        this.tokenService = tokenService;
        this.managerTokenService = managerTokenService;
    }

    /**
     * 用户注册
     *
     * @param emailKey
     * @param emailCode
     * @param userName
     * @param userEmail
     * @param sex
     * @param password
     */
    @Override
    public TokenBo register(String emailKey, String emailCode, String userName, String userEmail, int sex, String password) {
        //先验证邮箱验证码
        SingleAllBo<Boolean> booleanSingleAllBo = emailService.checkEmailCode(emailKey, emailCode, userEmail);
        if (!booleanSingleAllBo.getValue()) {
            throw new BizException("邮箱验证码验证不通过！");
        }
        boolean exists = userDao.existsByUserEmail(userEmail);
        if (exists) {
            throw new BizException("当前账户已经存在！");
        }
        //生成用户id
        long userId = IdUtil.getSnowflakeNextId();
        //获取加密盐
        String salt = selfConfigPropertiesBean.getConfig().getPassword().getSalt();
        //使用减少事务粒度的方式,(默认的，读已提交，效率更高)
        MyAloneHandlerReadWrite.write(() ->
                userDao.save(new User(
                        userId,
                        null,
                        userName,
                        userEmail,
                        sex,
                        PasswordUtil.getPassword(password, salt)
                ))
        );
        //返回登录状态
        return tokenService.createToken(LoginStateEnum.EMAIL_CODE.getType(), String.valueOf(userId));
    }

    /**
     * 账号密码登录
     *
     * @param userEmail
     * @param password
     * @return
     */
    @Override
    public TokenBo loginOnEmailAndPassword(String userEmail, String password) {
        boolean existsByUserEmail = userDao.existsByUserEmail(userEmail);
        if (!existsByUserEmail) {
            throw new BizException("用户不存在！");
        }
        String salt = selfConfigPropertiesBean.getConfig().getPassword().getSalt();
        User user = userDao.findByUserEmail(userEmail);
        if (userEmail.equals(user.getUserEmail()) && PasswordUtil.getPassword(password, salt).equals(user.getPassword())) {
            return tokenService.createToken(LoginStateEnum.EMAIL_CODE.getType(), String.valueOf(user.getUserId()));
        }
        return new TokenBo(
                false,
                null,
                null
        );
    }

    /**
     * 账号验证码登录
     *
     * @param userEmail
     * @param emailKey
     * @param emailCode
     * @return
     */
    @Override
    public TokenBo loginOnEmailCode(String userEmail, String emailKey, String emailCode) {
        SingleAllBo<Boolean> booleanSingleAllBo = emailService.checkEmailCode(emailKey, emailCode, userEmail);
        if (!booleanSingleAllBo.getValue()) {
            throw new BizException("邮箱验证码验证不通过！");
        }
        boolean existsByUserEmail = userDao.existsByUserEmail(userEmail);
        if (!existsByUserEmail) {
            throw new BizException("用户不存在！");
        }
        User user = userDao.findByUserEmail(userEmail);
        //返回登录状态
        return tokenService.createToken(LoginStateEnum.EMAIL_CODE.getType(), String.valueOf(user.getUserId()));
    }

    /**
     * 修改当前用户密码
     *
     * @param token
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public void modifyPassword(String token, String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            throw new BizException("旧密码不能与新密码一致！");
        }
        User user = managerTokenService.getUserOnToken(token);
        String salt = selfConfigPropertiesBean.getConfig().getPassword().getSalt();
        String password = PasswordUtil.getPassword(oldPassword, salt);
        if (!password.equals(user.getPassword())) {
            throw new BizException("请输入正确的旧密码！");
        }
        user.setPassword(PasswordUtil.getPassword(newPassword, salt));
        MyAloneHandlerReadWrite.write(() -> userDao.save(user));
    }

    /**
     * 修改当前用户信息
     *
     * @param token
     * @param imgData
     * @param userEmail
     * @param userName
     * @param userSex
     * @return
     */
    @Override
    public TokenBo modifyUserInfo(String token, String imgData, String userEmail, String userName, int userSex) {
        Long imgSize = selfConfigPropertiesBean.getConfig().getHeadImgFile().getImgSize();
        //base64转码大约会大1/3
        if (imgData.length() > Math.ceil(imgSize * GlobalConstant.BASE64_CONVERT_SIZE_TIMES)) {
            throw new BizException("你上传的文件超过允许范围，请更换！");
        }
        User user = managerTokenService.getUserOnToken(token);
        if (!userEmail.equals(user.getUserEmail())) {
            throw new AppException("要修改的用户账号不一致！");
        }
        //对于头像，因为要实时显示，所以直接存base64
        user.setUserHeadUrl(imgData);
        user.setUserName(userName);
        user.setUserSex(userSex);
        MyAloneHandlerReadWrite.write(() -> userDao.save(user));
        return tokenService.createToken(LoginStateEnum.EMAIL_CODE.getType(), String.valueOf(user.getUserId()));
    }

    /**
     * 获取全部用户
     *
     * @return
     */
    @Override
    public List<UserAndWechatBo> getAllUser() {
        List<User> all = userDao.findAll();
        List<UserAndWechatBo> userAndWechatBos = new ArrayList<>();
        for (User user : all) {
            userAndWechatBos.add(new UserAndWechatBo(
                    String.valueOf(user.getUserId()),
                    user.getUserHeadUrl(),
                    user.getUserName(),
                    user.getUserEmail(),
                    user.getUserSex(),
                    "",
                    UserStateEnum.USER.getType()
            ));
        }
        return userAndWechatBos;
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Override
    public SingleAllBo<Boolean> delUser(String id) {
        User user = userDao.findById(Long.parseLong(id)).orElseThrow(() -> {
            throw new BizException("用户不存在！");
        });
        MyAloneHandlerReadWrite.write(() -> {
            userDao.delete(user);
            return null;
        });
        return new SingleAllBo<>(true);
    }
}
