package com.sxbang.seckill.redis;

import com.sxbang.seckill.model.Course;
import org.springframework.stereotype.Repository;

/**
 * @author kaneki
 * @date 2019/6/26 16:40
 */
@Repository
public class CourseRedis extends AbstractBaseRedis<Course>{

    private static final String REDIS_KEY = "com.sxbang.seckill.redis.CourseRedis";

    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }
}
