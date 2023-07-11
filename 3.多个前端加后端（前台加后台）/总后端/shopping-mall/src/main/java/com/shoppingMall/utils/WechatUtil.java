package com.shoppingMall.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.entity.bo.WechatBo;
import com.shoppingMall.exception.BizException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * 微信登录工具类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/5
 */
public class WechatUtil {

    /**
     * 用于远程获取内容
     */
    private static HttpClient httpClient = HttpClient.newBuilder().build();

    /**
     * 获取登录二维码
     *
     * @return
     */
    public static byte[] getWechatLoginImg(String appId, String redirectUrl, String loginImgId) {
        redirectUrl = URLEncoder.encode(redirectUrl + GlobalConstant.REDIRECT_URL, StandardCharsets.UTF_8);
        String resultUrl = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=%s#wechat_redirect", appId, redirectUrl, loginImgId);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            QrCodeUtil.generate(resultUrl, 300, 300, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new BizException("获取二维码失败！");
        }
    }

    /**
     * 获取用户信息
     *
     * @param appId
     * @param appSecret
     * @param code
     * @return
     */
    public static WechatBo getUserInfo(String appId, String appSecret, String code) throws Exception {
        String resultUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", appId, appSecret, code);
        String content = getContent(resultUrl);
        JSONObject entries = JSONUtil.parseObj(content);
        String accessToken = entries.getStr("access_token");
        String openid = entries.getStr("openid");
        if (ObjectUtil.isNull(accessToken) || ObjectUtil.isNull(openid)) {
            throw new BizException("登录授权失败！");
        }
        String infoUrl = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", accessToken, openid);
        String userinfo = getContent(infoUrl);
        JSONObject userInfos = JSONUtil.parseObj(userinfo);
        return new WechatBo(
                userInfos.getStr("headimgurl"),
                userInfos.getStr("openid"),
                userInfos.getStr("nickname"),
                userInfos.getInt("sex") == GlobalConstant.MAN ? GlobalConstant.GIRL : GlobalConstant.MAN
        );
    }

    /**
     * 远程获取资源
     *
     * @param resultUrl
     * @return
     * @throws Exception
     */
    private static String getContent(String resultUrl) throws Exception {
        URI url = new URI(resultUrl);
        HttpRequest request = HttpRequest.newBuilder(url)
                .timeout(Duration.ofSeconds(10))
                .version(HttpClient.Version.HTTP_2).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
