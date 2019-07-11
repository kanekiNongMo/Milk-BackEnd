package com.sxbang.seckill.controller.api;

import com.sxbang.seckill.base.controller.BaseApiController;
import com.sxbang.seckill.base.result.Result;
import com.sxbang.seckill.model.Course;
import com.sxbang.seckill.service.CourseService;
import com.sxbang.seckill.util.CourseUtil;
import com.sxbang.seckill.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kaneki
 * @date 2019/6/26 16:12
 */
@RestController
public class CourseApiController extends BaseApiController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courseList")
    public Result<List<Course>> courseList() {
        return Result.success(courseService.findAllCourses());
    }

    @GetMapping("/courseDetail/{courseNo}")
    public Result<CourseVo> courseDetail(@PathVariable String courseNo) {
        return Result.success(CourseUtil.courseToCourseVo(courseService.findCourseByCourseNo(courseNo)));
    }
}
