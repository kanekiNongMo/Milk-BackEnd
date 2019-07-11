package com.sxbang.seckill.controller;

import com.sxbang.seckill.model.User;
import com.sxbang.seckill.service.UserService;
import com.sxbang.seckill.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author kaneki
 * @date 2019/6/21 17:09
 */

@Controller
@Slf4j
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public ModelAndView toRegister() {
        User user = new User();
        return new ModelAndView("register").addObject(user);
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }
        log.info("password:"+user.getPassword());
        String salt = "kaneki";
        String newPassword = Md5Util.inputToDb(user.getPassword(), salt);
        user.setPassword(newPassword);
        user.setDbFlag(salt);
        userService.register(user);
        return new ModelAndView("register");
    }
}
