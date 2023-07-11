package com.shoppingMall.controller;

import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.bo.TokenBo;
import com.shoppingMall.entity.vo.IdVo;
import com.shoppingMall.entity.vo.UserVo;
import com.shoppingMall.entity.vo.WechatVo;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.WechatService;
import com.shoppingMall.utils.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 微信控制类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/5
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {

    private final WechatService wechatService;

    public WechatController(WechatService wechatService) {
        this.wechatService = wechatService;
    }

    /**
     * 获取登录二维码id
     */
    @PostMapping("/get_wechat_login_id")
    @ResponseBody
    public SingleAllBo<String> getWechatLoginId() {
        return wechatService.getWechatLoginId();
    }

    /**
     * 获取登录二维码
     *
     * @param response
     */
    @GetMapping("/get_wechat_login_img")
    public void getWechatLoginImg(HttpServletResponse response, @RequestParam("login_img_id") String loginImgId) {
        Assert.isNotBlank(loginImgId, "微信登录二维码id");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(wechatService.getWechatLoginImg(loginImgId));
        } catch (IOException e) {
            throw new BizException("获取二维码失败！");
        }
    }

    /**
     * 获取登录code和state,这里是回调地址
     *
     * @param wechatVo
     */
    @GetMapping("/get_wechat_code")
    public String getWechatCode(WechatVo wechatVo) {
        String code = wechatVo.getCode();
        String state = wechatVo.getState();
        Assert.isNotBlank(code, "code码");
        Assert.isNotBlank(state, "回调图片id");
        return wechatService.getUserInfo(code, state);
    }

    /**
     * 轮询获取登录状态
     *
     * @param idVo
     * @return
     */
    @PostMapping("/check_login")
    @ResponseBody
    public TokenBo checkLogin(@RequestBody IdVo idVo) {
        String id = idVo.getId();
        Assert.isNotBlank(id, "微信登录二维码id");
        return wechatService.checkLogin(id);
    }

    /**
     * 绑定微信用户
     *
     * @param userVo
     * @return
     */
    @PostMapping("/bind_account")
    @ResponseBody
    public TokenBo bindAccount(@RequestBody UserVo userVo) {
        String token = userVo.getToken();
        String userEmail = userVo.getUserEmail();
        String password = userVo.getPassword();
        Assert.isNotBlank(token, "用户令牌");
        Assert.isNotBlank(userEmail, "用户邮箱");
        Assert.isNotBlank(password, "用户密码");
        return wechatService.bindAccount(token,userEmail,password);
    }

}
