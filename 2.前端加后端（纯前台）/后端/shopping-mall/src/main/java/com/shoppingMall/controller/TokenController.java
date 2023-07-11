package com.shoppingMall.controller;

import com.shoppingMall.entity.bo.LoginUserBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.bo.TokenBo;
import com.shoppingMall.entity.vo.TokenVo;
import com.shoppingMall.service.TokenService;
import com.shoppingMall.utils.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * token控制类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/13
 */
@RestController
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 验证当前用户是否登录
     *
     * @param tokenVo
     * @return
     */
    @PostMapping("/check_login")
    public SingleAllBo<Boolean> checkLogin(@RequestBody TokenVo tokenVo){
        String token = tokenVo.getToken();
        Assert.isNotBlank(token,"token令牌");
        return tokenService.checkToken(token);
    }

    /**
     * 刷新令牌
     *
     * @param tokenVo
     * @return
     */
    @PostMapping("/refresh_token")
    public TokenBo refreshToken(@RequestBody TokenVo tokenVo){
        String token = tokenVo.getToken();
        String refreshToken = tokenVo.getRefreshToken();
        Assert.isNotBlank(token,"token令牌");
        Assert.isNotBlank(refreshToken,"refreshToken刷新令牌");
        return tokenService.refreshToken(token,refreshToken);
    }

    /**
     * 根据token获取用户
     *
     * @param tokenVo
     * @return
     */
    @PostMapping("/get_user_on_token")
    public LoginUserBo getUserOnToken(@RequestBody TokenVo tokenVo){
        String token = tokenVo.getToken();
        Assert.isNotBlank(token,"token令牌");
        return tokenService.getUserOnToken(token);
    }
}
