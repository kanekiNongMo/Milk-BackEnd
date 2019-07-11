package com.sxbang.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author kaneki
 * @date 2019/6/23 21:32
 */
public abstract class AbstractBaseRedis<T> {

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Resource
    protected HashOperations<String, String, T> hashOperations;

    @Resource
    protected ValueOperations<String, Object> valueOperations;

    /**
     * 存入redis中的key
     *
     * @return key
     */
    protected abstract String getRedisKey();

    /**
     * 添加String
     *
     * @param key    key
     * @param value  对象
     * @param expire 过期时间(单位:秒),传入-1 时表示不设置过期时间
     */
    public void putString(String key, Object value, long expire, boolean isToString) {
        if (isToString) {
            String str = beanToString(value);
            valueOperations.set(key, str);
        }else {
            valueOperations.set(key, value);
        }
        if (expire != -1) {
            redisTemplate.expire(getRedisKey(), expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 查询String
     *
     * @param key 查询的key
     * @return 查询到的对象
     */
    public Object getString(String key) {
        return valueOperations.get(key);
    }

    public Object getString(String key, Class<T> clazz) {
        String str = (String) valueOperations.get(key);
        return stringToBean(str, clazz);
    }

    /**
     * 递减操作
     * @param key
     * @param by
     * @return
     */
    public double decr(String key, double by){
        return redisTemplate.opsForValue().increment(key, -by);
    }

    /**
     * 递增操作
     * @param key key
     * @param by by
     * @return
     */
    public double incr(String key, double by){
        return redisTemplate.opsForValue().increment(key, by);
    }

    /**
     * 递增操作
     * @param key key
     * @param by by
     * @return
     */
    public double incr(String key, double by , long expire){
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        return redisTemplate.opsForValue().increment(key, by);
    }

    /**
     * 添加
     *
     * @param hashKey key
     * @param value   对象
     * @param expire  过期时间(单位:秒),传入-1 时表示不设置过期时间
     */
    public void put(String hasKey, T value, long expire) {
        hashOperations.put(getRedisKey(), hasKey, value);
        if (expire != -1) {
            redisTemplate.expire(getRedisKey(), expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 删除
     *
     * @param hashKey 传入key的名称
     */
    public void remove(String hashKey) {
        hashOperations.delete(getRedisKey(), hashKey);
    }

    /**
     * 查询
     *
     * @param hashKey 查询的key
     * @return 查询到的对象
     */
    public T get(String hashKey) {
        return hashOperations.get(getRedisKey(), hashKey);
    }

    /**
     * 获取当前redis库下所有对象
     *
     * @return redis库下所有对象
     */
    public List<T> getAll() {
        return hashOperations.values(getRedisKey());
    }

    /**
     * 查询当前redis库下的所有key
     *
     * @return key集合
     */
    public Set<String> getKeys() {
        return hashOperations.keys(getRedisKey());
    }

    /**
     * 判断key是否存在redis中
     *
     * @param hashKey 传入key的名称
     * @return true or false
     */
    public boolean isKeyExists(String hashKey) {
        return hashOperations.hasKey(getRedisKey(), hashKey);
    }

    /**
     * 查询当前key下缓存数量
     *
     * @return 数量
     */
    public long count() {
        return hashOperations.size(getRedisKey());
    }

    /**
     * 清空redis
     */
    public void empty() {
        Set<String> set = hashOperations.keys(getRedisKey());
        set.forEach(hashKey -> hashOperations.delete(getRedisKey(), hashKey));
    }

    private static <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

}
