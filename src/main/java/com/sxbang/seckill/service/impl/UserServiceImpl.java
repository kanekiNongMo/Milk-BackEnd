package com.sxbang.seckill.service.impl;

import com.sxbang.seckill.model.User;
import com.sxbang.seckill.redis.UserRedis;
import com.sxbang.seckill.repository.UserRepository;
import com.sxbang.seckill.service.UserService;
import com.sxbang.seckill.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kaneki
 * @date 2019-06-21 13:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRedis userRedis;

    @Override
    public User register(User user) {
        user.setId(999);
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findByUsernameAndPassword(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public UserVo getUser(String username) {
        UserVo userVo = new UserVo();
        User user = userRedis.get(username);
        if (user == null) {
            user = userRepository.findByUsername(username);
            if (user != null) {
                userRedis.put(user.getUsername(), user, -1);
            }else {
                return null;
            }
        }
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public void saveUserToRedisByToken(UserVo userVo, String token) {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        userRedis.put(token, user, 3600);
    }

    @Override
    public Object getUserFromRedisByToken(String token) {
        return userRedis.get(token);
    }
}
