package com.sxbang.seckill.repository;

import com.sxbang.seckill.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author kaneki
 * @date 2019/6/26 16:06
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    /**
     * 根据课程编号减库存
     * @param courseNo 课程编号
     * @return 影响行数
     */

    @Modifying
    @Query("update Course c set stockQuantity = stockQuantity - 1 where courseNo = :courseNo and stockQuantity > 0")
    int reduceStockByCourseNo(@Param(value = "courseNo") String courseNo);
}
