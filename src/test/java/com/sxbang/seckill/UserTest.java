package com.sxbang.seckill;

import com.sxbang.seckill.model.User;
import com.sxbang.seckill.redis.UserRedis;
import com.sxbang.seckill.service.UserService;
import com.sxbang.seckill.util.Md5Util;
import com.sxbang.seckill.vo.UserVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author kaneki
 * @date 2019/6/21 13:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    public UserService userService;

    @Autowired
    public UserRedis userRedis;

    @Test
    public void userTest() {
        User user = new User("kaneki", "123456");
        user.setId(1231);
        userService.findByUsernameAndPassword(user);
        Assert.assertNotNull(userService.register(user));
    }

//    @Test
//    public void testPassword() {
//        UserVo userVo = userService.getUser("kaneki");
//        String password = Md5Util.inputToDb("123456", userVo.getDbFlag());
//        Assert.assertEquals(password, userVo.getPassword());
//    }
//
//    @Test
//    public void testPutRedis() {
//        User user = new User("kaneki", "123456");
//        userRedis.put(user.getUsername(), user, -1);
//    }
}
