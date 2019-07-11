package com.sxbang.seckill.config;

import com.sxbang.seckill.model.User;
import com.sxbang.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author kaneki
 * @date 2019/6/23 22:29
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    public UserService userService;

    public String getParameterCookies(HttpServletRequest request, String tokenName) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(tokenName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 判断传入参数是否是User
     * @param parameter 传入参数
     * @return true or false
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?>  pClass = parameter.getParameterType();
        return pClass == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request =  webRequest.getNativeRequest(HttpServletRequest.class);
        // 获取cookie
        String requestParameterToken = request.getParameter("token");
        // 获取cookie
        String cookiesToken = getParameterCookies(request, "token");
        if (requestParameterToken == null && cookiesToken == null) {
            return null;
        }
        return userService.getUserFromRedisByToken((requestParameterToken != null ? requestParameterToken : cookiesToken));
    }
}
