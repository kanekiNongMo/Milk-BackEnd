package com.sxbang.seckill.service;

import com.sxbang.seckill.model.Orders;
import com.sxbang.seckill.model.User;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author kaneki
 * @date 2019/6/27 16:20
 */
public interface OrderService {
    /**
     * 根据用户和课程编号获取订单信息
     *
     * @param username 用户名
     * @param courseNo 课程编号
     * @return 订单信息
     */
    Orders getOrderByUsernameAndCourseNo(String username, String courseNo);

    /**
     * 保存订单
     *
     * @param orders 订单信息
     * @return 订单
     */
    Orders saveAndFlush(Orders orders);

    /**
     * 根据用户名查询订单
     *
     * @param user       用户名
     * @param createDate 通过创建时间来排序
     * @return 订单
     */
    List<Orders> findAllByUsername(User user, Sort createDate);
}
