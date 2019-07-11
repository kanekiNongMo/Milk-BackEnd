package com.sxbang.seckill.service.impl;

import com.sxbang.seckill.model.Orders;
import com.sxbang.seckill.model.User;
import com.sxbang.seckill.repository.OrderRepository;
import com.sxbang.seckill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kaneki
 * @date 2019/6/27 16:21
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Orders getOrderByUsernameAndCourseNo(String username, String courseNo) {
        return orderRepository.findByUsernameAndCourseNo(username, courseNo);
    }

    @Override
    public Orders saveAndFlush(Orders orders) {
        return orderRepository.saveAndFlush(orders);
    }

    @Override
    public List<Orders> findAllByUsername(User user, Sort createDate) {
        return orderRepository.findByUsername(user.getUsername(), createDate);
    }
}
