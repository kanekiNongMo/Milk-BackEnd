package com.sxbang.seckill.util;

import com.sxbang.seckill.model.Course;
import com.sxbang.seckill.vo.CourseVo;

/**
 * @author kaneki
 * @date 2019/6/27 12:49
 */
public class CourseUtil {

    public static final int COURSE_NOT_START = 0;
    public static final int COURSE_PROCESSING = 1;
    public static final int COURSE_COMPLETE = 2;


    public static CourseVo courseToCourseVo(Course course) {
        CourseVo courseVo = new CourseVo();
        courseVo.setCourse(course);
        long startTime = course.getStartTime().getTime();
        long endTime = course.getEndTime().getTime();
        long now = System.currentTimeMillis();

        int courseStatus;
        int remainTime;

        if (now < startTime) {
            courseStatus = COURSE_NOT_START;
            remainTime = (int)((startTime - now)/1000);
        }else if (now > endTime) {
            courseStatus = COURSE_COMPLETE;
            remainTime = -1;
        }else {
            courseStatus = COURSE_PROCESSING;
            remainTime = -1;
        }
        courseVo.setCourseStatus(courseStatus);
        courseVo.setRemainTime(remainTime);
        return courseVo;
    }
}
