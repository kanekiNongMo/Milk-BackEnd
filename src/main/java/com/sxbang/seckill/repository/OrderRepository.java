package com.sxbang.seckill.repository;

import com.sxbang.seckill.model.Orders;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kaneki
 * @date 2019/6/27 16:25
 */

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {
    /**
     * 根据用户名和课程编号查询订单
     *
     * @param username 用户名
     * @param courseNo 课程编号
     * @return 订单
     */
    Orders findByUsernameAndCourseNo(String username, String courseNo);

    /**
     * 根据用户名查询订单
     * @param username 用户名
     * @param createDate 根据创建时间排序
     * @return 用户订单
     */
    List<Orders> findByUsername(String username, Sort createDate);
}
