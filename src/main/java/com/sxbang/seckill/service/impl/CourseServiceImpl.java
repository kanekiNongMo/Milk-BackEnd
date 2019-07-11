package com.sxbang.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.sxbang.seckill.model.Course;
import com.sxbang.seckill.redis.CourseRedis;
import com.sxbang.seckill.repository.CourseRepository;
import com.sxbang.seckill.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author kaneki
 * @date 2019/6/26 16:09
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseRedis courseRedis;

    public static final String ALL_COURSE_REDIS = "allCourseRedis";

    @Override
    public List<Course> findAllCourses() {
        List<Course> courseList;
        // 去redis中读取数据
        String courseListString = (String) courseRedis.getString(ALL_COURSE_REDIS);
        courseList = JSON.parseArray(courseListString, Course.class);
        if (StringUtils.isEmpty(courseListString)) {
            courseList = courseRepository.findAll();
            String coursesString = JSON.toJSONString(courseList);
            courseRedis.putString(ALL_COURSE_REDIS, coursesString, -1, false);
        }
        return courseList;
    }

    @Override
    public Course findCourseByCourseNo(String courseNo) {
       Optional<Course> course = courseRepository.findById(courseNo);
        return course.orElse(null);
    }

    @Override
    public int reduceStockByCourseNo(String courseNo) {
        return courseRepository.reduceStockByCourseNo(courseNo);
    }
}
