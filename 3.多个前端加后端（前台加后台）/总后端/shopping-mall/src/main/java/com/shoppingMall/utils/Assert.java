package com.shoppingMall.utils;

import com.shoppingMall.exception.BizException;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 检验参数不为空
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
public class Assert {
    public Assert() {
    }

    public static void isNotBlank(@Nullable String str, String name) throws BizException {
        if (ObjectUtils.isEmpty(str)) {
            throw new BizException("【" + name + "】信息项不允许为空，请检查！");
        }
        if(str.trim().length()==0){
            throw new BizException("【" + name + "】信息项不允许为空，请检查！");
        }
    }

    public static void isNotNull(@Nullable Object obj, String name) throws BizException {
        if (Objects.isNull(obj)) {
            throw new BizException("【" + name + "】信息项不允许为空，请检查！");
        }
    }

    public static void isNotEmpty(@Nullable Map map, String name) throws BizException {
        if (null == map || map.isEmpty()) {
            throw new BizException("【" + name + "】信息项不允许为空，请检查！");
        }
    }

    public static void isNotEmpty(@Nullable List list, String name) throws BizException {
        if (null == list || list.isEmpty()) {
            throw new BizException("【" + name + "】信息项不允许为空，请检查！");
        }
    }

    public static void isNotEmpty(@Nullable Set set, String name) throws BizException {
        if (null == set || set.isEmpty()) {
            throw new BizException("【" + name + "】信息项不允许为空，请检查！");
        }
    }

    public static void isQueryNotEmpty(@Nullable List list, String listName) throws BizException {
        if (null == list || list.isEmpty()) {
            throw new BizException("未查询到【" + listName + "】！");
        }
    }

    public static void isQueryNotEmpty(@Nullable Map map, String mapName) throws BizException {
        if (null == map || map.isEmpty()) {
            throw new BizException("未查询到【" + mapName + "】！");
        }
    }

    public static void isQueryNotEmpty(@Nullable Optional optional, String optionalName) throws BizException {
        if (null == optional || optional.isEmpty()) {
            throw new BizException("未查询到【" + optionalName + "】！");
        }
    }

    public static void isQueryNotNull(@Nullable Object obj, String objName) throws BizException {
        if (null == obj) {
            throw new BizException("未查询到【" + objName + "】！");
        }
    }
}
