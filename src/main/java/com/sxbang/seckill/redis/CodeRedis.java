package com.sxbang.seckill.redis;

import org.springframework.stereotype.Repository;

/**
 * @author kaneki
 * @date 2019/6/28 17:26
 */
@Repository
public class CodeRedis extends AbstractBaseRedis{

    private static final String REDIS_KEY = "com.sxbang.seckill.redis.CodeRedis";

    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }
}
