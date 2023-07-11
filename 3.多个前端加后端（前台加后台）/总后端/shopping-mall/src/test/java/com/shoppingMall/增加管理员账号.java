package com.shoppingMall;

import cn.hutool.core.util.IdUtil;
import com.shoppingMall.admin.dao.AdminDao;
import com.shoppingMall.admin.entity.po.Admin;
import com.shoppingMall.properties.SelfConfigPropertiesBean;
import com.shoppingMall.utils.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/31
 */
@SpringBootTest
public class 增加管理员账号 {
    @Autowired
    AdminDao adminDao;
    @Autowired
    SelfConfigPropertiesBean selfConfigPropertiesBean;

    @Test
    public void addAdmin() {
        // 账号
        String adminUser = "root";
        // 密码
        String adminPassword = "123456";
        adminDao.save(new Admin(
                IdUtil.getSnowflakeNextId(),
                adminUser,
                PasswordUtil.getPassword(adminPassword, selfConfigPropertiesBean.getConfig().getPassword().getSalt())
        ));
    }
}
