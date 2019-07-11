package com.sxbang.seckill.service;

import com.sxbang.seckill.base.result.Result;
import com.sxbang.seckill.model.Course;
import com.sxbang.seckill.model.Orders;
import com.sxbang.seckill.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kaneki
 * @date 2019/6/27 16:07
 */
public interface SeckillService {
    /**
     * 下订单
     *
     * @param user   用户
     * @param course 课程
     * @return 订单
     */
    Orders seckill(User user, Course course);

    /**
     * 判断库存和是否购买
     *
     * @param user     用户
     * @param courseNo 课程编号
     * @param path     路径
     * @return 状态
     */
    Result<Orders> seckillFlow(User user, String courseNo, String path);

    /**
     * 判断库存和是否购买
     *
     * @param user     用户
     * @param courseNo 课程编号
     * @return 状态
     */
    Result<Orders> seckillFlow(User user, String courseNo);

    /**
     * 缓存课程
     */
    void cacheAllCourse();

    /**
     * 判断订单是否创建成功
     *
     * @param user     用户
     * @param courseNo 课程编号
     * @return 状态
     */
    Result<Orders> seckillResult(User user, String courseNo);

    /**
     * 动态地址
     *
     * @param user     用户
     * @param courseNo 课程编号
     * @return path
     */
    String getPath(User user, String courseNo);

    /**
     * 判断ip
     *
     * @param user     用户
     * @param courseNo 课程编号
     * @param path     路径
     * @param request  请求
     * @return 状态
     */
    Result<Orders> seckillFlow(User user, String courseNo, String path, HttpServletRequest request);
}
