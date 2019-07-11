package com.sxbang.seckill.controller.api;

import com.sxbang.seckill.base.controller.BaseApiController;
import com.sxbang.seckill.base.result.Result;
import com.sxbang.seckill.model.Orders;
import com.sxbang.seckill.model.User;
import com.sxbang.seckill.service.SeckillService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kaneki
 * @date 2019/6/27 16:04
 */

@RestController
public class SeckillApiController extends BaseApiController implements InitializingBean {

    @Autowired
    private SeckillService seckillService;

    @GetMapping("{path}/seckill/{courseNo}")
    public Result<Orders> seckill(User user, @PathVariable String courseNo, @PathVariable String path, HttpServletRequest request) {
        if (user == null) {
            return Result.failure();
        }
        return seckillService.seckillFlow(user, courseNo, path, request);
    }

    @GetMapping("/seckillResult/{courseNo}")
    public Result<Orders> seckillResult(User user, @PathVariable String courseNo) {
        if (user == null) {
            return Result.failure();
        }
        return seckillService.seckillResult(user, courseNo);
    }

    @GetMapping("/getPath/{courseNo}")
    public String getPath(User user, @PathVariable String courseNo) {
        return user != null ? seckillService.getPath(user, courseNo) : null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        seckillService.cacheAllCourse();
    }
}
