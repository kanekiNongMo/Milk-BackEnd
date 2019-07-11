package com.sxbang.seckill.controller;

import com.sxbang.seckill.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @author kaneki
 * @date 2019/6/23 22:41
 */

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/home")
    public String login(Model model, User user) {
        log.info("username:"+user.getUsername());
        model.addAttribute("user", user);
        return "home";
    }
}
