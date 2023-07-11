package com.shoppingMall.admin.config;

import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.admin.annotation.CheckAdmin;
import com.shoppingMall.admin.constan.AdminConstant;
import com.shoppingMall.admin.manager.AdminManager;
import com.shoppingMall.entity.bo.ExceptionBo;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 后台的话都需要权限，那么直接从信息头拿
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/4/7
 */
@Component
@Order(0)
public class AdminFilter implements Filter {
    private final static List<String> URL_LIST = new ArrayList<>();
    private final AdminManager adminManager;
    private final ApplicationContext applicationContext;

    public AdminFilter(AdminManager adminManager, ApplicationContext applicationContext) {
        this.adminManager = adminManager;
        this.applicationContext = applicationContext;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        if (URL_LIST.contains(servletPath)) {
            //做验证
            String adminToken = request.getHeader(AdminConstant.ADMIN_TOKEN_KEY);
            if (ObjectUtil.isNull(adminToken) || adminToken.trim().length() == 0 || !adminManager.checkAdminToken(adminToken)) {
                //需要验证的验证不通过，直接抛异常
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    String errContent = new ExceptionBo(
                            "token过期或错误！",
                            HttpStatus.FORBIDDEN.value()
                    ).toJson();
                    //因为这里是异步请求，需要跨域，但是问题是过滤器的执行是在springMvc前面的，所以WebMvcConfigurer的跨域是没有执行的，需要在这里做一下跨域
                    String origin = request.getHeader(AdminConstant.ORIGIN_KEY);
                    if (origin != null) {
                        //多个跨域域名
                        if (AdminConstant.CROSS_DOMAIN_ORIGIN_VALUES.contains(origin)) {
                            response.setHeader(AdminConstant.CROSS_DOMAIN_ORIGIN_KEY, origin);
                        }
                    }
                    response.setHeader(AdminConstant.ERROR_RESPONSE_TYPE_KEY, AdminConstant.ERROR_RESPONSE_TYPE_VALUE);
                    response.setHeader(AdminConstant.CROSS_DOMAIN_METHOD_KEY, HttpMethod.POST.name());
                    response.setHeader(AdminConstant.CROSS_DOMAIN_AGE_KEY, AdminConstant.CROSS_DOMAIN_AGE_VALUE);
                    response.setHeader(AdminConstant.CROSS_DOMAIN_HEADERS_KEY, String.format("%s, %s, %s", AdminConstant.ADMIN_TOKEN_KEY, AdminConstant.ADMIN_REFRESH_TOKEN_KEY, AdminConstant.ALLOW_HEADERS));
                    outputStream.write(errContent.getBytes(StandardCharsets.UTF_8));
                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            //其他的放行
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * 拿到需要验证的链接
     */
    @PostConstruct
    public void getBean() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            HandlerMethod method = m.getValue();
            Class<?> declaringClass = method.getMethod().getDeclaringClass();
            CheckAdmin checkAdmin = declaringClass.getAnnotation(CheckAdmin.class);
            if (ObjectUtil.isNull(checkAdmin)) {
                if (method.hasMethodAnnotation(CheckAdmin.class)) {
                    //类上没有,方法上有
                    CheckAdmin methodCheckAdmin = method.getMethodAnnotation(CheckAdmin.class);
                    boolean cancel = methodCheckAdmin != null && methodCheckAdmin.isCancel();
                    if (!cancel) {
                        //没有取消，存储url
                        this.saveUrlToList(declaringClass, method);
                    }
                }
                //类上没有，方法上没有就不做处理了
            } else {
                if (method.hasMethodAnnotation(CheckAdmin.class)) {
                    //类上有,方法上也有
                    CheckAdmin methodCheckAdmin = method.getMethodAnnotation(CheckAdmin.class);
                    boolean cancel = methodCheckAdmin != null && methodCheckAdmin.isCancel();
                    if (!cancel) {
                        //没有取消，存储url
                        this.saveUrlToList(declaringClass, method);
                    }
                } else {
                    //类上有，方法上没有.存储url
                    this.saveUrlToList(declaringClass, method);
                }
            }
        }
    }

    /**
     * 保存需要验证的完整servletUrl
     *
     * @param declaringClass 类
     * @param method         方法u
     */
    private void saveUrlToList(Class<?> declaringClass, HandlerMethod method) {
        RequestMapping requestMapping = declaringClass.getAnnotation(RequestMapping.class);
        if (ObjectUtil.isNotNull(requestMapping)) {
            PostMapping postMapping = method.getMethodAnnotation(PostMapping.class);
            if (ObjectUtil.isNotNull(postMapping)) {
                String[] classUrls = requestMapping.value();
                String[] methodsUrls = postMapping.value();
                for (String classUrl : classUrls) {
                    for (String methodUrl : methodsUrls) {
                        URL_LIST.add(classUrl + methodUrl);
                    }
                }
            }
        }

    }
}
