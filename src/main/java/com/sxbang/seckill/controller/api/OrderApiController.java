package com.sxbang.seckill.controller.api;

import com.sxbang.seckill.base.controller.BaseApiController;
import com.sxbang.seckill.base.result.Result;
import com.sxbang.seckill.model.Orders;
import com.sxbang.seckill.model.User;
import com.sxbang.seckill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kaneki
 * @date 2019/6/28 16:13
 */
@RestController
public class OrderApiController extends BaseApiController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orderList")
    public Result<List<Orders>> courseList(User user) {
        return Result.success(orderService.findAllByUsername(user, new Sort(Sort.Direction.DESC, "createDate")));
    }

    @GetMapping("/order/{courseNo}")
    public Result<Orders> courseList(User user, @PathVariable String courseNo) {
        return Result.success(orderService.getOrderByUsernameAndCourseNo(user.getUsername(), courseNo));
    }
}
