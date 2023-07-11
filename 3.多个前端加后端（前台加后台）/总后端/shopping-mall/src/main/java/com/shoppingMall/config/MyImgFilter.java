package com.shoppingMall.config;

import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.constant.LocalResourceConstant;
import com.shoppingMall.constant.PayConstant;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * 过滤（对链接加安全性，对图片加防盗链）
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/2
 */
@Component
@Order(1)
public class MyImgFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getRequestURI();
        if (GlobalConstant.REDIRECT_URL.equals(requestUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (PayConstant.ALIPAY_CALLBACK_URL.equals(requestUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (PayConstant.VIP_CALLBACK_URL.equals(requestUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (PayConstant.VIP_PAY_URL.equals(requestUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (PayConstant.VIP_TO_PAY_URL.equals(requestUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String referer = request.getHeader(GlobalConstant.CHECK_FIELD);
        if (!ObjectUtils.isEmpty(referer)) {
            if (referer.startsWith(GlobalConstant.FORE_END_URL) || referer.startsWith(GlobalConstant.FORE_END_LOCAL_URL) || referer.startsWith(GlobalConstant.AFTER_END_URL) || referer.startsWith(GlobalConstant.AFTER_END_LOCAL_URL)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        String servletPath = request.getServletPath();
        if (Pattern.matches(GlobalConstant.CHECK_IMG_PATTERN, servletPath)) {
            File file = new File(LocalResourceConstant.LOCAL_IMG_URL.concat(servletPath));
            if (file.exists() && file.isFile()) {
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    outputStream.write(Files.readAllBytes(Path.of(LocalResourceConstant.LOCAL_IMG_URL, "/", LocalResourceConstant.LOCAL_ANTI_THEFT_CHAIN_IMG)));
                }
                return;
            }
        }
        response.sendError(404);
    }
}
