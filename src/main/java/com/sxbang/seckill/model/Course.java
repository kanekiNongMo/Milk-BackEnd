package com.sxbang.seckill.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author kaneki
 * @date 2019/6/26 15:59
 */
@Entity
@Table(name="course")
@Data
public class Course implements Serializable {
    private static final long serialVersionUID = 5675049956733490770L;

    @Id
    @Column(name="course_no")
    private String courseNo;

    @Column(name="course_name", nullable=false)
    private String courseName;

    @Column(name="teacher_name", nullable=false)
    private String teacherName;

    @Column(name="course_description")
    private String courseDescription;

    @Column(name="course_period")
    private String coursePeriod;

    @Column(name="start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name="end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name="course_price")
    private BigDecimal coursePrice;

    @Column(name="stock_quantity")
    private Integer stockQuantity;

    @Column(name="course_type")
    private Integer courseType;

    @Column(name="course_pic")
    private String coursePic;
}
