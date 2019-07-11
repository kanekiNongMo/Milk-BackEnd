package com.sxbang.seckill.redis;

import com.google.gson.Gson;
import com.sxbang.seckill.model.User;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author kaneki
 * @date 2019/6/23 22:02
 */
@Repository
public class UserRedis extends AbstractBaseRedis<User> {

    private static final String REDIS_KEY = "com.sxbang.seckill.redis.UserRedis";

    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }

    public void add(String key, Long time, User user) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(user), time, TimeUnit.SECONDS);
    }
}
