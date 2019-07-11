package com.sxbang.seckill.kafka;

import com.sxbang.seckill.model.Course;
import com.sxbang.seckill.model.Orders;
import com.sxbang.seckill.model.User;
import com.sxbang.seckill.service.CourseService;
import com.sxbang.seckill.service.OrderService;
import com.sxbang.seckill.service.SeckillService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author kaneki
 * @date 2019/6/28 10:13
 */
@Component
public class KafkaConsumer {
    @Autowired
    public CourseService courseService;

    @Autowired
    public OrderService orderService;

    @Autowired
    public SeckillService seckillService;

    @KafkaListener(id="seconds-kill", topics = "test", groupId = "seconds-kill")
    public void listener(ConsumerRecord<?, ?> record) {
        String[] messages = record.value().toString().split(",");
        String courseNo  = messages[0];
        String username  = messages[1];

        Course course = courseService.findCourseByCourseNo(courseNo);
        int stock = course.getStockQuantity();
        if(stock <= 0){
            return ;
        }
        //判断是否已经购买
        Orders order = orderService.getOrderByUsernameAndCourseNo(username, courseNo);
        if(order != null){
            return ;
        }
        //减库存 下订单
        User user = new User();
        user.setUsername(username);
        seckillService.seckill(user, course);
    }
}
