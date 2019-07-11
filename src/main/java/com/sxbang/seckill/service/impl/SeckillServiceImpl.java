package com.sxbang.seckill.service.impl;

import com.sxbang.seckill.base.result.Result;
import com.sxbang.seckill.base.result.ResultCode;
import com.sxbang.seckill.model.Course;
import com.sxbang.seckill.model.Orders;
import com.sxbang.seckill.model.User;
import com.sxbang.seckill.redis.CourseRedis;
import com.sxbang.seckill.redis.SeckillRedis;
import com.sxbang.seckill.service.CourseService;
import com.sxbang.seckill.service.OrderService;
import com.sxbang.seckill.service.SeckillService;
import com.sxbang.seckill.util.IpUtil;
import com.sxbang.seckill.util.SeckillUtil;
import com.sxbang.seckill.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kaneki
 * @date 2019/6/27 16:08
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRedis courseRedis;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillRedis seckillRedis;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static Map<String, Boolean> isSeckill = new HashMap<>();
    /**
     * 最大请求次数
     */
    private static final int MAXREQUEST= 3;
    /**
     * 超时时间
     */
    private static final int TIMEOUT = 60;

    @Override
    public Orders seckill(User user, Course course) {
        int result = courseService.reduceStockByCourseNo(course.getCourseNo());
        if (result > 0) {
            Orders orders = new Orders();
            BeanUtils.copyProperties(course, orders);
            orders.setUsername(user.getUsername());
            orders.setCreatBy(user.getUsername());
            orders.setCreateDate(new Date());
            orders.setPayPrice(course.getCoursePrice());
            orders.setPayStatus("0");
            return orderService.saveAndFlush(orders);
        }
        return null;
    }

    @Override
    public Result<Orders> seckillFlow(User user, String courseNo, String path) {
        String redisPath = (String) seckillRedis.getString("path" + "_" + courseNo + "_" + user.getUsername());
        if (!path.equals(redisPath)) {
            return Result.failure(ResultCode.SECKILL_PATH_ERROR);
        }
        return SeckillUtil.seckillUtil(user, courseNo, isSeckill, kafkaTemplate, orderService, courseRedis);
    }

    @Override
    public Result<Orders> seckillFlow(User user, String courseNo) {
        return SeckillUtil.seckillUtil(user, courseNo, isSeckill, kafkaTemplate, orderService, courseRedis);
    }

    @Override
    public void cacheAllCourse() {
        List<Course> courseList = courseService.findAllCourses();
        if (courseList != null) {
            for (Course course : courseList) {
                courseRedis.putString(course.getCourseNo(), course.getStockQuantity(), 60, true);
                courseRedis.put(course.getCourseNo(), course, -1);
                isSeckill.put(course.getCourseNo(), false);
            }
        }
    }

    @Override
    public Result<Orders> seckillResult(User user, String courseNo) {
        Orders orders = orderService.getOrderByUsernameAndCourseNo(user.getUsername(), courseNo);
        if (orders == null) {
            return Result.failure(ResultCode.SECKILL_LINE_UP);
        }
        return Result.success(orders);
    }

    @Override
    public String getPath(User user, String courseNo) {
        String path = UUIDUtil.getUuId();
        seckillRedis.putString("path" + "_" + courseNo + "_" + user.getUsername(), path, 60, false);
        return path;
    }

    @Override
    public Result<Orders> seckillFlow(User user, String courseNo, String path, HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        if (seckillRedis.incr(ip, 1, TIMEOUT) >= MAXREQUEST) {
            return Result.failure(ResultCode.SECKILL_IP_TIMEOUT);
        }
        String redisPath = (String) seckillRedis.getString("path" + "_" + courseNo + "_" + user.getUsername());
        if (!path.equals(redisPath)) {
            return Result.failure(ResultCode.SECKILL_PATH_ERROR);
        }
        return SeckillUtil.seckillUtil(user, courseNo, isSeckill, kafkaTemplate, orderService, courseRedis);
    }
}
