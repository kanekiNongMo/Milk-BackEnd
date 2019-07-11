package com.sxbang.seckill.service;

import com.sxbang.seckill.model.Course;

import java.util.List;

/**
 * @author kaneki
 * @date 2019/6/26 16:07
 */
public interface CourseService {
    /**
     * 获取所有课程并保存到redis
     *
     * @return 课程集合
     */
    List<Course> findAllCourses();

    /**
     * 获取课程详情
     *
     * @param courseNo 课程编号
     * @return 课程对象
     */
    Course findCourseByCourseNo(String courseNo);

    /**
     * 根据课程编号来减库存
     *
     * @param courseNo 课程编号
     * @return 影响行数
     */
    int reduceStockByCourseNo(String courseNo);
}
