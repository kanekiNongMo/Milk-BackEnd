package com.sxbang.seckill.vo;

import com.sxbang.seckill.model.Course;
import lombok.Data;

import java.io.Serializable;

/**
 * @author kaneki
 * @date 2019/6/27 12:41
 */
@Data
public class CourseVo implements Serializable {
    private static final long serialVersionUID = 4769212643022116100L;

    private Course course;
    private int courseStatus = 0;
    private int remainTime = 0;



}
