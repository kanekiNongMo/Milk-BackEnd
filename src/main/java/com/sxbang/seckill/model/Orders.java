package com.sxbang.seckill.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author kaneki
 * @date 2019/6/27 15:56
 */
@Entity
@Table(name="orders")
@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = -5876134123713752172L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long orderId;

    @Column(name="course_no", nullable=false)
    private String courseNo;

    @Column(name="username", nullable=false)
    private String username;

    @Column(name="course_name", nullable=false)
    private String courseName;

    @Column(name="course_price", nullable=false)
    private BigDecimal coursePrice;

    @Column(name="pay_price", nullable=false)
    private BigDecimal payPrice;

    @Column(name="payment")
    private String payment;

    @Column(name="pay_status", nullable=false)
    private String payStatus;

    @Column(name="pay_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDate;

    @Column(name="create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name="creat_by")
    private String creatBy;

    @Column(name="update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name="update_by")
    private String updateBy;

    @Column(name="course_pic")
    private String coursePic;

}
