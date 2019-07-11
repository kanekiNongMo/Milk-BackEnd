package com.sxbang.seckill.redis;

import com.sxbang.seckill.model.Orders;
import org.springframework.stereotype.Repository;

/**
 * @author kaneki
 * @date 2019/6/28 11:37
 */

@Repository
public class SeckillRedis extends AbstractBaseRedis<Orders>{

    private static final String REDIS_KEY = "com.sxbang.seckill.redis.SeckillRedis";

    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }
}
