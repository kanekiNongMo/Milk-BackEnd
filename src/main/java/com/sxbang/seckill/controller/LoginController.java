package com.sxbang.seckill.controller;

import com.sxbang.seckill.model.User;
import com.sxbang.seckill.service.UserService;
import com.sxbang.seckill.util.Md5Util;
import com.sxbang.seckill.util.UUIDUtil;
import com.sxbang.seckill.util.ValidateCode;
import com.sxbang.seckill.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author kaneki
 * @date 2019/6/22 19:39
 */

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/login")
    public String toLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/validateCode")
    public String validateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession();

        ValidateCode vCode = new ValidateCode(120, 40, 5, 100);
        session.setAttribute("code", vCode.getCode());
        vCode.write(response.getOutputStream());
        return null;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult, Model model,
                        HttpSession session, String code, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        String sessionCode = (String) session.getAttribute("code");
        if (!StringUtils.equalsIgnoreCase(code, sessionCode)) {
            model.addAttribute("message", "验证码不匹配");
            return "login";
        }
        log.info(user.getPassword());
        UserVo userVo = userService.getUser(user.getUsername());
        log.info(userVo.getPassword());
        if (null != userVo) {
            String inputPassword = Md5Util.inputToDb(user.getPassword(), userVo.getDbFlag());
            log.info(inputPassword);
            if (userVo.getPassword().equals(inputPassword)) {
                session.setAttribute("user", userVo);
                log.info("session:" + session.getId());
                userService.saveUserToRedisByToken(userVo, session.getId());
                Cookie cookie2 = new Cookie("JSESSIONID", session.getId());
                cookie2.setMaxAge(3600);
                cookie2.setPath("/");
                response.addCookie(cookie2);
                return "redirect:/home";
            } else {
                model.addAttribute("message", "密码错误");
                return "login";
            }
        }
        model.addAttribute("message", "用户不存在");
        return "login";
    }
}
