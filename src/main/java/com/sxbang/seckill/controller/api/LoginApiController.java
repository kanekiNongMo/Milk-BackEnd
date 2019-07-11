package com.sxbang.seckill.controller.api;

import com.sxbang.seckill.base.controller.BaseApiController;
import com.sxbang.seckill.base.result.Result;
import com.sxbang.seckill.base.result.ResultCode;
import com.sxbang.seckill.model.User;
import com.sxbang.seckill.service.UserService;
import com.sxbang.seckill.util.Md5Util;
import com.sxbang.seckill.util.UUIDUtil;
import com.sxbang.seckill.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author kaneki
 * @date 2019/6/24 16:19
 */
@RestController
@Slf4j
public class LoginApiController extends BaseApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<Object> login(@ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return Result.failure();
        }
        UserVo userVo = userService.getUser(user.getUsername());
        if (userVo != null) {
            if (userVo.getPassword().equals(Md5Util.inputToDb(user.getPassword(), userVo.getDbFlag()))) {
                String token = UUIDUtil.getUuId();
                userService.saveUserToRedisByToken(userVo, token);
                log.info("token:"+token);
                Cookie cookie = new Cookie("token", token);
                cookie.setMaxAge(3600);
                cookie.setPath("/");
                response.addCookie(cookie);
                return Result.success();
            } else {
                return Result.failure(ResultCode.USER_LOGIN_PASSWORD_ERROR);
            }
        } else {
            return Result.failure(ResultCode.USER_LOGIN_USERNAME_ERROR);
        }
    }
}
