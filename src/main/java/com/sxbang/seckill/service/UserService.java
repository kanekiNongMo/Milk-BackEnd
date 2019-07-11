package com.sxbang.seckill.service;

import com.sxbang.seckill.model.User;
import com.sxbang.seckill.vo.UserVo;

/**
 * @author kaneki
 * @date 2019-06-21 13:27
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param user 用户实体对象
     * @return 用户对象
     */
    User register(User user);

    /**
     * 查询用户
     *
     * @param user 用户对象
     * @return 用户对象
     */
    User findByUsernameAndPassword(User user);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    UserVo getUser(String username);

    /**
     * 保存用户对象到redis
     *
     * @param userVo 用户对象
     * @param token  标记
     */
    void saveUserToRedisByToken(UserVo userVo, String token);

    /**
     * 通过token从redis获取用户对象
     * @param token 标记
     * @return 对象
     */
    Object getUserFromRedisByToken(String token);
}
