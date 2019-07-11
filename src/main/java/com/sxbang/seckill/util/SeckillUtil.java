package com.sxbang.seckill.util;

import com.sxbang.seckill.base.result.Result;
import com.sxbang.seckill.base.result.ResultCode;
import com.sxbang.seckill.model.Orders;
import com.sxbang.seckill.model.User;
import com.sxbang.seckill.redis.CourseRedis;
import com.sxbang.seckill.service.OrderService;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

/**
 * @author kaneki
 * @date 2019/6/28 12:00
 */
public class SeckillUtil {

    public static Result<Orders> seckillUtil(User user, String courseNo, Map<String, Boolean> isSeckill, KafkaTemplate<String, String> kafkaTemplate, OrderService orderService, CourseRedis courseRedis){
        boolean isPass = isSeckill.get(courseNo);
        if (isPass) {
            return Result.failure(ResultCode.SECKILL_NO_QUOTE);
        }
        double stockQuantity = courseRedis.decr(courseNo, 1);
        System.out.println(stockQuantity);
        if (stockQuantity < 0) {
            isSeckill.put(courseNo, true);
            return Result.failure(ResultCode.SECKILL_NO_QUOTE);
        }
        Orders orders = orderService.getOrderByUsernameAndCourseNo(user.getUsername(), courseNo);
        if (orders != null) {
            return Result.failure(ResultCode.SECKILL_BOUGHT);
        }
        kafkaTemplate.send("test", courseNo + "," + user.getUsername());
        return Result.failure(ResultCode.SECKILL_LINE_UP);
    }
}
