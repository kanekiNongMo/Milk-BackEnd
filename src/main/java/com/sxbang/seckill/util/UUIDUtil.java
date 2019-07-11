package com.sxbang.seckill.util;

import java.util.UUID;

/**
 * @author kaneki
 * @date 2019/6/23 22:13
 */
public class UUIDUtil {
    public static String getUuId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
