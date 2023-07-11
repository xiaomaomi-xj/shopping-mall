package com.shoppingMall.controller;

import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.vo.EmailVo;
import com.shoppingMall.entity.vo.MixEmailAndCaptchaVo;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.CaptchaService;
import com.shoppingMall.service.EmailService;
import com.shoppingMall.utils.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户工具接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/2
 */
@RestController
@RequestMapping("/user_util")
public class UserUtilsController {
    private final CaptchaService captchaService;
    private final EmailService emailService;

    public UserUtilsController(CaptchaService captchaService, EmailService emailService) {
        this.captchaService = captchaService;
        this.emailService = emailService;
    }

    /**
     * 获取验证码存储的key
     *
     * @return
     */
    @PostMapping("/get_check_id")
    public SingleAllBo<String> getCheckId() {
        return captchaService.getCaptchaKey();
    }

    /**
     * 获取图片
     *
     * @param httpServletResponse
     * @param codeId
     */
    @GetMapping("/get_check_img")
    public void getCheckImg(HttpServletResponse httpServletResponse, @RequestParam("code_id") String codeId) {
        Assert.isNotBlank(codeId, "图片验证码id");
        try (ServletOutputStream outputStream = httpServletResponse.getOutputStream()) {
            outputStream.write(captchaService.getCaptchaContent(codeId));
        } catch (IOException e) {
            throw new BizException("获取验证码失败！");
        }
    }

    /**
     * 获取验证码存储的key
     *
     * @return
     */
    @PostMapping("/get_email_key")
    public SingleAllBo<String> getEmailKey(@RequestBody EmailVo emailVo) {
        String emailAccount = emailVo.getEmailAccount();
        Assert.isNotBlank(emailAccount, "邮箱账号");
        return emailService.getEmailKey(emailAccount);
    }

    /**
     * 发送邮箱
     *
     * @param mixEmailAndCaptchaVo
     */
    @PostMapping("/send_email")
    public void sendEmail(@RequestBody MixEmailAndCaptchaVo mixEmailAndCaptchaVo) {
        //发送验证码之前，先验证图片验证码是否正确
        String captchaId = mixEmailAndCaptchaVo.getCaptchaId();
        String captchaCode = mixEmailAndCaptchaVo.getCaptchaCode();
        String emailKey = mixEmailAndCaptchaVo.getEmailKey();
        String emailAccount = mixEmailAndCaptchaVo.getEmailAccount();
        Assert.isNotBlank(captchaId, "验证码id");
        Assert.isNotBlank(captchaCode, "输入的验证码内容");
        Assert.isNotBlank(emailKey, "邮箱id");
        Assert.isNotBlank(emailAccount, "接收者账号");
        emailService.sendEmail(captchaId, captchaCode, emailKey, emailAccount);
    }
}
